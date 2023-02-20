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

package me.videogamesm12.hotbarsplus.v1_19.gui;

import me.videogamesm12.hotbarsplus.api.util.Util;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CustomButtons
{
    public static class BackupButton extends ButtonWidget
    {
        private static final Text label = Util.advToNative(Component.text("\uD83D\uDCBE").style(Style.style().font(
                Key.key("hotbarsplus", "default")).build()));

        public BackupButton(int x, int y)
        {
            super(x, y, 16, 12, label,
                    button -> HBPCore.UBL.backupHotbar(),
                    supplier -> Util.advToNative(Component.translatable("gui.hotbarsplus.cis.backup_button.tooltip")).copy());
        }
    }

    public static class NextButton extends ButtonWidget
    {
        public NextButton(int x, int y)
        {
            super(x, y, 16, 12, Util.advToNative(Component.text("→")),
                    button -> HBPCore.UPL.incrementPage(),
                    supplier -> Util.advToNative(Component.translatable("gui.hotbarsplus.cis.next_button.tooltip")).copy());
        }
    }

    public static class PreviousButton extends ButtonWidget
    {
        public PreviousButton(int x, int y)
        {
            super(x, y, 16, 12, Util.advToNative(Component.text("←")),
                    button -> HBPCore.UPL.decrementPage(),
                    supplier -> Util.advToNative(Component.translatable("gui.hotbarsplus.cis.previous_button.tooltip")).copy());
        }
    }
}
