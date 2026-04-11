package ds.item.client;

import ds.item.custom.OverlordSwordItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class OverlordSwordRenderer extends GeoItemRenderer<OverlordSwordItem> {
    public OverlordSwordRenderer() {
        super(new OverlordSwordModel());
    }
}