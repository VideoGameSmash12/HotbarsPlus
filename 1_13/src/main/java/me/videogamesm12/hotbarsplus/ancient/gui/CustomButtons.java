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

package me.videogamesm12.hotbarsplus.ancient.gui;

import me.videogamesm12.hotbarsplus.core.HBPCore;
import net.minecraft.client.gui.widget.ButtonWidget;

/**
 * <b>CustomButtons</b>
 * <p>Custom buttons for the mod specific to 1.14.x.</p>
 * --
 * @implNote This is here because 1.14 doesn't support custom fonts in text components.
 */
public class CustomButtons
{
    public static class BackupButton extends ButtonWidget
    {
        public BackupButton(int x, int y)
        {
            super(976, x, y, 16, 12, "✍");
        }
    }

    public static class NextButton extends ButtonWidget
    {
        public NextButton(int x, int y)
        {
            super(977, x, y, 16, 12, "→");
        }
    }

    public static class PreviousButton extends ButtonWidget
    {
        public PreviousButton(int x, int y)
        {
            super(978, x, y, 16, 12, "←");
        }
    }
}
