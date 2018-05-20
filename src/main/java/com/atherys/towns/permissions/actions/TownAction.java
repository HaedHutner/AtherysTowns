package com.atherys.towns.permissions.actions;

import com.atherys.towns.town.Town;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(TownActions.class)
public class TownAction implements CatalogType, TownsAction<Town> {

    private String id;
    private String name;
    private String template;

    public TownAction(String id, String name, String template) {
        this.id = id;
        this.name = name;
        this.template = template;
        TownActionRegistry.getInstance().add(this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public String getPermission(Town root) {
        return String.format(getTemplate(), root.getUUID().toString());
    }
}
