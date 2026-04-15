package ds.item.client;

import ds.item.custom.OverlordWhipItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class OverlordWhipItemRenderer extends GeoItemRenderer<OverlordWhipItem> {
    public OverlordWhipItemRenderer() {
        super(new OverlordWhipItemModel());
    }
}
