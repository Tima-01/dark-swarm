package ds.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record MinionCountPayload(int count) implements CustomPayload {
    public static final Id<MinionCountPayload> ID = new Id<>(Identifier.of("darkswarm", "minion_count_sync"));


    public static final PacketCodec<RegistryByteBuf, MinionCountPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, MinionCountPayload::count,
            MinionCountPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}