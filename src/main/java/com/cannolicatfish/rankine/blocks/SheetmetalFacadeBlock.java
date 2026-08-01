package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class SheetmetalFacadeBlock extends Block {
    public static final MapCodec<SheetmetalFacadeBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("color").forGetter(SheetmetalFacadeBlock::getColor)
            ).apply(instance, SheetmetalFacadeBlock::new)
    );

    int alloyColor;

    @Override
    public MapCodec<? extends SheetmetalFacadeBlock> codec() {
        return CODEC;
    }

    public SheetmetalFacadeBlock(int color) {
        super(Properties.of().mapColor(MapColor.NONE).requiresCorrectToolForDrops().strength(4.0F, 10.0F).sound(SoundType.METAL));
        this.alloyColor = color;
    }

    public int getColor() {
        return this.alloyColor;
    }


}
