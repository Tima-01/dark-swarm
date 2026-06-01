package ds.util;

import ds.DarkSwarm;
import ds.entity.custom.MinionEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MinionManager {
    public static final Logger LOGGER = LoggerFactory.getLogger(DarkSwarm.MOD_ID);
    private static final Set<UUID> VALIDATING = new HashSet<>();
    private static final Map<UUID, Deque<SummonData>> PLAYER_SUMMONS = new HashMap<>();


    public static void push(UUID playerId, UUID entityId, float healthCost) {
        PLAYER_SUMMONS.computeIfAbsent(playerId, p -> new ArrayDeque<>())
                .push(new SummonData(entityId, healthCost));
    }


    public static SummonData pop(UUID playerId) {
        Deque<SummonData> summons = PLAYER_SUMMONS.get(playerId);

        if (summons == null || summons.isEmpty()) return null;

        SummonData removed = summons.pop();

        if (summons.isEmpty()) PLAYER_SUMMONS.remove(playerId);

        return removed;
    }


    public static void remove(UUID playerId, UUID entityId) {
        Deque<SummonData> summons = PLAYER_SUMMONS.get(playerId);

        if (summons == null) return;

        summons.removeIf(data -> data.entityId().equals(entityId));
        if (summons.isEmpty()) PLAYER_SUMMONS.remove(playerId);
    }

    public static Deque<SummonData> get(UUID playerId) {
        return PLAYER_SUMMONS.getOrDefault(playerId, new ArrayDeque<>());
    }

    public static Entity find(UUID uuid, MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            Entity entity = world.getEntity(uuid);

            if (entity != null) return entity;
        }
        return null;
    }


    public static void validateSummons(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();

        if (!VALIDATING.add(uuid)) return;
        try {
            EntityAttributeInstance playerHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            if (playerHealth == null) return;

            float currentHealth = (float) playerHealth.getValue();

            float reserved = 0f;

            for (SummonData data : get(uuid)) {
                reserved += data.healthCost();
            }

            float realMaxHealth = currentHealth + reserved;
            float bonusHealth = Math.max(0f, realMaxHealth - 20f);

            LOGGER.info("current=" + currentHealth + ", reserved=" + reserved + ", real=" + realMaxHealth + ", bonus=" + bonusHealth);

            while (reserved > bonusHealth) {
                SummonData removed = pop(uuid);
                if (removed == null) break;
                reserved -= removed.healthCost();
                Entity entity = find(removed.entityId(), player.getServer());

                if (entity instanceof MinionEntity minion) {
                    minion.returnHealthToOwner();
                    minion.discard();
                }
            }

        } finally {
            VALIDATING.remove(uuid);
        }
    }
}