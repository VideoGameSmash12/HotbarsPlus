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

package me.videogamesm12.multihotbar.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

/**
 * HotbarToast - Toast notifications for Hotbars+.
 * @author Video
 */
public class HotbarToast implements Toast
{
    final Identifier id = new Identifier("multihotbar", "textures/gui/toast_background.png");
    final TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
    //
    Type type;
    //
    Visibility toastVisibility;
    long time;
    boolean update;
    //
    Text title;
    Text description;

    public HotbarToast(Type type, Text title, Text description)
    {
        this.title = title;
        this.description = description;
        this.type = type;
        //
        this.toastVisibility = Visibility.SHOW;
        this.update = true;
    }

    /**
     * Hides the toast entirely.
     */
    public void hideToast()
    {
        this.toastVisibility = Visibility.HIDE;
    }

    @Override
    public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime)
    {
        // Resets the 5-second timer if the toast has been updated.
        if (update)
        {
            this.time = startTime;
            update = false;
        }

        // Prepares the background texture to be drawn
        textureManager.bindTexture(id);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);

        // Draws the background
        manager.drawTexture(matrices, 0, 0, 0, 0, this.getWidth(), this.getHeight());

        // Gets the icon offset
        int icon_offset_x = type.getOffsetX();
        int icon_offset_y = type.getOffsetY();

        // Draws the icon
        manager.drawTexture(matrices, 3, 3, 178 + (26 * icon_offset_x), 26 * icon_offset_y, 26, 26);

        // Draws the text
        if (description != null)
        {
            MinecraftClient.getInstance().textRenderer.draw(matrices, title, 30, 7, 0xFFFF00);
            MinecraftClient.getInstance().textRenderer.draw(matrices, description, 30, 18, 0xFFFFFF);
        }
        else
        {
            MinecraftClient.getInstance().textRenderer.draw(matrices, title, 18, 12, 0xFFFF00);
        }

        // Waits 5 seconds before hiding the toast
        if (startTime - this.time > 5000L)
        {
            hideToast();
        }

        return this.toastVisibility;
    }

    /**
     * Changes the text in the toast and updates it.
     * @param title Text
     * @param description Text
     */
    public void change(Text title, @Nullable Text description)
    {
        this.title = title;
        this.description = description;
        this.update = true;
    }

    /**
     * Changes the text and type in the toast and updates it.
     * @param title Text
     * @param description Text
     * @param type Type
     */
    public void change(Text title, @Nullable Text description, Type type)
    {
        this.type = type;
        //
        change(title, description);
    }

    /**
     * Gets the type of message the toast is for.
     * @return Type
     */
    public Type getType()
    {
        return this.type;
    }

    /**
     * Change the type of message the toast is for.
     * @param type Type
     */
    public void setType(Type type)
    {
        this.type = type;
        this.update = true;
    }

    public enum Type
    {
        BACKUP(2, 0),         // Toasts used for backups
        BACKUP_FAILED(0, 1),  // Toasts used for backup failures
        DEFAULT(2, 1),        // Toasts used for unknown reasons
        FAILED(1, 0),         // Toasts used for failures
        SELECTION(0, 0);      // Toasts used for selections

        private int x_offset = 0;
        private int y_offset = 0;

        Type(int x, int y)
        {
            this.x_offset = x;
            this.y_offset = y;
        }

        public int getOffsetX()
        {
            return this.x_offset;
        }

        public int getOffsetY()
        {
            return this.y_offset;
        }
    }
}
