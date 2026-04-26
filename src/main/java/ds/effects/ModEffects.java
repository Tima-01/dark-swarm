package ds.effects;

import ds.DarkSwarm;
import ds.effects.custom.BlazingAura;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> BLAZING_AURA = registerStatusEffect("blazing_aura",
            new BlazingAura(StatusEffectCategory.BENEFICIAL, 0xf05e0a));

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(DarkSwarm.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {}
}
