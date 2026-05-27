package ds.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import ds.entity.custom.MinionNewEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MinionNewRenderer extends GeoEntityRenderer<MinionNewEntity> {

    public MinionNewRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MinionNewModel());
    }

    @Override
    public Identifier getTextureLocation(MinionNewEntity animatable) {
        // Продублируем путь к текстуре для рендерера
        return Identifier.of("dark-swarm", "textures/entity/new_minion/minion_model_new.png");
    }
}
