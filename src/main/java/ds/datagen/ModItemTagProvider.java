package ds.datagen;

import ds.item.ModItems;
import ds.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.SOUL_BOOTS)
                .add(ModItems.SOUL_CHESTPLATE)
                .add(ModItems.SOUL_LEGGINGS)
                .add(ModItems.SOUL_HELMET);
        getOrCreateTagBuilder(ItemTags.WEAPON_ENCHANTABLE)
                .add(ModItems.OVERLORD_WHIP);
        getOrCreateTagBuilder(ModTags.Items.WHIP_ENCHANTABLE)
                .add(ModItems.OVERLORD_WHIP);
    }
}
