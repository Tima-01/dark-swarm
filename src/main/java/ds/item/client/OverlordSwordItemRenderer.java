package ds.item.client;

import ds.DarkSwarm;
import ds.item.custom.OverlordSwordItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class OverlordSwordItemRenderer extends GeoItemRenderer<OverlordSwordItem> {
    public OverlordSwordItemRenderer() {
        super(new OverlordSwordItemModel());
    }
}