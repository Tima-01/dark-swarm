package ds.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class ItemUtil {
    public static int getLevel(World world, ItemStack stack, RegistryKey<Enchantment> key) {
        RegistryEntry<Enchantment> ench =
                world.getRegistryManager()
                        .get(RegistryKeys.ENCHANTMENT)
                        .entryOf(key);

        return EnchantmentHelper.getLevel(ench, stack);
    }
}
