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

package me.videogamesm12.hotbarsplus.ancient;

import com.google.common.eventbus.Subscribe;
import me.videogamesm12.hotbarsplus.ancient.manager.HCommandManager;
import me.videogamesm12.hotbarsplus.ancient.mixin.CreativeInvScreenMixin;
import me.videogamesm12.hotbarsplus.api.event.navigation.NHotbarNavigateEvent;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.ancient.manager.KeybindManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.itemgroup.ItemGroup;

public class HotbarsPlus implements ClientModInitializer
{
    public static HCommandManager COMMANDS = null;

    @Override
    public void onInitializeClient()
    {
        HBPCore.KEYBINDS = new KeybindManager();
        HBPCore.COMMANDS = null; // Pre-Brigadier
        HBPCore.VHOOKS = new ThirteenHooks();
        //--
        COMMANDS = new HCommandManager();
        //--
        HBPCore.EVENTS.register(this);
    }

    @Subscribe
    public void onNavigate(NHotbarNavigateEvent event)
    {
        // Refreshes the menu if it is currently open;
        if (MinecraftClient.getInstance().currentScreen instanceof CreativeInventoryScreen)
        {
            Screen screen = MinecraftClient.getInstance().currentScreen;

            if (((CreativeInvScreenMixin.CISAccessor) screen).getSelectedTab() == ItemGroup.field_15657.getIndex())
            {
                ((CreativeInvScreenMixin.CISAccessor) screen).setSelectedTab(ItemGroup.field_15657);
            }
        }
    }
}