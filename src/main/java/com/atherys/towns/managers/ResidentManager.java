package com.atherys.towns.managers;

import com.atherys.core.database.api.DatabaseManager;
import com.atherys.towns.nation.Nation;
import com.atherys.towns.resident.Resident;
import com.atherys.towns.town.Town;

import java.util.Collection;
import java.util.List;

public interface ResidentManager extends DatabaseManager<Resident> {

    List<Resident> getByTown ( Town town );

    List<Resident> getByNation ( Nation nation );

    Collection<Resident> getAll ();



}
