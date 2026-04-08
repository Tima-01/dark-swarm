package ds.networking;

import ds.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModMessages {
    public static void registerPayloads() {
        PayloadTypeRegistry.playS2C().register(MinionCountPayload.ID, MinionCountPayload.CODEC);
    }

    public static void sendMinionCountSync(ServerPlayerEntity player, int count) {
        ServerPlayNetworking.send(player, new MinionCountPayload(count));
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(MinionCountPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                if (context.client().player instanceof IEntityDataSaver saver) {
                    saver.darkswarm$setMinionCount(payload.count());
                }
            });
        });
    }
}