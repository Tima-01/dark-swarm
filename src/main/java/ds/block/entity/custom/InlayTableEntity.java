package ds.block.entity.custom;

import ds.block.entity.ImplementedInventory;
import ds.block.entity.ModBlockEntities;
import ds.item.ModItems;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class InlayTableEntity extends BlockEntity
        implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public static final int SOUL_SLOT = 0;
    public static final int PROPERTY_SLOT = 1;
    public static final int MATERIAL_SLOT = 2;
    public static final int RESULT_SLOT = 3;

    public InlayTableEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INLAY_TABLE_ENTITY_BE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    // ===================== LOGIC =====================

    public boolean canCraft() {
        return getStack(SOUL_SLOT).isOf(ModItems.SOUL)
                && getStack(PROPERTY_SLOT).isOf(Items.BLAZE_ROD)
                && getStack(MATERIAL_SLOT).isOf(Items.DIAMOND);
    }

    public ItemStack getResultItem() {
        return canCraft() ? new ItemStack(ModItems.PROFANED_SOUL) : ItemStack.EMPTY;
    }

    public void updateResult() {
        if (world == null || world.isClient) return;

        inventory.set(RESULT_SLOT, getResultItem());
    }

    public void consumeInputs() {
        removeStack(SOUL_SLOT, 1);
        removeStack(PROPERTY_SLOT, 1);
        removeStack(MATERIAL_SLOT, 1);

        onInventoryChanged();
    }

    public void onInventoryChanged() {
        if (world == null || world.isClient) return;

        updateResult();
        markDirty();
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        onInventoryChanged();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        net.minecraft.inventory.Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        net.minecraft.inventory.Inventories.readNbt(nbt, inventory, registryLookup);
        onInventoryChanged();
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.dark-swarm.inlay_table");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ds.screen.custom.InlayTableScreenHandler(syncId, playerInventory, pos);
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}