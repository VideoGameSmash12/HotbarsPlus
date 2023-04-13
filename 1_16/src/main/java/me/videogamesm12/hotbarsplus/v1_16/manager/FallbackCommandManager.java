/*
 * Copyright (c) 2023 Video
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


package me.videogamesm12.hotbarsplus.v1_16.manager;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import me.videogamesm12.hotbarsplus.api.manager.ICommandManager;
import me.videogamesm12.hotbarsplus.core.commands.*;

public class FallbackCommandManager implements ICommandManager<CottonClientCommandSource>, ClientCommandPlugin
{
    private LiteralCommandNode<CottonClientCommandSource> hotbarsPlusCommand;

    @Override
    public void register()
    {
        // Unused, Cotton Client Commands does all the magic for us
    }

    @Override
    public LiteralCommandNode<CottonClientCommandSource> getCommandNode()
    {
        return hotbarsPlusCommand;
    }

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher)
    {
        hotbarsPlusCommand = dispatcher.register(
                ArgumentBuilders.literal("hotbars+").then(
                        ArgumentBuilders.literal("backup").executes(BackupCommand.impl())
                ).then(
                        ArgumentBuilders.literal("next").executes(NextCommand.impl())
                ).then(
                        ArgumentBuilders.literal("previous").executes(PreviousCommand.impl())
                ).then(
                        ArgumentBuilders.literal("goto").then(
                                ArgumentBuilders.argument("page", LongArgumentType.longArg()).executes(GoToCommand.impl())
                        )
                ).then(
                        ArgumentBuilders.literal("cache").then(
                                ArgumentBuilders.literal("list").executes(CacheCommand.ListCommand.impl())
                        ).then(
                                ArgumentBuilders.literal("clear").executes(CacheCommand.ClearCommand.impl())
                        )
                )
        );
    }
}
