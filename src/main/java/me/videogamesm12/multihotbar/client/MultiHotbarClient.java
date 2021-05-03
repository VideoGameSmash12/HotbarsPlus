package me.videogamesm12.multihotbar.client;

import me.videogamesm12.multihotbar.CommandManager;
import me.videogamesm12.multihotbar.MultiHotbar;
import me.videogamesm12.multihotbar.util.Util;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

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
