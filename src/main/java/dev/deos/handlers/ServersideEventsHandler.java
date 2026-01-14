package dev.deos.handlers;

import com.hypixel.hytale.server.core.event.events.BootEvent;
import dev.deos.TelegramBot;
import dev.deos.TelegramChain;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class ServersideEventsHandler {

    public static void onServerBoot(BootEvent event) {
        if (TelegramChain.getConfig().telegramBotToken.isEmpty()) {
            TelegramChain.LOGGER.info("Skipping telegram chain setup, telegramBotToken is empty");
            return;
        }

        if (TelegramChain.getConfig().telegramChatId == 0) {
            TelegramChain.LOGGER.info("Skipping telegram chain setup, telegramChatId is empty");
            return;
        }

        try {
            TelegramChain.LOGGER.info("Starting telegram bot polling");

            var bot = new TelegramBot();
            var botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(TelegramChain.getConfig().telegramBotToken, bot);

            TelegramChain.TELEGRAM_BOT = bot;
            TelegramChain.TELEGRAM_BOT.CLIENT = new OkHttpTelegramClient(TelegramChain.getConfig().telegramBotToken);

            TelegramChain.LOGGER.info("Telegram chain started!");

            if (!TelegramChain.getConfig().messageServerStarted.isEmpty()) {
                ServerMessageHandler.sendToTelegram(new HashMap<>(), TelegramChain.getConfig().messageServerStarted);
            }
        } catch (TelegramApiException e) {
            TelegramChain.LOGGER.error("Unable to start bot polling", e);
        }
    }
}
