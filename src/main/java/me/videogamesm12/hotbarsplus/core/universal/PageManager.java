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

import me.videogamesm12.hotbarsplus.api.event.keybind.NextBindPressEvent;
import me.videogamesm12.hotbarsplus.api.event.keybind.PreviousBindPressEvent;
import me.videogamesm12.hotbarsplus.api.event.navigation.HotbarNavigateEvent;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.core.HotbarsPlusStorage;
import me.videogamesm12.hotbarsplus.core.mixin.HotbarStorageMixin;
import me.videogamesm12.hotbarsplus.core.mixin.PlayerEntityMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.HotbarStorage;
import net.minecraft.client.option.HotbarStorageEntry;
import net.minecraft.entity.player.PlayerInventory;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>PageManager</b>
 * <p>Version-independent storage for the current page number in Hotbars+.</p>
 */
public class PageManager implements NextBindPressEvent, PreviousBindPressEvent
{
    private final Map<BigInteger, HotbarStorage> cache = new HashMap<>();
    private BigInteger currentPage = BigInteger.valueOf(0);

    public PageManager()
    {
        NextBindPressEvent.EVENT.register(this);
        PreviousBindPressEvent.EVENT.register(this);
    }

    public void saveToNearestRow()
    {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null)
        {
            return;
        }

        boolean saved = false;
        //--
        BigInteger page = getCurrentPage();
        HotbarStorage current = getHotbarPage();

        // This constantly loops until it finds everything.
        while (!saved)
        {
            // Try to load the file if it isn't already loaded.
            if (!((HotbarStorageMixin.HSAccessor) current).isLoaded())
            {
                ((HotbarStorageMixin.HSAccessor) current).invokeLoad();
            }

            HBPCore.LOGGER.info("Starting iteration");

            // Let's try to do some saving.
            for (HotbarStorageEntry entry : ((HotbarStorageMixin.HSAccessor) current).getEntries())
            {
                // DEBUG SHIT REMOVE PLEASE
                HBPCore.LOGGER.info("Iterating on a new row");

                // If the row is empty, save to it.
                if (entry.isEmpty())
                {
                    HBPCore.LOGGER.info("Found an empty row! Saving!");

                    for (int col = 0; col < PlayerInventory.getHotbarSize(); col++)
                    {
                        // I'm aware that 1.16+ uses the built in getInventory(), however 1.14.x doesn't have that.
                        entry.set(col, ((PlayerEntityMixin) player).getInventory().getStack(col));
                    }

                    saved = true;
                    break;
                }
            }

            // Did the iteration above do anything?
            if (saved)
            {
                HBPCore.LOGGER.info("Saving changes to disk!");
                current.save();
            }
            // Ok, then keep going on.
            else
            {
                HBPCore.LOGGER.info("Fuck, moving on to page #" + page.toString());

                page = page.add(BigInteger.ONE);
                current = getHotbarPage(page);
            }
        }
    }

    public void decrementPage()
    {
        currentPage = currentPage.subtract(BigInteger.ONE);
        HotbarNavigateEvent.EVENT.invoker().onNavigate(currentPage);
    }

    public void incrementPage()
    {
        currentPage = currentPage.add(BigInteger.ONE);
        HotbarNavigateEvent.EVENT.invoker().onNavigate(currentPage);
    }

    public void goToPage(BigInteger page)
    {
        currentPage = page;
        HotbarNavigateEvent.EVENT.invoker().onNavigate(currentPage);
    }

    public Map<BigInteger, HotbarStorage> getCache()
    {
        return cache;
    }

    public int getCacheSize()
    {
        return cache.size();
    }

    public BigInteger getCurrentPage()
    {
        return currentPage;
    }

    public HotbarStorage getHotbarPage(BigInteger page)
    {
        // Get from disk if not cached in memory
        if (!cache.containsKey(page))
        {
            HotbarStorage storage = page.equals(BigInteger.ZERO) ?
                    new HotbarStorage(MinecraftClient.getInstance().runDirectory, MinecraftClient.getInstance().getDataFixer()) :
                    new HotbarsPlusStorage(page);

            cache.put(page, storage);
        }

        return cache.get(page);
    }

    public HotbarStorage getHotbarPage()
    {
        return getHotbarPage(currentPage);
    }

    public boolean hotbarPageExists()
    {
        return ((HotbarStorageMixin.HSAccessor) getHotbarPage()).getFile().exists();
    }

    @Override
    public void onNextPress()
    {
        incrementPage();
    }

    @Override
    public void onPreviousPress()
    {
        decrementPage();
    }
}
