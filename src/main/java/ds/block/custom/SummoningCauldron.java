package ds.block.custom;

import com.mojang.serialization.MapCodec;
import ds.block.entity.custom.SummoningCauldronEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SummoningCauldron extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2, 2, 3, 3, 14, 13),
            Block.createCuboidShape(13, 2, 3, 14, 14, 13),
            Block.createCuboidShape(3, 2, 2, 13, 14, 3),
            Block.createCuboidShape(3, 2, 13, 13, 14, 14),

            Block.createCuboidShape(0, 0, 0, 3, 2, 3),
            Block.createCuboidShape(0, 0, 13, 3, 2, 16),
            Block.createCuboidShape(13, 0, 0, 16, 2, 3),
            Block.createCuboidShape(13, 0, 13, 16, 2, 16),

            Block.createCuboidShape(1, 2, 1, 3, 14, 3),
            Block.createCuboidShape(1, 2, 13, 3, 14, 15),
            Block.createCuboidShape(13, 2, 1, 15, 14, 3),
            Block.createCuboidShape(13, 2, 13, 15, 14, 15),

            Block.createCuboidShape(0, 14, 0, 3, 16, 16),
            Block.createCuboidShape(13, 14, 0, 16, 16, 16),
            Block.createCuboidShape(3, 14, 0, 13, 16, 3),
            Block.createCuboidShape(3, 14, 13, 13, 16, 16),

            Block.createCuboidShape(3, 1, 3, 13, 3, 13),
            Block.createCuboidShape(3, 1, 1, 13, 2, 3),
            Block.createCuboidShape(3, 1, 13, 13, 2, 15),
            Block.createCuboidShape(1, 1, 3, 3, 2, 13),
            Block.createCuboidShape(13, 1, 3, 15, 2, 13)
    );
    public static final MapCodec<SummoningCauldron> CODEC = SummoningCauldron.createCodec(SummoningCauldron::new);

    public SummoningCauldron(Settings settings) {
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

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SummoningCauldronEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof SummoningCauldronEntity) {
                ItemScatterer.spawn(world, pos, ((SummoningCauldronEntity) blockEntity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if(world.getBlockEntity(pos) instanceof SummoningCauldronEntity summoningCauldronEntity) {
            if(!world.isClient()) {
                player.openHandledScreen(summoningCauldronEntity);
            }
        }

        return ActionResult.SUCCESS;
    }
}
