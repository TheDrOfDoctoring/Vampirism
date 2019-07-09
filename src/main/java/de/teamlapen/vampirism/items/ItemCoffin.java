package de.teamlapen.vampirism.items;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.blocks.BlockCoffin;
import de.teamlapen.vampirism.core.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Used to place coffings
 */
public class ItemCoffin extends VampirismItem {
    public static final String name = "item_coffin";

    public ItemCoffin() {
        super(name, new ItemInjection.Properties().group(VampirismMod.creativeTab));
    }


    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        //TODO 1.13 Test

        ItemStack stack = context.getItem();
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos targetPos = context.getPos();
        Direction side = context.getFace();

        if (side != Direction.UP) {
            return ActionResultType.FAIL;
        }

        if (world.isRemote)
            return ActionResultType.PASS;
        // Increasing y, so the coffin is placed on top of the block that was
        // clicked at except if the block is replaceable
        BlockState iblockstate = world.getBlockState(targetPos);
        boolean replaceable = iblockstate.isReplaceable(new BlockItemUseContext(context));

        BlockPos pos = replaceable ? targetPos : targetPos.up();

        // Direction the player is facing
        int direction = MathHelper.floor((player != null ? player.rotationYaw * 4F : 0) / 360F + 0.5D) & 3;
        Direction facing = Direction.byHorizontalIndex(direction);
        BlockPos other = pos.offset(facing);
        boolean other_replaceable = world.getBlockState(other).isReplaceable(new BlockItemUseContext(context.getWorld(), context.getPlayer(), context.getItem(), other, context.getFace(), context.getHitX(), context.getHitY(), context.getHitZ()));
        boolean flag1 = world.isAirBlock(pos) || replaceable;
        boolean flag2 = world.isAirBlock(other) || other_replaceable;

        if (player == null || player.canPlayerEdit(pos, side, stack) && player.canPlayerEdit(other, side, stack)) {
            if (flag1 && flag2 && UtilLib.doesBlockHaveSolidTopSurface(world, pos.down()) && UtilLib.doesBlockHaveSolidTopSurface(world, other.down())) {
                BlockState state1 = ModBlocks.block_coffin.getDefaultState().with(BlockCoffin.OCCUPIED, Boolean.FALSE).with(BlockCoffin.PART, BlockCoffin.CoffinPart.FOOT).with(BlockCoffin.FACING, facing);
                if (world.setBlockState(pos, state1, 3)) {
                    BlockState state2 = state1.with(BlockCoffin.PART, BlockCoffin.CoffinPart.HEAD).with(BlockCoffin.FACING, facing);
                    world.setBlockState(other, state2, 3);


                }
                stack.shrink(1);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }
}
