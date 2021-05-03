package me.videogamesm12.multihotbar.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.videogamesm12.multihotbar.util.Util;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

/**
 * NextCommand - Subcommand for advancing the current hotbar page.
 * @author Video
 */
public class NextCommand implements Command<FabricClientCommandSource>
{
	@Override
	public int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException
	{
		if (Util.getPage() == 2147483647)
		{
			context.getSource().sendFeedback(new TranslatableText("command.multihotbars.reached_maximum_number").formatted(Formatting.RED));
			return 2;
		}

		Util.nextPage();
		return 1;
	}
}
