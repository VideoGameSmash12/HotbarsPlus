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

package me.videogamesm12.hotbarsplus.core.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import me.videogamesm12.hotbarsplus.api.util.Util;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public interface BackupCommand<T> extends Command<T>
{
    @Override
    default int run(CommandContext<T> context)
    {
        if (!HBPCore.UPL.hotbarPageExists())
        {
            Util.msg(Component.translatable("command.backup.file_does_not_exist", NamedTextColor.RED));
            return 1;
        }

        HBPCore.UBL.backupHotbar();
        return 1;
    };

    static <Z> BackupCommand<Z> impl()
    {
        return new BackupCommand<Z>() {};
    }
}
