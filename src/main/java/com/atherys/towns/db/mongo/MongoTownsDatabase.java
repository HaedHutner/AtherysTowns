package com.atherys.towns.db.mongo;

import com.atherys.core.database.mongo.AbstractMongoDatabase;
import com.atherys.towns.AtherysTowns;
import com.atherys.towns.db.TownsDatabase;
import com.atherys.towns.managers.*;
import com.atherys.towns.managers.mongo.*;

/**
 * A simple implementation of AbstractMongoDatabase designed for the AtherysTowns plugin
 */
public class MongoTownsDatabase extends AbstractMongoDatabase implements TownsDatabase {

    private static MongoTownsDatabase instance = new MongoTownsDatabase();

    private MongoNationManager nationManager;
    private MongoTownManager townManager;
    private MongoPlotManager plotManager;
    private MongoResidentManager residentManager;
    private MongoWildernessManager wildernessManager;

    private MongoTownsDatabase() {
        super( AtherysTowns.getConfig().DATABASE );
    }

    @Override
    public void init() {
        MongoWildernessManager.getInstance().init();
        wildernessManager = MongoWildernessManager.getInstance();

        MongoNationManager.getInstance().loadAll();
        nationManager = MongoNationManager.getInstance();

        MongoTownManager.getInstance().loadAll();
        townManager = MongoTownManager.getInstance();

        MongoPlotManager.getInstance().loadAll();
        plotManager = MongoPlotManager.getInstance();

        MongoResidentManager.getInstance().loadAll();
        residentManager = MongoResidentManager.getInstance();
    }

    @Override
    public void stop() {
        getResidentManager().saveAll( getResidentManager().getAll() );
        getPlotManager().saveAll( getPlotManager().getAll() );
        getTownManager().saveAll( getTownManager().getAll() );
        getNationManager().saveAll( getNationManager().getAll() );
    }

    public static MongoTownsDatabase getInstance () {
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
