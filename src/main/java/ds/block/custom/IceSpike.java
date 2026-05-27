package ds.block.custom;

import com.mojang.serialization.MapCodec;
import ds.block.entity.custom.IceSpikeEntity;
import ds.block.entity.custom.NetherSpikeEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IceSpike extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 0, 3, 13, 1, 13),
            Block.createCuboidShape(4, 1, 4, 12, 2, 13),
            Block.createCuboidShape(5, 2, 4.5, 11.5, 4, 12),
            Block.createCuboidShape(5, 4, 5, 11, 6, 11),
            Block.createCuboidShape(6, 6, 6, 10.5, 8, 10.5),
            Block.createCuboidShape(6, 8, 6, 10, 10, 10),
            Block.createCuboidShape(6.75, 10, 6.75, 9.25, 12, 9.25),
            Block.createCuboidShape(7, 12, 7, 8.75, 15, 8.75),
            Block.createCuboidShape(7.5, 15, 7.5, 8.5, 16, 8.5)
    );
    public static final MapCodec<ds.block.custom.IceSpike> CODEC = ds.block.custom.IceSpike.createCodec(ds.block.custom.IceSpike::new);

    public IceSpike(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IceSpikeEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof IceSpikeEntity) {
                ItemScatterer.spawn(world, pos, ((IceSpikeEntity) blockEntity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof IceSpikeEntity iceSpikeEntity) {
            if(iceSpikeEntity.isEmpty() && !stack.isEmpty()) {
                iceSpikeEntity.setStack(0, stack.copyWithCount(1));
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);
                stack.decrement(1);

                iceSpikeEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            } else if(stack.isEmpty() && !player.isSneaking()) {
                ItemStack stackOnIceSpike = iceSpikeEntity.getStack(0);
                player.setStackInHand(Hand.MAIN_HAND, stackOnIceSpike);
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
                iceSpikeEntity.clear();

                iceSpikeEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            }
        }

        return ItemActionResult.SUCCESS;
    }
}