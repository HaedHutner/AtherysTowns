package com.atherys.towns.managers.sql;

import com.atherys.towns.base.AreaObject;
import com.atherys.towns.managers.AreaObjectManager;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLAreaObjectManager<T extends AreaObject> implements AreaObjectManager<T> {
    @Override
    public Optional<List<T>> getByName( String name ) {
        return Optional.empty();
    }

    @Override
    public Optional<T> getFirstByName( String name ) {
        return Optional.empty();
    }

    @Override
    public Optional<T> getByUUID( UUID uuid ) {
        return Optional.empty();
    }

    @Override
    public Optional<T> getByLocation( Location<World> loc ) {
        return Optional.empty();
    }

    @Override
    public Collection<T> getAll() {
        return null;
    }

    @Override
    public void add( T ao ) {

    }

    @Override
    public <P extends AreaObject> List<T> getByParent( P test ) {
        return null;
    }

    @Override
    public void save( T t ) {

    }

    @Override
    public Optional<T> get( UUID uuid ) {
        return Optional.empty();
    }

    @Override
    public void update( T t ) {

    }

    @Override
    public void remove( T t ) {

    }

    @Override
    public void saveAll( Collection<T> collection ) {

    }

    @Override
    public void loadAll() {

    }

    @Override
    public void updateAll( Collection<T> collection ) {

    }

    @Override
    public void removeAll( Collection<T> collection ) {

    }
}
