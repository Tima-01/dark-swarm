package ds.entity;

import ds.entity.custom.MinionEntity;
import ds.entity.custom.SoulEaterEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<MinionEntity> MINION = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("dark-swarm", "minion"),
            EntityType.Builder.create(MinionEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6f, 1.8f)
                    .build()
    );

    public static final EntityType<SoulEaterEntity> SOUL_EATER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("dark-swarm", "soul_eater"),
            EntityType.Builder.create(SoulEaterEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 1.8f)
                    .build()
    );
    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(MINION, MinionEntity.createMinionAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SOUL_EATER, SoulEaterEntity.createAttributes());
    }
}
