
# Changelog
All notable changes to the mod, Project Rankine, will be kept in this file.

## Project Rankine Version 1.1.3 Minor Changelog [2020-12-22]

### Additions
- Added Flint Hoe for pre-bronze age farming

- Added tags for tool types (ex: rankine:bronze_tools)

- Added Perlite and Block of Perlite, which is a material that crushes from Obsidian

- Added Maple Planks, Stairs, Trapdoor, Door, Fence, Button, Slab, and Pressure Plate 

- Added tag based crafting recipes for multiple items and blocks

- Added Block of Pewter, Block of Tungsten Heavy Alloy, and Block of Cobalt Superalloy

- Added Bone Char, which can be used to obtain more sugar from sugar cane and as a source of black dye

- Added Beehive Oven Recipe for Block of Bone to Block of Bone Char

- Added recipe to Pumice Soap to extract Blaze Powder from Magma Cream

- Added recipe for Minerails using Cast Iron Rods and Steel Rods

- Added recipe for Powered Rails using Graphite Electrodes

- Added recipe for Cobblestone involving mortar for increased output

- Added #rankine:mortar_special tag for use in certain mortar recipes (currently includes pozzolan and perlite)


### Changes
- Each vanilla tool can be disabled seperately via config

- Breaking blocks in #forge:dirt has a chance of dropping vegetables with flint tools or with an empty hand (enable/disable and chance in config)

- Changed stones dropping flint from lootable to event (config options for chance and enablement)

- Quarry Barrier blocks prevent the Laser Quarry from mining the column underneath them

- Beehive Oven Pit can be lit using spark lighter

- Items used to light Beehive Oven will take damage / be consumed

- Optional config for Beehive Oven to convert all blocks at once on random tick

### Misc
- Added/fixed lootable for induction furnace, interspinifex ore, anthracite coal ore, magnolia log, and juniper log


## Project Rankine Version 1.1.2 Minor Changelog [2020-12-19]

### Additions
- Renamed Industrial Quarry to Laser Quarry and added crafting recipe

- Added new Laser Quarry structure, which uses new blocks such as the Laser Quarry Base and Laser Quarry Pylon

- Added Lithium Power Cell and Yttrium Aluminum Garnet as components for Laser Quarry and future updates

- Added slots to Coal Forge for storage of templates and other items

- Added configs for ore and stone harvest levels

- Added villager trades to several professions and configs

- Added Red, Green and Blue LED lamp blocks

- Added Project Rankine Challenges advancement page and moved certain advancement challenges

- Added advancements for Laser Quarry and Thorium Arrow

- Added Black Sand to the Nether

- Added Packaged Tool, which gives a completely random tool on right-click

- Added initial element formulas for: Lithium, Rhenium, Germanium, Calcium, and Iridium

- Added Rhenium to Tungsten Heavy Alloy

- Added Calcium to Steel, Stainless Steel, and Duralumin

- Added Spark Lighter, which acts as a higher durability Flint and Steel

- Added Thorium Arrows, which uses Thorium to produce lightning

- Added Thorite, which can be obtained by sieving black sand blocks

- Added right-click functionality to Pumice Soap to repair/clean stone blocks of cracks and moss or polish stone blocks

### Changes

- Changed recipes of templates to use tools rather than items

- Changed cobblestone generator to produce random igneous rocks by default, added config to re-enable vanilla cobblestone generator

- Changed Molybdenite secondary crushing product from Magnetite (10%) to Rhenium Nugget (10%)

- Changed Sphalerite secondary crushing product from Opal (5%) to Germanium Nugget (10%)

- Changed coal ores to give their respective coal on crushing

- Changed localization on coal ores to include the word "coal"

- Increased explosion radius and modified location for all levels of Blast enchantment

- Piston Crusher recipe changed to include #rankine:piston_crusher_sheetmetals (by default includes bronze, brass, wrought iron, steel, stainless steel)

- Removed right-click functionality for config disabled tools (hoes, axes, and shovels)

- Reduced durability penalty for all levels of Blast enchantment 

### Misc

- Fixed localization terbium nugget, ingot, and block

