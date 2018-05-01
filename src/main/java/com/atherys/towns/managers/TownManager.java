package com.atherys.towns.managers;

import com.atherys.towns.AtherysTowns;
import com.atherys.towns.commands.TownsValues;
import com.atherys.towns.nation.Nation;
import com.atherys.towns.plot.PlotFlags;
import com.atherys.towns.plot.flags.*;
import com.atherys.towns.resident.Resident;
import com.atherys.towns.town.Town;
import com.atherys.towns.town.TownBuilder;
import com.atherys.towns.town.TownStatus;
import com.atherys.towns.utils.DbUtils;
import org.bson.Document;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.atherys.towns.AtherysTowns.getConfig;

public final class TownManager extends AreaObjectManager<Town> {

    private static TownManager instance = new TownManager();
    private final Task townBorderTask;

    private TownManager () {
        super( "towns" );

        townBorderTask = Task.builder()
                .interval( getConfig().TOWN.BORDER_UPDATE_RATE, TimeUnit.SECONDS )
                .execute( () -> {
                    for ( Player p : Sponge.getServer().getOnlinePlayers() ) {
                        if ( TownsValues.get( p.getUniqueId(), TownsValues.TownsKey.TOWN_BORDERS ).isPresent() ) {
                            Optional<Resident> resOpt = ResidentManager.getInstance().get( p.getUniqueId() );
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

    @Override
    public Optional<Document> toDocument ( Town object ) {
        Document doc = new Document( "uuid", object.getUUID() );
        doc.append( "nation", object.getParent().isPresent() ? object.getParent().get().getUUID() : null );
        doc.append( "status", object.getStatus().id() );
        doc.append( "name", object.getName() );

        Document flags = new Document();
        object.getTownFlags().getAll().forEach( ( k, v ) -> flags.append( k.getId(), v.getId() ) );
        doc.append( "flags", flags );

        doc.append( "max_size", object.getMaxSize() );
        doc.append( "spawn", DbUtils.Serialize.location( object.getSpawn() ) );

        doc.append( "color", object.getColor().getId() );
        doc.append( "motd", object.getMOTD() );
        doc.append( "description", object.getDescription() );

        return Optional.of( doc );
    }

    @Override
    public Optional<Town> fromDocument ( Document doc ) {
        TownBuilder builder = Town.fromUUID( doc.get( "uuid", UUID.class ) ); // UUID.fromString(doc.getString("uuid")));

        UUID nation_uuid = doc.get( "nation", UUID.class );
        if ( nation_uuid != null ) {
            Optional<Nation> nation = NationManager.getInstance().getByUUID( nation_uuid );
            nation.ifPresent( n -> {
                builder.nation( n );
                builder.status( TownStatus.fromId( doc.getInteger( "status" ) ) );
            } );
        }

        builder.name( doc.getString( "name" ) );

        // load flags
        Document flags = doc.get( "flags", Document.class );
        PlotFlags plotFlags = PlotFlags.regular();
        for ( Map.Entry<String, Object> flagData : flags.entrySet() ) {
            Optional<Flag> flag = FlagRegistry.getInstance().getById( flagData.getKey() );
            if ( !flag.isPresent() ) continue;
            Extent extent = ExtentRegistry.getInstance().getById( (String) flagData.getValue() ).orElse( Extents.NONE );
            plotFlags.set( flag.get(), extent );
        }
        builder.flags( plotFlags );

        builder.maxSize( doc.getInteger( "max_size" ) );

        // load spawn
        Optional<Location<World>> spawn = DbUtils.Deserialize.location( doc.get( "spawn", Document.class ) );
        if ( spawn.isPresent() ) {
            builder.spawn( spawn.get() );
        } else {
            AtherysTowns.getInstance().getLogger().error( "[MongoDB] Town load failure. Had improper spawn." );
            return Optional.empty();
        }

        builder.color( Sponge.getGame().getRegistry().getType( TextColor.class, doc.getString( "color" ) ).orElse( TextColors.WHITE ) );
        builder.motd( doc.getString( "motd" ) );
        builder.description( doc.getString( "description" ) );

        return Optional.of( builder.build() );
    }

    public static TownManager getInstance () {
        return instance;
    }

    public Task getTownBorderTask() {
        return townBorderTask;
    }
}
