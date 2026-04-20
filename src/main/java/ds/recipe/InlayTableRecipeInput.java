package ds.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.input.RecipeInput;

public record InlayTableRecipeInput(ItemStack input) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }
}
