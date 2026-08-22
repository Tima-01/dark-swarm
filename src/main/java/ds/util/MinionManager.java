package ds.util;

import ds.DarkSwarm;
import ds.entity.custom.SummonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MinionManager {
    public static final Logger LOGGER = LoggerFactory.getLogger(DarkSwarm.MOD_ID);
    private static final Set<UUID> VALIDATING = new HashSet<>();
    private static final Map<UUID, Deque<SummonData>> PLAYER_SUMMONS = new HashMap<>();

    public static void remove(UUID playerId, UUID entityId) {
        Deque<SummonData> summons = PLAYER_SUMMONS.get(playerId);

        if (summons == null) return;

        summons.removeIf(data -> data.entityId().equals(entityId));
        if (summons.isEmpty()) PLAYER_SUMMONS.remove(playerId);
    }
}