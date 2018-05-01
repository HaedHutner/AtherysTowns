package com.atherys.towns.db;

import com.atherys.towns.managers.*;

/**
 * Represents a common place to store all database-related manager objects.
 * Is responsible for initializing the connection to the database, and properly initializing all managers.
 * Is also responsible for properly stopping all managers, and ceasing the connection to the database.
 */
public interface TownsDatabase {

    NationManager getNationManager();

    TownManager getTownManager();

    PlotManager getPlotManager();

    ResidentManager getResidentManager();

    WildernessManager getWildernessManager();

    void init();

    void stop();

}
