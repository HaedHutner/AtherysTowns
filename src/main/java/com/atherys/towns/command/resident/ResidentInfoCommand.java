package com.atherys.towns.command.resident;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.towns.AtherysTowns;
import com.atherys.towns.api.command.exception.TownsCommandException;
import com.atherys.towns.entity.Resident;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;

import java.util.Optional;

import static org.spongepowered.api.Sponge.getServiceManager;

@Aliases("info")
@Description("Displays information about a resident")
@Permission("atherystowns.resident.info")
public class ResidentInfoCommand implements ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.string(Text.of("player"))
        };
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<User> user = Sponge.getServiceManager().provide(UserStorageService.class).get().get(args.<String>getOne("player").get());
        if (user.isPresent()) {
            Resident resident = AtherysTowns.getInstance().getResidentService().getOrCreate(user.get());
            AtherysTowns.getInstance().getResidentFacade().sendResidentInfo(resident, src);
            return CommandResult.success();
        } else {
            throw new TownsCommandException("Player \"" + args.<String>getOne("player").get() + "\" does not exist.");
        }
    }
}
