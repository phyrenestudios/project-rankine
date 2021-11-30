package com.cannolicatfish.rankine.blocks.tap;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static com.cannolicatfish.rankine.init.RankineBlocks.TREE_TAP_TILE;

public class TreeTapTile extends TileEntity implements ITickableTileEntity {

    public TreeTapTile() {
        super(TREE_TAP_TILE);
    }

    public void tick() {

    }







    private boolean isTreeAlive(BlockPos pos, World worldIn) {
        Set<BlockPos> checkedBlocks = new HashSet<>();
        Stack<BlockPos> toCheck = new Stack<>();

        toCheck.add(pos.offset(worldIn.getBlockState(pos).get(TreeTapBlock.FACING).getOpposite()));
        while (!toCheck.isEmpty()) {
            BlockPos cp = toCheck.pop();
            if (!checkedBlocks.contains(cp)) {
                checkedBlocks.add(cp);
                for (BlockPos b : BlockPos.getAllInBoxMutable(cp.add(-1,-1,-1), cp.add(1,1,1))) {
                    BlockState target = worldIn.getBlockState(b.toImmutable());
                    if (target.isIn(RankineTags.Blocks.TREE_LEAVES)) {
                        if (!target.get(LeavesBlock.PERSISTENT)) {
                            return true;
                        }
                    } else if (target.isIn(RankineTags.Blocks.TREE_LOGS)) {
                        toCheck.add(b.toImmutable());
                    }
                }
                if (toCheck.size() > 300) {
                    break;
                }
            }
        }
        return false;
    }
}
