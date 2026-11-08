package ds.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import ds.util.ArmorHealthManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingEntityHealMixin {

    @WrapMethod(method = "heal")
    private void darkSwarm$heal(float amount, Operation<Void> original) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!(entity instanceof PlayerEntity player)) {
            original.call(amount);
            return;
        }

        if (player.getWorld().isClient()) {
            original.call(amount);
            return;
        }

        if (amount <= 0.0f) {
            return;
        }

        float healedArmor = ArmorHealthManager.healArmorHealth(player, amount);

        float remaining = amount - healedArmor;

        if (remaining > 0.0f) {
            original.call(remaining);
        }
    }
}