- Fixed localization for Block of Ferrocerium

- Fixed localization for Ore Detector

- Fixed hammer and crowbar not showing cooldown when swinging offhand

- Fixed Brigandine Leggings smithing table recipe

- Updated textures for: Crucible, Graphite Electrode, Pumice Soap, Pyrolusite, Chromite, Wolframite, Columbite, Tantalite, Magnesia, Calcium Silicate, Block of Tiger Iron, Tiger Iron









## Project Rankine Version 1.1.1 Minor Changelog [2020-12-12]
### Additions

Configs

-   Meteorite spawn chance

-   Enable/Disable rankine tree generation

-   Processing time for Evaporation Tower

-   Chance for stones to drop nuggets of nearby ores

-   Range for how far ore veins will drop nuggets

-   Enable/Disable recipes for pendants

-   Movement speed modifiers for gravel and roman concrete

-   Range of ore detector

-   Enable/Disable Amalgam tools

-   Allow tool recipes for alloys which are not by default intended to be used as tools (experimental)
    

Blocks

-   Cast Iron Support
    

-   Block of Galinstan, Block of Sterling Silver, Block of Magnesium Alloy, Block of Mischmetal, Block of Ferrocerium
    

Items

-   Galinstan Alloy, Sterling Silver Alloy, Magnesium Alloy
    

-   Biome Indicators (for Evaporation Tower JEI)
    

Tags

-   forge:metal_bars
    
-   rankine:crowbar (blocks that can be harvested by crowbar tool)
    
-   Added tags for new alloy items/blocks
    

  

Removals

-   Transformation Staff
    

-   Gem core and shard
    

  
  

### Crafting Recipes

Additions

-   Piston crusher can also be made with brass sheetmetal
    
-   Torches can be made from coke
    
-   Refractory bricks can be broken apart into brick components
    

Removals

-   Alternate bucket recipe, Alternate shield recipe
    
-   Tuffs
    
-   Direct upgrade of refractory bricks (block) to higher tiers
    

Changes

-   Coal forge
    
-   Induction furnace
    

  
  
  

### MISC

Worldgen

-   Meteorite generation tweaked (smaller and more frequent by default)
    
-   Berry bushes spawn in world
    
-   Molybdenite spawns in end
    
-   Default generation settings for end ores and petalite decreased
    

Alloys and elements

-   Alnico alloy is now craftable
    
-   Added alternate recipe for Blue Gold Alloy
    
-   Selenium has been added to Brass
    
-   Buffed Rose Gold durability bonus and fixed additional element error
    
-   Fixed Titanium corrosion resistance formula
    
-   Fixed Palladium durability formula
    
-   Modified and simplified various element formulas
    
-   Added new formulas to: Magnesium, Gallium, Indium, Selenium
    
-   Adjusted formulas for: Boron, Titanium, Yttrium, Molybdenum, Antimony
    

-   Migrated damage formula to use float (allows for more variation between elements, still uses highest element value)
    

Tools

-   Glass Cutter has Silk Touch by default
    
-   Flint knife can harvest vines and has a JEI information page
    
-   Minecraft golden and iron tools are now disabled by default
    
-   Lava brass bucket damages the player less often
    
-   Element Indexer scrolls to 1% instead of 0%
    
-   Element Indexer now shows percentages for Corrosion Resistance, Heat Resistance, and Toughness
    

Added color variation to Element Indexer (green for positive, red for negative)

Added bar in Element Indexer which follows current percentage

Piston Crusher Recipes

-   Added recipes for: Blackstone, Netherrack, Basalt, Slate, Gabbro, Schist, Breccia, Tuffs, End Stone
    
-   Removed recipes for: Polished stone variants, Stone Brick variants
    
-   Modified Ironstone to give 2 iron nuggets on primary crushing
    

Advancements

-   Added new advancement in main progression for Blast Furnace
    
-   Added new advancement in main progression for Cast Iron Alloy
    
-   Added challenge advancement for obtaining all types of meteoric iron
    
-   Added optional Ironstone advancement
    

-   Changed location for Water Extraction and Automatic Labor advancement
    

