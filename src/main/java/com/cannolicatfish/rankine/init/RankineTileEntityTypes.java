package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.signs.RankineSignTileEntity;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RankineTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ProjectRankine.MODID);



    public static final RegistryObject<TileEntityType<RankineSignTileEntity>> SIGN = TILE_ENTITIES.register("rankine_sign",
            () -> TileEntityType.Builder.create(RankineSignTileEntity::new,
                RankineBlocks.CEDAR_SIGN.get(), RankineBlocks.CEDAR_WALL_SIGN.get()
            ).build(null));


    @OnlyIn(Dist.CLIENT)
    public static void registerTileEntityRenders() {
        ClientRegistry.bindTileEntityRenderer(SIGN.get(), SignTileEntityRenderer::new);
    }

}
