package com.atherys.towns.permissions.actions;

import com.atherys.towns.nation.Nation;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(NationActions.class)
public class NationAction implements CatalogType, TownsAction<Nation> {

    private String id;
    private String name;
    private String template;

    public NationAction(String id, String name, String template) {
        this.id = id;
        this.name = name;
        this.template = template;

        NationActionRegistry.getInstance().add(this);
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
    public String getPermission(Nation root) {
        return String.format(getTemplate(), root.getUUID().toString());
    }
}
