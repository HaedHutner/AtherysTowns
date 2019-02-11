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

@Aliases("friend")
@Description("Adds a player as a friend.")
@Permission("atherystowns.resident.friend")
public class AddFriendCommand implements PlayerCommand, ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.player(Text.of("player"))
        };
    }

    @Override
    public CommandResult execute(Player src, CommandContext args) throws CommandException {
        AtherysTowns.getInstance().getResidentFacade().addPlayerFriend(src, args.<User>getOne("player").orElse(null));
        return CommandResult.success();
    }
}
