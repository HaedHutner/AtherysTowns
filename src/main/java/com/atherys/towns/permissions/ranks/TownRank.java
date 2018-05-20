package com.atherys.towns.permissions.ranks;

import com.atherys.towns.permissions.actions.TownAction;
import com.atherys.towns.town.Town;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import java.util.List;

@CatalogedBy(TownRanks.class)
public class TownRank extends Rank<Town,TownAction> implements CatalogType {

    private NationRank defaultNationRank;

    protected TownRank(String id, String name, List<TownAction> permittedActions, NationRank defaultNationRank, TownRank child) {
        super(id, name, permittedActions, child);
        this.defaultNationRank = defaultNationRank;
        TownRankRegistry.getInstance().add(this);
    }

    public NationRank getDefaultNationRank() {
        return defaultNationRank;
    }
}
