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

package me.videogamesm12.hotbarsplus.ancient.command;

import me.videogamesm12.hotbarsplus.api.util.Util;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.legacyfabric.fabric.api.command.v2.lib.sponge.CommandException;
import net.legacyfabric.fabric.api.command.v2.lib.sponge.CommandResult;
import net.legacyfabric.fabric.api.command.v2.lib.sponge.args.CommandContext;
import net.legacyfabric.fabric.api.command.v2.lib.sponge.spec.CommandExecutor;
import net.legacyfabric.fabric.api.permission.v1.PermissibleCommandSource;

import java.util.ArrayList;
import java.util.List;

public interface CacheCommand
{
    class ListCommand implements CommandExecutor
    {
        @Override
        public CommandResult execute(PermissibleCommandSource src, CommandContext args) throws CommandException
        {
            Util.msg(Component.translatable("command.cache.list.amount", Component.text(HBPCore.UPL.getCacheSize())));

            List<Component> texts = new ArrayList<>();
            HBPCore.UPL.getCache().keySet().stream().sorted().forEach((key) -> texts.add(Component.text(Util.getHotbarFilename(key))));

            Util.msg(Component.translatable("command.cache.list.entries",
                    Component.join(JoinConfiguration.builder().separator(Component.text(", ")).build(), texts)));

            return CommandResult.success();
        }
    }

    class ClearCommand implements CommandExecutor
    {
        @Override
        public CommandResult execute(PermissibleCommandSource src, CommandContext args) throws CommandException
        {
            HBPCore.UPL.getCache().clear();
            Util.msg(Component.translatable("command.cache.clear.success"));
            return CommandResult.success();
        }
    }
}
