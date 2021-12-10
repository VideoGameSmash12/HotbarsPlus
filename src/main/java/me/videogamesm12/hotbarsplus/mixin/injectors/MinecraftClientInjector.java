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

package me.videogamesm12.hotbarsplus.mixin.injectors;

import me.videogamesm12.hotbarsplus.callbacks.ClientInitCallback;
import me.videogamesm12.hotbarsplus.client.HotbarsPlusClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.option.HotbarStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * MinecraftClientInjector - Modifies the main class to call events and redirect queries for saved hotbar data.
 * --
 * @since v1.1
 * @author Video
 */
@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientInjector
{
    /**
     * Early versions of Hotbars+ stored additional hotbar pages in the same folder as the hotbar.nbt file. This was
     *   changed in v1.1 to save additional hotbar pages to a dedicated folder. To ensure that anyone using v1.0 could
     *   update the mod to v1.1 or newer while still keeping their saved hotbar pages, this was added to show a pop-up
     *   prompt on startup asking the user if they wanted to to migrate the files to the aforementioned folder.
     *   directory.
     * --
     * @since v1.1
     * --
     * @param args RunArgs
     * @param ci CallbackInfo
     */
    @Inject(method = "<init>", at = @At("RETURN"))
    public void injectInit(RunArgs args, CallbackInfo ci)
    {
        ClientInitCallback.EVENT.invoker().onInitialize();
    }

    /**
     * In versions of Minecraft 1.17 and newer, modifying anything marked as "final" causes the client to instantly
     *  crash to the launcher with a Mixin error. To combat this, versions of Hotbars+ after v1.3-Pre4 use their own
     *  instance of HotbarStorage. What this injection does is elementary: it redirects all queries for the built-in
     *  HotbarStorage instance to the one in this mod.
     * --
     * @since v1.3-Pre4
     * --
     * @param cir CallbackInfoReturnable<HotbarStorage>
     */
    @Inject(method = "getCreativeHotbarStorage", at = @At("HEAD"), cancellable = true)
    public void injectGetHotbarStorage(CallbackInfoReturnable<HotbarStorage> cir)
    {
        cir.setReturnValue(HotbarsPlusClient.hotbarStorage);
    }
}
