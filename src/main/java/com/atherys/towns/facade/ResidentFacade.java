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
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.channel.MessageReceiver;
import static org.spongepowered.api.text.format.TextStyles.*;

import static org.spongepowered.api.text.format.TextColors.*;
import static org.spongepowered.api.text.Text.NEW_LINE;

import java.math.BigDecimal;
import java.util.Map;
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
        boolean isOnline = Sponge.getServer().getPlayer(resident.getId()).isPresent();
        Text.Builder message = Text.builder().append(Text.of(TownsMessagingFacade.HEADER_FIRST, GOLD, resident.getName(), GREEN, " (",
                isOnline ? "Online" : "Offline", ")", TownsMessagingFacade.HEADER_SECOND));

        AtherysCore.getEconomyService().ifPresent(economyService -> {
            Optional<UniqueAccount> account  = economyService.getOrCreateAccount(resident.getId());
            if (account.isPresent()) {
                Text.Builder currencies = Text.builder();
                Map<Currency, BigDecimal> balances = account.get().getBalances();
                int i = 0;
                for(Map.Entry<Currency, BigDecimal> balance : balances.entrySet()) {
                    currencies.append(Text.of(DARK_GREEN, balance.getKey().getPluralDisplayName(), ": ", GOLD, balance.getValue(),
                            i == balances.size() ? NEW_LINE : ""));
                    i++;
                }

                message.append(
                    Text.of(NEW_LINE, DARK_GREEN, "Balance: "),
                    Text.builder().append(Text.of(BOLD, GOLD, "View")).onHover(TextActions.showText(currencies.build())).build()
                );
            }
        });

        if (resident.getTown() != null) {
            message.append(Text.of(NEW_LINE, DARK_GREEN, "Town: ", Text.builder().onClick(TextActions.executeCallback(commandSource -> {
                AtherysTowns.getInstance().getTownFacade().sendTownInfo(resident.getTown(), commandSource);
            })).onHover(TextActions.showText(Text.of("View town"))).append(Text.of(GOLD, BOLD, resident.getTown().getName())).build()));
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
