package com.atherys.towns.permissions.actions;

public final class TownActions {

    public static final TownAction INVITE_PLAYER = new TownAction("invite_player", "Invite Player",
            "atherystowns.actions.town.%s.invite");

    public static final TownAction KICK_PLAYER = new TownAction("kick_player", "Kick Player",
            "atherystowns.actions.town.%s.kick");

    public static final TownAction CLAIM_PLOT = new TownAction("claim_plot", "Claim Plot",
            "atherystowns.actions.town.%s.claim");

    public static final TownAction UNCLAIM_PLOT = new TownAction("unclaim_plot", "Unclaim Plot",
            "atherystowns.actions.town.%s.unclaim");

    public static final TownAction SET_NAME = new TownAction("set_name", "Set Town Name",
            "atherystowns.actions.town.%s.set.name");

    public static final TownAction SET_MOTD = new TownAction("set_motd", "Set Town MOTD",
            "atherystowns.actions.town.%s.set.motd");

    public static final TownAction SET_DESCRIPTION = new TownAction("set_description",
            "Set Town Description", "atherystowns.actions.town.%s.set.description");

    public static final TownAction SET_RANK = new TownAction("set_rank", "Set Rank",
            "atherystowns.actions.town.%s.set.rank");

    public static final TownAction SET_SPAWN = new TownAction("set_spawn", "Set Spawn",
            "atherystowns.actions.town.%s.set.spawn");

    public static final TownAction SET_MAYOR = new TownAction("set_mayor", "Set Mayor",
            "atherystowns.actions.town.%s.set.mayor");

    public static final TownAction SET_COLOR = new TownAction("set_color", "Set Color",
            "atherystowns.actions.town.%s.set.color");

    public static final TownAction SET_NATION = new TownAction("set_nation", "Set Nation",
            "atherystowns.actions.town.%s.set.nation");

    public static final TownAction SET_FLAGS = new TownAction("set_flags", "Set Flags",
            "atherystowns.actions.town.%s.set.flag");

    public static final TownAction SET_FLAG_PVP = new TownAction("set_pvp", "Set Flag: PVP",
            "atherystowns.actions.town.%s.set.flag.pvp");

    public static final TownAction SET_FLAG_BUILD = new TownAction("set_build", "Set Flag: Build",
            "atherystowns.actions.town.%s.set.flag.build");

    public static final TownAction SET_FLAG_DESTROY = new TownAction("set_destroy",
            "Set Flag: Destroy", "atherystowns.actions.town.%s.set.flag.destroy");

    public static final TownAction SET_FLAG_SWITCH = new TownAction("set_switch",
            "Set Flag: Switch", "atherystowns.actions.town.%s.set.flag.switch");

    public static final TownAction SET_FLAG_DAMAGE_ENTITY = new TownAction("set_damage_entity",
            "Set Flag: Damage Entity", "atherystowns.actions.town.%s.set.flag.damage_entity");

    public static final TownAction SET_FLAG_JOIN = new TownAction("set_join", "Set Flag: Join",
            "atherystowns.actions.town.%s.set.flag.join");

    public static final TownAction CHAT = new TownAction("chat", "Chat",
            "atherystowns.actions.town.%s.chat");

    public static final TownAction RUIN_TOWN = new TownAction("ruin_town", "Ruin Town",
            "atherystowns.actions.town.%s.ruin");

    public static final TownAction SHOW_TOWN_BORDER = new TownAction("town_border",
            "Show Town Border", "atherystowns.actions.town.%s.border");

    public static final TownAction TOWN_DEPOSIT = new TownAction("town_deposit", "Town Deposit",
            "atherystowns.actions.town.%s.deposit");

    public static final TownAction TOWN_WITHDRAW = new TownAction("town_withdraw", "Town Withdraw",
            "atherystowns.actions.town.%s.withdraw");

    public static final TownAction TOWN_SPAWN = new TownAction("town_spawn", "Town Spawn",
            "atherystowns.actions.town.%s.spawn");

    public static final TownAction MODIFY_PLOT_NAME = new TownAction("plot_name",
            "Modify Plot Name", "atherystowns.actions.town.%s.set_plot_name");

    public static final TownAction MODIFY_PLOT_FLAG = new TownAction("plot_flag",
            "Modify Flag Name", "atherystowns.actions.town.%s.set_plot_flag");

    /*
    Flags should be represented as TownActions instead.
     */

    public static final TownAction BUILD = new TownAction("destroy")
}
