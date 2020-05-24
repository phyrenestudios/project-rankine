package com.cannolicatfish.rankine.world.feature;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.feature.structures.BeaverLodgePieces;
import com.cannolicatfish.rankine.world.feature.structures.BeaverLodgeStructure;
import com.cannolicatfish.rankine.world.feature.structures.TropicsHousePieces;
import com.cannolicatfish.rankine.world.feature.structures.TropicsHouseStructure;
import com.cannolicatfish.rankine.world.feature.trees.BalsamFirTreeFeature;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Locale;


public class RankineFeatures {
    public static Structure<NoFeatureConfig> BEAVER_LODGE = new BeaverLodgeStructure(NoFeatureConfig::deserialize);
    public static IStructurePieceType BEAVER_LODGE_PIECE = BeaverLodgePieces.Piece::new;
    public static Structure<NoFeatureConfig> TROPICS_HOUSE = new TropicsHouseStructure(NoFeatureConfig::deserialize);
    public static IStructurePieceType TROPICS_HOUSE_PIECE = TropicsHousePieces.Piece::new;
    public static final Feature<TreeFeatureConfig> BALSAM_FIR_TREE 	= new BalsamFirTreeFeature(TreeFeatureConfig::func_227338_a_);


    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        ProjectRankine.register(registry, BALSAM_FIR_TREE, "balsam_tree");

        ProjectRankine.register(registry, BEAVER_LODGE, "beaver_lodge");
        register(BEAVER_LODGE_PIECE, "beaver_lodge_piece");

        ProjectRankine.register(registry, TROPICS_HOUSE, "tropics_house");
        register(TROPICS_HOUSE_PIECE, "tropics_house_piece");
    }

    static IStructurePieceType register(IStructurePieceType structurePiece, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), structurePiece);
    }
}
