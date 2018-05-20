package com.atherys.towns.permissions.ranks;

import com.atherys.towns.AtherysTowns;
import com.atherys.towns.base.TownsObject;
import com.atherys.towns.permissions.actions.TownsAction;
import com.atherys.towns.resident.Resident;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.SubjectReference;
import org.spongepowered.api.util.Tristate;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

public abstract class Rank<T extends TownsObject, V extends TownsAction<T>> {

    protected Rank child;
    protected List<V> actions;
    private String id;
    private String name;

    protected Rank(String id, String name, List<V> actions, @Nullable Rank child) {
        this.id = id;
        this.name = name;
        this.actions = actions;
        if (child != null) this.child = child;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Rank getChild() {
        return child;
    }

    /**
     * Checks whether or not this rank is a parent of the provided one.
     *
     * @param rank The child rank to check
     * @return true if the provided rank is a child of this one.
     */
    public boolean isRankGreaterThan(Rank rank) {
        return child != null && (child == rank || child.isRankGreaterThan(rank));
    }

    /**
     * Applies all the permissions taken from the TownsAction permissions templates to the provided resident with the given TownsObject.
     * This is the equivalent to calling {@link #update(Resident, TownsObject, Tristate)} with {@link Tristate#TRUE}
     *
     * @param resident The resident to receive the permissions within this Rank
     * @param object   The TownsObject for which these permissions will apply
     */
    public void applyTo(Resident resident, T object) {
        update(resident, object, Tristate.TRUE);
    }

    /**
     * Removes all permissions held within this Rank from the Resident.
     * This is the equivalent to calling {@link #update(Resident, TownsObject, Tristate)} with {@link Tristate#FALSE}
     *
     * @param resident The resident whose permissions are to be removed
     * @param object   The TownsObject for which these permissions are relevant
     */
    public void removeFrom(Resident resident, T object) {
        update(resident, object, Tristate.FALSE);
    }

    public void update(Resident resident, T object, Tristate state) {
        Optional<Player> player = resident.getPlayer();
        if (player.isPresent()) {
            LuckPermsApi api = LuckPerms.getApi();
            // TODO: Use LuckPerms API instead

            PermissionService service = AtherysTowns.getPermissionService();
            SubjectReference permissions = service.getUserSubjects().newSubjectReference("atherystowns");

            permissions.resolve().thenAccept(subject -> {
                for (V action : actions) {
                    subject.getSubjectData().setPermission(new LinkedHashSet<>(), action.getPermission(object), state);
                }
            });

            player.get().getSubjectData().addParent(new LinkedHashSet<>(), permissions);
        }
    }
}
