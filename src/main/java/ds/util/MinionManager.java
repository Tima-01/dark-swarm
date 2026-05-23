package ds.util;

import ds.entity.custom.MinionEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.*;

public class MinionManager {

    private static final Map<UUID, Deque<SummonData>> PLAYER_SUMMONS =
            new HashMap<>();


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
        EntityAttributeInstance maxHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        if (maxHealth == null) return;

        float max = (float) maxHealth.getValue();
        float reserved = 0;

        for (SummonData data : get(player.getUuid())) {
            reserved += data.healthCost();
        }

        float available = max - reserved;

        while (available < 20f) {
            SummonData removed = pop(player.getUuid());

            if (removed == null) break;

            Entity entity = find(removed.entityId(), player.getServer());

            if (entity instanceof MinionEntity minion) {
                available += removed.healthCost();

                minion.returnHealthToOwner();
                minion.discard();
            }
        }
    }
}