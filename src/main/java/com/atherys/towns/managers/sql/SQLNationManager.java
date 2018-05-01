package com.atherys.towns.managers.sql;

import com.atherys.towns.managers.NationManager;
import com.atherys.towns.nation.Nation;
import com.atherys.towns.plot.Plot;
import com.atherys.towns.resident.Resident;
import com.atherys.towns.town.Town;

import java.util.Optional;

public class SQLNationManager extends SQLAreaObjectManager<Nation> implements NationManager {

    @Override
    public Optional<Nation> getByPlot( Plot plot ) {
        return Optional.empty();
    }

    @Override
    public Optional<Nation> getByResident( Resident res ) {
        return Optional.empty();
    }

    @Override
    public Optional<Nation> getByTown( Town town ) {
        return Optional.empty();
    }

}
