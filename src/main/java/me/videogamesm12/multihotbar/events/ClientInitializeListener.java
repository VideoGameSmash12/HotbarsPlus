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

package me.videogamesm12.multihotbar.events;

import me.videogamesm12.multihotbar.MultiHotbar;
import me.videogamesm12.multihotbar.callbacks.ClientInitCallback;
import me.videogamesm12.multihotbar.client.MultiHotbarClient;
import me.videogamesm12.multihotbar.util.HotbarToast;
import me.videogamesm12.multihotbar.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * ClientInitializeListener - Listens for ClientInitCallbacks to display a message if any legacy hotbar files were found
 * in the game's root directory.
 * --
 * @since v1.1
 * @author Video
 */
@Environment(EnvType.CLIENT)
public class ClientInitializeListener implements ClientInitCallback
{
    final Pattern pattern = Pattern.compile("hotbar\\.[0-9]*\\.nbt");
    final FilenameFilter pageFilter = (dir, name) -> pattern.matcher(name).matches();

    /**
     * Checks for non-default hotbar files saved in the root directory and prompts the player to migrate these files if
     *  any are present.
     * --
     * @return ActionResult
     */
    @Override
    public ActionResult onInitialize()
    {
        // Legacy location check
        if (legacyFilesPresent() && !MultiHotbarClient.configManager.getConfig().migration.ignoreLegacyFiles)
        {
            promptMigrateScreen();
        }

        // Everything is good to go.
        return ActionResult.SUCCESS;
    }

    /**
     * Checks the root directory of Minecraft for any legacy hotbar files (from ~1.0 of the mod) are present.
     * @return True if any legacy hotbar files are present
     */
    public boolean legacyFilesPresent()
    {
        boolean present = false;

        if (MinecraftClient.getInstance().runDirectory.listFiles(pageFilter).length != 0)
        {
            present = true;
        }

        return present;
    }

    /**
     * Asks the user to ask if they would like to migrate the hotbar files from the root directory into the new folder.
     * If yes, the file is moved on over.
     */
    public void verifyMigration(boolean yes)
    {
        if (yes)
        {
            migrateHotbars();
        }

        MinecraftClient.getInstance().openScreen(null);
    }

    /**
     * Creates a prompt for the user to choose whether or not to migrate their legacy hotbar files.
     */
    public void promptMigrateScreen()
    {
        Text text = new TranslatableText("migration.legacy_hotbars_were_found");
        Text prompt = new TranslatableText("migration.legacy_hotbars_confirm");
        Text yes = ScreenTexts.YES;
        Text no = ScreenTexts.NO;
        MinecraftClient.getInstance().openScreen(new ConfirmScreen(this::verifyMigration, text, prompt, yes, no));
    }

    /**
     * Migrates any legacy hotbar files the mod finds in the root directory.
     */
    public void migrateHotbars()
    {
        Util.showToastMessage(HotbarToast.Type.SELECTION, new TranslatableText("toast.migration.migrating"), null, false);

        MultiHotbar.logger.info("Migrating hotbars");
        int migrated = 0;

        for (File file : MinecraftClient.getInstance().runDirectory.listFiles(pageFilter))
        {
            File fileInFolder = new File(Util.getStorageFolder(), file.getName());

            if (fileInFolder.exists())
            {
                MultiHotbar.logger.warn("Hotbar already exists - " + file.getName());
                continue;
            }

            try
            {
                FileUtils.moveFileToDirectory(file, Util.getStorageFolder(), true);
                migrated++;
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        Util.showToastMessage(HotbarToast.Type.SELECTION, new TranslatableText("toast.migration.completed"), new TranslatableText("toast.migration.count", migrated), true);

        MultiHotbar.logger.info("Migration completed");
    }
}
