package ds.entity.client;

import ds.entity.custom.SoulEaterEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SoulEaterRenderer extends GeoEntityRenderer <SoulEaterEntity>{
    public SoulEaterRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SoulEaterModel());
    }
}
