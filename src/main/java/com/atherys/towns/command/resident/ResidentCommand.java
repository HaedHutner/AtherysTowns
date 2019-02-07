package com.atherys.towns.command.resident;

import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Children;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.towns.AtherysTowns;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

@Aliases({"resident", "res"})
@Description("Base resident command.")
@Children({
        AddFriendCommand.class,
        RemoveFriendCommand.class,
        ResidentInfoCommand.class
})
@Permission("atherystowns.resident")
public class ResidentCommand implements PlayerCommand {
    @Override
    public CommandResult execute(Player src, CommandContext args) throws CommandException {
        AtherysTowns.getInstance().getResidentFacade().sendResidentInfo(src);
        return CommandResult.empty();
    }
}
