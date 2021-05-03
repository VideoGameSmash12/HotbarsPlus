package me.videogamesm12.multihotbar.mixin.accessors;

import net.minecraft.client.options.HotbarStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.File;

@Mixin(HotbarStorage.class)
public interface HotbarStorageAccessor
{
	@Accessor("file")
	public File getFile();
}
