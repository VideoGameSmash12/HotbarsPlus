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
import me.videogamesm12.multihotbar.ConfigurationManager;
import me.videogamesm12.multihotbar.MultiHotbar;
import me.videogamesm12.multihotbar.callbacks.ClientInitCallback;
import me.videogamesm12.multihotbar.callbacks.HotbarLoadFailCallback;
import me.videogamesm12.multihotbar.callbacks.HotbarSaveFailCallback;
import me.videogamesm12.multihotbar.events.ClientInitializeListener;
import me.videogamesm12.multihotbar.events.ClientTickListener;
import me.videogamesm12.multihotbar.events.HotbarFailListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
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
    public static final ConfigurationManager configManager = new ConfigurationManager();
    //
    public static KeyBinding backup_binding;
    public static KeyBinding next_binding;
    public static KeyBinding previous_binding;

    @Override
    public void onInitializeClient()
    {
        backup_binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.multihotbar.backup",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "category.multihotbar.navigation"
        ));

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

        if (configManager.getConfig().main.useCommands)
        {
            MultiHotbar.logger.info("Registering commands");
            try
            {
                commandManager.register();
            }
            catch (Exception ex)
            {
                MultiHotbar.logger.error("Failed to register commands");
                MultiHotbar.logger.error(ex);
            }
            finally
            {
                MultiHotbar.logger.info("Commands registered");
            }
        }

        MultiHotbar.logger.info("Initializing listeners");
        try
        {
            initListeners();
        }
        catch (Exception ex)
        {
            MultiHotbar.logger.error("Failed to initialize listeners");
            MultiHotbar.logger.error(ex);
        }
        finally
        {
            MultiHotbar.logger.info("Listeners initialized");
        }

        if (configManager.getConfig().main.warnIfUsingUnsupportedSetup)
        {
            warnIfUsingUnsupportedSetup();
        }
    }

    /**
     * Initializes the event listeners.
     */
    public void initListeners()
    {
        ClientInitCallback.EVENT.register(new ClientInitializeListener());
        ClientTickEvents.END_CLIENT_TICK.register(new ClientTickListener());
        HotbarLoadFailCallback.EVENT.register(new HotbarFailListener());
        HotbarSaveFailCallback.EVENT.register(new HotbarFailListener());
    }

    /**
     * Checks for anything that would be considered an "unsupported" setup and warn the user in the logs accordingly.
     */
    public void warnIfUsingUnsupportedSetup()
    {
        /* While More Toolbars does technically work with Hotbars+, there have been reports of conflicts between the two
           causing unexpected and unwanted behavior. */
        if (FabricLoader.getInstance().isModLoaded("moretoolbars"))
        {
            MultiHotbar.logger.warn("****** ATTENTION *******");
            MultiHotbar.logger.warn("We have detected that you are using More Toolbars in addition to Hotbars+. "
                    + "This setup is not recommended as the mods could conflict and cause unexpected behavior.");
            MultiHotbar.logger.warn("It is strongly recommended that you make a backup of your hotbar files before "
                    + "proceeding in the event they get corrupted.");
            MultiHotbar.logger.warn("Messages like this can be disabled in the configuration under the "
                    + "\"Warn About Unsupported Setups\" option.");
            MultiHotbar.logger.warn("************************");
        }
    }
}