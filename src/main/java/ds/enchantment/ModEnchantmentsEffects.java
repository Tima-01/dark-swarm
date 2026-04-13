package ds.enchantment;

import com.mojang.serialization.MapCodec;
import ds.DarkSwarm;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantmentsEffects {

    private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name,
                                                                                    MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.tryParse(DarkSwarm.MOD_ID, name), codec);
    };

    public static void registerEnchantmentEffects() {
    }
}
