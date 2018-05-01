package com.atherys.towns.managers;

import com.atherys.towns.nation.Nation;
import com.atherys.towns.plot.Plot;
import com.atherys.towns.resident.Resident;
import com.atherys.towns.town.Town;

import java.util.Optional;

public interface NationManager extends AreaObjectManager<Nation> {

    Optional<Nation> getByPlot ( Plot plot );

    Optional<Nation> getByResident ( Resident res );

    Optional<Nation> getByTown ( Town town );



}
