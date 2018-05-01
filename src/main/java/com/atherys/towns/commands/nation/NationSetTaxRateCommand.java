package com.atherys.towns.commands.nation;

import com.atherys.towns.commands.TownsMasterCommand;
import com.atherys.towns.messaging.TownMessage;
import com.atherys.towns.nation.Nation;
import com.atherys.towns.permissions.actions.NationActions;
import com.atherys.towns.resident.Resident;
import com.atherys.towns.town.Town;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class NationSetTaxRateCommand extends TownsMasterCommand {
    private static NationSetTaxRateCommand instance = new NationSetTaxRateCommand();

    @Override
    protected CommandResult execute( Player player, CommandContext args, Resident resident, @Nullable Town town, @Nullable Nation nation ) {
        if ( nation != null ) {
            double tax = args.<Double>getOne( "tax_rate" ).orElse( nation.getTax() );
            nation.setTax( tax );
            TownMessage.informAll( Text.of( nation.getName(), " has changed their tax rate to ", tax, "%." ) );
            return CommandResult.success();
        } else {
            TownMessage.warn( player, "You do not belong to a nation." );
            return CommandResult.success();
        }
    }

    @Override
    public CommandSpec getSpec() {
        return CommandSpec.builder()
                .permission( NationActions.SET_TAX_RATE.getPermission() )
                .arguments( GenericArguments.doubleNum( Text.of( "tax_rate" ) ) )
                .executor( this )
                .build();
    }

    public static NationSetTaxRateCommand getInstance() {
        return instance;
    }
}
