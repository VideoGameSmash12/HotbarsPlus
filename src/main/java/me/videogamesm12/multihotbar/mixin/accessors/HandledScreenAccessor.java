package me.videogamesm12.multihotbar.mixin.accessors;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * HandledScreenAccessor - Accesses certain variables in HandledScreen.
 * @author Video
 */
@Mixin(HandledScreen.class)
public interface HandledScreenAccessor
{
    @Accessor("x")
    public int getX();

    @Accessor("y")
    public int getY();
}
