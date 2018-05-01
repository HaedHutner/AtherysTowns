package com.atherys.towns.managers;

import com.atherys.towns.plot.Plot;
import com.atherys.towns.plot.PlotDefinition;

public interface PlotManager extends AreaObjectManager<Plot> {

    boolean checkIntersection ( PlotDefinition definition );

}
