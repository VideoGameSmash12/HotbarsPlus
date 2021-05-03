package me.videogamesm12.multihotbar;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.videogamesm12.multihotbar.commands.BackupCommand;
import me.videogamesm12.multihotbar.commands.NextCommand;
import me.videogamesm12.multihotbar.commands.PageCommand;
import me.videogamesm12.multihotbar.commands.PreviousCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;

public class CommandManager
{
	public void register()
	{
		System.out.println("Registering commands");
		ClientCommandManager.DISPATCHER.register(
			ClientCommandManager.literal("hotbars+").then(
				ClientCommandManager.literal("backup").executes(new BackupCommand())
			).then(
				ClientCommandManager.literal("next").executes(new NextCommand())
			).then(
				ClientCommandManager.literal("previous").executes(new PreviousCommand())
			).then(
				ClientCommandManager.literal("goto").then(
					ClientCommandManager.argument("page", IntegerArgumentType.integer()).executes(new PageCommand())
				)
			)
		);
		System.out.println("Commands registered");
	}
}
