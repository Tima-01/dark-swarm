package ds.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import ds.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    private static final Identifier CREEPER_ID
            = Identifier.of("minecraft", "entities/creeper");
    private static final Identifier ZOMBIE_ID
            = Identifier.of("minecraft", "entities/zombie");
    private static final Identifier SKELETON_ID
            = Identifier.of("minecraft", "entities/skeleton");
    private static final Identifier ENDERMAN_ID
            = Identifier.of("minecraft", "entities/enderman");
    private static final Identifier SPIDER_ID
            = Identifier.of("minecraft", "entities/spider");
    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registry) -> {
            if(CREEPER_ID.equals(key.getValue()) || ZOMBIE_ID.equals(key.getValue()) || SKELETON_ID.equals(key.getValue()) || ENDERMAN_ID.equals(key.getValue()) || SPIDER_ID.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.75f)) // Drops 75% of the time
                        .with(ItemEntry.builder(ModItems.SOUL))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
