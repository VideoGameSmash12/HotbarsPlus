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
import java.util.Date;

/**
 * Util - Miscellaneous methods used by Hotbars+.
 * @author Video
 */
public class Util
{
	private static long page = MultiHotbarClient.configManager.getConfig().main.page;
	public static boolean backupInProgress = false;
	public static Thread thread;
	public static Runnable backupRunnable = new BackupRunnable();
	public static HotbarToast toast;

	/**
	 * Backs up the currently loaded hotbar file.
	 * Does not do anything if a backup is currently ongoing.
	 * @return Boolean
	 */
	public static boolean backupCurrentHotbar()
	{
		// Don't do anything if a backup is currently ongoing.
		if (backupInProgress)
		{
			return false;
		}

		// Prevents any further backups from occurring in the meantime.
		backupInProgress = true;

		// Sends a toast message noting that the backup was in progress.
		showToastMessage(HotbarToast.Type.BACKUP, new TranslatableText("toast.backup.in_progress"), new TranslatableText("toast.backup.file", getHotbarName()), true);

		// Creates a new thread to run the backup on. This prevents lag spikes caused by backing up large hotbar files.
		thread = new Thread(backupRunnable);
		thread.start();

		return true;
	}

	/**
	 * Shows a toast message if the player has toast notifications enabled.
	 * @param type Type
	 * @param newType Type
	 * @param title Text
	 * @param description Text
	 * @param updateExisting Boolean
	 */
	public static void showToastMessage(HotbarToast.Type type, HotbarToast.Type newType, Text title, @Nullable Text description, boolean updateExisting)
	{
		if (MultiHotbarClient.configManager.getConfig().notifs.toastNotifications)
		{
			if (updateExisting && MinecraftClient.getInstance().getToastManager().getToast(HotbarToast.class, type) != null)
			{
				HotbarToast toast = MinecraftClient.getInstance().getToastManager().getToast(HotbarToast.class, type);
				toast.change(title, description, newType);
			}
			else
			{
				toast = new HotbarToast(newType, title, description);
				MinecraftClient.getInstance().getToastManager().add(toast);
			}
		}
	}

	/**
	 * Shows a toast message if the player has toast notifications enabled.
	 * @param type Type
	 * @param title Text
	 * @param description Text
	 * @param updateExisting Boolean
	 */
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

	/**
	 * Shows an overlay message if the player has overlay notifications enabled.
	 * @param text Text
	 */
	public static void showOverlayMessage(Text text)
	{
		if (MultiHotbarClient.configManager.getConfig().notifs.overlayNotifications)
		{
			MinecraftClient.getInstance().inGameHud.setOverlayMessage(text, false);
		}
	}

	/**
	 * Checks whether or not the current hotbar file actually exists and returns the value accordingly.
	 * @return Boolean
	 */
	public static boolean hotbarFileExists()
	{
		HotbarStorage storage = MinecraftClient.getInstance().getCreativeHotbarStorage();
		File file = ((HotbarStorageAccessor) storage).getFile();

		return file.exists();
	}

	/**
	 * Gets the `hotbars` directory and automatically creates it if the directory doesn't already exist..
	 * @return File
	 */
	public static File getStorageFolder()
	{
		File dir = new File(MinecraftClient.getInstance().runDirectory, "hotbars");

		if (!dir.exists() || !dir.isDirectory())
		{
			dir.mkdir();
		}

		return dir;
	}

	/**
	 * Gets the `backups` directory and automatically creates it if the directory doesn't already exist.
	 * @return File
	 */
	public static File getBackupFolder()
	{
		File dir = new File(MinecraftClient.getInstance().runDirectory, "backups");

		if (!dir.exists() || !dir.isDirectory())
		{
			dir.mkdir();
		}

		return dir;
	}

	/**
	 * Gets the folder in which the current hotbar page is located.
	 * If the page is 0, it will be the game's root directory. Otherwise, it will be the `hotbars` directory.
	 * @return File
	 */
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

	/**
	 * Gets the file name of the current hotbar page.
	 * @return Stirng
	 */
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

	/**
	 * Gets the current hotbar page.
	 * @return long
	 */
	public static long getPage()
	{
		return page;
	}

	/**
	 * Sets the current page to the one given.
	 * Does not do anything if the one given is less than 0.
	 * @param newPage long
	 */
	public static void goToPage(long newPage)
	{
		if (newPage < 0)
		{
			return;
		}

		page = newPage;
		refreshHotbar();
	}

	/**
	 * Increase the current hotbar page.
	 * Does not do anything when the maximum of 9,223,372,036,854,775,807 is reached.
	 */
	public static void nextPage()
	{
		if (page == Long.MAX_VALUE)
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

	/**
	 * Go back a hotbar page.
	 * Does not do anything when the page is less than or equal to 0.
	 */
	public static void previousPage()
	{
		if (page > 0)
		{
			page--;
			refreshHotbar();
		}
	}

	/**
	 * Reloads the current hotbar page.
	 * Usually called when changing the currently loaded page.
	 */
	public static void refreshHotbar()
	{
		HotbarStorage storage = new HotbarStorage(MinecraftClient.getInstance().runDirectory, MinecraftClient.getInstance().getDataFixer());
		((MinecraftClientAccessor) MinecraftClient.getInstance()).setCreativeHotbarStorage(storage);
		//
		MultiHotbar.logger.info("Hotbar selected: " + getHotbarName());
		showOverlayMessage(new TranslatableText("overlay.hotbar_selected", getHotbarName()));
		showToastMessage(HotbarToast.Type.SELECTION, new TranslatableText("toast.selected.title"), new LiteralText(getHotbarName()), true);
	}

	public static class BackupRunnable implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				HotbarStorage storage = MinecraftClient.getInstance().getCreativeHotbarStorage();
				File file = ((HotbarStorageAccessor) storage).getFile();
				File copy = new File(getBackupFolder(), String.format("[%s] %s.nbt", new Date().getTime(), file.getName().replace(".nbt", "")));
				//
				// Does the hotbar file even exist?
				if (!hotbarFileExists())
				{
					MultiHotbar.logger.info("Refused to back up hotbar file " + getHotbarName() + " as the file does not exist");
					//
					showToastMessage(HotbarToast.Type.BACKUP, HotbarToast.Type.BACKUP_FAILED, new TranslatableText("toast.backup.failed"), new TranslatableText("toast.backup.empty"), true);
					showOverlayMessage(new TranslatableText("overlay.hotbar_is_empty"));
					//
					backupInProgress = false;
					return;
				}
				//
				FileUtils.copyFile(file, copy);
				//
				MultiHotbar.logger.info("Backed up hotbar file " + getHotbarName() + " to /backups/" + copy.getName());
				//
				showToastMessage(HotbarToast.Type.BACKUP, new TranslatableText("toast.backup.success"), new TranslatableText("toast.backup.saved", getHotbarName()), true);
				showOverlayMessage(new TranslatableText("overlay.hotbar_backup_completed", getHotbarName()));
			}
			catch (Exception ex)
			{
				MultiHotbar.logger.error("An error occurred whilst backing up hotbar file " + getHotbarName());
				//
				showToastMessage(HotbarToast.Type.BACKUP, HotbarToast.Type.BACKUP_FAILED, new TranslatableText("toast.backup.failed"), new TranslatableText("toast.backup.failed_error"), true);
				showOverlayMessage(new TranslatableText("overlay.hotbar_backup_failed"));
				//
				ex.printStackTrace();
			}

			toast = null;
			backupInProgress = false;
		}
	}
}