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
        String minecraftName = player.getGameProfile().getName();

        // ถ้าผู้เล่นยังไม่มีชื่อที่ตั้งเอง (custom name) ให้ใช้ชื่อ Minecraft เป็นค่าเริ่มต้น
        String customName = ConfigManager.getDisplayName(uuid);
        if (customName == null || customName.equals("Unknown")) {
            ConfigManager.setDisplayName(uuid, minecraftName);
        }

    }
}
