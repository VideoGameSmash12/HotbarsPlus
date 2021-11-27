/*
 * Copyright (c) 2021 Video
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.videogamesm12.hotbarsplus.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.videogamesm12.hotbarsplus.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

/**
 * BackupCommand - Subcommand for making a backup of the current hotbar page.
 * --
 * @author Video
 */
@Environment(EnvType.CLIENT)
public class BackupCommand implements Command<FabricClientCommandSource>
{
	@Override
	public int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException
	{
		// Checks to see if the hotbar file itself exists before attempting to make a backup.
		// This fixes the NPE caused when trying to make a backup of a file that does not exist.
		if (!Util.hotbarFileExists())
		{
			context.getSource().sendError(new TranslatableText("command.hotbarsplus.cant_backup_empty_hotbar_pages"));
			return 2;
		}

		// Checks if a backup is already in progress beforehand.
		if (Util.backupInProgress)
		{
			context.getSource().sendError(new TranslatableText("command.hotbarsplus.backup_in_progress"));
			return 2;
		}

		// Runs the backup and checks to see if the backup was a success, then notifies the player about it.
		if (Util.backupCurrentHotbar())
		{
			context.getSource().sendFeedback(new TranslatableText("command.hotbarsplus.backup_success", Util.getHotbarName()).formatted(Formatting.GREEN));
			return 1;
		}
		else
		{
			context.getSource().sendFeedback(new TranslatableText("command.hotbarsplus.backup_failed", Util.getHotbarName()).formatted(Formatting.RED));
			return 2;
		}
	}
}
