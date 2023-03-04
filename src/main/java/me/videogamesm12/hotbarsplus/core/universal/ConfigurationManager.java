/*
 * Copyright (c) 2022 Video
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

package me.videogamesm12.hotbarsplus.core.universal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

import me.videogamesm12.hotbarsplus.api.config.Configuration;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;

import javax.naming.ConfigurationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

/**
 * <b>ConfigurationManager</b>
 * <p>Controls pretty much everything about the configuration.</p>
 */
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

            // Just for good measure...
            if (config == null)
            {
                throw new ConfigurationException("The configuration that was loaded in ended up being blank");
            }
        }
        // Failed to read the configuration. Oh well, we tried.
        catch (Exception ex)
        {
            // If there's not already a configuration in memory, create one from scratch.
            if (config == null)
            {
                config = new Configuration();
            }

            // Saves the new copy of the configuration.
            save();
        }

        ClientLifecycleEvents.CLIENT_STOPPING.register(this);
    }

    public void save()
    {
        HBPCore.LOGGER.info("Saving configuration to disk...");

        // Try to save the configuration to disk
        try
        {
            Writer wtf = new FileWriter(Configuration.getLocation());
            gson.toJson(config, wtf);
            wtf.close();
        }
        catch (Exception ex)
        {
            HBPCore.LOGGER.error("Failed to save configuration", ex);
        }
    }

    @Override
    public void onClientStopping(MinecraftClient client)
    {
        // Automatically saves to disk.
        save();
    }
}