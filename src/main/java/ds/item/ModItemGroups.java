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
                        entries.add(ModBlocks.SUMMONING_CAULDRON);
                        entries.add(ModItems.MINION_SPAWN_EGG);
                    }).build());

    public static void registerItemGroups() {
        DarkSwarm.LOGGER.info("Registering Mod Item Groups");
    }
}
