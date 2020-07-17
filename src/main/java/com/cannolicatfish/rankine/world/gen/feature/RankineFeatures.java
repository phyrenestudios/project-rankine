package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.world.gen.feature.erratics.LargeErraticFeature;
import com.cannolicatfish.rankine.world.gen.feature.structures.*;
import com.cannolicatfish.rankine.world.gen.feature.trees.BalsamFirTreeFeature;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Locale;


public abstract class RankineFeatures<FC extends IFeatureConfig> extends net.minecraftforge.registries.ForgeRegistryEntry<net.minecraft.world.gen.feature.Feature<?>> {
    public static IStructurePieceType BEAVER_LODGE_PIECE = BeaverLodgePieces.Piece::new;
    public static IStructurePieceType TROPICS_HOUSE_PIECE = TropicsHousePieces.Piece::new;
    public static IStructurePieceType LAGOON_FOUNTAIN_PIECE = LagoonFountainPieces.Piece::new;


    public static final Feature<NoFeatureConfig> SPIKE = register("rankine:spike", new SpikesFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<BlockBlobConfig> METEOR = register("rankine:meteor", new MeteoriteFeature(BlockBlobConfig.field_236449_a_));
    public static final Feature<BlockBlobConfig> LARGE_ERRATIC = register("rankine:large_erratic", new LargeErraticFeature(BlockBlobConfig.field_236449_a_));
    //public static final Feature<BaseTreeFeatureConfig> BALSAM_FIR_TREE = register("balsam_tree", new BalsamFirTreeFeature(TreeFeatureConfig::deserializeFoliage));
    //public static final Structure<NoFeatureConfig> BEAVER_LODGE = register("beaver_lodge", new BeaverLodgeStructure(NoFeatureConfig::deserialize));
    //public static final Structure<NoFeatureConfig> TROPICS_HOUSE = register("tropics_house", new TropicsHouseStructure(NoFeatureConfig::deserialize));
    //public static final Structure<NoFeatureConfig> LAGOON_FOUNTAIN = register("lagoon_fountain", new LagoonFountainStructure(NoFeatureConfig::deserialize));

    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();
        register(BEAVER_LODGE_PIECE, "rankine:beaver_lodge_piece");
        register(TROPICS_HOUSE_PIECE, "rankine:tropics_house_piece");
        register(LAGOON_FOUNTAIN_PIECE, "rankine:lagoon_fountain_piece");
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        return (F)(Registry.<Feature<?>>register(Registry.FEATURE, key, value));
    }

    static IStructurePieceType register(IStructurePieceType structurePiece, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), structurePiece);
    }
}
