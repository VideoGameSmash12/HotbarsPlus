package me.videogamesm12.hotbarsplus.core.universal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.videogamesm12.hotbarsplus.api.config.Configuration;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

import java.io.FileReader;
import java.nio.file.Files;
import java.util.Arrays;

public class ConfigurationManager implements ClientLifecycleEvents.ClientStopping
{
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Getter
    private Configuration config = null;

    public ConfigurationManager()
    {
        load();
    }

    public void load()
    {
        // Try loading the configuration from disk.
        try
        {
            config = gson.fromJson(new FileReader(Configuration.getLocation()), Configuration.class);
        }
        // Failed to read the configuration. Oh well, we tried.
        catch (Exception ex)
        {
            // If there's not already a configuration in memory, create one from scratch.
            if (config == null)
            {
                config = new Configuration();
            }
        }
    }

    public void save()
    {
        HBPCore.LOGGER.info("Saving configuration to disk...");

        // Try to save the configuration to disk
        try
        {
            gson.toJson(config, Files.newBufferedWriter(Configuration.getLocation().toPath()));
        }
        catch (Exception ex)
        {
            HBPCore.LOGGER.error("Failed to save configuration");
            HBPCore.LOGGER.error(ex);
        }
    }

    @Override
    public void onClientStopping(MinecraftClient client)
    {
        save();
    }
}