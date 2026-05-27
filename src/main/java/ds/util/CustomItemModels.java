package ds.util;

import ds.DarkSwarm;
import ds.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
/**
 * Stores mappings for items that use separate 2D and 3D models.
 *
 * <p>This class provides centralized registration for custom-rendered items
 * that require:
 * <ul>
 *     <li>A flat 2D model for GUI, ground, or item frame rendering</li>
 *     <li>A full 3D model for first-person and third-person held rendering</li>
 * </ul>
 *
 * <p>The mappings are used by {@link ds.mixin.ItemRendererMixin} to dynamically
 * swap models depending on the render context.
 *
 * <p>New items can be added by calling {@link #register(Item, String, String)}.
 *
 * @see ds.mixin.ItemRendererMixin
 * @see ds.mixin.ModelLoaderMixin
 */
public class CustomItemModels {
    /**
     * Maps items to their 2D inventory model identifiers.
     */
    public static final Map<Item, Identifier> INVENTORY_MODELS = new HashMap<>();
    /**
     * Maps items to their 3D held model identifiers.
     */
    public static final Map<Item, Identifier> HELD_MODELS = new HashMap<>();

    static {
        register(ModItems.OVERLORD_SWORD, "overlord_sword", "overlord_sword_3d");
        register(ModItems.OVERLORD_WHIP, "overlord_whip", "overlord_whip_3d");
    }
    /**
     * Registers an item with separate inventory and held models.
     *
     * @param item the item being registered
     * @param inventoryModel the path of the 2D inventory model
     * @param heldModel the path of the 3D held model
     */
    private static void register(Item item, String inventoryModel, String heldModel) {
        INVENTORY_MODELS.put(item, Identifier.of(DarkSwarm.MOD_ID, inventoryModel));
        HELD_MODELS.put(item, Identifier.of(DarkSwarm.MOD_ID, heldModel));
    }
}