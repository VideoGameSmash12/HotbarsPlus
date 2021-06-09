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

import me.videogamesm12.multihotbar.callbacks.HotbarLoadFailCallback;
import me.videogamesm12.multihotbar.callbacks.HotbarSaveFailCallback;
import me.videogamesm12.multihotbar.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.options.HotbarStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/**
 * HotbarStorageInjector - Intercepts the Hotbar file used by Minecraft to save to other files.
 * @author Video
 */
@Environment(EnvType.CLIENT)
@Mixin(HotbarStorage.class)
public class HotbarStorageInjector
{
    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/io/File;<init>(Ljava/io/File;Ljava/lang/String;)V"))
    private void injectFile(Args args)
    {
        args.set(0, Util.getHotbarFolder());
        args.set(1, Util.getHotbarName());
    }

    /**
     * Loading Injection - Calls the HotbarLoadFailCallback if the client fails to load the hotbar file.
     * @param ci CallbackInfo
     */
    @Inject(method = "load", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Throwable;)V", shift = At.Shift.AFTER))
    public void injectLoad(CallbackInfo ci)
    {
        HotbarLoadFailCallback.EVENT.invoker().onHotbarLoadFail();
    }

    /**
     * Saving Injection - Calls the HotbarSaveFailCallback if the client fails to save the hotbar file.
     * @param ci CallbackInfo
     */
    @Inject(method = "save", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Throwable;)V", shift = At.Shift.AFTER))
    public void injectSave(CallbackInfo ci)
    {
        HotbarSaveFailCallback.EVENT.invoker().onHotbarSaveFail();
    }
}
