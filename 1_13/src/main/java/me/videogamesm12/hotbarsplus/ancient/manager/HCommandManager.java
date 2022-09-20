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

package me.videogamesm12.hotbarsplus.ancient.manager;

import me.videogamesm12.hotbarsplus.ancient.command.*;
import net.legacyfabric.fabric.api.command.v2.CommandRegistrar;
import net.legacyfabric.fabric.api.command.v2.lib.sponge.CommandManager;
import net.legacyfabric.fabric.api.command.v2.lib.sponge.args.GenericArguments;
import net.legacyfabric.fabric.api.command.v2.lib.sponge.spec.CommandSpec;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class HCommandManager implements CommandRegistrar
{
    public HCommandManager()
    {
        CommandRegistrar.EVENT.register(this);
    }

    @Override
    public void register(CommandManager manager, boolean dedicated)
    {
        CommandSpec backup = CommandSpec.builder()
                .description(new TranslatableText("hotbarsplus.command.backup.description"))
                .executor(new BackupCommand()).build();

        CommandSpec cache = CommandSpec.builder()
                .description(new TranslatableText("hotbarsplus.command.cache.description"))
                .child(CommandSpec.builder()
                        .description(new TranslatableText("hotbarsplus.command.cache.list.description"))
                        .executor(new CacheCommand.ListCommand())
                        .build(),
                        "list")
                .child(CommandSpec.builder()
                        .description(new TranslatableText("hotbarsplus.command.cache.clear.description"))
                        .executor(new CacheCommand.ClearCommand())
                        .build(),
                        "clear")
                .build();

        CommandSpec goTo = CommandSpec.builder()
                .description(new TranslatableText("hotbarsplus.command.goto.description"))
                .arguments(GenericArguments.bigInteger(new LiteralText("page")))
                .executor(new GoToCommand()).build();

        CommandSpec next = CommandSpec.builder()
                .description(new TranslatableText("hotbarsplus.command.next.description"))
                .executor(new NextCommand()).build();

        CommandSpec previous = CommandSpec.builder()
                .description(new TranslatableText("hotbarsplus.command.previous.description"))
                .executor(new PreviousCommand()).build();

        manager.register(CommandSpec.builder()
                .description(new TranslatableText("hotbarsplus.command.description"))
                        .child(backup, "backup")
                        .child(cache, "cache")
                        .child(goTo, "goto")
                        .child(next, "next")
                        .child(previous, "previous")
                .build());
    }
}
