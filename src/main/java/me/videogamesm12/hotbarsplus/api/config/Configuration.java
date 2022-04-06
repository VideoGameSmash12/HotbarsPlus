package me.videogamesm12.hotbarsplus.api.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class Configuration
{
    private static final File location = new File(FabricLoader.getInstance().getConfigDir().toFile(), "hotbarsplus.json");

    public static File getLocation()
    {
        return location;
    }

    public boolean notifsEnabled;
}
