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

package me.videogamesm12.hotbarsplus.legacy.manager;

import me.videogamesm12.hotbarsplus.api.manager.IKeybindManager;
import me.videogamesm12.hotbarsplus.api.event.keybind.BackupBindPressEvent;
import me.videogamesm12.hotbarsplus.api.event.keybind.NextBindPressEvent;
import me.videogamesm12.hotbarsplus.api.event.keybind.PreviousBindPressEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeybindManager implements IKeybindManager<KeyBinding>, ClientTickEvents.EndTick
{
    public KeyBinding backup = new KeyBinding("key.hotbarsplus.backup", InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN, "category.hotbarsplus.navigation");
    public KeyBinding next = new KeyBinding("key.hotbarsplus.next", InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_BRACKET, "category.hotbarsplus.navigation");
    public KeyBinding previous = new KeyBinding("key.hotbarsplus.previous", InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_BRACKET, "category.hotbarsplus.navigation");

    public KeybindManager()
    {
        registerKeybinds();
    }

    @Override
    public void registerKeybinds()
    {
        backup = KeyBindingHelper.registerKeyBinding(backup);
        next = KeyBindingHelper.registerKeyBinding(next);
        previous = KeyBindingHelper.registerKeyBinding(previous);
        //--
        ClientTickEvents.END_CLIENT_TICK.register(this);
    }

    @Override
    public void onEndTick(MinecraftClient client)
    {
        if (backup.wasPressed())
        {
            BackupBindPressEvent.EVENT.invoker().onBackupPress();
        }

        if (next.wasPressed())
        {
            NextBindPressEvent.EVENT.invoker().onNextPress();
        }
        else if (previous.wasPressed())
        {
            PreviousBindPressEvent.EVENT.invoker().onPreviousPress();
        }
    }
}
