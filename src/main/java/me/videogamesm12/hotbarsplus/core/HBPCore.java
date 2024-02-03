/*
 * Copyright (c) 2022 Video
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

package me.videogamesm12.hotbarsplus.core;

import me.videogamesm12.hotbarsplus.api.manager.ICommandManager;
import me.videogamesm12.hotbarsplus.api.manager.IHotbarConstructor;
import me.videogamesm12.hotbarsplus.api.manager.IKeybindManager;
import me.videogamesm12.hotbarsplus.api.IVersionHook;
import me.videogamesm12.hotbarsplus.api.manager.IToastManager;
import me.videogamesm12.hotbarsplus.core.notifications.ActionBarNotification;
import me.videogamesm12.hotbarsplus.core.notifications.ToastNotification;
import me.videogamesm12.hotbarsplus.core.universal.BackupManager;
import me.videogamesm12.hotbarsplus.core.universal.ConfigurationManager;
import me.videogamesm12.hotbarsplus.core.universal.NotificationManager;
import me.videogamesm12.hotbarsplus.core.universal.PageManager;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <b>HBPCore</b>
 * <p>Hotbars+'s surface-level mod initializer. Handles anything that can be done universally.</p>
 */
public class HBPCore implements ClientModInitializer
{
    // UTILITIES
    public static Logger LOGGER = LogManager.getLogger("Hotbars+");

    // VERSION SPECIFIC
    public static IKeybindManager<?> KEYBINDS = null;
    public static ICommandManager<?> COMMANDS = null;
    public static IToastManager TOASTS = null;
    public static IVersionHook VHOOKS = null;
    public static IHotbarConstructor CONSTRUCTOR = null;

    // UNIVERSAL
    public static PageManager UPL = new PageManager();
    public static BackupManager UBL = new BackupManager();
    public static ConfigurationManager UCL = new ConfigurationManager();
    public static NotificationManager UNL = new NotificationManager();

    @Override
    public void onInitializeClient()
    {
        // LAST HOTBAR PAGE TRACKING
        //------------------------------------------------------------------------
        if (UCL.getConfig().getLastHotbarPage().isEnabled())
            UPL.goToPageQuietly(UCL.getConfig().getLastHotbarPage().getPage());
    }
}