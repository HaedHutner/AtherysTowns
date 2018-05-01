package com.atherys.towns.managers;

import com.atherys.core.database.api.DatabaseManager;
import com.atherys.towns.base.AreaObject;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AreaObjectManager<T extends AreaObject> extends DatabaseManager<T> {

    Optional<List<T>> getByName( String name );

    Optional<T> getFirstByName ( String name );

    Optional<T> getByUUID ( UUID uuid );

    Optional<T> getByLocation ( Location<World> loc );

    Collection<T> getAll ();

    void add ( T ao );

    <P extends AreaObject> List<T> getByParent ( P test );

}
