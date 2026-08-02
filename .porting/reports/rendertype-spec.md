# Render-type assignment spec (from the 1.18.2 ClientProxy)

Implemented by `RankineBlockStateProvider.applyRenderTypes()`. Kept here as the coverage
checklist to verify against generated model JSON after `runData`.

```
    /*
     * PORT-TODO(datagen-rendertype): ItemBlockRenderTypes.setRenderLayer is GONE — render types
     * must be set in the block model JSONs via datagen ("render_type": "minecraft:cutout" etc.,
     * BlockStateProvider .renderType(...)). The 1.18.2 ClientProxy assigned:
     *
     * cutout:
     *   - RankineStoneBricksBlock instances from every RankineLists.RANKINE_STONES group
     *   - per RankineLists.RANKINE_WOODS: trapdoors, doors, hollow logs, saplings, leaves, leaf litters
     *   - RankineLists.LEAF_LITTERS, MINING_HEADS, METAL_DOORS, METAL_TRAPDOORS, ALLOY_LADDERS,
     *     ALLOY_POLES, ALLOY_BARS, HOLLOW_LOGS, GLAZED_PORCELAIN_BLOCKS, VANILLA_BRICKS,
     *     NATIVE_ORES, CRUSHING_ORES, SPECIAL_ORES, ASPHALT_BLOCKS, RED_ASPHALT_BLOCKS,
     *     GRAY_ASPHALT_BLOCKS, DARK_GRAY_ASPHALT_BLOCKS, BLUE_ASPHALT_BLOCKS, GREEN_ASPHALT_BLOCKS
     *   - RankineBlocks: FLOOD_GATE, ORNAMENT, LEAD_GLASS, BOROSILICATE_GLASS, CVD_GLASS,
     *     REACTION_CHAMBER_CELL, WILLOW_BRANCHLET, WILLOW_BRANCHLET_PLANT, TREE_TAP, TAP_LINE, TRAMPOLINE
     *   - each RankineLists.RANKINE_DRIPSTONES pointed dripstone
     *
     * cutout_mipped:
     *   - flower pots from RANKINE_WOODS
     *   - RankineLists.GRASS_BLOCKS, CROPS_SINGLE, CROPS_DOUBLE, CROPS_TRIPLE, BUSH_PLANTS,
     *     DOUBLE_BUSH_PLANTS, TALL_FLOWERS, LANTERNS
     *   - RankineBlocks: CAST_IRON_SUPPORT (+slab/stairs), COB, CORN_STALK, SHORT_GRASS,
     *     LOCUST_SPINE, GWIHABAITE_CRYSTAL, STINGING_NETTLE, RED_CLOVER, CRIMSON_CLOVER,
     *     WHITE_CLOVER, YELLOW_CLOVER, CALCIUM_SILICATE_BLOCK, BIOTITE_BLOCK, MUSCOVITE_BLOCK,
     *     SODIUM_CHLORIDE_BLOCK, PINK_SALT_BLOCK
     *
     * translucent:
     *   - RankineLists.GAS_BLOCKS, LIGHTNING_GLASSES
     *   - RankineBlocks: SODIUM_CHLORIDE_BLOCK, PINK_SALT_BLOCK, ICE_BRICKS (+slab/stairs/wall),
     *     all *_GAS_BLOCKs (hydrogen..oganesson, ammonia, carbon dioxide, hydrogen chloride,
     *     hydrogen fluoride, hydrogen sulfide, sulfur dioxide, tungsten hexafluoride),
     *     all element blocks (HYDROGEN_BLOCK..OGANESSON_BLOCK), ANTIMATTER,
     *     BLACK/GREEN/GRAY/BROWN_TEKTITE, MUSCOVITE_BLOCK, BIOTITE_BLOCK
     */

```
