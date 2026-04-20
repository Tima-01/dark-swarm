package ds.screen.custom;

import ds.block.entity.custom.InlayTableEntity;
import ds.item.ModItems;
import ds.screen.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class InlayTableScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    public final InlayTableEntity blockEntity;

    public InlayTableScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        super(ModScreenHandlers.INLAY_TABLE_SCREEN_HANDLER, syncId);

        this.blockEntity = (InlayTableEntity) playerInventory.player.getWorld().getBlockEntity(pos);
        this.inventory = blockEntity;

        addSlot(new Slot(inventory, 0, 13, 35));
        addSlot(new Slot(inventory, 1, 46, 35));
        addSlot(new Slot(inventory, 2, 80, 35));

        addSlot(new ResultSlot(inventory, 3, 127, 35));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private class ResultSlot extends Slot {

        public ResultSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }

        @Override
        public void onTakeItem(PlayerEntity player, ItemStack stack) {
            super.onTakeItem(player, stack);
            blockEntity.consumeInputs();
            blockEntity.onInventoryChanged();
        }
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        Slot slot = this.slots.get(invSlot);

        if (slot == null || !slot.hasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack original = slot.getStack();

        if (invSlot == 3) {
            if (!this.insertItem(original, 4, 40, true)) {
                return ItemStack.EMPTY;
            }
        }

        else if (invSlot >= 0 && invSlot <= 2) {
            if (!this.insertItem(original, 4, 40, true)) {
                return ItemStack.EMPTY;
            }
        }

        else if (invSlot >= 4 && invSlot < 31) {

            if (original.isOf(ModItems.SOUL)) {
                if (!this.insertItem(original, 0, 1, false)) return ItemStack.EMPTY;
            }
            else if (original.isOf(Items.BLAZE_ROD)) {
                if (!this.insertItem(original, 1, 2, false)) return ItemStack.EMPTY;
            }
            else if (original.isOf(Items.DIAMOND)) {
                if (!this.insertItem(original, 2, 3, false)) return ItemStack.EMPTY;
            }
            else {
                if (!this.insertItem(original, 31, 40, false)) return ItemStack.EMPTY;
            }
        }

        else if (invSlot >= 31 && invSlot < 40) {

            if (original.isOf(ModItems.SOUL)) {
                if (!this.insertItem(original, 0, 1, false)) return ItemStack.EMPTY;
            }
            else if (original.isOf(Items.BLAZE_ROD)) {
                if (!this.insertItem(original, 1, 2, false)) return ItemStack.EMPTY;
            }
            else if (original.isOf(Items.DIAMOND)) {
                if (!this.insertItem(original, 2, 3, false)) return ItemStack.EMPTY;
            }
            else {
                if (!this.insertItem(original, 4, 31, false)) return ItemStack.EMPTY;
            }
        }
        if (original.isEmpty()) {
            slot.setStack(ItemStack.EMPTY);
        } else {
            slot.markDirty();
        }

        slot.onTakeItem(player, original);

        blockEntity.onInventoryChanged();
        return original;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        blockEntity.updateResult();
    }
}