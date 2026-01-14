package dev.deos;

import com.hypixel.hytale.server.core.event.events.BootEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import dev.deos.handlers.DeathHandler;
import dev.deos.handlers.ServerMessageHandler;
import dev.deos.handlers.ServersideEventsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class TelegramChain extends JavaPlugin {

    private static Config<TelegramChainConfig> config;
    public static Logger LOGGER = LoggerFactory.getLogger("TelegramChain");
    public static TelegramBot TELEGRAM_BOT;

    public TelegramChain(@Nonnull JavaPluginInit init) {
        super(init);

        TelegramChain.config = this.withConfig("TelegramChain", TelegramChainConfig.CODEC);
    }

    @Override
    protected void setup() {

        super.setup();

        TelegramChain.config.save();

        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ServerMessageHandler::onPlayerReady);
        this.getEventRegistry().registerGlobal(PlayerChatEvent.class, ServerMessageHandler::onPlayerChat);
        this.getEventRegistry().registerGlobal(PlayerDisconnectEvent.class, ServerMessageHandler::onPlayerDisconnect);
        this.getEventRegistry().registerGlobal(BootEvent.class, ServersideEventsHandler::onServerBoot);

        this.getEntityStoreRegistry().registerSystem(new DeathHandler());
    }

    public static TelegramChainConfig getConfig() {
        return TelegramChain.config.get();
    }
}