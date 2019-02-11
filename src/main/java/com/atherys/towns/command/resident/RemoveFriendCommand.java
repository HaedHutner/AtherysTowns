package com.atherys.towns.command.resident;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.towns.AtherysTowns;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

@Aliases("unfriend")
@Description("Removes a player as a friend.")
@Permission("atherystowns.resident.unfriend")
public class RemoveFriendCommand implements PlayerCommand, ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.user(Text.of("friend"))
        };
    }

    @Override
    public CommandResult execute(Player src, CommandContext args) throws CommandException {
        AtherysTowns.getInstance().getResidentFacade().removePlayerFriend(src, args.<User>getOne("friend").orElse(null));
        return CommandResult.success();
    }
}
