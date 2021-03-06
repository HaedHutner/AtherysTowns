package com.atherys.towns;

import com.atherys.core.AtherysCore;
import com.atherys.core.command.CommandService;
import com.atherys.core.economy.Economy;
import com.atherys.core.event.AtherysHibernateConfigurationEvent;
import com.atherys.core.event.AtherysHibernateInitializedEvent;
import com.atherys.towns.api.chat.TownsChatService;
import com.atherys.towns.api.permission.Permission;
import com.atherys.towns.api.permission.PermissionRegistryModule;
import com.atherys.towns.command.nation.NationCommand;
import com.atherys.towns.command.plot.PlotCommand;
import com.atherys.towns.command.resident.ResidentCommand;
import com.atherys.towns.command.town.TownCommand;
import com.atherys.towns.entity.*;
import com.atherys.towns.facade.*;
import com.atherys.towns.listener.PlayerListener;
import com.atherys.towns.persistence.*;
import com.atherys.towns.persistence.cache.TownsCache;
import com.atherys.towns.service.*;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import static com.atherys.towns.AtherysTowns.*;

@Plugin(
        id = ID,
        name = NAME,
        description = DESCRIPTION,
        version = VERSION,
        dependencies = {
                @Dependency(id = "atheryscore")
        }
)
public class AtherysTowns {

    final static String ID = "atherystowns";
    final static String NAME = "A'therys Towns";
    final static String DESCRIPTION = "A land-management plugin for the Atherys Horizons server";
    final static String VERSION = "%PLUGIN_VERSION%";

    private static AtherysTowns instance;

    private boolean economyEnabled;

    private static boolean init = false;

    @Inject
    private Logger logger;

    @Inject
    private Injector spongeInjector;

    private Components components;

    private Injector townsInjector;

    public static AtherysTowns getInstance() {
        return instance;
    }

    private void init() {
        instance = this;

        // Register Permission Catalogue registry module
        Sponge.getRegistry().registerModule(Permission.class, new PermissionRegistryModule());

        components = new Components();
        townsInjector = spongeInjector.createChildInjector(new AtherysTownsModule());
        townsInjector.injectMembers(components);

        init = true;
    }

    private void start() {
        getTownsCache().initCache();

        Sponge.getEventManager().registerListeners(this, components.playerListener);

        economyEnabled = Economy.isPresent() && components.config.ECONOMY;

        try {
            AtherysCore.getCommandService().register(new ResidentCommand(), this);
            AtherysCore.getCommandService().register(new PlotCommand(), this);
            AtherysCore.getCommandService().register(new TownCommand(), this);
            AtherysCore.getCommandService().register(new NationCommand(), this);
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }

        if (components.config.TOWN_WARMUP < 0) {
            logger.warn("Town spawn warmup is negative. Will default to zero.");
            components.config.TOWN_WARMUP = 0;
        }

        if (components.config.TOWN_COOLDOWN < 0) {
            components.config.TOWN_COOLDOWN = 0;
            logger.warn("Town spawn cooldown is negative. Will default to zero.");
        }
    }

    private void stop() {
        getTownsCache().flushCache();
    }

    @Listener
    public void onHibernateInit(AtherysHibernateInitializedEvent event) {
        init();
    }

    @Listener
    public void onHibernateConfiguration(AtherysHibernateConfigurationEvent event) {
        event.registerEntity(Nation.class);
        event.registerEntity(Town.class);
        event.registerEntity(Plot.class);
        event.registerEntity(Resident.class);
        event.registerEntity(PermissionNode.class);
    }

    @Listener
    public void onStart(GameStartingServerEvent event) {
        if (init) start();
    }

    @Listener
    public void onStop(GameStoppingServerEvent event) {
        if (init) stop();
    }

    public static boolean economyIsEnabled() {
        return getInstance().economyEnabled;
    }

    public TownsConfig getConfig() {
        return components.config;
    }

    public Logger getLogger() {
        return logger;
    }

    public NationRepository getNationRepository() {
        return components.nationRepository;
    }

    public TownRepository getTownRepository() {
        return components.townRepository;
    }

    public PlotRepository getPlotRepository() {
        return components.plotRepository;
    }

    public ResidentRepository getResidentRepository() {
        return components.residentRepository;
    }

    public PermissionRepository getPermissionRepository() {
        return components.permissionRepository;
    }

    public NationService getNationService() {
        return components.nationService;
    }

    public TownService getTownService() {
        return components.townService;
    }

    public PlotService getPlotService() {
        return components.plotService;
    }

    public ResidentService getResidentService() {
        return components.residentService;
    }

    public PermissionService getPermissionService() {
        return components.permissionService;
    }

    public TownsMessagingFacade getTownsMessagingService() {
        return components.townsMessagingFacade;
    }

    public NationFacade getNationFacade() {
        return components.nationFacade;
    }

    public TownFacade getTownFacade() {
        return components.townFacade;
    }

    public TownSpawnFacade getTownSpawnCommand() {
        return components.townSpawnFacade;
    }

    public TownAdminFacade getTownAdminFacade() {
        return components.townAdminFacade;
    }

    public PlotFacade getPlotFacade() {
        return components.plotFacade;
    }

    public ResidentFacade getResidentFacade() {
        return components.residentFacade;
    }

    public PermissionFacade getPermissionFacade() {
        return components.permissionFacade;
    }

    public PlotSelectionFacade getPlotSelectionFacade() {
        return components.plotSelectionFacade;
    }

    public TownsChatService getChatService() {
        return components.chatService;
    }

    protected TownsCache getTownsCache() {
        return components.townsCache;
    }

    private static class Components {

        @Inject
        private TownsConfig config;

        @Inject
        private TownsCache townsCache;

        @Inject
        private NationRepository nationRepository;

        @Inject
        private TownRepository townRepository;

        @Inject
        private PlotRepository plotRepository;

        @Inject
        private ResidentRepository residentRepository;

        @Inject
        private PermissionRepository permissionRepository;

        @Inject
        private NationService nationService;

        @Inject
        private TownService townService;

        @Inject
        private PlotService plotService;

        @Inject
        private ResidentService residentService;

        @Inject
        private PermissionService permissionService;

        @Inject
        private TownsChatService chatService;

        @Inject
        private TownsMessagingFacade townsMessagingFacade;

        @Inject
        private NationFacade nationFacade;

        @Inject
        private TownFacade townFacade;

        @Inject
        private TownSpawnFacade townSpawnFacade;

        @Inject
        private TownAdminFacade townAdminFacade;

        @Inject
        private PlotFacade plotFacade;

        @Inject
        private ResidentFacade residentFacade;

        @Inject
        private PermissionFacade permissionFacade;

        @Inject
        private PlotSelectionFacade plotSelectionFacade;

        @Inject
        private PlayerListener playerListener;
    }
}
