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

package me.videogamesm12.hotbarsplus.client;

import me.videogamesm12.hotbarsplus.CommandManager;
import me.videogamesm12.hotbarsplus.ConfigurationManager;
import me.videogamesm12.hotbarsplus.HotbarsPlus;
import me.videogamesm12.hotbarsplus.callbacks.ClientInitCallback;
import me.videogamesm12.hotbarsplus.callbacks.HotbarLoadFailCallback;
import me.videogamesm12.hotbarsplus.callbacks.HotbarSaveFailCallback;
import me.videogamesm12.hotbarsplus.events.ClientInitializeListener;
import me.videogamesm12.hotbarsplus.events.ClientTickListener;
import me.videogamesm12.hotbarsplus.events.HotbarFailListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.HotbarStorage;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

/**
 * HotbarsPlusClient - The client-side main class for Hotbars+.
 * --
 * @author Video
 */
@Environment(EnvType.CLIENT)
public class HotbarsPlusClient implements ClientModInitializer
{
    public static final CommandManager commandManager = new CommandManager();
    public static final ConfigurationManager configManager = new ConfigurationManager();
    //
    public static KeyBinding backup_binding;
    public static KeyBinding next_binding;
    public static KeyBinding previous_binding;
    //
    public static HotbarStorage hotbarStorage;

    @Override
    public void onInitializeClient()
    {
        hotbarStorage = new HotbarStorage(MinecraftClient.getInstance().runDirectory, MinecraftClient.getInstance().getDataFixer());
        //
        backup_binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hotbarsplus.backup",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "category.hotbarsplus.navigation"
        ));

        next_binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hotbarsplus.next",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                "category.hotbarsplus.navigation"
        ));

        previous_binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hotbarsplus.previous",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                "category.hotbarsplus.navigation"
        ));

        if (configManager.getConfig().main.useCommands)
        {
            HotbarsPlus.logger.info("Registering commands");
            try
            {
                commandManager.register();
            }
            catch (Exception ex)
            {
                HotbarsPlus.logger.error("Failed to register commands");
                HotbarsPlus.logger.error(ex);
            }
            finally
            {
                HotbarsPlus.logger.info("Commands registered");
            }
        }

        HotbarsPlus.logger.info("Initializing listeners");
        try
        {
            initListeners();
        }
        catch (Exception ex)
        {
            HotbarsPlus.logger.error("Failed to initialize listeners");
            HotbarsPlus.logger.error(ex);
        }
        finally
        {
            HotbarsPlus.logger.info("Listeners initialized");
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
            HotbarsPlus.logger.warn("****** ATTENTION *******");
            HotbarsPlus.logger.warn("We have detected that you are using More Toolbars in addition to Hotbars+. "
                    + "This setup is not recommended as the mods could conflict and cause unexpected behavior.");
            HotbarsPlus.logger.warn("It is strongly recommended that you make a backup of your hotbar files before "
                    + "proceeding in the event they get corrupted.");
            HotbarsPlus.logger.warn("Messages like this can be disabled in the configuration under the "
                    + "\"Warn About Unsupported Setups\" option.");
            HotbarsPlus.logger.warn("************************");
        }
    }
}