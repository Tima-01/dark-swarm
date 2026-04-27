package ds.item;

import ds.DarkSwarm;
import ds.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup DARK_SWARM = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DarkSwarm.MOD_ID, "dark_swarm_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.SOUL))
                    .displayName(Text.translatable("itemgroup.dark-swarm.dark_swarm_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.SOUL);
                        entries.add(ModItems.PROFANED_SOUL);
                        entries.add(ModItems.FIRE_STONE);
                        entries.add(ModItems.ICE_STONE);
                        entries.add(ModItems.ICE_ESSENCE);
                        entries.add(ModItems.FIRE_ESSENCE);
                        entries.add(ModBlocks.SUMMONING_CAULDRON);
                        entries.add(ModBlocks.INLAY_TABLE);
                        entries.add(ModBlocks.NETHER_SPIKE);
                        entries.add(ModBlocks.ICE_SPIKE);
                        entries.add(ModItems.MINION_SPAWN_EGG);
                        entries.add(ModItems.SOUL_EATER_SPAWN_EGG);
                        entries.add(ModItems.OVERLORD_SWORD);
                        entries.add(ModItems.OVERLORD_WHIP);
                        entries.add(ModItems.HANDLE);

                        entries.add(ModItems.SOUL_HELMET);
                        entries.add(ModItems.SOUL_CHESTPLATE);
                        entries.add(ModItems.SOUL_BOOTS);
                        entries.add(ModItems.SOUL_LEGGINGS);
                    }).build());

    public static void registerItemGroups() {}
}
