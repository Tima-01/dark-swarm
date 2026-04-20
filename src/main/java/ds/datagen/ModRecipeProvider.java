package ds.datagen;

import ds.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.OVERLORD_WHIP)
                .pattern(" KI")
                .pattern("I  ")
                .pattern(" KH")
                .input('I', Items.IRON_INGOT)
                .input('K', Items.LEATHER)
                .input('H', ModItems.HANDLE)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        /** Recipe generation for crafting custom modded armor, uses ShapedRecipeJsonBuilder to then  define the shape/pattern of the craftable item
         for example the helmet is:
         SSS
         S S
         */
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.SOUL_HELMET)
                .pattern("AAA")
                .pattern("A A")
                .input('A', ModItems.SOUL)
                .criterion("has_soul", InventoryChangedCriterion.Conditions.items(ModItems.SOUL))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.SOUL_CHESTPLATE)
                        .pattern("A A")
                        .pattern("AAA")
                        .pattern("AAA")
                        .input('A', ModItems.SOUL)
                        .criterion("has_soul", InventoryChangedCriterion.Conditions.items(ModItems.SOUL))
                        .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.SOUL_LEGGINGS)
                        .pattern("AAA")
                        .pattern("A A")
                        .pattern("A A")
                        .input('A', ModItems.SOUL)
                        .criterion("has_soul", InventoryChangedCriterion.Conditions.items(ModItems.SOUL))
                        .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.SOUL_BOOTS)
                        .pattern("A A")
                        .pattern("A A")
                        .input('A', ModItems.SOUL)
                        .criterion("has_soul", InventoryChangedCriterion.Conditions.items(ModItems.SOUL))
                        .offerTo(exporter);

        SmithingTransformRecipeJsonBuilder.create(Ingredient.EMPTY, Ingredient.ofItems(Items.STICK), Ingredient.ofItems(ModItems.SOUL), RecipeCategory.MISC, ModItems.HANDLE)
                .criterion("has_soul", InventoryChangedCriterion.Conditions.items(ModItems.SOUL))
                .criterion("has_stick", InventoryChangedCriterion.Conditions.items(Items.STICK))
                .offerTo(exporter, "handle_recipe");
    }
}
