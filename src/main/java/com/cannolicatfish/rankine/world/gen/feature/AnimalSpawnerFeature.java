package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class AnimalSpawnerFeature extends Feature<NoFeatureConfig> {


    public AnimalSpawnerFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if ((reader.getBlockState(pos.down()).isIn(RankineTags.Blocks.GRASS) && reader.isAirBlock(pos) && reader.getLightSubtracted(pos,0) >= 7)) {
            LivingEntity ent;
            if (rand.nextFloat() < 0.5) {
                ent = new SheepEntity(EntityType.SHEEP, reader.getWorld());
            } else {
                ent = new CowEntity(EntityType.COW, reader.getWorld());
            }

            ent.setPosition(pos.down().getX(),pos.down().getY(),pos.down().getZ());
            reader.addEntity(ent);
            return true;
        }
        return false;
    }

}

