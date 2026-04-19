package ds.entity.client;

import ds.DarkSwarm;
import ds.entity.custom.SoulEaterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SoulEaterModel extends GeoModel <SoulEaterEntity> {
    @Override
    public Identifier getModelResource(SoulEaterEntity soulEaterEntity) {
        return Identifier.of(DarkSwarm.MOD_ID, "geo/entity/soul_eater.geo.json");
    }

    @Override
    public Identifier getTextureResource(SoulEaterEntity soulEaterEntity) {
        return Identifier.of(DarkSwarm.MOD_ID, "textures/entity/soul_eater/soul_eater.png");
    }

    @Override
    public Identifier getAnimationResource(SoulEaterEntity soulEaterEntity) {
        return Identifier.of(DarkSwarm.MOD_ID, "animations/entity/soul_eater.animation.json");
    }
}
