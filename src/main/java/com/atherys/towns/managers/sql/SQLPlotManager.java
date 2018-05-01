package com.atherys.towns.managers.sql;

import com.atherys.towns.managers.PlotManager;
import com.atherys.towns.plot.Plot;
import com.atherys.towns.plot.PlotDefinition;

public class SQLPlotManager extends SQLAreaObjectManager<Plot> implements PlotManager {
    @Override
    public boolean checkIntersection( PlotDefinition definition ) {
        return false;
    }
}
