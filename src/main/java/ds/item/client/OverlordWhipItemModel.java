package ds.item.client;

import ds.DarkSwarm;
import ds.item.custom.OverlordWhipItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class OverlordWhipItemModel extends GeoModel<OverlordWhipItem> {
    @Override
    public Identifier getModelResource(OverlordWhipItem overlordWhipItem) {
        return Identifier.of(DarkSwarm.MOD_ID, "geo/item/overlord_whip.geo.json");
    }

    @Override
    public Identifier getTextureResource(OverlordWhipItem overlordWhipItem) {
        return Identifier.of(DarkSwarm.MOD_ID, "textures/item/overlord_whip_texture.png");
    }

    @Override
    public Identifier getAnimationResource(OverlordWhipItem overlordWhipItem) {
        return Identifier.of(DarkSwarm.MOD_ID, "animations/item/overlord_whip_animation.json");
    }


}
