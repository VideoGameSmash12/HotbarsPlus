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

package me.videogamesm12.hotbarsplus.v1_17;

import me.videogamesm12.hotbarsplus.api.event.navigation.HotbarNavigateEvent;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.v1_17.manager.CommandManager;
import me.videogamesm12.hotbarsplus.v1_17.manager.KeybindManager;
import me.videogamesm12.hotbarsplus.v1_17.mixin.CreativeInvScreenMixin;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ActionResult;

import java.math.BigInteger;

public class HotbarsPlus implements ClientModInitializer, HotbarNavigateEvent
{
    @Override
    public void onInitializeClient()
    {
        HBPCore.KEYBINDS = new KeybindManager();
        HBPCore.COMMANDS = new CommandManager();
        //--
        HotbarNavigateEvent.EVENT.register(this);
    }

    @Override
    public ActionResult onNavigate(BigInteger page)
    {
        // Refreshes the menu if it is currently open;
        if (MinecraftClient.getInstance().currentScreen instanceof CreativeInventoryScreen)
        {
            Screen screen = MinecraftClient.getInstance().currentScreen;

            if (((CreativeInventoryScreen) screen).getSelectedTab() == ItemGroup.HOTBAR.getIndex())
            {
                ((CreativeInvScreenMixin.CISAccessor) screen).setSelectedTab(ItemGroup.HOTBAR);
            }
        }

        return ActionResult.PASS;
    }
}
