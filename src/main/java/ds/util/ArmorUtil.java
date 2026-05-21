
package ds.util;

import ds.DarkSwarm;
import ds.item.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ArmorUtil {
    /**
     * Checks if a player is wearing a complete armor set (helmet, chestplate, leggings, boots)
     * of a specific armor material.
     *
     * @param material the {@link RegistryEntry} of the armor material to check for
     * @param player the player to check
     * @return {@code true} if the player is wearing a full set of the specified material, {@code false} otherwise
     */
    public static boolean hasCorrectArmorOn(RegistryEntry<ArmorMaterial> material, PlayerEntity player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if (!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }
        ArmorItem boots = ((ArmorItem)player.getInventory().getArmorStack(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmorStack(1).getItem());
        ArmorItem chestplate = ((ArmorItem)player.getInventory().getArmorStack(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmorStack(3).getItem());

        return helmet.getMaterial() == material &&
                chestplate.getMaterial() == material &&
                boots.getMaterial() == material &&
                leggings.getMaterial() == material;
    }
    /**
     * Checks if a player is wearing a complete armor set (helmet, chestplate, leggings, boots).
     * The armor material does not matter, only that all four slots are filled.
     *
     * @param player the player to check
     * @return {@code true} if the player is wearing any full armor set, {@code false} otherwise
     */
    public static boolean hasFullSetOfArmorOn(PlayerEntity player) {
        ItemStack boots = player.getInventory().getArmorStack(0);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack helmet = player.getInventory().getArmorStack(3);

        return !helmet.isEmpty() && !leggings.isEmpty() && !boots.isEmpty() && !chestplate.isEmpty();
    }

    public static boolean hasEnhancement(PlayerEntity player,
                                         EquipmentSlot slot,
                                         String enhancement) {

        ItemStack stack = player.getEquippedStack(slot);

        return hasEnhancement(stack, enhancement);
    }

    public static boolean hasEnhancement(ItemStack stack, String enhancement) {
        NbtComponent customData = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (customData == null) return false;

        NbtCompound root = customData.copyNbt();

        if (!root.contains(DarkSwarm.MOD_ID, NbtCompound.COMPOUND_TYPE))
            return false;

        NbtCompound modData = root.getCompound(DarkSwarm.MOD_ID);

        return modData.getBoolean(enhancement);
    }

    public static void registerEnhancementPredicate(Item item, String enhancement) {
        ModelPredicateProviderRegistry.register(
                item,
                Identifier.of(DarkSwarm.MOD_ID, enhancement),
                (stack, world, entity, seed) ->
                        ArmorUtil.hasEnhancement(stack, enhancement) ? 1.0f : 0.0f
        );
    }
}
