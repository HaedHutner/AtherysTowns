package com.atherys.towns;

import com.atherys.core.utils.PluginConfig;
import com.atherys.towns.api.permission.Permission;
import com.atherys.towns.api.permission.nation.NationPermissions;
import com.atherys.towns.api.permission.town.TownPermissions;
import com.atherys.towns.api.permission.world.WorldPermissions;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Singleton;
import ninja.leaping.configurate.objectmapping.Setting;
import org.spongepowered.api.service.economy.Currency;

import java.io.IOException;
import java.util.Set;

@Singleton
public class TownsConfig extends PluginConfig {

    @Setting("plot-max-area")
    public int MAX_PLOT_AREA = 1024;

    @Setting("plot-min-side")
    public int MIN_PLOT_SIDE = 16;

    @Setting("default-town-max-size")
    public int DEFAULT_TOWN_MAX_SIZE = 4096;

    @Setting("max-town-name-size")
    public int MAX_TOWN_NAME_LENGTH = 25;

    @Setting("max-nation-name-size")
    public int MAX_NATION_NAME_LENGTH = 25;

    @Setting("max-residents-to-display")
    public int MAX_RESIDENTS_DISPLAY = 25;

    @Setting("economy-enabled")
    public boolean ECONOMY = true;

    @Setting("require-being-in-town-for-transactions")
    public boolean LOCAL_TRANSACTIONS = false;

    @Setting("respawn-in-town")
    public boolean SPAWN_IN_TOWN = true;

    @Setting("currency")
    public Currency CURRENCY;

    @Setting("town-spawn-cooldown-minutes")
    public int TOWN_COOLDOWN = 0;

    @Setting("town-spawn-warmup-seconds")
    public int TOWN_WARMUP = 0;

    @Setting("default-nation-leader-permissions")
    public Set<Permission> DEFAULT_NATION_LEADER_PERMISSIONS = ImmutableSet.of(
            NationPermissions.INVITE_TOWN,
            NationPermissions.KICK_TOWN,
            NationPermissions.ADD_PERMISSION,
            NationPermissions.REVOKE_PERMISSION,
            NationPermissions.WITHDRAW_FROM_BANK,
            NationPermissions.DEPOSIT_INTO_BANK,
            NationPermissions.SET_NAME,
            NationPermissions.SET_DESCRIPTION,
            NationPermissions.SET_FREELY_JOINABLE,
            NationPermissions.ADD_ALLY,
            NationPermissions.ADD_ENEMY,
            NationPermissions.ADD_NEUTRAL,
            NationPermissions.DECLARE_WAR,
            NationPermissions.DECLARE_PEACE,
            NationPermissions.TRANSFER_LEADERSHIP,
            NationPermissions.CHAT
    );

    @Setting("default-nation-town-permissions")
    public Set<Permission> DEFAULT_NATION_TOWN_PERMISSIONS = ImmutableSet.of(
            NationPermissions.DEPOSIT_INTO_BANK,
            NationPermissions.CHAT
    );

    @Setting("default-nation-resident-permissions")
    public Set<Permission> DEFAULT_NATION_RESIDENT_PERMISSIONS = ImmutableSet.of(
            NationPermissions.DEPOSIT_INTO_BANK,
            NationPermissions.CHAT
    );

    @Setting("default-town-leader-permissions")
    public Set<Permission> DEFAULT_TOWN_LEADER_PERMISSIONS = ImmutableSet.of(
            TownPermissions.INVITE_RESIDENT,
            TownPermissions.KICK_RESIDENT,
            TownPermissions.CLAIM_PLOT,
            TownPermissions.UNCLAIM_PLOT,
            TownPermissions.GRANT_PLOT,
            TownPermissions.ADD_PERMISSION,
            TownPermissions.REVOKE_PERMISSION,
            TownPermissions.WITHDRAW_FROM_BANK,
            TownPermissions.DEPOSIT_INTO_BANK,
            TownPermissions.LEAVE_NATION,
            TownPermissions.JOIN_NATION,
            TownPermissions.SET_NAME,
            TownPermissions.SET_DESCRIPTION,
            TownPermissions.SET_MOTD,
            TownPermissions.SET_COLOR,
            TownPermissions.SET_FREELY_JOINABLE,
            TownPermissions.SET_SPAWN,
            TownPermissions.SET_PVP,
            TownPermissions.TRANSFER_LEADERSHIP,
            TownPermissions.CHAT,

            WorldPermissions.BUILD,
            WorldPermissions.DESTROY,
            WorldPermissions.DAMAGE_NONPLAYERS,
            WorldPermissions.DAMAGE_PLAYERS,
            WorldPermissions.INTERACT_CHESTS,
            WorldPermissions.INTERACT_DOORS,
            WorldPermissions.INTERACT_REDSTONE,
            WorldPermissions.INTERACT_ENTITIES
    );

    @Setting("default-town-resident-permissions")
    public Set<Permission> DEFAULT_TOWN_RESIDENT_PERMISSIONS = ImmutableSet.of(
            TownPermissions.DEPOSIT_INTO_BANK,
            TownPermissions.CHAT,

            WorldPermissions.BUILD,
            WorldPermissions.DESTROY,
            WorldPermissions.DAMAGE_NONPLAYERS,
            WorldPermissions.DAMAGE_PLAYERS,
            WorldPermissions.INTERACT_CHESTS,
            WorldPermissions.INTERACT_DOORS,
            WorldPermissions.INTERACT_REDSTONE,
            WorldPermissions.INTERACT_ENTITIES
    );

    protected TownsConfig() throws IOException {
        super("config/" + AtherysTowns.ID, "config.conf");
        init();
    }
}
