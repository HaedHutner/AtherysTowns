package com.atherys.towns.permissions.actions;

import com.atherys.towns.base.TownsObject;

public interface TownsAction<T extends TownsObject> {

    String getTemplate();

    default String getPermission(T root) {
        return String.format(getTemplate(), root.getUUID().toString());
    }

}
