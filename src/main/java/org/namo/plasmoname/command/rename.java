package org.namo.plasmoname.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import org.namo.plasmoname.json.ConfigManager;


import java.io.IOException;

public class rename {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("rename")
                        .requires(src ->
                                src.hasPermission(2)
                        )
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.argument("name", StringArgumentType.greedyString())
                                        .executes(ctx -> {
                                            try {
                                                ConfigManager.init();
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                            ServerPlayer target =
                                                    EntityArgument.getPlayer(ctx, "target");

                                            String name =
                                                    StringArgumentType.getString(ctx, "name");

                                            String uuid = target.getUUID().toString();

                                            try {
                                                ConfigManager.setDisplayName(uuid, name);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }


                                            return 1;
                                        })
                                )
                        )
        );
    }


}
