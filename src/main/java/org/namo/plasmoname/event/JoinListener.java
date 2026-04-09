package org.namo.plasmoname.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.namo.plasmoname.json.ConfigManager;

import java.io.IOException;

@Mod.EventBusSubscriber
public class JoinListener {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) throws IOException {

        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        String uuid = player.getUUID().toString();
        String name = player.getGameProfile().getName();

        // เก็บชื่อปกติของ Minecraft ลง config
        try{
         ConfigManager.getDisplayName(uuid);
        } catch (Exception e) {
            ConfigManager.setDisplayName(uuid, name);
        }

    }
}
