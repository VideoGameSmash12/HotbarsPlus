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

import me.videogamesm12.multihotbar.mixin.accessors.HotbarStorageAccessor;
import me.videogamesm12.multihotbar.mixin.accessors.MinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.HotbarStorage;
import net.minecraft.text.TranslatableText;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Date;

/**
 * Util - Miscellaneous methods used by Hotbars+.
 * @author Video
 */
public class Util
{
	private static int page = 0;
	public static boolean backupInProgress = false;

	public static boolean backupCurrentHotbar()
	{
		if (backupInProgress)
		{
			return false;
		}

		backupInProgress = true;

		Thread thread = new Thread(() ->
		{
			try
			{
				HotbarStorage storage = MinecraftClient.getInstance().getCreativeHotbarStorage();
				File file = ((HotbarStorageAccessor) storage).getFile();
				File copy = new File(getBackupFolder(), String.format("[%s] %s.nbt", new Date().getTime(), file.getName().replace(".nbt", "")));
				//
				if (!hotbarFileExists())
				{
					MinecraftClient.getInstance().inGameHud.setOverlayMessage(new TranslatableText("overlay.hotbar_is_empty"), false);
					return;
				}
				//
				FileUtils.copyFile(file, copy);
				//
				MinecraftClient.getInstance().inGameHud.setOverlayMessage(new TranslatableText("overlay.hotbar_backup_completed", getHotbarName()), false);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}

			backupInProgress = false;
		});

		thread.start();

		return true;
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
		MinecraftClient.getInstance().inGameHud.setOverlayMessage(new TranslatableText("overlay.hotbar_selected", getHotbarName()), false);
	}
}
