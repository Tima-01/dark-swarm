package ds.block.custom;

import com.mojang.serialization.MapCodec;
import ds.block.entity.custom.SummoningCauldronEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
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

public class SummoningCauldron extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE = VoxelShapes.union(
            // Дно
            Block.createCuboidShape(0, 3, 0, 16, 4, 16),

            // Стенки (БЕЗ внешнего отступа)
            Block.createCuboidShape(0, 4, 0, 2, 16, 16),   // запад
            Block.createCuboidShape(14, 4, 0, 16, 16, 16), // восток
            Block.createCuboidShape(2, 4, 0, 14, 16, 2),   // север
            Block.createCuboidShape(2, 4, 14, 14, 16, 16), // юг

            // Ножки
            Block.createCuboidShape(0, 0, 0, 4, 3, 4),
            Block.createCuboidShape(12, 0, 0, 16, 3, 4),
            Block.createCuboidShape(0, 0, 12, 4, 3, 16),
            Block.createCuboidShape(12, 0, 12, 16, 3, 16)
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
