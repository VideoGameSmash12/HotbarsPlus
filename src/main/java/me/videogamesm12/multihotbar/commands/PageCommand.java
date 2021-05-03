package me.videogamesm12.multihotbar.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.videogamesm12.multihotbar.util.Util;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class PageCommand implements Command<FabricClientCommandSource>
{
	@Override
	public int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException
	{
		Integer page = context.getArgument("page", Integer.class);

		if (page < 0)
		{
			context.getSource().sendFeedback(new TranslatableText("command.multihotbars.goto_invalid_number", page).formatted(Formatting.RED));
			return 2;
		}

		Util.goToPage(page);
		return 1;
	}
}
