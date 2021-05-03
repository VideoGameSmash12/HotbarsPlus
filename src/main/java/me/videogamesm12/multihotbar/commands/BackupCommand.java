package me.videogamesm12.multihotbar.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.videogamesm12.multihotbar.util.Util;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

/**
 * BackupCommand - Subcommand for making a backup of the current hotbar page.
 * @author Video
 */
public class BackupCommand implements Command<FabricClientCommandSource>
{
	@Override
	public int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException
	{
		// Checks to see if the hotbar file itself exists before attempting to make a backup.
		// This fixes the NPE caused when trying to make a backup of a file that does not exist.
		if (!Util.hotbarFileExists())
		{
			context.getSource().sendFeedback(new TranslatableText("command.multihotbars.cant_backup_empty_hotbar_pages").formatted(Formatting.RED));
			return 2;
		}

		// Runs the backup and checks to see if the backup was a success, then notifies the player about it.
		if (Util.backupCurrentHotbar())
		{
			context.getSource().sendFeedback(new TranslatableText("command.multihotbars.backup_success", Util.getHotbarName()).formatted(Formatting.GREEN));
			return 1;
		}
		else
		{
			context.getSource().sendFeedback(new TranslatableText("command.multihotbars.backup_failed", Util.getHotbarName()).formatted(Formatting.RED));
			return 2;
		}
	}
}
