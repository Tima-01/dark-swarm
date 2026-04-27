package ds.block;

import ds.DarkSwarm;
import ds.block.custom.IceSpike;
import ds.block.custom.InlayTable;
import ds.block.custom.NetherSpike;
import ds.block.custom.SummoningCauldron;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks{

    public static final Block SUMMONING_CAULDRON = registerBlock("summoning_cauldron",
            new SummoningCauldron(AbstractBlock.Settings.create().strength(1f).requiresTool()));

    public static final Block INLAY_TABLE = registerBlock("inlay_table",
            new InlayTable(AbstractBlock.Settings.create().strength(1f).requiresTool()));

    public static final Block NETHER_SPIKE = registerBlock("nether_spike",
            new NetherSpike(AbstractBlock.Settings.create().strength(1f).requiresTool()));

    public static final Block ICE_SPIKE = registerBlock("ice_spike",
            new IceSpike(AbstractBlock.Settings.create().strength(1f).requiresTool()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(DarkSwarm.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(DarkSwarm.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ICE_SPIKE);
            entries.add(NETHER_SPIKE);
            entries.add(SUMMONING_CAULDRON);
            entries.add(INLAY_TABLE);
        });
    }
}
