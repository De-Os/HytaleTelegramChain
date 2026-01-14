package dev.deos.handlers;

import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.Universe;
import dev.deos.TelegramChain;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class TelegramMessageHandler {
    public static void handle(Message message) {
        if (!TelegramChain.getConfig().broadcastFromTelegram) {
            return;
        }

        if (message.getText() == null || message.getText().isEmpty()) {
            return;
        }

        if (isCommand(message)) {
            return;
        }

        var intMessage = TelegramChain.getConfig().messageTelegramBoilerplate
                .replace("${nickname}", prepareUserName(message.getFrom()))
                .replace("${text}", message.getText());

        var server = Universe.get();

        if (server == null) {
            return;
        }

        server.sendMessage(com.hypixel.hytale.server.core.Message.raw(intMessage));

        TelegramChain.LOGGER.info(intMessage);
    }

    private static void sendPlayersList() {
        var players = ServerMessageHandler.currentUsers;

        if (players.isEmpty()) {
            ServerMessageHandler.sendToTelegram(new HashMap<>(), TelegramChain.getConfig().messageTelegramPlayersListEmpty);
            return;
        }

        var playersList = new StringBuilder();

        var number = 1;
        for (var player : players) {
            playersList.append(TelegramChain.getConfig().messageTelegramPlayersListEntryBoilerplate
                    .replace("${number}", String.valueOf(number))
                    .replace("${nickname}", player));
            number++;
        }

        var values = new HashMap<String, String>();
        values.put("${currentPlayers}", String.valueOf(players.size()));
        values.put("${totalPlayers}", String.valueOf(HytaleServer.get().getConfig().getMaxPlayers()));
        values.put("${playersList}", playersList.toString());

        ServerMessageHandler.sendToTelegram(values, TelegramChain.getConfig().messageTelegramPlayersListBoilerplate);
    }

    private static String prepareUserName(User user) {
        var name = new ArrayList<String>();
        if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
            name.add(user.getFirstName());
        }

        if (user.getLastName() != null && !user.getLastName().isEmpty()) {
            name.add(user.getLastName());
        }

        return String.join(" ", name).replaceAll("[^\\p{L}\\p{N} ]+", "").trim();
    }

    private static boolean isCommand(Message message) {
        var text = message.getText();

        if (!TelegramChain.getConfig().commandTelegramPlayersList.isEmpty() && text.equals(TelegramChain.getConfig().commandTelegramPlayersList)) {
            sendPlayersList();
            return true;
        }

        return false;
    }
}
