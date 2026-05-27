package ds.screen.custom;

import ds.entity.ModEntities;
import ds.item.ModItems;
import ds.screen.ModScreenHandlers;
import ds.util.MinionManager;
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

public class SummoningCauldronScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final BlockPos pos;
    private boolean notEnoughHealth = false;

    public SummoningCauldronScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, pos, playerInventory.player.getWorld().getBlockEntity(pos));
    }

    public SummoningCauldronScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos, BlockEntity blockEntity) {
        super(ModScreenHandlers.SUMMONING_CAULDRON_SCREEN_HANDLER, syncId);

        this.pos = pos;
        this.inventory = (Inventory) blockEntity;

        this.addSlot(new Slot(inventory, 0, 92, 35));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id == 0) {
            if (player.getWorld().isClient()) { return true; }
            notEnoughHealth = false;
            EntityAttributeInstance maxHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (maxHealth == null) { return true; }
            if (player.getMaxHealth() <= 2.0f) {
                notEnoughHealth = true;
                sendContentUpdates();
                return true;
            }
            ItemStack stack = inventory.getStack(0);
            if (!stack.isEmpty() && stack.getItem() == ModItems.SOUL) {
                stack.decrement(1);
                var world = player.getWorld();
                var summon = ModEntities.MINION.create(world);
                if (summon != null) {
                    Identifier modifierId = Identifier.of(
                            "dark-swarm",
                            "minion_health_" + UUID.randomUUID()
                    );
                    EntityAttributeModifier modifier = new EntityAttributeModifier(
                            modifierId,
                            -2.0,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    );

                    maxHealth.addPersistentModifier(modifier);

                    if (player.getHealth() > player.getMaxHealth()) { player.setHealth(player.getMaxHealth()); }

                    summon.setHealthModifierId(modifierId);

                    summon.refreshPositionAndAngles(
                            pos.getX() + 0.5,
                            pos.getY() + 0.3,
                            pos.getZ() + 0.5,
                            0,
                            0
                    );
                    summon.setOwner(player);
                    summon.setTamed(true, true);
                    world.spawnEntity(summon);
                    MinionManager.push(
                            player.getUuid(),
                            summon.getUuid(),
                            summon.getHealthCost()
                    );
                }
            }
            sendContentUpdates();
            return true;
        }
        return super.onButtonClick(player, id);
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

    public boolean hasNotEnoughHealth() {
        return notEnoughHealth;
    }
}
