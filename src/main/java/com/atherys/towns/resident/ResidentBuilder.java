package com.atherys.towns.resident;

import com.atherys.towns.AtherysTowns;
import com.atherys.towns.permissions.ranks.NationRank;
import com.atherys.towns.permissions.ranks.TownRank;
import com.atherys.towns.town.Town;

import java.util.Date;
import java.util.UUID;

public class ResidentBuilder {

    private Resident res;

    ResidentBuilder ( UUID uuid ) {
        res = new Resident( uuid );
    }

    public ResidentBuilder town ( Town town, TownRank rank ) {
        if ( town != null ) {
            res.setTown( town, rank );
        }
        return this;
    }

    public ResidentBuilder townRank ( TownRank rank ) {
        res.setTownRank( rank );
        return this;
    }

    public ResidentBuilder nationRank ( NationRank rank ) {
        res.setNationRank( rank );
        return this;
    }

    public ResidentBuilder registerTimestamp ( Date time ) {
        res.setRegisteredDate( time );
        return this;
    }

    public ResidentBuilder updateLastOnline () {
        res.updateLastOnline();
        return this;
    }

    public Resident build () {
        AtherysTowns.getResidentManager().save( res );
        res.updatePermissions();
        return res;
    }
}
