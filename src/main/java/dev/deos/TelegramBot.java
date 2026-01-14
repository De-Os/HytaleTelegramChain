package dev.deos;

import dev.deos.handlers.TelegramMessageHandler;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    public TelegramClient CLIENT;

    @Override
    public void consume(Update update) {
        if (!update.hasMessage()) {
            return;
        }

        var message = update.getMessage();

        var chat = message.getChat();

        if (!chat.getId().equals(TelegramChain.getConfig().telegramChatId)) {
            return;
        }

        if (TelegramChain.getConfig().telegramThreadId != 0) {
            var thread = message.getMessageThreadId();
            if (thread == null || thread == 0 || thread != TelegramChain.getConfig().telegramThreadId) {
                return;
            }
        }

        TelegramMessageHandler.handle(message);
    }

    public SendMessage.SendMessageBuilder prepareMessageForChat(){
        var message = SendMessage.builder().chatId(TelegramChain.getConfig().telegramChatId);

        if(TelegramChain.getConfig().telegramThreadId != 0){
            message.messageThreadId(TelegramChain.getConfig().telegramThreadId);
        }

        return message;
    };
}
