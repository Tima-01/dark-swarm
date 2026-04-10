package ds.entity;

import ds.entity.custom.MinionEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
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

    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(MINION, MinionEntity.createMinionAttributes());
    }
}
