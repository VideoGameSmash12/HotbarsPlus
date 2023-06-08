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

package me.videogamesm12.hotbarsplus.v1_20.manager;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.videogamesm12.hotbarsplus.api.manager.ICommandManager;
import me.videogamesm12.hotbarsplus.core.commands.*;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;

public class CommandManager implements ICommandManager<FabricClientCommandSource>, ClientCommandRegistrationCallback
{
    private LiteralCommandNode<FabricClientCommandSource> hotbarsPlusCommand;

    public CommandManager()
    {
        ClientCommandRegistrationCallback.EVENT.register(this);
    }

    @Override
    public void register()
    {
        // Unused, Fabric API v2 removed the original method of registering commands
    }

    @Override
    public LiteralCommandNode<FabricClientCommandSource> getCommandNode()
    {
        return hotbarsPlusCommand;
    }

    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess)
    {
        hotbarsPlusCommand = dispatcher.register(
                ClientCommandManager.literal("hotbars+").then(
                        ClientCommandManager.literal("backup").executes(BackupCommand.impl())
                ).then(
                        ClientCommandManager.literal("next").executes(NextCommand.impl())
                ).then(
                        ClientCommandManager.literal("previous").executes(PreviousCommand.impl())
                ).then(
                        ClientCommandManager.literal("goto").then(
                                ClientCommandManager.argument("page", LongArgumentType.longArg()).executes(GoToCommand.impl())
                        )
                ).then(
                        ClientCommandManager.literal("cache").then(
                                ClientCommandManager.literal("list").executes(CacheCommand.ListCommand.impl())
                        ).then(
                                ClientCommandManager.literal("clear").executes(CacheCommand.ClearCommand.impl())
                        )
                )
        );
    }
}
