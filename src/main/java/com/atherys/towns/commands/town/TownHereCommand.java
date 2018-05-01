package com.atherys.towns.commands.town;

import com.atherys.towns.AtherysTowns;
import com.atherys.towns.commands.TownsSimpleCommand;
import com.atherys.towns.messaging.TownMessage;
import com.atherys.towns.nation.Nation;
import com.atherys.towns.plot.Plot;
import com.atherys.towns.resident.Resident;
import com.atherys.towns.town.Town;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.Optional;

public class TownHereCommand extends TownsSimpleCommand {

    private static TownHereCommand instance = new TownHereCommand();

    public static TownHereCommand getInstance () {
        return instance;
    }

    @Override
    protected CommandResult execute ( Player player, CommandContext args, Resident resident, @Nullable Town town, @Nullable Nation nation ) {
        Optional<Plot> pHere = AtherysTowns.getPlotManager().getByLocation( player.getLocation() );
        if ( pHere.isPresent() ) {
            pHere.get().createView().show( player );
            return CommandResult.success();
        } else {
            TownMessage.warn( player, "You are in the wilderness." );
        }
        return CommandResult.empty();
    }

    @Override
    public CommandSpec getSpec () {
        return CommandSpec.builder()
                .description( Text.of( "Used to get information on the town you are currently standing in." ) )
                .executor( this )
                .build();
    }
}
