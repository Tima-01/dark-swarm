package ds.block.entity;

import ds.DarkSwarm;
import ds.block.ModBlocks;
import ds.block.entity.custom.SummoningCauldronEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<SummoningCauldronEntity> SUMMONING_CAULDRON_ENTITY_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(DarkSwarm.MOD_ID, "summoning_cauldron_be"),
                    BlockEntityType.Builder.create(SummoningCauldronEntity::new, ModBlocks.SUMMONING_CAULDRON).build(null));

    public static void registerBlockEntities() {
        DarkSwarm.LOGGER.info("Registering Block Entities for " + DarkSwarm.MOD_ID);
    }
}
