package ds.screen.custom;

import ds.entity.ModEntities;
import ds.item.ModItems;
import ds.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class SoulExtractorScreenHandler extends ScreenHandler {

    private static final float SOUL_COST = 2.0f;
    private static final float MIN_MAX_HEALTH = 2.0f;

    private final Inventory inventory;
    private final BlockPos pos;

    private boolean notEnoughHealth = false;
    private boolean outputFull = false;


    public SoulExtractorScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, pos, playerInventory.player.getWorld().getBlockEntity(pos));
    }

    public SoulExtractorScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos, BlockEntity blockEntity) {
        super(ModScreenHandlers.SOUL_EXTRACTOR_SCREEN_HANDLER, syncId);

        this.pos = pos;
        this.inventory = (Inventory) blockEntity;

//        Soul Slot
        this.addSlot(
                new Slot(inventory, 0, 92, 35) {

                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return stack.isOf(ModItems.SOUL);
                    }

                    @Override
                    public boolean canTakeItems(PlayerEntity playerEntity) {
                        return true;
                    }

                    @Override
                    public int getMaxItemCount() {
                        return 64;
                    }
                }
        );

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
    }


    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id != 0) return super.onButtonClick(player, id);

        if (player.getWorld().isClient()) return true;

        notEnoughHealth = false;
        outputFull = false;

        ItemStack output = inventory.getStack(0);

        if (!output.isEmpty() && output.isOf(ModItems.SOUL) && output.getCount() >= output.getMaxCount()) {
            outputFull = true;
            sendContentUpdates();
            return true;
        }

        if (!output.isEmpty() && !output.isOf(ModItems.SOUL)) {
            outputFull = true;
            sendContentUpdates();
            return true;
        }

        if (player.getMaxHealth() - SOUL_COST < MIN_MAX_HEALTH) {
            notEnoughHealth = true;
            sendContentUpdates();
            return true;
        }

        EntityAttributeInstance maxHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        if (maxHealth == null) return true;

        Identifier modifierId = Identifier.of(
                "dark-swarm",
                "soul_" + UUID.randomUUID()
        );

        EntityAttributeModifier modifier = new EntityAttributeModifier(modifierId, -SOUL_COST, EntityAttributeModifier.Operation.ADD_VALUE);
        maxHealth.addPersistentModifier(modifier);

        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }

        if (output.isEmpty()) {
            inventory.setStack(0, new ItemStack(ModItems.SOUL, 1));}
        else {
            output.increment(1);
            inventory.markDirty();
        }

        sendContentUpdates();

        return true;
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;

        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {

            ItemStack originalStack = slot.getStack();

            newStack = originalStack.copy();

            if (invSlot < inventory.size()) {
                if (!this.insertItem(originalStack, inventory.size(), slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.insertItem(originalStack, 0, inventory.size(), false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }


    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }


    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }


    public boolean hasNotEnoughHealth() {
        return notEnoughHealth;
    }


    public boolean isOutputFull() {
        return outputFull;
    }
}