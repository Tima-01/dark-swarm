
package ds.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;

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
}
