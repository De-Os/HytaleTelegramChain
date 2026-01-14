package dev.deos;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class TelegramChainConfig{
    public static final BuilderCodec<TelegramChainConfig> CODEC = BuilderCodec.builder(TelegramChainConfig.class, TelegramChainConfig::new)
            .append(new KeyedCodec<String>("TelegramBotToken", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.telegramBotToken = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.telegramBotToken).add()
            .append(new KeyedCodec<Long>("TelegramChatId", Codec.LONG),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.telegramChatId = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.telegramChatId).add()
            .append(new KeyedCodec<Integer>("TelegramThreadId", Codec.INTEGER),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.telegramThreadId = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.telegramThreadId).add()
            .append(new KeyedCodec<Boolean>("BroadcastToTelegram", Codec.BOOLEAN),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.broadcastToTelegram = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.broadcastToTelegram).add()
            .append(new KeyedCodec<Boolean>("BroadcastFromTelegram", Codec.BOOLEAN),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.broadcastFromTelegram = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.broadcastFromTelegram).add()
            .append(new KeyedCodec<String>("MessageServerParseMode", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.messageServerParseMode = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.messageServerParseMode).add()
            .append(new KeyedCodec<String>("MessageTelegramBoilerplate", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.messageTelegramBoilerplate = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.messageTelegramBoilerplate).add()
            .append(new KeyedCodec<String>("MessageServerStarted", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.messageServerStarted = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.messageServerStarted).add()
            .append(new KeyedCodec<String>("MessageServerPlayerJoinedBoilerplate", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.messageServerPlayerJoinedBoilerplate = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.messageServerPlayerJoinedBoilerplate).add()
            .append(new KeyedCodec<String>("MessageServerPlayerQuitBoilerplate", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.messageServerPlayerQuitBoilerplate = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.messageServerPlayerQuitBoilerplate).add()
            .append(new KeyedCodec<String>("MessageServerBoilerplate", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.messageServerBoilerplate = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.messageServerBoilerplate).add()
            .append(new KeyedCodec<String>("MessageServerPlayerDiedBoilerplate", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.messageServerPlayerDiedBoilerplate = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.messageServerPlayerDiedBoilerplate).add()
            .append(new KeyedCodec<String>("MessageTelegramPlayersListBoilerplate", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.messageTelegramPlayersListBoilerplate = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.messageTelegramPlayersListBoilerplate).add()
            .append(new KeyedCodec<String>("MessageTelegramPlayersListEmpty", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.messageTelegramPlayersListEmpty = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.messageTelegramPlayersListEmpty).add()
            .append(new KeyedCodec<String>("MessageTelegramPlayersListEntryBoilerplate", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.messageTelegramPlayersListEntryBoilerplate = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.messageTelegramPlayersListEntryBoilerplate).add()
            .append(new KeyedCodec<String>("CommandTelegramPlayersList", Codec.STRING),
                    (TelegramChainConfig, val, extraInfo) -> TelegramChainConfig.commandTelegramPlayersList = val,
                    (TelegramChainConfig, extraInfo) -> TelegramChainConfig.commandTelegramPlayersList).add()
            .build();

    public String telegramBotToken = "";
    public Long telegramChatId = 0L;
    public int telegramThreadId = 0;
    public boolean broadcastToTelegram = true;
    public boolean broadcastFromTelegram = true;
    public String messageServerParseMode = "HTML";

    public String messageTelegramBoilerplate = ">TG [${nickname}]: ${text}";
    public String messageServerStarted = "<b><i>Server started!</i></b>";
    public String messageServerPlayerJoinedBoilerplate = "<b>${nickname}</b> joined the server! There are ${currentPlayers} of ${totalPlayers} players";
    public String messageServerPlayerQuitBoilerplate = "<b>${nickname}</b> disconnected. There are ${currentPlayers} of ${totalPlayers} players";
    public String messageServerBoilerplate = "<b>[${nickname}]</b>: ${text}";
    public String messageServerPlayerDiedBoilerplate = "<b>${nickname}</b> was killed by ${killer}";

    public String messageTelegramPlayersListBoilerplate = "There are ${currentPlayers} of ${totalPlayers} players total:\n\n${playersList}";
    public String messageTelegramPlayersListEmpty = "There are no players connected";
    public String messageTelegramPlayersListEntryBoilerplate = "${number}. ${nickname}\n";
    public String commandTelegramPlayersList = "/players";
}