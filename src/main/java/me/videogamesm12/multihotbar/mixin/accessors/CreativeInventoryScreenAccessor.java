package me.videogamesm12.multihotbar.mixin.accessors;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * CreativeInventoryScreenAccessor - Accesses certain variables in CreativeInventoryScreen.
 * @author Video
 */
@Mixin(CreativeInventoryScreen.class)
public interface CreativeInventoryScreenAccessor
{
    @Accessor("selectedTab")
    public int getSelectedTab();
}
