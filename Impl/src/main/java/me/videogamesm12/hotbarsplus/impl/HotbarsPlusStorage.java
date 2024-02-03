package me.videogamesm12.hotbarsplus.impl;

import me.videogamesm12.hotbarsplus.api.IHotbarsPlusStorage;
import me.videogamesm12.hotbarsplus.api.util.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.HotbarStorage;

import java.math.BigInteger;
import java.nio.file.Path;

public class HotbarsPlusStorage extends HotbarStorage implements IHotbarsPlusStorage
{
    private BigInteger page;
    private Path location;

    public HotbarsPlusStorage(BigInteger page)
    {
        super(Util.getHotbarFile(page), MinecraftClient.getInstance().getDataFixer());

        this.location = Util.getHotbarFile(page).toPath(); // God damnit
        this.page = page;
    }

    @Override
    public BigInteger getPage()
    {
        return page;
    }

    @Override
    public Path getLocation()
    {
        return this.location;
    }
}
