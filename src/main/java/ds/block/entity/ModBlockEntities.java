package ds.block.entity;

import ds.DarkSwarm;
import ds.block.ModBlocks;
import ds.block.entity.custom.IceSpikeEntity;
import ds.block.entity.custom.InlayTableEntity;
import ds.block.entity.custom.NetherSpikeEntity;
import ds.block.entity.custom.SummoningCauldronEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<SummoningCauldronEntity> SUMMONING_CAULDRON_ENTITY_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(DarkSwarm.MOD_ID, "summoning_cauldron_be"),
                    BlockEntityType.Builder.create(SummoningCauldronEntity::new, ModBlocks.SUMMONING_CAULDRON).build(null));

    public static final BlockEntityType<InlayTableEntity> INLAY_TABLE_ENTITY_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(DarkSwarm.MOD_ID, "inlay_table_be"),
                    BlockEntityType.Builder.create(InlayTableEntity::new, ModBlocks.INLAY_TABLE).build(null));

    public static final BlockEntityType<NetherSpikeEntity> NETHER_SPIKE_ENTITY_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(DarkSwarm.MOD_ID, "nether_spike_be"),
                    BlockEntityType.Builder.create(NetherSpikeEntity::new, ModBlocks.NETHER_SPIKE).build(null));

    public static final BlockEntityType<IceSpikeEntity> ICE_SPIKE_ENTITY_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(DarkSwarm.MOD_ID, "ice_spike_be"),
                    BlockEntityType.Builder.create(IceSpikeEntity::new, ModBlocks.ICE_SPIKE).build(null));

    public static void registerBlockEntities() {}
}