Patchouli Integration

-   Created new textures for book
    
-   Large update to Journal pages
    

JEI Integration

-   Added recipe category for evaporation tower
    
-   Added additional information pages
    

Other

-   Renamed metal detector to ore detector
    
-   Beehive oven no longer converts lignite to subbituminous
    
-   Corrected misspell of Terbium
    
-   Added check to prevent crash in Coal Forge


## Version 1.1 Major Changelog [2020-12-05]

  
  

### Additions

Configs
    

-   Overworld stone layering adjustments
    
-   Almost all ores that spawn as veins (min, max, size, count)

Blocks
    

-   Metal Bars
    

-   Cast Iron
    
-   Magnesium
    
-   Aluminum
    
-   Nickel
    

-   Ores
    

-   Stibnite
    
-   Xenotime
    
-   Halite
    
-   Pink Halite
    
-   Cobalite
    
-   Petalite
    
-   Interspinifex
    
-   Majorite
    
-   Celestine
    
-   Phosphorite
    

-   Stones & variants
    

-   Gabbro
    
-   Schist
    
-   Slate
    
-   Breccia
    
-   Basalt (minecraft) Variants
    

-   Blocks for gems
    

-   Multiple
    
-   Cinnabar Block acts like redstone lamp
    

-   Industrial Quarry
    
-   Quarry Barriers
    
-   Template Table
    
-   Induction Furnace
    
-   Evaporation Tower
    
-   Black Birch Log / Wood
    
-   Black and Yellow Birch Leaves
    
-   Black Birch Sapling
    
-   Lead Glass
    
-   3 types of Etched Glass
    
-   Rope Coil
    

 Items
    

-   Pendants
    

-   Haste Pendant
    
-   Repulsion Pendant
    
-   Levitation Pendant
    
-   Health Pendant
    

-   Compressed Biomass
    
-   Ore Related
    

-   Petalite
    
-   Tourmaline
    
-   Chalcopyrite
    
-   Celestine
    

-   Compost
    
-   Alloys
    

-   Black Gold (and tools)
    
-   Pewter (and tools)
    
-   Stainless Steel
    
-   Rose’s Metal
    
-   Mischmetal
    
-   Tungsten Heavy Alloy
    
-   Ferrocerium
    
-   Duralumin
    
-   Nickel Superalloy (recipe changed)
    
-   Amalgam (requirements changed)
    

-   Saddle Tree and Saddle recipes
    
-   Element Indexer
    
-   Alloy Template and Triple Alloy Template
    
-   Hardness Tester
    
-   Ore Cycler
    

-   Textures for all elements and their different forms (block, nugget)
    


    

  

### Removals

Blocks
    

-   Finery Forge
    
-   Various pressure plates and buttons
    
-   Red grape vines
    
-   Sandy Dirt
    
-   Muddy Dirt
    
-   Permafrost
    
-   Creeper Block


Items
    

-   Short Grass
    
-   Swamp Grass
    
-   Duckweed
    
-   Pink & White Clover
    
-   Grapes
    
-   Foxfire
    
-   Remedies
    
-   Barks
    
-   Reactive items
    
Entities
    

-   Shrouded King
    
-   Solar Flare
    
-   Steamer
    

    

  

### MISC

-   Updated to Forge 35.1.10
    
Worldgen
    

-   Stone layering
    
-   Tuffs spawn
    
-   Intrusions gen changed
    
-   Nether rocks now as intrusions
    
-   Trees spawn in vanilla biomes
    

-   Metal Detector works on all forge:ores
    
-   Element adjustments
    
-   Improved alloy recipe handling and compatibility
    
-   Renamed certain stones
    
-   Aluvium spawns in world
    
-   Pendants no longer have durability
    
-   New process to obtain wrought iron
    
-   Birch saplings fall from their respective leaves
    
-   Removed redstone requirement for all machines
    
-   General
    

-   Improved JEI Support
    
-   Various recipe changes
    
-   Texture improvements
    
-   Localizations
    
-   Journal Updates
    
-   Added additional tags for compatibility and internal use
