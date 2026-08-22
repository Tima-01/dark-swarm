package ds.screen.custom;

import ds.entity.ModEntities;
import ds.item.ModItems;
import ds.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class SummoningCauldronScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final BlockPos pos;

    public SummoningCauldronScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, pos, playerInventory.player.getWorld().getBlockEntity(pos));
    }

    public SummoningCauldronScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos, BlockEntity blockEntity) {
        super(ModScreenHandlers.SUMMONING_CAULDRON_SCREEN_HANDLER, syncId);

        this.pos = pos;
        this.inventory = (Inventory) blockEntity;

        this.addSlot(new Slot(inventory, 0, 92, 35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.SOUL);
            }

            @Override
            public int getMaxItemCount() {
                return 64;
            }
        });

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id != 0) {
            return super.onButtonClick(player, id);
        }

        if (player.getWorld().isClient()) {
            return true;
        }

        ItemStack stack = inventory.getStack(0);

        if (stack.isEmpty() || !stack.isOf(ModItems.SOUL)) {
            return true;
        }

        stack.decrement(1);
        inventory.markDirty();

        var world = player.getWorld();
        var summon = ModEntities.MINION.create(world);

        if (summon != null) {
            summon.refreshPositionAndAngles(
                    pos.getX() + 0.5,
                    pos.getY() + 1,
                    pos.getZ() + 0.5,
                    0,
                    0
            );

            summon.setOwner(player);
            summon.setTamed(true, true);

            world.spawnEntity(summon);
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

            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
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
        return this.inventory.canPlayerUse(player);
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
}