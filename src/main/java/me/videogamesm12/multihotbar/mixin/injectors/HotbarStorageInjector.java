package me.videogamesm12.multihotbar.mixin.injectors;

import me.videogamesm12.multihotbar.util.Util;
import net.minecraft.client.options.HotbarStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(HotbarStorage.class)
public class HotbarStorageInjector
{
    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/io/File;<init>(Ljava/io/File;Ljava/lang/String;)V"))
    private void injectFile(Args args)
    {
        args.set(0, Util.getHotbarFolder());
        args.set(1, Util.getHotbarName());
    }
}
