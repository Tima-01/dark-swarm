package ds.events.custom;

import ds.util.MinionManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HealthWatcher {

    private static final Map<UUID, Float> LAST_HEALTH = new HashMap<>();

    public static void register() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, player, alive) -> {
                LAST_HEALTH.remove(player.getUuid());
            }
        );
    }

    public static void tick(ServerPlayerEntity player) {
        float current = player.getMaxHealth();

        Float previous = LAST_HEALTH.put(player.getUuid(), current);

        if (previous == null) return;

        if (current < previous) MinionManager.validateSummons(player);
    }
}