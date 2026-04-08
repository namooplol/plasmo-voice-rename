package org.namo.plasmoname;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import su.plo.voice.api.server.PlasmoVoiceServer;
import org.namo.plasmoname.command.rename;

import java.io.IOException;

@Mod(Plasmoname.MODID)
public final class Plasmoname {

    public static final String MODID = "plasmoname";

    // Addon instance
    private final PlasmonameAddon addon = new PlasmonameAddon();

    public Plasmoname() throws IOException {
        // ✅ Load addon ใน Forge constructor ตาม docs
        MinecraftForge.EVENT_BUS.register(this);
        PlasmoVoiceServer.getAddonsLoader().load(addon);

    }
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        rename.register(event.getDispatcher());
    }
}