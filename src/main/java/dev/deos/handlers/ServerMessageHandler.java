package dev.deos.handlers;

import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.event.events.entity.EntityRemoveEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import dev.deos.TelegramChain;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerMessageHandler {
    public static ArrayList<String> currentUsers = new ArrayList<>();

    public static void onPlayerChat(PlayerChatEvent event) {
        if (TelegramChain.getConfig().messageServerBoilerplate.isEmpty()) {
            return;
        }

        var values = new HashMap<String, String>();
        values.put("${nickname}", event.getSender().getUsername());
        values.put("${text}", event.getContent());

        sendToTelegram(values, TelegramChain.getConfig().messageServerBoilerplate);
    }

    public static void onPlayerReady(PlayerReadyEvent event) {
        if (TelegramChain.getConfig().messageServerPlayerJoinedBoilerplate.isEmpty()) {
            return;
        }

        if (!currentUsers.contains(event.getPlayer().getDisplayName())) {
            currentUsers.add(event.getPlayer().getDisplayName());
        } else {
            return;
        }

        var values = new HashMap<String, String>();
        values.put("${nickname}", event.getPlayer().getDisplayName());
        values.put("${currentPlayers}", String.valueOf(currentUsers.size()));
        values.put("${totalPlayers}", String.valueOf(HytaleServer.get().getConfig().getMaxPlayers()));

        sendToTelegram(values, TelegramChain.getConfig().messageServerPlayerJoinedBoilerplate);
    }

    public static void onPlayerDisconnect(PlayerDisconnectEvent event) {
        if (TelegramChain.getConfig().messageServerPlayerQuitBoilerplate.isEmpty()) {
            return;
        }

        if (currentUsers.contains(event.getPlayerRef().getUsername())) {
            currentUsers.remove(event.getPlayerRef().getUsername());
        } else {
            return;
        }

        var values = new HashMap<String, String>();
        values.put("${nickname}", event.getPlayerRef().getUsername());
        values.put("${currentPlayers}", String.valueOf(currentUsers.size()));
        values.put("${totalPlayers}", String.valueOf(HytaleServer.get().getConfig().getMaxPlayers()));

        sendToTelegram(values, TelegramChain.getConfig().messageServerPlayerQuitBoilerplate);
    }

    public static void sendToTelegram(HashMap<String, String> replacements, String text) {
        if (TelegramChain.TELEGRAM_BOT == null || !TelegramChain.getConfig().broadcastToTelegram) {
            return;
        }

        var intMessage = text;

        for (String key : replacements.keySet()) {
            intMessage = intMessage.replace(key, replacements.get(key));
        }

        try {
            TelegramChain.TELEGRAM_BOT.CLIENT.execute(
                    TelegramChain.TELEGRAM_BOT.prepareMessageForChat()
                            .text(intMessage)
                            .parseMode(TelegramChain.getConfig().messageServerParseMode)
                            .build()
            );
        } catch (TelegramApiException e) {
            TelegramChain.LOGGER.error("Unable to resend message to Telegram", e);
        }
    }
}
