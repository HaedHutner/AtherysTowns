package com.atherys.towns.managers.sql;

import com.atherys.towns.managers.ResidentManager;
import com.atherys.towns.nation.Nation;
import com.atherys.towns.resident.Resident;
import com.atherys.towns.town.Town;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLResidentManager implements ResidentManager {
    @Override
    public List<Resident> getByTown( Town town ) {
        return null;
    }

    @Override
    public List<Resident> getByNation( Nation nation ) {
        return null;
    }

    @Override
    public Collection<Resident> getAll() {
        return null;
    }

    @Override
    public void save( Resident resident ) {

    }

    @Override
    public Optional<Resident> get( UUID uuid ) {
        return Optional.empty();
    }

    @Override
    public void update( Resident resident ) {

    }

    @Override
    public void remove( Resident resident ) {

    }

    @Override
    public void saveAll( Collection<Resident> collection ) {

    }

    @Override
    public void loadAll() {

    }

    @Override
    public void updateAll( Collection<Resident> collection ) {

    }

    @Override
    public void removeAll( Collection<Resident> collection ) {

    }
}
