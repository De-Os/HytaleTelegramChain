package dev.deos.handlers;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.*;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.deos.TelegramChain;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashMap;

import static dev.deos.handlers.ServerMessageHandler.sendToTelegram;

public class DeathHandler extends DeathSystems.OnDeathSystem {
    @Nonnull
    @Override
    public Query getQuery() {
        return Player.getComponentType();
    }

    @Override
    public void onComponentAdded(
            @NotNull Ref<EntityStore> ref,
            @NotNull DeathComponent deathComponent,
            @NotNull Store<EntityStore> store,
            @NotNull CommandBuffer<EntityStore> commandBuffer
    ) {
        Player player = store.getComponent(ref, Player.getComponentType());

        Damage deathInfo = deathComponent.getDeathInfo();
        DamageCause cause = deathComponent.getDeathCause();

        TelegramChain.LOGGER.info(cause.getInherits());

        Player killer = null;
        if (deathInfo != null && deathInfo.getSource() instanceof Damage.EntitySource source) {
            Ref<EntityStore> killerRef = source.getRef();
            if (killerRef.isValid()) {
                killer = store.getComponent(killerRef, Player.getComponentType());
            }
        }

        if (TelegramChain.getConfig().messageServerPlayerDiedBoilerplate.isEmpty()) {
            return;
        }

        var killerName = killer == null ? "???" : killer.getDisplayName();

        var values = new HashMap<String, String>();
        values.put("${nickname}", player.getDisplayName());
        values.put("${killer}", killerName);

        sendToTelegram(values, TelegramChain.getConfig().messageServerPlayerDiedBoilerplate);
    }
}
