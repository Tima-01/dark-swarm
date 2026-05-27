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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModArmorRenderer implements ArmorRenderer {

    // Base textures
    private static final Identifier SOUL =
            Identifier.of(DarkSwarm.MOD_ID, "textures/models/armor/soul_armor_layer_1.png");

    private static final Identifier SUMMONER_DIAMOND =
            Identifier.of(DarkSwarm.MOD_ID, "textures/models/armor/summoner_diamond_layer_1.png");

    private static final Identifier SUMMONER_GOLD =
            Identifier.of(DarkSwarm.MOD_ID, "textures/models/armor/summoner_gold_layer_1.png");

    private static final Identifier SUMMONER_IRON =
            Identifier.of(DarkSwarm.MOD_ID, "textures/models/armor/summoner_iron_layer_1.png");

    // Overlays
    private static final Identifier FIRE =
            Identifier.of(DarkSwarm.MOD_ID, "textures/models/armor/fire_stone_overlay.png");

    private static final Identifier ICE =
            Identifier.of(DarkSwarm.MOD_ID, "textures/models/armor/ice_stone_overlay.png");

    private BipedEntityModel<LivingEntity> armorModel;

    private BipedEntityModel<LivingEntity> getModel() {
        if (armorModel != null) return armorModel;

        var loader = MinecraftClient.getInstance().getEntityModelLoader();
        if (loader == null) return null;

        armorModel = new BipedEntityModel<>(
                loader.getModelPart(EntityModelLayers.PLAYER_OUTER_ARMOR)
        );

        return armorModel;
    }

    @Override
    public void render(MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers,
                       ItemStack stack,
                       LivingEntity entity,
                       EquipmentSlot slot,
                       int light,
                       BipedEntityModel<LivingEntity> contextModel) {

        if (slot != EquipmentSlot.CHEST) return;

        BipedEntityModel<LivingEntity> model = getModel();
        if (model == null) return;

        contextModel.copyBipedStateTo(model);

        model.setVisible(false);
        model.body.visible = true;
        model.rightArm.visible = true;
        model.leftArm.visible = true;

        Identifier baseTexture = getBaseTexture(stack);

        VertexConsumer base = vertexConsumers.getBuffer(
                RenderLayer.getArmorCutoutNoCull(baseTexture)
        );

        model.render(matrices, base, light, OverlayTexture.DEFAULT_UV);

        renderOverlayIfNeeded(stack, matrices, vertexConsumers, model, light, FIRE, "fire_enhanced");
        renderOverlayIfNeeded(stack, matrices, vertexConsumers, model, light, ICE, "ice_enhanced");
    }

    private Identifier getBaseTexture(ItemStack stack) {

        Item item = stack.getItem();

        if (item == ModItems.SOUL_CHESTPLATE) {
            return SOUL;
        }

        if (item == ModItems.SUMMONER_DIAMOND_CHESTPLATE) {
            return SUMMONER_DIAMOND;
        }

        if (item == ModItems.SUMMONER_GOLD_CHESTPLATE) {
            return SUMMONER_GOLD;
        }

        if (item == ModItems.SUMMONER_IRON_CHESTPLATE) {
            return SUMMONER_IRON;
        }

        return SOUL;
    }

    private void renderOverlayIfNeeded(ItemStack stack,
                                       MatrixStack matrices,
                                       VertexConsumerProvider vertexConsumers,
                                       BipedEntityModel<LivingEntity> model,
                                       int light,
                                       Identifier texture,
                                       String enhancement) {

        if (!ArmorUtil.hasEnhancement(stack, enhancement)) return;

        matrices.push();
        matrices.scale(1.01f, 1.01f, 1.01f);

        VertexConsumer consumer = vertexConsumers.getBuffer(
                RenderLayer.getEntityTranslucent(texture)
        );

        model.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
    }
}