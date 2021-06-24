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

package me.videogamesm12.multihotbar.mixin.injectors;

import me.videogamesm12.multihotbar.mixin.accessors.CreativeInventoryScreenAccessor;
import me.videogamesm12.multihotbar.mixin.accessors.HandledScreenAccessor;
import me.videogamesm12.multihotbar.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * CreativeInventoryScreenInjector - Adds a few buttons to the CreativeInventoryScreen.
 * @author Video
 */
@Environment(EnvType.CLIENT)
@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenInjector extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> implements CreativeInventoryScreenAccessor, HandledScreenAccessor
{
    public ButtonWidget backupButton;
    public ButtonWidget nextButton;
    public ButtonWidget prevButton;

    public CreativeInventoryScreenInjector(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text)
    {
        super(screenHandler, playerInventory, text);
    }

    /**
     * Adds the hotbar buttons to the menu.
     * @param ci CallbackInfo
     */
    @Inject(method = "init", at = @At("RETURN"))
    public void injectInit(CallbackInfo ci)
    {
        int x = this.getX() + 159;
        int y = this.getY() + 4;
        //
        prevButton = new ButtonWidget(x - 16, y, 16, 12, new LiteralText("←"), (buttonWidget) ->
        {
            Util.previousPage();
            invokeSetSelectedTab(ItemGroup.HOTBAR);
        });
        //
        // TODO: Figure out how to get custom text fonts to display correctly and replace this button's icon with a
        //  floppy disk (namely, this one: 💾) so that the button icon is more accurate
        backupButton = new ButtonWidget(x, y, 16, 12, new LiteralText("✍"), (buttonWidget) ->
        {
            if (!Util.backupInProgress)
            {
                Util.backupCurrentHotbar();
            }
        });
        nextButton = new ButtonWidget(x + 16, y, 16, 12, new LiteralText("→"), (buttonWidget) ->
        {
            Util.nextPage();
            invokeSetSelectedTab(ItemGroup.HOTBAR);
        });
        //
        prevButton.visible = false;
        backupButton.visible = false;
        nextButton.visible = false;
        //
        addButton(prevButton);
        addButton(backupButton);
        addButton(nextButton);
    }

    /**
     * Rendering Injection Part 1 - Displays, hides, and disables the hotbar buttons depending on certain conditions.
     * @param matrices MatrixStack
     * @param mouseX int
     * @param mouseY int
     * @param delta float
     * @param ci CallbackInfo
     */
    @Inject(method = "render", at = @At("HEAD"))
    public void injectRenderHead(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        if (this.getSelectedTab() == ItemGroup.HOTBAR.getIndex())
        {
            prevButton.active = Util.getPage() > 0;
            backupButton.active = Util.hotbarFileExists() && !Util.backupInProgress;
            nextButton.active = Util.getPage() != Long.MAX_VALUE;
            //
            prevButton.visible = true;
            backupButton.visible = true;
            nextButton.visible = true;
        }
        else
        {
            prevButton.visible = false;
            backupButton.visible = false;
            nextButton.visible = false;
        }
    }

    /**
     * Rendering Injection Part 2 - Calls `renderButtonToolTips` to render the tooltips for the hotbar buttons.
     * @param matrices MatrixStack
     * @param mouseX int
     * @param mouseY int
     * @param delta float
     * @param ci CallbackInfo
     */
    @Inject(method = "render", at = @At("RETURN"))
    public void injectRenderReturn(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        renderButtonToolTips(matrices, mouseX, mouseY);
    }

    /**
     * Renders the button tooltips.
     * @param matrices MatrixStack
     * @param mouseX int
     * @param mouseY int
     */
    public void renderButtonToolTips(MatrixStack matrices, int mouseX, int mouseY)
    {
        if (!(this.getSelectedTab() == ItemGroup.HOTBAR.getIndex()))
        {
            return;
        }

        if (prevButton.isMouseOver(mouseX, mouseY))
        {
            this.renderTooltip(matrices, new TranslatableText("tooltip.previous_page_button"), mouseX, mouseY);
        }
        else if (backupButton.isMouseOver(mouseX, mouseY))
        {
            if (!Util.hotbarFileExists())
            {
                this.renderTooltip(matrices, new TranslatableText("tooltip.hotbar_is_empty").formatted(Formatting.RED), mouseX, mouseY);
            }
            else if (Util.backupInProgress)
            {
                this.renderTooltip(matrices, new TranslatableText("tooltip.backup_in_progress").formatted(Formatting.RED), mouseX, mouseY);
            }
            else
            {
                this.renderTooltip(matrices, new TranslatableText("tooltip.backup_hotbar_button"), mouseX, mouseY);
            }
        }
        else if (nextButton.isMouseOver(mouseX, mouseY))
        {
            this.renderTooltip(matrices, new TranslatableText("tooltip.next_page_button"), mouseX, mouseY);
        }
    }
}
