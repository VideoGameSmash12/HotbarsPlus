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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

/**
 * HotbarToast - Toast notifications for Hotbars+.
 * @author Video
 */
public class HotbarToast implements Toast
{
    final Identifier id = TEXTURE;
    final TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
    //
    final Type type;
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

    public HotbarToast(Text title, Text description)
    {
        this(Type.MISC, title, description);
    }

    public void hideToast()
    {
        this.toastVisibility = Visibility.HIDE;
    }

    @Override
    public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime)
    {
        if (update)
        {
            this.time = startTime;
            update = false;
        }

        // Prepares the texture to be drawn
        textureManager.bindTexture(id);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);

        // Draws the textures
        manager.drawTexture(matrices, 0, 0, 0, 64, this.getWidth(), this.getHeight());

        // Draws the text
        if (description != null)
        {
            MinecraftClient.getInstance().textRenderer.draw(matrices, title, 18, 7, 0xFFFF00);
            MinecraftClient.getInstance().textRenderer.draw(matrices, description, 18, 18, 0xFFFFFF);
        }
        else
        {
            MinecraftClient.getInstance().textRenderer.draw(matrices, title, 18, 12, 0xFFFF00);
        }

        if (startTime - this.time > 5000L)
        {
            hideToast();
        }

        return this.toastVisibility;
    }

    public void change(Text title, @Nullable Text description)
    {
        this.title = title;
        this.description = description;
        this.update = true;
    }

    public Type getType()
    {
        return this.type;
    }

    public enum Type
    {
        BACKUP,     // Toasts used for backups
        MIGRATION,  // Toasts used for migrations
        MISC,       // Toasts used for miscellaneous things
        SELECTION   // Toasts used for selections
    }
}
