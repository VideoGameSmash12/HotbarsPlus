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

package me.videogamesm12.hotbarsplus.core.mixin;

import me.videogamesm12.hotbarsplus.api.util.Util;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import net.minecraft.client.option.HotbarStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.io.File;

/**
 * <b>HotbarStorageMixin</b>
 * <p>Controls where hotbar files are stored.</p>
 */
@Mixin(HotbarStorage.class)
public class HotbarStorageMixin
{
    /**
     * <p>Hijacks what is used as the location by HotbarStorage on initialization.</p>
     * FIXME: This implementation works when you want information for the currently selected page, however it does not
     *        work at all if you want data for, say, other hotbar pages that haven't been loaded yet. Look into a way to
     *        more cleanly implement this if possible.
     * @param args Args
     */
    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/io/File;<init>(Ljava/io/File;Ljava/lang/String;)V"))
    private void injectFile(Args args)
    {
        // Changes the folder
        args.set(0, Util.getHotbarFolderForPage(HBPCore.UPL.getCurrentPage()));

        // Changes the file name
        args.set(1, Util.getHotbarFilename(HBPCore.UPL.getCurrentPage()));
    }

    @Mixin(HotbarStorage.class)
    public interface HSAccessor
    {
        @Accessor
        File getFile();
    }
}