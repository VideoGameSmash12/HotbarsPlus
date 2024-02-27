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

import me.videogamesm12.hotbarsplus.api.event.failures.BackupFailEvent;
import me.videogamesm12.hotbarsplus.api.event.keybind.BackupBindPressEvent;
import me.videogamesm12.hotbarsplus.api.event.success.BackupSuccessEvent;
import me.videogamesm12.hotbarsplus.api.util.Util;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.core.mixin.HotbarStorageMixin;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupManager extends Thread implements BackupBindPressEvent
{
    private final SimpleDateFormat FORMAT =  new SimpleDateFormat("yyyy-MM-dd 'at' HH.mm.ss z");

    public BackupManager()
    {
        start();
    }

    @Override
    public void run()
    {
        BackupBindPressEvent.EVENT.register(this);
    }

    @Override
    public synchronized void onBackupPress()
    {
        if (!HBPCore.UPL.hotbarPageExists())
        {
            return;
        }

        backupHotbar();
    }

    public synchronized void backupHotbar(File file)
    {
        String name = String.format("%s [%s].nbt", file.getName(), FORMAT.format(new Date()));
        File destination = new File(getBackupFolder(), name);

        // Let's try to copy the file from one place to the next. If that works call the event that indicates such.
        try
        {
            FileUtils.copyFile(file, destination);

            BackupSuccessEvent.EVENT.invoker().onBackupSuccess(file, destination);
        }
        // Well shit that didn't work. Call the event that indicates the failure
        catch (Exception ex)
        {
            BackupFailEvent.EVENT.invoker().onBackupFailure(ex);

            HBPCore.LOGGER.error("Failed to backup hotbar file", ex);
        }
    }

    public synchronized void backupHotbar()
    {
        backupHotbar(((HotbarStorageMixin.HSAccessor) HBPCore.UPL.getHotbarPage()).getFile().toFile());
    }

    public File getBackupFolder()
    {
        File folder = new File(Util.getHotbarFolder(), "backups");

        if (!folder.isDirectory())
        {
            folder.mkdirs();
        }

        return folder;
    }
}
