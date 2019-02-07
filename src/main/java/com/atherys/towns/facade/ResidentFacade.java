package com.atherys.towns.facade;

import com.atherys.core.AtherysCore;
import com.atherys.towns.AtherysTowns;
import com.atherys.towns.entity.Nation;
import com.atherys.towns.entity.Resident;
import com.atherys.towns.entity.Town;
import com.atherys.towns.service.ResidentService;
import com.atherys.towns.service.TownService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextStyles;

import static org.spongepowered.api.text.format.TextColors.*;
import static org.spongepowered.api.text.Text.NEW_LINE;

import java.util.Optional;

@Singleton
public class ResidentFacade {

    @Inject
    ResidentService residentService;

    @Inject
    TownService townService;

    ResidentFacade() {
    }

    public boolean isPlayerInTown(Player player, Town town) {
        return town.equals(residentService.getOrCreate(player).getTown());
    }

    public boolean isPlayerInNation(Player player, Nation nation) {
        Resident resident = residentService.getOrCreate(player);

        if (resident.getTown() == null) {
            return false;
        }

        return nation.equals(resident.getTown().getNation());
    }

    public Optional<Town> getPlayerTown(Player player) {
        return Optional.ofNullable(residentService.getOrCreate(player).getTown());
    }

    public Optional<Nation> getPlayerNation(Player player) {
        return getPlayerTown(player).map(Town::getNation);
    }

    public void sendResidentInfo(Player player) {
        sendResidentInfo(residentService.getOrCreate(player), player);
    }

    public void sendResidentInfo(Player player, MessageReceiver receiver) {
        sendResidentInfo(residentService.getOrCreate(player), receiver);
    }

    public void sendResidentInfo(Resident resident, MessageReceiver receiver) {
        Text.Builder message = Text.builder().append(Text.of(TownsMessagingFacade.HEADER_FIRST, GOLD, resident.getName(), TownsMessagingFacade.HEADER_SECOND));

        AtherysCore.getEconomyService().ifPresent(economyService -> {
            message.append(Text.of(NEW_LINE, DARK_GREEN, "Bank: ", economyService.getOrCreateAccount(resident.getId()).get().getBalance(economyService.getDefaultCurrency())));
        });

        if (resident.getTown() != null) {
            message.append(Text.of(NEW_LINE, DARK_GREEN, "Town: ", Text.builder().onClick(TextActions.executeCallback(commandSource -> {
                AtherysTowns.getInstance().getTownFacade().sendTownInfo(resident.getTown(), commandSource);
            })).onHover(TextActions.showText(Text.of("View town"))).append(Text.of(GOLD, TextStyles.BOLD, resident.getTown().getName())).build()));
        }

        if (resident.getFriends().size() > 0) {
            Text.Builder friends = Text.builder().append(Text.of(NEW_LINE, DARK_GREEN, "Friends: "));
            resident.getFriends().forEach(friend -> {
                friends.append(Text.builder().onClick(TextActions.executeCallback(commandSource -> {
                    sendResidentInfo(friend, commandSource);
                })).append(Text.of(GOLD, friend.getName())).build());
            });
        }

        receiver.sendMessage(message.build());
    }
}
