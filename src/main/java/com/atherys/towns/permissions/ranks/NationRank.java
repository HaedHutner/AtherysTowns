package com.atherys.towns.permissions.ranks;

import com.atherys.towns.nation.Nation;
import com.atherys.towns.permissions.actions.NationAction;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import java.util.List;

@CatalogedBy(NationRanks.class)
public class NationRank extends Rank<Nation, NationAction> implements CatalogType {

    protected NationRank(String id, String name, List<NationAction> permittedActions, NationRank child) {
        super(id, name, permittedActions, child);
        NationRankRegistry.getInstance().add(this);
    }
}
