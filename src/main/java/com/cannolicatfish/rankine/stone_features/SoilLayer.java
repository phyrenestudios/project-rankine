package com.cannolicatfish.rankine.stone_features;

import com.cannolicatfish.rankine.blocks.block_enums.SoilBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Optional;

public final class SoilLayer {

    private ResourceLocation name;
    private final String soilType;
    private final ResourceLocation oLayer;
    private final ResourceLocation aLayer;
    private final ResourceLocation bLayer;
    private final ResourceLocation podzol;
    private final ResourceLocation mycelium;
    private final ResourceLocation path;
    private final ResourceLocation coarseDirt;
    private final ResourceLocation mud;
    private final ResourceLocation rootedDirt;
    private final ResourceLocation farmland;
    private final ResourceLocation permafrost;

    public static final Codec<SoilLayer> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.optionalFieldOf("soilType").forGetter(l -> Optional.of(l.soilType)),
                    ResourceLocation.CODEC.optionalFieldOf("O").forGetter(l -> Optional.of(l.oLayer)),
                    ResourceLocation.CODEC.optionalFieldOf("A").forGetter(l -> Optional.of(l.aLayer)),
                    ResourceLocation.CODEC.optionalFieldOf("B").forGetter(l -> Optional.of(l.bLayer)),
                    ResourceLocation.CODEC.optionalFieldOf("podzol").forGetter(l -> Optional.of(l.podzol)),
                    ResourceLocation.CODEC.optionalFieldOf("mycelium").forGetter(l -> Optional.of(l.mycelium)),
                    ResourceLocation.CODEC.optionalFieldOf("path").forGetter(l -> Optional.of(l.path)),
                    ResourceLocation.CODEC.optionalFieldOf("coarseDirt").forGetter(l -> Optional.of(l.coarseDirt)),
                    ResourceLocation.CODEC.optionalFieldOf("rootedDirt").forGetter(l -> Optional.of(l.rootedDirt)),
                    ResourceLocation.CODEC.optionalFieldOf("farmland").forGetter(l -> Optional.of(l.farmland)),
                    ResourceLocation.CODEC.optionalFieldOf("mud").forGetter(l -> Optional.of(l.mud)),
                    ResourceLocation.CODEC.optionalFieldOf("permafrost").forGetter(l -> Optional.of(l.permafrost))
            ).apply(instance, SoilLayer::new));


    public SoilLayer(Optional<String> soilTypeIn, Optional<ResourceLocation> oLayerIn, Optional<ResourceLocation> aLayerIn, Optional<ResourceLocation> bLayerIn, Optional<ResourceLocation> podzolIn, Optional<ResourceLocation> myceliumIn, Optional<ResourceLocation> pathIn, Optional<ResourceLocation> coarseDirtIn, Optional<ResourceLocation> rootedDirtIn, Optional<ResourceLocation> farmlandIn, Optional<ResourceLocation> mudIn, Optional<ResourceLocation> permafrostIn) {
        this.soilType = soilTypeIn.orElse("null");
        this.oLayer = oLayerIn.orElse(new ResourceLocation("minecraft:air"));
        this.aLayer = aLayerIn.orElse(new ResourceLocation("minecraft:air"));
        this.bLayer = bLayerIn.orElse(new ResourceLocation("minecraft:air"));
        this.podzol = podzolIn.orElse(new ResourceLocation("minecraft:air"));
        this.mycelium = myceliumIn.orElse(new ResourceLocation("minecraft:air"));
        this.path = pathIn.orElse(new ResourceLocation("minecraft:air"));
        this.coarseDirt = coarseDirtIn.orElse(new ResourceLocation("minecraft:air"));
        this.rootedDirt = rootedDirtIn.orElse(new ResourceLocation("minecraft:air"));
        this.farmland = farmlandIn.orElse(new ResourceLocation("minecraft:air"));
        this.mud = mudIn.orElse(new ResourceLocation("minecraft:air"));
        this.permafrost = permafrostIn.orElse(new ResourceLocation("minecraft:air"));
    }

    public SoilLayer setRegistryName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    @Nullable
    public ResourceLocation getRegistryName() {
        return name;
    }

    public SoilBlocks getSoilType() {
        return soilType.equals("null") ? null : SoilBlocks.byName(soilType);
    }
    public Block getOLayer() {
        return getSoilType() != null ? getSoilType().getGrassBlock() : ForgeRegistries.BLOCKS.getValue(oLayer);
    }
    public Block getALayer() {
        return getSoilType() != null ? getSoilType().getSoilBlock() : ForgeRegistries.BLOCKS.getValue(aLayer);
    }
    public Block getBLayer() {
        return getSoilType() != null ? getSoilType().getCoarseSoilBlock() : ForgeRegistries.BLOCKS.getValue(bLayer);
    }
    public Block getPodzol() {
        return getSoilType() != null ? getSoilType().getPodzolBlock() : ForgeRegistries.BLOCKS.getValue(podzol);
    }
    public Block getMycelium() {
        return getSoilType() != null ? getSoilType().getMyceliumBlock() : ForgeRegistries.BLOCKS.getValue(mycelium);
    }
    public Block getPath() {
        return getSoilType() != null ? getSoilType().getPathBlock() : ForgeRegistries.BLOCKS.getValue(path);
    }
    public Block getCoarseDirt() {
        return getSoilType() != null ? getSoilType().getCoarseSoilBlock() : ForgeRegistries.BLOCKS.getValue(coarseDirt);
    }
    public Block getRootedDirt() {
        return getSoilType() != null ? getSoilType().getRootedSoilBlock() : ForgeRegistries.BLOCKS.getValue(rootedDirt);
    }
    public Block getFarmland() {
        return getSoilType() != null ? getSoilType().getFarmlandBlock() : ForgeRegistries.BLOCKS.getValue(farmland);
    }
    public Block getMud() {
        return getSoilType() != null ? getSoilType().getMudBlock() : ForgeRegistries.BLOCKS.getValue(mud);
    }
    public Block getPermafrost() {
        return getSoilType() != null ? getSoilType().getPermafrostBlock() : ForgeRegistries.BLOCKS.getValue(permafrost);
    }


}
