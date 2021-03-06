package com.atherys.towns.service;

import com.atherys.core.AtherysCore;
import com.atherys.towns.AtherysTowns;
import com.atherys.towns.TownsConfig;
import com.atherys.towns.entity.Nation;
import com.atherys.towns.entity.Town;
import com.atherys.towns.persistence.NationRepository;
import com.atherys.towns.persistence.TownRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.text.Text;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class NationService {

    public static final Text DEFAULT_NATION_DESCRIPTION = Text.of("No description available.");

    private NationRepository nationRepository;

    private TownService townService;

    private PermissionService permissionService;

    private TownRepository townRepository;

    private TownsConfig config;

    @Inject
    NationService(
            NationRepository nationRepository,
            TownService townService,
            TownRepository townRepository,
            TownsConfig config,
            PermissionService permissionService
    ) {
        this.nationRepository = nationRepository;
        this.townService = townService;
        this.townRepository = townRepository;
        this.config = config;
        this.permissionService = permissionService;
    }

    public Optional<Nation> getNationFromName(String nationName) {
        return nationRepository.findByName(nationName);
    }

    public Nation createNation(String name, Town capital) {
        Nation nation = new Nation();
        nation.setName(name);
        nation.setDescription(DEFAULT_NATION_DESCRIPTION);

        nationRepository.saveOne(nation);

        townService.setTownNation(capital, nation);
        nation.setCapital(capital);
        nation.setLeader(capital.getLeader());

        permissionService.permit(nation.getLeader(), nation, config.DEFAULT_NATION_LEADER_PERMISSIONS);
        permissionService.permit(capital, nation, config.DEFAULT_NATION_TOWN_PERMISSIONS);

        nation.setBank(UUID.randomUUID());
        if (AtherysTowns.economyIsEnabled()) {
            AtherysCore.getEconomyService().get().getOrCreateAccount(nation.getBank().toString());
        }

        nationRepository.saveOne(nation);

        return nation;
    }

    public void setNationName(Nation nation, String name) {
        nation.setName(name);
        nationRepository.saveOne(nation);
    }

    public void setNationDescription(Nation nation, Text description) {
        nation.setDescription(description);
        nationRepository.saveOne(nation);
    }

    public void addTown(Nation nation, Town town) {
        town.setNation(nation);
        nation.addTown(town);

        townRepository.saveOne(town);
        nationRepository.saveOne(nation);
    }

    public void removeTown(Nation nation, Town town) {
        if (town.getNation() != null) {
            town.getResidents().forEach(resident -> permissionService.removeAll(resident, nation));
        }
        town.setNation(null);
        nation.removeTown(town);

        townRepository.saveOne(town);
        nationRepository.saveOne(nation);
    }

    public int getNationPopulation(Nation nation) {
        return nation.getTowns().stream()
                .mapToInt(town -> town.getResidents().size())
                .reduce(0, Integer::sum);
    }

    public void setCapital(Nation nation, Town town) {
        nation.setCapital(town);
        nationRepository.saveOne(nation);
    }

    public void addNationAlly(Nation nation, Nation ally) {
        nation.addAlly(ally);
        nationRepository.saveOne(nation);
    }

    public void addNationNeutral(Nation nation, Nation neutral) {
        nation.removeAlly(neutral);
        nation.removeEnemy(neutral);

        nationRepository.saveOne(nation);
    }

    public void addNationEnemy(Nation nation, Nation enemy) {
        nation.addEnemy(enemy);
        nationRepository.saveOne(nation);
    }

    public Collection<Nation> getAllNations() {
        return nationRepository.getAllNations();
    }

}
