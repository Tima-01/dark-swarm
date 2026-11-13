package ds.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ArmorHealthS2CPayload(float health) implements CustomPayload {
    public static final Id<ArmorHealthS2CPayload> ID = new Id<>(Identifier.of("dark-swarm", "armor_health"));
    public static final PacketCodec<RegistryByteBuf, ArmorHealthS2CPayload> CODEC = PacketCodec.tuple(PacketCodecs.FLOAT, ArmorHealthS2CPayload::health, ArmorHealthS2CPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}