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

package me.videogamesm12.hotbarsplus.core.notifications;

import me.videogamesm12.hotbarsplus.core.universal.NotificationManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class ActionBarNotification implements NotificationManager.NotificationRoute
{
    @Override
    public void display(NotificationManager.NotificationType type, Text... text)
    {
        if (MinecraftClient.getInstance().player == null)
        {
            return;
        }

        Text txt;

        // Use the miniature component.
        if (text.length > 2)
        {
            txt = text[2];
        }
        // Fallback, use the title instead
        else if (text.length > 0)
        {
            txt = text[0];
        }
        // If there isn't anything to display, don't even bother;
        else
        {
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(txt, true);
    }

    @Override
    public @NotNull Identifier getId()
    {
        return new Identifier("hotbarsplus", "actionbar");
    }
}
