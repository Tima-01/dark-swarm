package ds.item.client;

import ds.DarkSwarm;
import ds.item.custom.OverlordSwordItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class OverlordSwordModel extends GeoModel<OverlordSwordItem> {
    @Override
    public Identifier getModelResource(OverlordSwordItem animatable) {
        return Identifier.of(DarkSwarm.MOD_ID, "geo/item/overlord_sword.geo.json");
    }

    @Override
    public Identifier getTextureResource(OverlordSwordItem animatable) {
        return Identifier.of(DarkSwarm.MOD_ID, "textures/item/overlord_sword.png");
    }

    @Override
    public Identifier getAnimationResource(OverlordSwordItem animatable) {
        return Identifier.of(DarkSwarm.MOD_ID, "animations/item/overlord_sword.animation.json");
    }
}
