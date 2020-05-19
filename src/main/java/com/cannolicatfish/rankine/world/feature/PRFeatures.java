package com.cannolicatfish.rankine.world.feature;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.feature.structures.BeaverLodgePieces;
import com.cannolicatfish.rankine.world.feature.structures.BeaverLodgeStructure;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Locale;

public class PRFeatures
{
    //Static instance of our structure so we can reference it and add it to biomes easily.
    public static Structure<NoFeatureConfig> BEAVER_LODGE = new BeaverLodgeStructure(NoFeatureConfig::deserialize);
    public static IStructurePieceType BEAVER_LODGE_PIECE = BeaverLodgePieces.Piece::new;


    /*
     * Registers the features and structures. Normal Features will be registered here too.
     */
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
    {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        /* Registers the structure itself and sets what its path is. In this case,
         * the structure will have the resourcelocation of structure_tutorial:run_down_house .
         *
         * It is always a good idea to register your regular features too so that other mods
         * can use them too directly from the Forge Registry. It great for mod compatibility.
         */
        ProjectRankine.register(registry, BEAVER_LODGE, "beaver_lodge");
        register(BEAVER_LODGE_PIECE, "beaver_lodge_piece");
    }


    /*
     * Registers the structures pieces themselves. If you don't do this part, Forge will complain to you in the Console.
     */
    static IStructurePieceType register(IStructurePieceType structurePiece, String key)
    {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), structurePiece);
    }
}
