package ds.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import ds.util.CustomItemModels;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Injects custom item model swapping behavior into {@link ItemRenderer}.
 *
 * <p>This mixin dynamically replaces an item's baked model depending on the render context:
 * <ul>
 *     <li>GUI, ground, and fixed transforms use the 2D inventory model</li>
 *     <li>Held rendering uses the 3D model</li>
 * </ul>
 *
 * <p>This enables items to appear like vanilla flat icons in inventories while rendering
 * as full 3D objects in the player's hand.
 *
 * <p>Injection points:
 * <ul>
 *     <li>{@code renderItem()} at {@code @At("HEAD")} modifies the incoming baked model for GUI-like rendering</li>
 *     <li>{@code getModel()} at {@code @At("STORE")} modifies the selected model for held rendering</li>
 * </ul>
 *
 * @see CustomItemModels
 */

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

	@Shadow @Final
	private ItemModels models;

	@Shadow
	public abstract ItemModels getModels();
	/**
	 * Replaces the baked model for GUI, ground, and fixed rendering contexts.
	 *
	 * @param bakedModel the original model
	 * @param stack the item stack being rendered
	 * @param renderMode the current render transformation mode
	 * @return the replacement model if registered, otherwise the original
	 */
	@ModifyVariable(
			method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
			at = @At("HEAD"),
			argsOnly = true
	)
	public BakedModel renderItem(BakedModel bakedModel,
	                             @Local(argsOnly = true) ItemStack stack,
	                             @Local(argsOnly = true) ModelTransformationMode renderMode) {

		if (renderMode == ModelTransformationMode.GUI
				|| renderMode == ModelTransformationMode.GROUND
				|| renderMode == ModelTransformationMode.FIXED) {

			Identifier id = CustomItemModels.INVENTORY_MODELS.get(stack.getItem());
			if (id != null) {
				return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(id));
			}
		}

		return bakedModel;
	}
	/**
	 * Replaces the baked model for held item rendering.
	 *
	 * @param bakedModel the original model
	 * @param stack the item stack being rendered
	 * @return the replacement 3D model if registered, otherwise the original
	 */
	@ModifyVariable(
			method = "getModel",
			at = @At(value = "STORE"),
			ordinal = 1
	)
	public BakedModel getHeldItemModelMixin(BakedModel bakedModel,
	                                        @Local(argsOnly = true) ItemStack stack) {

		Identifier id = CustomItemModels.HELD_MODELS.get(stack.getItem());
		if (id != null) {
			return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(id));
		}

		return bakedModel;
	}
}