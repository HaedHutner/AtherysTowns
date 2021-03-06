package com.atherys.towns.facade;

import com.atherys.towns.api.command.TownsCommandException;
import com.atherys.towns.api.permission.Permission;
import com.atherys.towns.api.permission.Subject;
import com.atherys.towns.api.permission.nation.NationPermission;
import com.atherys.towns.api.permission.town.TownPermission;
import com.atherys.towns.entity.Nation;
import com.atherys.towns.entity.Plot;
import com.atherys.towns.entity.Resident;
import com.atherys.towns.entity.Town;
import com.atherys.towns.service.PermissionService;
import com.atherys.towns.service.PlotService;
import com.atherys.towns.service.ResidentService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class PermissionFacade {

    @Inject
    ResidentService residentService;

    @Inject
    PermissionService permissionService;

    @Inject
    PlotService plotService;

    static final String NOT_PERMITTED = "You are not permitted to ";

    public final Map<String, TownPermission> TOWN_PERMISSIONS = getTownPermissions();

    public final Map<String, NationPermission> NATION_PERMISSIONS = getNationPermissions();

    PermissionFacade() {
    }

    public boolean isPermitted(Player source, Subject subject, Permission permission) throws TownsCommandException {
        if (subject == null) {
            throw new TownsCommandException("Failed to establish command subject. Will not proceed.");
        }

        if (permission == null) {
            throw new TownsCommandException("Failed to establish command permission. Will not proceed.");
        }

        Resident resident = residentService.getOrCreate(source);

        return permissionService.isPermitted(resident, subject, permission);
    }

    public void checkPermitted(Player source, Subject subject, Permission permission, String message) throws TownsCommandException {
        if (!isPermitted(source, subject, permission)) {
            throw new TownsCommandException(NOT_PERMITTED + message);
        }
    }

    public boolean isPlayerPermittedInOwnTown(Player source, Permission permission) throws TownsCommandException {
        Town town = residentService.getOrCreate(source).getTown();

        if (town == null) {
            throw TownsCommandException.notPartOfTown();
        }

        return isPermitted(source, town, permission);
    }

    public boolean isPlayerPermittedInOwnNation(Player source, Permission permission) throws TownsCommandException {
        Town town = residentService.getOrCreate(source).getTown();

        if (town == null) {
            throw TownsCommandException.notPartOfTown();
        }

        Nation nation = town.getNation();

        if (nation == null) {
            throw new TownsCommandException("Your town is not part of a nation.");
        }

        return isPermitted(source, nation, permission);
    }

    public boolean isPlayerPermittedInCurrentPlot(Player source, Permission permission) throws TownsCommandException {
        Optional<Plot> plot = plotService.getPlotByLocation(source.getLocation());

        if (!plot.isPresent()) {
            throw new TownsCommandException("You are not standing on any plot.");
        }

        return isPermitted(source, plot.get(), permission);
    }

    private Map<String, TownPermission> getTownPermissions() {
        Collection<TownPermission> perms = Sponge.getGame().getRegistry().getAllOf(Permission.class).stream()
                .filter(permission -> permission instanceof TownPermission)
                .map(permission -> (TownPermission) permission)
                .collect(Collectors.toList());

        Map<String, TownPermission> townPerms = new HashMap<>();
        perms.forEach(permission -> townPerms.put(permission.getId(), permission));

        return townPerms;
    }

    private Map<String, NationPermission> getNationPermissions() {
        Collection<NationPermission> perms = Sponge.getGame().getRegistry().getAllOf(Permission.class).stream()
                .filter(permission -> permission instanceof NationPermission)
                .map(permission -> (NationPermission) permission)
                .collect(Collectors.toList());

        Map<String, NationPermission> nationPerms = new HashMap<>();
        perms.forEach(permission -> nationPerms.put(permission.getId(), permission));

        return nationPerms;
    }
}
