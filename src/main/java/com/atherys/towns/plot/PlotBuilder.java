package com.atherys.towns.plot;

import com.atherys.towns.AtherysTowns;
import com.atherys.towns.town.Town;

import java.util.UUID;

public final class PlotBuilder {

    private Plot plot;

    PlotBuilder () {
        this.plot = new Plot( UUID.randomUUID() );
    }

    PlotBuilder ( UUID uuid ) {
        this.plot = new Plot( uuid );
    }

    public PlotBuilder definition ( PlotDefinition definition ) {
        plot.setDefinition( definition );
        return this;
    }

    public PlotBuilder town ( Town town ) {
        plot.setParent( town );
        return this;
    }

    public PlotBuilder flags ( PlotFlags flags ) {
        plot.setFlags( flags );
        return this;
    }

    public PlotBuilder name ( String name ) {
        plot.setName( name );
        return this;
    }

    public Plot build () {
        AtherysTowns.getPlotManager().add( plot );
        return plot;
    }

}
