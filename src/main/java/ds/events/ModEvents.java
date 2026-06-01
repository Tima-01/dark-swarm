package ds.events;

import ds.entity.custom.MinionEntity;
import ds.events.custom.HealthWatcher;
import ds.util.MinionManager;
import ds.util.SummonData;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.Objects;

public class ModEvents {

    public static void registerEvents() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;

            List<SummonData> summons =
                    List.copyOf(MinionManager.get(player.getUuid()));

            for (SummonData data : summons) {
                Entity found = MinionManager.find(
                        data.entityId(),
                        Objects.requireNonNull(player.getServer())
                );

                if (found instanceof MinionEntity minion) {
                    minion.returnHealthToOwner();
                    minion.discard();
                }
            }
        });
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getPlayerManager()
                    .getPlayerList()
                    .forEach(
                            HealthWatcher::tick
                    );
        });
    }
}