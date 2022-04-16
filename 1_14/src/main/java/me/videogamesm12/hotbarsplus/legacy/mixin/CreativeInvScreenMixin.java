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

package me.videogamesm12.hotbarsplus.legacy.mixin;

import me.videogamesm12.hotbarsplus.api.event.keybind.BackupBindPressEvent;
import me.videogamesm12.hotbarsplus.api.event.keybind.NextBindPressEvent;
import me.videogamesm12.hotbarsplus.api.event.keybind.PreviousBindPressEvent;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.legacy.gui.CustomButtons;
import me.videogamesm12.hotbarsplus.legacy.manager.KeybindManager;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInvScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeContainer>
        implements CSAccessor
{
    public CustomButtons.NextButton next;
    public CustomButtons.BackupButton backup;
    public CustomButtons.PreviousButton previous;

    @Shadow private boolean field_2888;

    public CreativeInvScreenMixin(CreativeInventoryScreen.CreativeContainer container, PlayerInventory playerInventory, Text text)
    {
        super(container, playerInventory, text);
    }

    @Inject(method = "init", at = @At("RETURN"))
    public void injInit(CallbackInfo ci)
    {
        // Offset
        int x = this.getX() + 159;
        int y = this.getY() + 4;

        // Initialize buttons
        this.next = new CustomButtons.NextButton(x + 16, y);
        this.backup = new CustomButtons.BackupButton(x, y);
        this.previous = new CustomButtons.PreviousButton(x - 16, y);

        // Modify buttons to adjust for the currently selected tab
        if (((CISAccessor) this).getSelectedTab() != ItemGroup.HOTBAR.getIndex())
        {
            next.visible = false;
            backup.visible = false;
            previous.visible = false;
        }

        // Regardless of the current tab, disable this button if the selected hotbar file doesn't exist
        backup.active = HBPCore.UPL.hotbarPageExists();

        // Adding buttons
        addButton(next);
        addButton(backup);
        addButton(previous);
    }

    @Inject(method = "setSelectedTab", at = @At("HEAD"))
    public void injSetCreativeTab(ItemGroup group, CallbackInfo ci)
    {
        if (next != null && backup != null && previous != null)
        {
            if (group == ItemGroup.HOTBAR)
            {
                next.visible = true;
                backup.visible = true;
                previous.visible = true;
            }
            else
            {
                next.visible = false;
                backup.visible = false;
                previous.visible = false;
            }

            backup.active = HBPCore.UPL.hotbarPageExists();
        }
    }

    @Inject(method = "keyPressed", at = @At(value = "HEAD", shift = At.Shift.AFTER), cancellable = true)
    public void injectKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir)
    {
        if (((CISAccessor) this).getSelectedTab() == ItemGroup.HOTBAR.getIndex())
        {
            KeybindManager manager = (KeybindManager) HBPCore.KEYBINDS;

            if (manager.next.matchesKey(keyCode, scanCode))
            {
                NextBindPressEvent.EVENT.invoker().onNextPress();
                field_2888 = true;
                cir.setReturnValue(true);
            }
            else if (manager.backup.matchesKey(keyCode, scanCode))
            {
                BackupBindPressEvent.EVENT.invoker().onBackupPress();
                field_2888 = true;
                cir.setReturnValue(true);
            }
            else if (manager.previous.matchesKey(keyCode, scanCode))
            {
                PreviousBindPressEvent.EVENT.invoker().onPreviousPress();
                field_2888 = true;
                cir.setReturnValue(true);
            }
            else if (manager.save2nearest.matchesKey(keyCode, scanCode))
            {
                HBPCore.UPL.saveToNearestRow();
                field_2888 = true;
                cir.setReturnValue(true);
            }
        }
    }

    @Mixin(CreativeInventoryScreen.class)
    public interface CISAccessor
    {
        @Invoker("setSelectedTab")
        void setSelectedTab(ItemGroup group);

        @Accessor
        int getSelectedTab();
    }
}
