package com.atherys.towns.entity;

import com.atherys.core.db.SpongeIdentifiable;
import com.atherys.core.db.converter.TransformConverter;
import com.atherys.towns.api.permission.Actor;
import com.atherys.towns.api.permission.Subject;
import com.atherys.towns.chat.TownMessageChannel;
import com.atherys.towns.persistence.converter.TextColorConverter;
import com.atherys.towns.persistence.converter.TextConverter;
import org.hibernate.annotations.GenericGenerator;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.world.World;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Town implements SpongeIdentifiable, Subject<Nation>, Actor {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Convert(converter = TextConverter.class)
    private Text name;

    @Convert(converter = TextConverter.class)
    private Text description;

    @Convert(converter = TextConverter.class)
    private Text motd;

    @Convert(converter = TextColorConverter.class)
    private TextColor color;

    @OneToOne
    private Resident leader;

    @ManyToOne(optional = true)
    @JoinColumn(name = "nation_id")
    private Nation nation;

    private UUID world;

    @Convert(converter = TransformConverter.class)
    private Transform<World> spawn;

    @OneToMany(mappedBy = "town")
    private Set<Resident> residents = new HashSet<>();

    @OneToMany(mappedBy = "town")
    private Set<Plot> plots = new HashSet<>();

    @Transient
    private TownMessageChannel messageChannel;

    private int maxSize;

    private boolean freelyJoinable;

    private boolean pvpEnabled;

    @Nonnull
    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }

    public Text getDescription() {
        return description;
    }

    public void setDescription(Text description) {
        this.description = description;
    }

    public Text getMotd() {
        return motd;
    }

    public void setMotd(Text motd) {
        this.motd = motd;
    }

    public Resident getLeader() {
        return leader;
    }

    public void setLeader(Resident leader) {
        this.leader = leader;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public UUID getWorld() {
        return world;
    }

    public void setWorld(UUID world) {
        this.world = world;
    }

    public Transform<World> getSpawn() {
        return spawn;
    }

    public void setSpawn(Transform<World> spawn) {
        this.spawn = spawn;
    }

    public Set<Resident> getResidents() {
        return residents;
    }

    public void setResidents(Set<Resident> residents) {
        this.residents = residents;
    }

    public void addResident(Resident resident) {
        residents.add(resident);
    }

    public void removeResident(Resident resident) {
        residents.remove(resident);
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public Set<Plot> getPlots() {
        return plots;
    }

    public void setPlots(Set<Plot> plots) {
        this.plots = plots;
    }

    public void addPlot(Plot plot) {
        plots.add(plot);
    }

    public void removePlot(Plot plot) {
        plots.remove(plot);
    }

    public boolean isFreelyJoinable() {
        return freelyJoinable;
    }

    public void setFreelyJoinable(boolean freelyJoinable) {
        this.freelyJoinable = freelyJoinable;
    }

    @Override
    public boolean hasParent() {
        return true;
    }

    @Override
    public Nation getParent() {
        return nation;
    }

    public boolean isPvpEnabled() {
        return pvpEnabled;
    }

    public void setPvpEnabled(boolean pvpEnabled) {
        this.pvpEnabled = pvpEnabled;
    }

    public TextColor getColor() {
        return color;
    }

    public void setColor(TextColor color) {
        this.color = color;
    }


    public TownMessageChannel getMessageChannel() {
        return messageChannel;
    }

    public void setMessageChannel(TownMessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Town town = (Town) o;
        return id.equals(town.id) &&
                name.equals(town.name) &&
                description.equals(town.description) &&
                motd.equals(town.motd) &&
                leader.equals(town.leader) &&
                nation.equals(town.nation) &&
                world.equals(town.world) &&
                spawn.equals(town.spawn) &&
                residents.equals(town.residents) &&
                plots.equals(town.plots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, motd, world, spawn);
    }
}
