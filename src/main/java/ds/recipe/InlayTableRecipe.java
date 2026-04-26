package ds.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public record InlayTableRecipe(
        Ingredient soul,
        Ingredient property,
        Ingredient material,
        ItemStack result
) implements Recipe<InlayTableRecipeInput> {

    @Override
    public boolean matches(InlayTableRecipeInput input, World world) {
        if (world.isClient()) return false;

        return soul.test(input.getStackInSlot(0))
                && property.test(input.getStackInSlot(1))
                && material.test(input.getStackInSlot(2));
    }

    @Override
    public ItemStack craft(InlayTableRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return result.copy();
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup lookup) {
        return result;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(soul);
        list.add(property);
        list.add(material);
        return list;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.INLAY_TABLE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.INLAY_TABLE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<InlayTableRecipe> {

        public static final MapCodec<InlayTableRecipe> CODEC =
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("soul").forGetter(InlayTableRecipe::soul),
                        Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("property").forGetter(InlayTableRecipe::property),
                        Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("material").forGetter(InlayTableRecipe::material),
                        ItemStack.CODEC.fieldOf("result").forGetter(InlayTableRecipe::result)
                ).apply(inst, InlayTableRecipe::new));

        public static final PacketCodec<RegistryByteBuf, InlayTableRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, InlayTableRecipe::soul,
                        Ingredient.PACKET_CODEC, InlayTableRecipe::property,
                        Ingredient.PACKET_CODEC, InlayTableRecipe::material,
                        ItemStack.PACKET_CODEC, InlayTableRecipe::result,
                        InlayTableRecipe::new
                );

        @Override
        public MapCodec<InlayTableRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, InlayTableRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }
}