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

package me.videogamesm12.hotbarsplus.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

/**
 * ClothConfiguration - Configuration data for Hotbars+.
 * --
 * @since v1.3-Pre2
 * @author Video
 */
@Config(name = "hotbars+")
public class ClothConfiguration implements ConfigData
{
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public Main main = new Main();

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public Notifications notifs = new Notifications();

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public Migration migration = new Migration();

    public static class Main
    {
        public long page = 0;

        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean useCommands = true;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean warnIfUsingUnsupportedSetup = true;
    }

    public static class Migration
    {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean ignoreLegacyFiles = false;
    }

    public static class Notifications
    {
        public boolean toastNotifications = true;

        public boolean overlayNotifications = true;
    }
}
