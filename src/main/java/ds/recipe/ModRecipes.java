package ds.recipe;

import ds.DarkSwarm;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModRecipes {
    public static final RecipeSerializer<InlayTableRecipe> INLAY_TABLE_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(DarkSwarm.MOD_ID, "inlay_table"),
                new InlayTableRecipe.Serializer());

    public static final RecipeType<InlayTableRecipe> INLAY_TABLE_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(DarkSwarm.MOD_ID, "inlay_table"),
                new RecipeType<InlayTableRecipe>() {
                    @Override
                    public String toString() {
                        return "inlay_table";
                    }
                }
            );
    public static void registerRecipes() {}
}
