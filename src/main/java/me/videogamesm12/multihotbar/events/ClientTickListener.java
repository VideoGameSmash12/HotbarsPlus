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

package me.videogamesm12.multihotbar.events;

import me.videogamesm12.multihotbar.client.MultiHotbarClient;
import me.videogamesm12.multihotbar.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

/**
 * ClientTickListener - Listens for client-side tick events to handle binding events.
 * @author Video
 */
@Environment(EnvType.CLIENT)
public class ClientTickListener implements ClientTickEvents.EndTick
{
    @Override
    public void onEndTick(MinecraftClient client)
    {
        // Backups
        if (MultiHotbarClient.backup_binding.wasPressed())
        {
            Util.backupCurrentHotbar();

            // Prevents the binding from doing multiple backups at once when pressing the key. This is a stupid way to
            // do it that probably doesn't even work, but I couldn't find anything that handles when a key has been held.
            MultiHotbarClient.backup_binding.setPressed(false);
        }

        // Next page
        while (MultiHotbarClient.next_binding.wasPressed())
        {
            Util.nextPage();
        }

        // Previous page
        while (MultiHotbarClient.previous_binding.wasPressed())
        {
            Util.previousPage();
        }
    }
}
