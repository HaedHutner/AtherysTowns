package com.atherys.towns;

import com.atherys.core.database.config.DatabaseTypes;
import com.atherys.towns.commands.TownsValues;
import com.atherys.towns.commands.nation.NationMasterCommand;
import com.atherys.towns.commands.plot.PlotMasterCommand;
import com.atherys.towns.commands.resident.ResidentCommand;
import com.atherys.towns.commands.town.TownMasterCommand;
import com.atherys.towns.commands.wilderness.WildernessRegenCommand;
import com.atherys.towns.db.TownsDatabase;
import com.atherys.towns.db.mongo.MongoTownsDatabase;
import com.atherys.towns.db.sql.SQLTownsDatabase;
import com.atherys.towns.listeners.PlayerListeners;
import com.atherys.towns.managers.*;
import com.atherys.towns.permissions.actions.NationAction;
import com.atherys.towns.permissions.actions.NationActionRegistry;
import com.atherys.towns.permissions.actions.TownAction;
import com.atherys.towns.permissions.actions.TownActionRegistry;
import com.atherys.towns.permissions.ranks.NationRank;
import com.atherys.towns.permissions.ranks.NationRankRegistry;
import com.atherys.towns.permissions.ranks.TownRank;
import com.atherys.towns.permissions.ranks.TownRankRegistry;
import com.atherys.towns.plot.flags.Extent;
import com.atherys.towns.plot.flags.ExtentRegistry;
import com.atherys.towns.plot.flags.Flag;
import com.atherys.towns.plot.flags.FlagRegistry;
import com.atherys.towns.resident.Resident;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.permission.PermissionService;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.atherys.towns.AtherysTowns.*;

@Plugin( id = ID,
        name = NAME,
        description = DESCRIPTION,
        version = VERSION,
        dependencies = {
                @Dependency( id = "atheryscore", optional = false )
        } )
public class AtherysTowns {
    public final static String ID = "atherystowns";
    public final static String NAME = "A'therys Towns";
    public final static String DESCRIPTION = "A custom plugin responsible for agile land management. Created for the A'therys Horizons server.";
    public final static String VERSION = "1.0.0a";

    @Inject
    private Game game;

    @Inject
    private Logger logger;

    private static AtherysTowns instance;
    private static boolean init = false;

    private TownsConfig config;

    private String workingDir = "config/" + ID + "/";

    private Task townBorderTask;
    private Task wildernessRegenTask;

    private PermissionService permissionService;

    private TownsDatabase database;

    private void init () {
        instance = this;

        game.getRegistry().registerModule( Extent.class, ExtentRegistry.getInstance() );
        game.getRegistry().registerModule( Flag.class, FlagRegistry.getInstance() );
        game.getRegistry().registerModule( NationAction.class, NationActionRegistry.getInstance() );
        game.getRegistry().registerModule( TownAction.class, TownActionRegistry.getInstance() );

        Optional<PermissionService> permissionService = Sponge.getServiceManager().provide( PermissionService.class );
        if ( !permissionService.isPresent() ) {
            getLogger().warn( "No permission service found. This plugin requires a permissions plugin implementing the Sponge Permissions API to function properly. Aborting start." );
            init = false;
            return;
        }

        this.permissionService = permissionService.get();

        if ( !getEconomyService().isPresent() ) {
            getLogger().warn( "No economy service found. No features relating to economy will function!" );
        }

        try {
            config = new TownsConfig();
            config.init();
        } catch ( IOException e ) {
            e.printStackTrace();
            init = false;
            return;
        }

        if ( config.DEFAULT ) {
            getLogger().warn( "Config set to default. Plugin will not initialize further than this." );
            init = false;
            return;
        }

        game.getRegistry().registerModule( NationRank.class, NationRankRegistry.getInstance() );
        game.getRegistry().registerModule( TownRank.class, TownRankRegistry.getInstance() );

        if ( config.DATABASE.TYPE.equals( DatabaseTypes.MONGODB ) ) {
            database = MongoTownsDatabase.getInstance();
        } else {
            database = SQLTownsDatabase.getInstance();
        }

        init = true;
    }

    private void start () {

        database.init();

        game.getEventManager().registerListeners( this, new PlayerListeners() );

        ResidentCommand.register();
        Sponge.getCommandManager().register( AtherysTowns.getInstance(), PlotMasterCommand.getInstance().getSpec(), "plot", "p" );
        Sponge.getCommandManager().register( AtherysTowns.getInstance(), TownMasterCommand.getInstance().getSpec(), "town", "t" );
        Sponge.getCommandManager().register( AtherysTowns.getInstance(), NationMasterCommand.getInstance().getSpec(), "nation", "n" );
        WildernessRegenCommand.getInstance().register();

        townBorderTask = Task.builder()
                .interval( getConfig().TOWN.BORDER_UPDATE_RATE, TimeUnit.SECONDS )
                .execute( () -> {
                    for ( Player p : Sponge.getServer().getOnlinePlayers() ) {
                        if ( TownsValues.get( p.getUniqueId(), TownsValues.TownsKey.TOWN_BORDERS ).isPresent() ) {
                            Optional<Resident> resOpt = AtherysTowns.getResidentManager().get( p.getUniqueId() );
                            if ( resOpt.isPresent() ) {
                                if ( resOpt.get().getTown().isPresent() ) {
                                    resOpt.get().getTown().get().showBorders( p );
                                }
                            }
                        }
                    }
                } )
                .name( "atherystowns-town-border-task" )
                .submit( this );
    }

    private void stop () {
        database.stop();
    }

    @Listener
    public void onInit ( GameInitializationEvent event ) {
        init();
    }

    @Listener
    public void onStart ( GameStartedServerEvent event ) {
        if ( init ) start();
    }

    @Listener
    public void onStop ( GameStoppingServerEvent event ) {
        if ( init ) stop();
    }

    public static AtherysTowns getInstance () {
        return instance;
    }

    public Game getGame () {
        return game;
    }

    public Logger getLogger () {
        return this.logger;
    }

    public Optional<EconomyService> getEconomyService () {
        return Sponge.getServiceManager().provide( EconomyService.class );
    }

    public static PermissionService getPermissionService () {
        return getInstance().permissionService;
    }

    public String getWorkingDirectory () {
        return workingDir;
    }

    public static TownsConfig getConfig () {
        return AtherysTowns.getInstance().config;
    }

    public static NationManager getNationManager() {
        return getInstance().database.getNationManager();
    }

    public static TownManager getTownManager() {
        return getInstance().database.getTownManager();
    }

    public static PlotManager getPlotManager() {
        return getInstance().database.getPlotManager();
    }

    public static ResidentManager getResidentManager() {
        return getInstance().database.getResidentManager();
    }

    public static WildernessManager getWildernessManager() {
        return getInstance().database.getWildernessManager();
    }

}
