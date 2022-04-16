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

import com.mojang.datafixers.DataFixer;
import me.videogamesm12.hotbarsplus.core.HotbarsPlusStorage;
import net.minecraft.client.option.HotbarStorage;
import net.minecraft.client.option.HotbarStorageEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
     * @param ci        CallbackInfo
     * @param dataFixer DataFixer
     * @param file      File
     */
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void inject(File file, DataFixer dataFixer, CallbackInfo ci)
    {
        if (HotbarsPlusStorage.class.isAssignableFrom(getClass()))
        {
            ((HSAccessor) this).setFile(file);
        }
    }

    @Mixin(HotbarStorage.class)
    public interface HSAccessor
    {
        @Accessor
        boolean isLoaded();

        @Invoker
        void invokeLoad();

        @Accessor
        File getFile();

        @Accessor
        void setFile(File file);

        @Accessor
        HotbarStorageEntry[] getEntries();
    }
}