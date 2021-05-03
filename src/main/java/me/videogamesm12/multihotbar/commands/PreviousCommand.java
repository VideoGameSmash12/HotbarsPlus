package me.videogamesm12.multihotbar.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.videogamesm12.multihotbar.util.Util;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

/**
 * PreviousCommand - Subcommand for going a page back.
 * @author Video
 */
public class PreviousCommand implements Command<FabricClientCommandSource>
{
	@Override
	public int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException
	{
		Util.previousPage();
		return 1;
	}
}
