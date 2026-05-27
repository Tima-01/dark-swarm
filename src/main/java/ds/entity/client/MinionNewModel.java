package ds.entity.client;

import net.minecraft.util.Identifier;
import ds.entity.custom.MinionNewEntity;
import software.bernie.geckolib.model.GeoModel;

public class MinionNewModel extends GeoModel<MinionNewEntity> {

    @Override
    public Identifier getModelResource(MinionNewEntity animatable) {
        return Identifier.of("dark-swarm", "geo/entity/minion_model_new.json");
    }

    @Override
    public Identifier getTextureResource(MinionNewEntity animatable) {
        return Identifier.of("dark-swarm", "textures/entity/new_minion/minion_model_new.png");
    }

    @Override
    public Identifier getAnimationResource(MinionNewEntity animatable) {
        return Identifier.of("dark-swarm", "animations/entity/minion_model_new.animation.json");
    }
}