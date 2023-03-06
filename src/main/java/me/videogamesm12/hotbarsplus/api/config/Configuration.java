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

package me.videogamesm12.hotbarsplus.api.config;

import lombok.Getter;
import lombok.Setter;
import me.videogamesm12.hotbarsplus.api.event.notification.NotificationTypeRegistered;
import me.videogamesm12.hotbarsplus.core.universal.NotificationManager;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Configuration</b>
 * <p>The configuration class for Hotbars+. Note that this doesn't control the configuration, it just stores the data from it.</p>
 */
public class Configuration
{
    @Getter
    private static final File location = new File(FabricLoader.getInstance().getConfigDir().toFile(), "hotbarsplus.json");

    @Getter
    private Notifications notificationConfig = new Notifications();

    @Getter
    public static class Notifications implements NotificationTypeRegistered
    {
        public Notifications()
        {
            NotificationTypeRegistered.EVENT.register(this);
        }

        @Setter
        private boolean enabled = true;

        private Map<String, Boolean> types = new HashMap<>();

        public boolean isTypeEnabled(Identifier id)
        {
            return types.getOrDefault(id.toString(), true);
        }

        @Override
        public void onTypeRegistered(NotificationManager.NotificationRoute type)
        {
            if (!types.containsKey(type.getId().toString()))
                types.put(type.getId().toString(), true);
        }
    }
}
