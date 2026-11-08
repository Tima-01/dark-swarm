package ds.events;

import ds.util.ArmorHealthManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ModEvents {

    public static void registerEvents() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getPlayerManager()
                    .getPlayerList()
                    .forEach(ArmorHealthManager::tick);
        });
    }
}