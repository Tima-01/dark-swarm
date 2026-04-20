package ds.block.custom;

import com.mojang.serialization.MapCodec;
import ds.block.entity.custom.InlayTableEntity;
import ds.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
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

public class InlayTable extends BlockWithEntity {

    private static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 14, 0, 16, 16, 16),
            Block.createCuboidShape(2, 13, 3, 14, 14, 13),
            Block.createCuboidShape(1, 0, 1, 3, 14, 3),
            Block.createCuboidShape(13, 0, 1, 15, 14, 3),
            Block.createCuboidShape(1, 0, 13, 3, 14, 15),
            Block.createCuboidShape(13, 0, 13, 15, 14, 15)
    );

    public static final MapCodec<InlayTable> CODEC = InlayTable.createCodec(InlayTable::new);

    public InlayTable(Settings settings) {
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
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InlayTableEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof InlayTableEntity table) {
                ItemScatterer.spawn(world, pos, table);
                world.updateComparators(pos, this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world,
                                             BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory factory = (NamedScreenHandlerFactory) world.getBlockEntity(pos);
            if (factory != null) {
                player.openHandledScreen(factory);
            }
        }
        return ItemActionResult.SUCCESS;
    }
}