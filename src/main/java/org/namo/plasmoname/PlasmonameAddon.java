package org.namo.plasmoname;

import org.namo.plasmoname.json.ConfigManager;
import su.plo.voice.api.addon.AddonInitializer;
import su.plo.voice.api.addon.InjectPlasmoVoice;
import su.plo.voice.api.addon.annotation.Addon;
import su.plo.voice.api.event.EventSubscribe;
import su.plo.voice.api.server.PlasmoVoiceServer;
import su.plo.voice.api.server.audio.source.ServerAudioSource;
import su.plo.voice.api.server.audio.source.ServerPlayerSource;
import su.plo.voice.api.server.event.audio.source.PlayerSpeakEvent;
import su.plo.voice.api.server.player.VoiceServerPlayer;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

@Addon(
        id = "pv-name-addon",
        name = "Name Addon",
        version = "1.0.0",
        authors = {"namooplol"}
)
public final class PlasmonameAddon implements AddonInitializer {


    @InjectPlasmoVoice
    private PlasmoVoiceServer voiceServer;

    @Override
    public void onAddonInitialize() {
        voiceServer.getEventBus().register(this, new EventListener(voiceServer));
        try {
            ConfigManager.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Addon initialized");
    }

    @Override
    public void onAddonShutdown() {
        System.out.println("Addon shut down");
    }

    public static final class EventListener {

        private final PlasmoVoiceServer voiceServer;

        public EventListener(PlasmoVoiceServer voiceServer) {
            this.voiceServer = voiceServer;
        }


        @EventSubscribe(ignoreCancelled = false)
        public void onPlayerSpeak(PlayerSpeakEvent event) throws IOException {
            VoiceServerPlayer player = (VoiceServerPlayer) event.getPlayer();
            UUID PlayerUUID = player.getInstance().getUuid();

            voiceServer.getSourceLineManager()
                    .getLineByName("proximity")
                    .ifPresent(line -> {

                        Collection<ServerAudioSource<?>> sources = line.getSources();
                        if (sources == null) return;
                        for (ServerAudioSource<?> source : sources) {
                            if (source instanceof ServerPlayerSource playerSource) {
                                if (playerSource.getPlayer().equals(player)) {
                                    playerSource.setName(ConfigManager.getDisplayName(String.valueOf(PlayerUUID)));
                                    System.out.println(player.getInstance().getName());
                                    break;
                                }
                            }
                        }
                    });
        }
    }
}