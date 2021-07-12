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

import me.videogamesm12.multihotbar.callbacks.ClientInitCallback;
import me.videogamesm12.multihotbar.client.MultiHotbarClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.options.HotbarStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * MinecraftClientInjector - Calls an event when the Minecraft client finishes initializing.
 * @author Video
 */
@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientInjector
{
    @Inject(method = "<init>", at = @At("RETURN"))
    public void injectInit(RunArgs args, CallbackInfo ci)
    {
        ClientInitCallback.EVENT.invoker().onInitialize();
    }

    @Inject(method = "getCreativeHotbarStorage", at = @At("HEAD"), cancellable = true)
    public void injectGetHotbarStorage(CallbackInfoReturnable<HotbarStorage> cir)
    {
        cir.setReturnValue(MultiHotbarClient.hotbarStorage);
    }
}
