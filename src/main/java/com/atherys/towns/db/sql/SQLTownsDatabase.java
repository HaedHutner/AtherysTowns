package com.atherys.towns.db.sql;

import com.atherys.towns.db.TownsDatabase;
import com.atherys.towns.managers.*;
import com.atherys.towns.managers.sql.*;

public class SQLTownsDatabase implements TownsDatabase {

    private static SQLTownsDatabase instance = new SQLTownsDatabase();

    private SQLNationManager nationManager;
    private SQLTownManager townManager;
    private SQLPlotManager plotManager;
    private SQLResidentManager residentManager;
    private SQLWildernessManager wildernessManager;

    private SQLTownsDatabase() {
    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {

    }

    public static SQLTownsDatabase getInstance() {
        return instance;
    }

    @Override
    public NationManager getNationManager() {
        return nationManager;
    }

    @Override
    public TownManager getTownManager() {
        return townManager;
    }

    @Override
    public PlotManager getPlotManager() {
        return plotManager;
    }

    @Override
    public ResidentManager getResidentManager() {
        return residentManager;
    }

    @Override
    public WildernessManager getWildernessManager() {
        return wildernessManager;
    }
}
