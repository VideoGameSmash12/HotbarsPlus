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

package me.videogamesm12.multihotbar.util;

import me.videogamesm12.multihotbar.MultiHotbar;
import me.videogamesm12.multihotbar.client.MultiHotbarClient;
import me.videogamesm12.multihotbar.mixin.accessors.HotbarStorageAccessor;
import me.videogamesm12.multihotbar.mixin.accessors.MinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.HotbarStorage;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Util - Miscellaneous methods used by Hotbars+.
 * @author Video
 */
public class Util
{
	private static int page = MultiHotbarClient.configManager.getConfig().main.page;
	public static boolean backupInProgress = false;
	public static Thread thread;
	public static HotbarToast toast;

	public static boolean backupCurrentHotbar()
	{
		if (backupInProgress)
		{
			return false;
		}

		backupInProgress = true;

		showToastMessage(HotbarToast.Type.BACKUP, new TranslatableText("toast.backup.in_progress"), new TranslatableText("toast.backup.file", getHotbarName()), true);

		thread = new Thread(() ->
		{
			try
			{
				HotbarStorage storage = MinecraftClient.getInstance().getCreativeHotbarStorage();
				File file = ((HotbarStorageAccessor) storage).getFile();
				File copy = new File(getBackupFolder(), String.format("[%s] %s.nbt", new Date().getTime(), file.getName().replace(".nbt", "")));
				//
				if (!hotbarFileExists())
				{
					showToastMessage(HotbarToast.Type.BACKUP, new TranslatableText("toast.backup.failed"), new TranslatableText("toast.backup.empty"), true);
					showOverlayMessage(new TranslatableText("overlay.hotbar_is_empty"));
					backupInProgress = false;
					return;
				}
				//
				FileUtils.copyFile(file, copy);
				//
				showToastMessage(HotbarToast.Type.BACKUP, new TranslatableText("toast.backup.success"), new TranslatableText("toast.backup.saved", getHotbarName()), true);
				showOverlayMessage(new TranslatableText("overlay.hotbar_backup_completed", getHotbarName()));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}

			toast = null;
			backupInProgress = false;
		});

		thread.start();

		return true;
	}

	public static void showToastMessage(HotbarToast.Type type, Text title, @Nullable Text description, boolean updateExisting)
	{
		if (MultiHotbarClient.configManager.getConfig().notifs.toastNotifications)
		{
			if (updateExisting && MinecraftClient.getInstance().getToastManager().getToast(HotbarToast.class, type) != null)
			{
				HotbarToast toast = MinecraftClient.getInstance().getToastManager().getToast(HotbarToast.class, type);
				toast.change(title, description);
			}
			else
			{
				toast = new HotbarToast(type, title, description);
				MinecraftClient.getInstance().getToastManager().add(toast);
			}
		}
	}

	public static void showOverlayMessage(Text text)
	{
		if (MultiHotbarClient.configManager.getConfig().notifs.overlayNotifications)
		{
			MinecraftClient.getInstance().inGameHud.setOverlayMessage(text, false);
		}
	}

	public static boolean hotbarFileExists()
	{
		HotbarStorage storage = MinecraftClient.getInstance().getCreativeHotbarStorage();
		File file = ((HotbarStorageAccessor) storage).getFile();

		return file.exists();
	}

	public static File getStorageFolder()
	{
		File dir = new File(MinecraftClient.getInstance().runDirectory, "hotbars");

		if (!dir.exists() || !dir.isDirectory())
		{
			dir.mkdir();
		}

		return dir;
	}

	public static File getBackupFolder()
	{
		File dir = new File(MinecraftClient.getInstance().runDirectory, "backups");

		if (!dir.exists() || !dir.isDirectory())
		{
			dir.mkdir();
		}

		return dir;
	}

	public static File getHotbarFolder()
	{
		if (page != 0)
		{
			return getStorageFolder();
		}
		else
		{
			return MinecraftClient.getInstance().runDirectory;
		}
	}

	public static String getHotbarName()
	{
		if (page > 0)
		{
			return "hotbar." + page + ".nbt";
		}
		else
		{
			return "hotbar.nbt";
		}
	}

	public static int getPage()
	{
		return page;
	}

	public static void goToPage(int newPage)
	{
		if (newPage < 0)
		{
			return;
		}

		page = newPage;
		refreshHotbar();
	}

	public static void nextPage()
	{
		if (page == 2147483647)
		{
			return;
		}

		if (page < 0)
		{
			page = 0;
		}
		else
		{
			page++;
		}

		refreshHotbar();
	}

	public static void previousPage()
	{
		if (page > 0)
		{
			page--;
			refreshHotbar();
		}
	}

	public static void refreshHotbar()
	{
		HotbarStorage storage = new HotbarStorage(MinecraftClient.getInstance().runDirectory, MinecraftClient.getInstance().getDataFixer());
		((MinecraftClientAccessor) MinecraftClient.getInstance()).setCreativeHotbarStorage(storage);
		//
		MultiHotbar.logger.info("Hotbar selected: " + getHotbarName());
		showOverlayMessage(new TranslatableText("overlay.hotbar_selected", getHotbarName()));
		showToastMessage(HotbarToast.Type.SELECTION, new TranslatableText("toast.selected.title"), new LiteralText(getHotbarName()), true);
	}
}