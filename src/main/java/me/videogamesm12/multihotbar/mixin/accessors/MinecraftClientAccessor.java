package me.videogamesm12.multihotbar.mixin.accessors;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.HotbarStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * MinecraftClientAccessor - Accesses certain variables in MinecraftClient.
 * @author Video
 */
@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor
{
    @Accessor("creativeHotbarStorage")
    public void setCreativeHotbarStorage(HotbarStorage storage);
}
