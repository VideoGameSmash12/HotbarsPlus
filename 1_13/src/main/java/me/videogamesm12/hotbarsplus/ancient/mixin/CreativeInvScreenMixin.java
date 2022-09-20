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

package me.videogamesm12.hotbarsplus.ancient.mixin;

import me.videogamesm12.hotbarsplus.ancient.gui.CustomButtons;
import me.videogamesm12.hotbarsplus.api.event.keybind.NBindPressEvent;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.ancient.manager.KeybindManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.itemgroup.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInvScreenMixin extends Screen implements CSAccessor
{
    public CustomButtons.NextButton next;
    public CustomButtons.BackupButton backup;
    public CustomButtons.PreviousButton previous;

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
        if (((CISAccessor) this).getSelectedTab() != ItemGroup.field_15657.getIndex())
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
            if (group == ItemGroup.field_15657)
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
    public void injectKeyPressed(char character, int code, CallbackInfo ci)
    {
        if (((CISAccessor) this).getSelectedTab() == ItemGroup.field_15657.getIndex())
        {
            KeybindManager manager = (KeybindManager) HBPCore.KEYBINDS;

            if (manager.next.getCode() == code)
            {
                HBPCore.EVENTS.post(new NBindPressEvent(NBindPressEvent.Bind.NEXT));
                ci.cancel();
            }
            else if (manager.backup.getCode() == code)
            {
                HBPCore.EVENTS.post(new NBindPressEvent(NBindPressEvent.Bind.BACKUP));
                ci.cancel();
            }
            else if (manager.previous.getCode() == code)
            {
                HBPCore.EVENTS.post(new NBindPressEvent(NBindPressEvent.Bind.PREVIOUS));
                ci.cancel();
            }
        }
    }

    @Inject(method = "buttonClicked", at = @At("TAIL"))
    public void injectButtonClicked(ButtonWidget button, CallbackInfo ci)
    {
        switch (button.id)
        {
            case 976:
            {
                HBPCore.UBL.backupHotbar();
                break;
            }
            case 977:
            {
                HBPCore.UPL.incrementPage();
                break;
            }
            case 978:
            {
                HBPCore.UPL.decrementPage();
                break;
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
