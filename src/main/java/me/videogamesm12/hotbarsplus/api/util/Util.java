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

package me.videogamesm12.hotbarsplus.api.util;

import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.math.BigInteger;

public class Util
{
    public static File getHotbarFolder()
    {
        File folder = new File(MinecraftClient.getInstance().runDirectory, "hotbars");

        if (!folder.isDirectory())
        {
            folder.mkdir();
        }

        return folder;
    }

    public static File getHotbarFile(BigInteger page)
    {
        return new File(
                page.equals(BigInteger.ZERO) ? MinecraftClient.getInstance().runDirectory : getHotbarFolder(),
                getHotbarFilename(page)
        );
    }

    public static String getHotbarFilename(BigInteger page)
    {
        return page.equals(BigInteger.ZERO) ? "hotbar.nbt" : "hotbar." + page + ".nbt";
    }
}
