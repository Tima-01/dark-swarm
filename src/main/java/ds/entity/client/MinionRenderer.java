package ds.entity.client;

import ds.entity.custom.MinionEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class MinionRenderer extends MobEntityRenderer<MinionEntity, MinionModel<MinionEntity>> {

    private static final Identifier TEXTURE = Identifier.of("darkswarm", "textures/entity/minion/minion.png");

    public MinionRenderer(EntityRendererFactory.Context context) {

        super(context, new MinionModel<>(context.getPart(MinionModel.LAYER_LOCATION)), 1.0f);
    }

    @Override
    public Identifier getTexture(MinionEntity entity) {
        return TEXTURE;
    }
}