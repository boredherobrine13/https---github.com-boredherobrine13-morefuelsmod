package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class CommandDifficulty extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getCommandName()
    {
        return "difficulty";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    /**
     * Gets the usage string for the command.
     */
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.difficulty.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length <= 0)
        {
            throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
        }
        else
        {
            EnumDifficulty enumdifficulty = this.getDifficultyFromCommand(args[0]);
            server.setDifficultyForAllWorlds(enumdifficulty);
            notifyCommandListener(sender, this, "commands.difficulty.success", new Object[] {new TextComponentTranslation(enumdifficulty.getDifficultyResourceKey(), new Object[0])});
        }
    }

    protected EnumDifficulty getDifficultyFromCommand(String difficultyString) throws CommandException, NumberInvalidException
    {
        return !difficultyString.equalsIgnoreCase("peaceful") && !difficultyString.equalsIgnoreCase("p") ? (!difficultyString.equalsIgnoreCase("easy") && !difficultyString.equalsIgnoreCase("e") ? (!difficultyString.equalsIgnoreCase("normal") && !difficultyString.equalsIgnoreCase("n") ? (!difficultyString.equalsIgnoreCase("hard") && !difficultyString.equalsIgnoreCase("h") ? EnumDifficulty.getDifficultyEnum(parseInt(difficultyString, 0, 3)) : EnumDifficulty.HARD) : EnumDifficulty.NORMAL) : EnumDifficulty.EASY) : EnumDifficulty.PEACEFUL;
    }

    /**
     * Get a list of options for when the user presses the TAB key
     */
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"peaceful", "easy", "normal", "hard"}): Collections.<String>emptyList();
    }
}