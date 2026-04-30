package ds.mixin;

import ds.item.ModItems;
import ds.util.ArmorUtil;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ArmorFeatureRenderer.class)
public abstract class EnhancedChestplateOverlayMixin {
    private static final Identifier SOUL_CHESTPLATE_FIRE_OVERLAY =
            Identifier.of("minecraft", "textures/trims/models/armor/redstone.png");

    @Invoker("renderArmorParts")
    public abstract void darkSwarm$renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                                           BipedEntityModel<?> model, int color, Identifier identifier);

    @Inject(
            method = "renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V",
            at = @At("TAIL")
    )
    private void darkSwarm$renderFireEnhancedOverlay(MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                                     LivingEntity entity, EquipmentSlot armorSlot, int light,
                                                     BipedEntityModel<?> model, CallbackInfo ci) {
        if (armorSlot != EquipmentSlot.CHEST) return;

        ItemStack stack = entity.getEquippedStack(armorSlot);
        if (!stack.isOf(ModItems.SOUL_CHESTPLATE)) return;
        if (!ArmorUtil.isFireEnhanced(stack)) return;

        // White tint; overlay texture should be mostly transparent.
        this.darkSwarm$renderArmorParts(matrices, vertexConsumers, light, model, 0xFFFFFF, SOUL_CHESTPLATE_FIRE_OVERLAY);
    }
}

