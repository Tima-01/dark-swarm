package ds.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import ds.util.ArmorHealthManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingEntityDamageMixin {

    @WrapMethod(method = "damage")
    private boolean darkSwarm$damage(DamageSource source, float amount, Operation<Boolean> original) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!(entity instanceof PlayerEntity player)) {
            return original.call(source, amount);
        }

        if (player.getWorld().isClient()) {
            return original.call(source, amount);
        }

        float armorHealth = ArmorHealthManager.getCurrentArmorHealth(player);

        if (armorHealth <= 0.0f) {
            return original.call(source, amount);
        }

        float absorbed = Math.min(amount, armorHealth);

        ArmorHealthManager.damageArmorHealth(player, absorbed);

        float remaining = amount - absorbed;

        if (remaining <= 0.0f) {
            return true;
        }

        return original.call(source, remaining);
    }
}