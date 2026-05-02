package ds.mixin;

import ds.util.CustomItemModels;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Injects additional model loading behavior into {@link ModelLoader}.
 *
 * <p>This mixin ensures that custom 3D held-item models are preloaded during
 * Minecraft's model loading phase.
 *
 * <p>Without this, dynamically requested models may not be baked and can result
 * in missing model errors during rendering.
 *
 * <p>Injection point:
 * {@code ModelLoader.<init>} after vanilla item model loading begins.
 *
 * @see CustomItemModels
 */

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

	@Shadow
	protected abstract void loadItemModel(ModelIdentifier id);

	/**
	 * Loads all registered held-item models during model initialization.
	 *
	 * @param ci callback info
	 */
	@Inject(method = "<init>", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V",
			ordinal = 1
	))
	private void onInit(CallbackInfo ci) {
		CustomItemModels.HELD_MODELS.values().forEach(id ->
				this.loadItemModel(ModelIdentifier.ofInventoryVariant(id))
		);
	}
}