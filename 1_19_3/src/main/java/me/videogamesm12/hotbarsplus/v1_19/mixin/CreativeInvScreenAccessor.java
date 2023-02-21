package me.videogamesm12.hotbarsplus.v1_19.mixin;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CreativeInventoryScreen.class)
public interface CreativeInvScreenAccessor
{
    @Accessor("selectedTab")
    ItemGroup getSelectedTab();

    @Invoker
    void invokeSetSelectedTab(ItemGroup group);
}
