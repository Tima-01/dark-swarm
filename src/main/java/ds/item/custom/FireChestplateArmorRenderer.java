package ds.item.custom;

import ds.DarkSwarm;
import ds.item.ModItems;
import ds.util.ArmorUtil;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class FireChestplateArmorRenderer implements ArmorRenderer {

    private static final Identifier SOUL_LAYER_1 =
            Identifier.of(DarkSwarm.MOD_ID, "textures/models/armor/soul_armor_layer_1.png");

    private static final Identifier OVERLAY =
            Identifier.of(DarkSwarm.MOD_ID, "textures/models/armor/fire_stone_overlay.png");

    private BipedEntityModel<LivingEntity> armorModel;

    private BipedEntityModel<LivingEntity> getArmorModel() {
        if (armorModel != null) return armorModel;

        // During early init the loader can be null; only create when rendering actually happens.
        var loader = MinecraftClient.getInstance().getEntityModelLoader();
        if (loader == null) return null;

        armorModel = new BipedEntityModel<>(loader.getModelPart(EntityModelLayers.PLAYER_OUTER_ARMOR));
        return armorModel;
    }

    @Override
    public void render(MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers,
                       ItemStack stack,
                       LivingEntity entity,
                       EquipmentSlot slot, int light,
                       BipedEntityModel<LivingEntity> contextModel) {

        if (slot != EquipmentSlot.CHEST) return;
        if (!stack.isOf(ModItems.SOUL_CHESTPLATE)) return;

        BipedEntityModel<LivingEntity> armorModel = getArmorModel();
        if (armorModel == null) return;

        contextModel.copyBipedStateTo(armorModel);

        armorModel.setVisible(false);
        armorModel.body.visible = true;
        armorModel.rightArm.visible = true;
        armorModel.leftArm.visible = true;

        // Base armor layer (vanilla would normally render this; registering an ArmorRenderer overrides it).
        VertexConsumer baseVc = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(SOUL_LAYER_1));
        armorModel.render(matrices, baseVc, light, OverlayTexture.DEFAULT_UV);

        // Enhancement overlay.
        if (ArmorUtil.isFireEnhanced(stack)) {
            VertexConsumer overlayVc = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(OVERLAY));
            armorModel.render(matrices, overlayVc, light, OverlayTexture.DEFAULT_UV);
        }
    }
}