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

package me.videogamesm12.multihotbar.client;

import me.videogamesm12.multihotbar.CommandManager;
import me.videogamesm12.multihotbar.util.Util;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

/**
 * MultiHotbarClient - The client-side main class for Hotbars+.
 * @author Video
 */
@Environment(EnvType.CLIENT)
public class MultiHotbarClient implements ClientModInitializer
{
    public static final CommandManager commandManager = new CommandManager();
    //
    private static KeyBinding next_binding;
    private static KeyBinding previous_binding;

    @Override
    public void onInitializeClient()
    {
        next_binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.multihotbar.next",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                "category.multihotbar.navigation"
        ));

        previous_binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.multihotbar.previous",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                "category.multihotbar.navigation"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            while (next_binding.wasPressed())
            {
                Util.nextPage();
                //client.player.sendMessage(new LiteralText("next page " + MultiHotbar.getPage()), false);
            }

            while (previous_binding.wasPressed())
            {
                Util.previousPage();
                //client.player.sendMessage(new LiteralText("previous page " + MultiHotbar.getPage()), false);
            }
        });

        commandManager.register();
    }
}
