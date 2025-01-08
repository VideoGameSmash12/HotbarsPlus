/*
 * Copyright (c) 2023 Video
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

package me.videogamesm12.hotbarsplus.v1_20_5;

import me.videogamesm12.hotbarsplus.api.event.navigation.HotbarNavigateEvent;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.v1_20_5.manager.CommandManager;
import me.videogamesm12.hotbarsplus.v1_20_5.manager.CustomToastManager;
import me.videogamesm12.hotbarsplus.v1_20_5.manager.KeybindManager;
import me.videogamesm12.hotbarsplus.v1_20_5.mixin.CreativeInvScreenAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;

import java.math.BigInteger;

public class HotbarsPlus implements ClientModInitializer, HotbarNavigateEvent
{
    @Override
    public void onInitializeClient()
    {
        HBPCore.COMMANDS = new CommandManager();
        HBPCore.KEYBINDS = new KeybindManager();
        HBPCore.TOASTS = new CustomToastManager();
        //--
        HBPCore.VHOOKS = new TwentyPointFiveHooks();
        //--
        HotbarNavigateEvent.EVENT.register(this);
    }

    @Override
    public Boolean onNavigate(BigInteger page)
    {
        MinecraftClient client = MinecraftClient.getInstance();
        Screen openScreen = client.currentScreen;

        if (openScreen == null)
        {
            return null;
        }

        // Refreshes the menu if it is currently open
        if (!(openScreen instanceof CreativeInventoryScreen))
        {
            return null;
        }

        CreativeInventoryScreen creativeScreen = (CreativeInventoryScreen) openScreen;
        CreativeInvScreenAccessor accessor = (CreativeInvScreenAccessor) creativeScreen;
        ItemGroup currentTab = accessor.getSelectedTab();

        if (currentTab.getType() != ItemGroup.Type.HOTBAR)
        {
            return null;
        }

        accessor.invokeSetSelectedTab(Registries.ITEM_GROUP.get(ItemGroups.HOTBAR));

        return null;
    }
}
