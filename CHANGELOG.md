
# Changelog
All notable changes to the mod, Project Rankine, will be kept in this file.


## Project Rankine Version 1.3.3 (1.18.2 Port) Changelog [14Jun2022]

- This update is the initial port to 1.18.2 from 1.16.5. Many minor changes may have occured during the update, but the significant ones are recorded below.

### BLOCKS
- Added dripstone and pointed dripstone variants (ferric, magnesitic, gypsic, zirconic, nitric, boracitic)
- Mineral Deposits no longer generate Gwihabaite
- Added Rankine Copper Ore equivalent
- Added Banded Iron Formations
- Removed Evaporite
- Removed Copper Block
- Removed Calcite Block
- Updated loottables of vanilla ore equivalents to match vanilla
- Adjusted xp drop from ores
- Unlit campfires can be lit using flint

### ITEMS
- Removed Copper Ingot

### WORLDGEN
- Added a retrogen system to handle block replacements. Config option available. This should improve continuity of replacements across chunk borders
- Larger patches of white sand
- White sandstone generates under white sand
- Desert sand and desert sandstone replace default sand in badlands biomes
- Adjusted biome placement of trees and plants
- Adjusted ore placements

### TAGS
- Added blocks to respective needs_tool tags
- Added blocks to respective minable tags
- Added Rankine blocks to animal spawn on tags

### MISC
- Changed misc item textures
- Removed AnimalSpawning mixin
- Fixed Fusion Furnace recipes not working when an item was not included in the gas slot
- Fixed shonkinite crushing recipe
- Adjusted output of alternative gunpowder recipes from 4 to 2
- Added conversion crafting recipes for vanilla based ores

## Project Rankine Version 1.3.2 Changelog [28Apr2022]
### Hotfix 2 [13Jun2022]
- Fixed bug related to block placement of wall mushrooms
### Hotfix 1 [29Apr2022]
- Added Apatite fusion furnace recipe
- Added pulp recipe involving talc
- Added alternative methods to get certain potions
- Added special item that has a chance to drop when breaking clover blocks
- Fixed lang for Conductivity potions
- Fixed connection issue to multiplayer servers
- Fixed tag errors for added recipes
- Fixed penning trap recipe 

### BLOCKS
- Added model variants for stumps
- Blocks under stumps take longer to mine unless using a shovel
- Stumps break if floating
- Leaf Litters will only convert grass blocks to podzol under light level 10
- Leaf Litters can convert cobblestone and stone bricks to their mossy equivalents
- Introduced an age blockstate to leaf litters to control growth
- Added signs for all wood types
- Added Bales for grain crops
- Charred Logs can be obtained from an improperly built charcoal pit
- Shrunk collision box of mud blocks to be 15 pixels tall
- Updated some wood textures (primarily doors and trapdoors)

### ITEMS
- Updated Boat and Door item textures
- Added/updated miscellaneous textures

### RECIPES
- Optimized and changed format of Sluicing Recipe JSON (now uses a JSON array for outputs rather than numbered outputs)
- Added Fusion Furnace recipes to obtain previously unobtainable elements (Note: These recipes will be removed in the next major update)
- Changed Steel Wire recipe to Duralumin Wire
- Fixed Alloy Crafting recipes not linking to the right ingredients
- Changed all crafting recipes involving Alloy Wire to use specific types
- Added Terbium Nugget to Xenotime Crushing
- Removed Geode sluicing recipe (changed to be an innate feature of hammers)

### ALLOYING
- Added alloy stats to gaseous elements
- Added gaseous elements to various alloys

### CONFIG
- Removed unused configs
- Added option to disable fire clay generation under coal veins
- Added option to disable soil replacement independently of the biomeSettings config
- Added option to disable mud block generation near water. Mud placing can be a slightly laggy process
- Added option to disable Stone and Wooden Hammers similar to other vanilla tools
- Added leafLitterDecay config to adjust rate of Leaf Litter decay.

### MISC
- Added recipe for plant fiber from willow branchlet
- Added tag #rankine:knife_shearable to dictate which blocks knives can harvest, effectively silk touching them
- Added tag #forge:breedables/llama and added associated crops and bales for breeding llamas
- Added clovers to #minecraft:small_flowers
- Added sounds to miscellaneous actions
- Added applicable blocks to #twilight_forest:portal/edge and #twilight_forest:portal/decoration

### Bug Fixes
- Fixed crash with Ore Detector and related devices on server
- Alloy furnace will now drop inventory when broken
- Fixed flowers and other plants not being placable on rankine podzols and mycelliums
- Added missing lootables

## Project Rankine Version 1.3.1 Changelog [9Mar2022]

### Hotfix 1 [11Mar2022]
- Corrected generated recipe names for alloy tools
- Fixed issue where generated alloy tool recipes did not have the inheritRecipe variable when using multiple alloy recipes, which made certain tools uncraftable
- Fix crash related to specific instances of incorrect ore blockstates
- Added missing lang entries
- Added a crafting recipe and use for the Totem of Blazing
- Added element stats to Neptunium

### BLOCKS
- Blocks under a stone cobble or stump take twice as long to mine
- Added colored versions of cement
- Cement remapped to light gray cement


### ITEMS
- Removed Soy Milk
- Added hunger value to Tofu
- Added Cooked Tofu and Tofu Curry as foods
- Added Inner Bark as a food item dropped from stripping certain logs
- Prospecting Stick has a chance to detect an ore sample from stone cobbles
- Prospecting Stick message now displays ore name and harvest level
- Ore Detector functionality changed to return the name and position of the closest ore
- While held, the Magnetometer reads a magnetic field strength based on surrounding blocks
- Hammers now also deal 1.5x damage to Guardians
- Totem of Promising increase luck attribute

### ENCHANTING
- Changed enchantment requirements to be based on alloy recipe rather than item to allow alloy-specific enchantments to be applied at Enchanting Tables
- Added Endospore, an enchantment for an Ender Amalgam Hoe which can cause a mature crop block to grow near a harvested crop block if space is available
- Added Endure, an enchantment for an Ender Amalgam Sword which heals the user when standing nearby End Crystals
- Added Endolithic, an enchantment for an Ender Amalgam Shovel which allows right-clicking stone blocks to teleport to the nearest empty space in the direction of the block you are facing
- Added Endless, an enchantment for an Ender Amalgam Pickaxe which applies or extends a random beneficial potion effect when mining ores
- Added Endeavor, an enchantment for an Ender Amalgam Hammer which further increases the damage dealt to elemental-type enemies and adds a chance for those enemies to drop items on hit
- Added Endothermic, an enchantment for an Ender Amalgam Axe which causes harvested logs to turn into charcoal
- Added Endplay, an enchantment for an Ender Amalgam Blundberbuss which converts normal cannonballs into Enderballs that have increased veolcity and spawns Endermites which attack targets in the impact area

### ALLOYING
- Added Damascus Steel as a special steel variant available from Villagers
- Added new enchantments to Ender Amalgam
- Added Depth Strider enchantment to Brass
- Added bonus corrosion resistance to Stainless Steel and slightly modified bonus stats
- Added bonus stats to Niobium Alloy
- Modified mining speed, harvest level, and enchantability formulas for Zirconium
- Zirconium Alloy can now be made in the Alloy Furnace
- Modified enchantability formula for Endositum

### CONFIG
- Config added to disable pumice soap right click ability
- Implemented replaceVanillaOres config

### TAGS
- Added blocks to Terraforged tags
- Added new toolsets to appropriate tags

### RECIPES
- Added "stripping" json recipe type for spawning extra items when an axe right clicks a block
- Fixed recipe name typos
- Decreased the number of spins for most mixing barrel recipes
- Fixed recipe errors that caused incompatibilities with certain mods
- Added missing recipes for blocks and items
- Adjusted unaged_cheese_mixing recipe
- Added smelting/blasting recipes for rankine vanilla ores

### MISC
- Added textures for miscellaneous items with placeholder textures
- Added additional details to JEI recipes
- Added chance for sediments in the sedimentary generator to be removed
- Added various status messages as Lang entries
- Fixed Mixing Barrel GUI not showing progress
- Fixed incorrect Mixing Barrel fluid requirements
- Fixed tile entities using old conditions for checking for elements
- Fixed blunderbusses being included in the "forge:knives" tag
- Fixed out of bounds error for Packaged Tool and Packaged Armor
- Fixed unregistered placements
- Fixed worldgen crash regarding stone columns
- Fixed issue with stone generators always removing accesory mineral blocks
- Fixed issues with tree chopping
- Fixed tree tap not working past the first day
- Fixed Crucible Steel to use composition modifiers in Crucible recipe
- Updated Rankine Journal
- Removed depreciated/unused code


## Project Rankine Version 1.3 Changelog [27Feb2022]
A new world is highly recomended/needed to support new/changed features. This changelog has been simplified to account for 8 months of changes and multiple alpha versions.

### REGISTRY CHANGES
- Some blocks/items have changed registry names. These should all be remapped to the appropriate new block/item

### BLOCKS
#### --- Removals ---
- Removed all variants of Pumice and Scoria
- Reduced the variants of Skarn and Breccia to just slab, vertical slab, stairs and walls
- Removed Checkered Dacite and Checkered Porphyry and all variants
- Removed Columbite Ore, Salt Ore, Pink Salt Ore, Tantalite Ore, Aquamarine Ore, Majorite Ore, Opal Ore, Vanadinite Ore, Native Aluminum Ore, Native Copper Ore, Moissanite Ore
- Removed Mud (replaced by soil variants)
- Removed Etched Glasses (replaced with other variants - WIP)
- Removed Rope Coil (functionality was migrated to rope block)
- Removed Aluminum Bars, Nickel Bars, and Magnesium Bars (replaced by alloy bars - WIP)
- Removed Tap Barrel, Fluid Drain, Charred Wood, Laser Quarry, Laser Pylon Top, Laser Pylon Base, High Refractory Beehive Oven, Dacitic Tuff, Bamboo Wall and Banboo Culms Wall
#### --- Additions ---
- Added the following as new stones with variants: Rose Marble, Gray Marble, Serpetinite, Marlstone, Norite, Shonkinite, Pyroxenite, Soul Sandstone, Blueschist, Greenschist, Diabase, Eclogite, Honeystone, Whiteschist, Soapstone, Graywacke, Harzburgite, Lherzolite, Wehrlite
- Added Infested Stone, Column and Cobble variant for Rankine stones
- Added Clay Loam, Sandy Loam, Silty Loam, Loamy Sand, Sandy Clay Loam, Silty Clay Loam, Sandy Clay, Silty Clay, Loam, Humus as soil blocks
- Added Graasy, Grass Path, Mycellium, Podzol, Mud, Coarse variants of Rankine soil blocks
- Added Weeping Willow, Honey Locust, Red Birch, Western Hemlock, Erythrina as tree and wood variants
- Added Petrified Chorus and Charred wood variants
- Added Building Mode states to Rankine Stone Bricks, Bricks, Planks
- Added Leaf Litters for all Rankine and Vanilla leaves
- Added Hollow Logs for all Rankine and Vanilla Logs
- Added Sulfur Dioxide Fumarole, Carbon Dioxide Fumarole, Hydrogen Chloride Fumarole, Hydrogen Sulfide Fumarole
- Added Sulfur Dioxide Gas Block, Carbon Dioxide Gas Block, Hydrogen Chloride Gas Block, Hydrogen Sulfide Gas Block, Hydrogen Fluoride Gas Block, Ammonia Gas Block, and elemental gas blocks
- Added Rheniite Ore, Coltan Ore, Chalcocite Ore, Hematite Ore and associated mineral and block
- Added Tilled Soil (farmland block for all soils)
- Added Goldenrod, Red Lily, Orange Lily, White Lily, Purple Morning Glory, Black Morning Glory, Blue Morning Glory as double block flowers
- Added White Clover, Red Clover, Crimson Clover, and Yellow Clover as ground flowers
- Added Green, Brown, Gray, and Black Tektite
- Added garland blockstate to metal poles
- Added an Ornament block (acess variants with building tool)
- Added snowy variants for rankine Leaves
- Added Cob and Refined Cob
- Added Snoflake and Blood Obsidian
- Added slab, stairs, vertical slab, and wall variant for various blocks without a complete set
- Added Komatiitic Tuff, Kimberlitic Tuff
- Added Grassy Soil version of each soil (grass blocks with additional functionality)
- Added Cement, Fire Clay Bricks and Kaolin Bricks with variants
- Added Light Gravel and Dark Gravel
- Added Sod Block, White Sand and Silt
- Added Flood Gate, Metal Pipe, Ground Tap
- Added Pokeberry Bush
- Added Short Grass, Stinging Nettle
- Added Stump block
- Added Mixing Barrel, a new tile entity used to make products similiar to the Crucible
- Added Desert Sand
- Added White Sandstone, Black Sandstone, Desert Sandstone and associated variants
- Added Iron Sheetmetal and slab variants
- Added Gas Bottler (bottles gas blocks)
- Added Gas Vent (moves gas blocks)
- Added Fulgurite, Lightning Glass, Red Lightning Glass, Soul Lightning Glass, Black Lightning Glass, White Lightning Glass
- Added Rankine variant of the vanilla ores for texture adapting 
- Added Bog Iron, Porphyry Copper, Kimberlitic Diamond Ore
- Added Pedestal variants for Galinstan Alloy and Sodium Potassium Alloy
- Added Frozen Meteorite and Frozen Meteorite Bricks
- Added Meteoric Ice (more slippery than blue ice)
- Added Metal Bars and Pole variants for all alloys
- Added slab, stairs, vertical slab and wall variants for Fiber Block
- Added the Fusion Furnace, a new tile entity that uses battery power for chemical reactions involving fluids, gases, and items
- Added Invar Ladder
- Added Distillation Tower and Air Distillation Packing
- Added Borosilicate and CVD Glass
- 
#### --- Changes ---
- Harvest level, hardness and resistance values adjusted for many blocks
- Ores now drop their ore-related item by default and are affected by the Fortune enchantment (1.17 parity)
- Added flamability to many blocks
- Rankine Ore blocks will take the texture of the clicked block when placed
- Tree Taps will place fluid in the world using a Flood Gate through a line of Tap Lines
- Charcoal Pit produces Charcoal Blocks instead of Charred Wood
- Beehive Oven has randomized cook times based off how upgraded the structure is
- Berry bush lootables adjusted to match Sweet Berries


### ITEMS
#### --- Removals ---
- Removed Metal Scraper
- Removed Stone Hammer, Iron Hammer, Diamond Hammer, and Netherite Hammer
- Removed Iron Spear, Diamond Spear and Netherite Spear
- Removed Titanium Crowbar and changed Steel Crowbar to an alloy tool
- Removed Steel Rod, Cast Iron Rod, YAG Rod
- Removed Dry Mortar
- Removed Element Transmuters

#### --- Additions ---
- Added toolsets for Osmiridium, Niobium Alloy, Zirconium Alloy, Ender Amalgam
- Added Bismanol and Permalloy as alternative recipes to Rose Metal and Invar respectively
- Added Gas Bottles
- Added Cement Mix
- Added Wooden Mallet 
- Added Wooden Gold Pan and Pewter Gold Pan
- Added nuggets for alloys
- Added Alloy Knives to all existing Alloy toolsets
- Added Alloy Crowbars to all existing Alloy toolsets
- Added Rope Coil Arrow, which can deploy rope held in the off-hand
- Minerals: Added bauxite, ringwoodite, wadsleyite, forsterite, mellite, bridgmanite, ferropericlase, apatite, laurite, realgar, gypsum, rutile, chlorite, diopside, azurmalachite, goethite, monazite (Gd), monazite (Y), pyrite, spodumene, cooperite, sanidine, hedenbergite, talc, stishovite, asbestos, serpentine, potash
- Added Kaolinite
- Added Pokeberries
- Added Jams for each of the berries
- Building Tool: hold in offhand to place vertical planks and bricks
- Added Lorandite (Thallium ore mineral), Pollucite (Caesium/Rubidium ore mineral), Boron Trioxide, Sodium Fluorosilicate, Iron Chloride, Magnesium Chloride, Hafnia
- Added Buckets for all added fluids
- Added Ammonia Gas Bottle
- Added Bleach
- Added a Meteoric Iron-type item for natural Osmiridium
- Added Silver-Zinc Battery
- Implemented Vanadium Pentoxide, Gwihabaite, Stishovite, and Zirconia 
- Added Osmiridium Toolset (Pickaxe, Axe, Shovel, Sword, Hoe, Hammer, Knife, Crowbar)
- Added Americium RTG, Curium RTG, Plutonium RTG, Polonium RTG, and Strontium RTG
- Added Totem of Enduring, Totem of Hastening, Totem of Levitating, Totem of Promising, Totem of Repulsing, and Totem of Timesaving
- Added buckets for Gray Mud, Hexaflurosilicic Acid, Carbon Disulfide
- Added Silicon-Germanium Thermocouple
- Added Cannonball
- Added Hematite related items
- Implemented Building Tool
- Reduced Jams to a single item
- Added Rye, Oats, Barley, Millet, Sorghum, Soybean (not implemented yet)
- Added Rye Seeds, Oat  Seeds, Barley Seeds, Millet Seeds, Sorghum Seeds, Soybean Seeds (not implemented yet)
- Added Rye Grain, Oat  Grain, Barley Grain, Millet Grain, Sorghum Grain (not implemented yet)
- Added Tofu and Soy Milk (not implemented yet)
- Added the Alloy Blunderbuss, a new ranged weapon that uses cannonball-type items as ammo
- Added the Alloy Surf Rod, a metallic fishing rod that uses different loot tables
- Added Lithium Hydroxide, Potassium Hydroxide, Rubidium Hydroxide, Cesium Hydroxide, Francium Hydroxide
- Added Yellowcake
- Added new cannonball types (Carcass)
- Added Emergency Flotation Device
- Added Magnetometer
- Added Ice Melt
- Bandages can now heal other entities on right-click
- Added Garland

### FLUIDS
- Added Sulfuric Acid
- Added Hydrobromic Acid
- Added Aqua Regia
- Added Red Mud
- Added Gray Mud
- Added Hexafluorosilicic Acid
- Added Carbon Disulfide

### RECIPES
- Added json recipe support for Elements
- Added json recipe support for Tree Tapping
- Added json recipe support for Air Distillation
- Added json recipe support for Mixing Barrel
- Added json recipe support for Fusion Furnace
- Added json recipe support for Stone Generation
- Overhauled Alloying and Alloy Crafting json recipes
- Modifications to other json recipe types
- Overhauled the ingredient requirements for various recipes

### WORLDGEN
- Major changes in generation with both block pallet and features
- Smaller and more frequent intrusions
- Wavier layers
- Multiple types of ore veins

### ADVANCEMENTS
- Overhauled advancements

### ENCHANTMENTS
- Added many new enchantments. Use JEI / Enchantment Descriptions for info. Will be updated in journal and on wiki

### CONFIG
- Overhauled config file. Some options are still WIP
- Major changes include merging into a single file and the addition of worldgen settings

### TAGS
- Added and removed many tags to better suit the needs of the mod. A full tag list and description will be available on the wiki soon

### MISC
- Journal overhaul, still WIP along with all other documentation
- Added custom stone generators using a mix of vanilla an custom methods
- Updated many textures
- Russian lang (ru_ru) added by liottan
- Added custom sound events for subtitles (more to come)
- Updated Forge version to 36.2.20
- Updated Patchouli version to 1.16.4-53.2
- Updated JEI version to 1.16.5:7.7.1.150
- Hammers now deal 1.5x damage to skeletons and golem-type mobs
- Brass can now be used to make tools

### COMPAT
- Added Biomes O' Plenty, Biomes You Go, Botania, Immersive Engineering, Mekanism, Quark, Thermal Series, and Tinker's Construct as optional dependencies 
- Added crushing recipes for Quark stones and cobblestones
- Added alloying recipes for Thermal Series alloys
- Added crucible recipes for Thermal Series glass
- Added alloying recipes for Tinker's Construct alloys
- Added alloying recipe for Andesite Alloy from Create
- Added relevant elements for compatibility with certain alloys


















## Project Rankine Version 1.3 Alpha Changelog [2021-10-26]

### Alpha 3 [2021-11-30]

### BLOCKS
- Added Mixing Barrel, a new tile entity used to make products similiar to the Crucible
- Added Hydrogen Fluoride Gas Block
- Added Hollow Logs for all logs
- Added Leaf Litters for all leaves
- Added Desert Sand
- Added White Sandstone, Black Sandstone, Desert Sandstone and associated variants
- Added Iron Sheetmetal and slab variants
- Added Gas Condenser (bottles gas blocks)
- Added Gas Vent (moves gas blocks)
- Added Red Lightning Glass, Soul Lightning Glass, Black Lightning Glass, White Lightning Glass
- Added Hematite Ore and Block
- Converted Material Testing Table to a TileEntity and added slots for tools
- Harvest Level of Sperrylite Ore and Phosphorite changed to HL1
- Removed Polished Soul Sandstone and Soul Sandstone Bricks and variants
- Mud blocks are hoeable
- Dead Grass Blocks cannot be bonemealed
- Both vanilla and rankine plants can be placed on appropriate ground blocks
- Added snowy variants for rankine Leaves
- Added Cob
- Building Tool appropritely affects blocks with modes; Planks, Stone Bricks, Bricks
- Removed Tap Barrel
- Converted Tree Tap to a tile entity with json recipe support

### ITEMS
#### --- Additions ---
- Added Osmiridium Toolset (Pickaxe, Axe, Shovel, Sword, Hoe, Hammer, Knife, Crowbar)
- Added Americium RTG, Curium RTG, Plutonium RTG, Polonium RTG, and Strontium RTG
- Added Totem of Enduring, Totem of Hastening, Totem of Levitating, Totem of Promising, Totem of Repulsing, and Totem of Timesaving
- Added buckets for Gray Mud, Hexaflurosilicic Acid, Carbon Disulfide
- Added Silicon-Germanium Thermocouple
- Added Cannonball
- Added Hematite related items
- Implemented Building Tool
- Reduced Jams to a single item
- Added Rye, Oats, Barley, Millet, Sorghum, Soybean (not implemented yet)
- Added Rye Seeds, Oat  Seeds, Barley Seeds, Millet Seeds, Sorghum Seeds, Soybean Seeds (not implemented yet)
- Added Rye Grain, Oat  Grain, Barley Grain, Millet Grain, Sorghum Grain (not implemented yet)
- Added Tofu and Soy Milk (not implemented yet)
#### --- Changes ---
- Alloy tools can now be repairable in an anvil by ingots that have the same alloy recipe and alloy composition
- Crowbars now have adjusted stats
- Packaged Tools can now be forced to give a certain tool by providing NBT (ex. forceTool: "pickaxe")
- Jams are now using recipe type similar to fireworks (1x Glass Bottle + 2x Sugar + 6x Any Berry)
- Drink-type food items now use the drink ActionType
#### --- Removals ---
- Removed Pendants (replaced by Totems)

### ENCHANTMENTS
#### ---Additions---
- Added Accuracy, which decreases the spread of all projectiles fired from the Alloy Blunderbuss
#### --- Changes ---


### RECIPES
#### --- Additions ---
- Added Mixing and TreeTapping json recipe types
- Added Fusion Furnace recipe to convert Ilmenite into Rutile
- Added an alternative Shulker Gas Vacuum Recipe that uses rubidium
- Added alternative recipes for Silver-Zinc Battery, Magnesium Battery, and Lead-Acid Battery that uses vulcanized rubber
- Added crushing recipes to Beryl Ore
- Added Sodium Fluorosilicate to Fumarole Deposit crushing
- Added Fusion Furnace recipes relating to the Kraft Process
- Added Fusion Furnace recipes for producing Alkali Cellulose, Carbon Nugget, Carbon Disulfide, Cryolite, Fertilzer, Hydrogen Fluoride, Lithium Cobalt Oxide, Phantom Membrane, Slimeball, Sodium Fluorosilicate, Sodium Sulfide, Sponge 
- Added Fusion Furnace smelting recipes for Celestine, Greenockite, Lautarite, Thortveitite, Xenotime
- Added crushing recipes for new sandstones
- Added sedimentary stone generator recipes for new sandstones
- Added alternative recipe for End Rod and Brewing Stand using Ferrocerium
- Added alternative Flint and Steel recipe using Steel Nuggets
- Added Muds, Myceliums, Podzols and Path blocks to Pan Sluicing
- Added alloy crafting recipes for all Alloy Nuggets and Alloy Blocks (composition is now inherited)
#### --- Changes ---
- Added forceNBT arguement to Alloy Recipe JSON, which if set to true (false by default) causes the item to be given alloyData even if it is not an AlloyItem
- Adjusted some outputs for Fusion Furnace recipes
- Interspinifex ore now gives Iron Nuggets for HL1 Crushing
- White dye recipe for Ilmenite changed to use Rutile instead
- Modified recipes related with Hafnia and Zirconia
- Sediment Fan now uses Brass instead of Aluminum
- Fixed broken recipes
#### --- Removals ---
- Removed Blasting recipes for all Monazites, Thortveitite, Xenotime
- Removed Beehive Oven recipes for all Monazite Blocks, Thortveitite Block, Xenotime Block
- Removed Crushing recipes for Lepidolite, all Monazites
- Removed crafting recipe for Slimeballs

### FLUIDS
- Added Gray Mud
- Added Hexafluorosilicic Acid
- Added Carbon Disulfide

### TAGS
- Added fluid tags
- Added missing tool tags
- Added #rankine:construction_sand (includes Desert Sand for building purposes)
- Added blocks to Vanilla tags; mushroom_grow_blocks, bee_growables, enderman_holdable
- Removed tree tapping log tags (replaced by recipes)

### MISC
- Added custom SoundEvents (for subtitles only currently)
- Overhaul of advancements
#### --- Changes ---
- Added config options to disable various features
- Added new config options for ore vein generation
- Adjusted ore generation 
- Gas Blocks now render an overlay when within them
- Ores appropriately replace blocks with different texture names from the block (basalt and sandstones)
- Tree felling makes lwess noise
- Leaves in fast graphics mode render appropriately
- Coke now has proper forge tags
#### --- Config ---
- Condensed settings into fewer categories
- Added option to replace vanilla ores with Rankine versions for texturing (enabled by default)
- Decreased chances for plant events to occur
- Added client config, currently contains option to color grass based off elevation
#### --- Fixes ---
- Remade block localizations to be more consistent with Vanilla
- Fixed ore harvest level configs
- Grass Paths are appropriately replaced
- Grass Blocks spreading to Dirt grow vanilla grass blocks
- Packaged tool now ignores alloy recipes with no inputs (ex. Crucible Steel) and no longer produces debug info
- Fixed issue where alloy armor items were not able to inherit stats from elements properly
- Fixed Osmium and Iridium Enchantment formula
- Fixed incompatibility with Quark Automatic Recipe Unlock Module
- Machines with inventories drop items when broken
#### --- Alloying ---
##### Alloys
- Alloys now use the same tool material, meaning that base stats for every alloy tool will now be the same before considering elements and bonus alloy stats
- All gold alloys and Osmiridium now have an extra 50 durability
- Amalgam now gives a random enchantment if enchantability conditions are met
- Invar now confers +0.5 bonus damage
- All Steels now have unique lang identifiers (Crucible Steel, Maraging Steel, Tool Steel)
- Unified Maraging Steel into a single recipe and adjusted compositions
- Potential compositions for Osmiridium have been adjusted
- Added additional elements to Nitinol
- Phosphorus and Bismuth can now be used in Brass
- Iron and Manganese can now be used in Nickel Silver
- Germanium can now be used in all Gold alloys
- Gallium and Indium can be used in Pewter
- Cesium can now be used in Galinstan and Pewter
- Rubidium can now be used in all Gold alloys and Sodium-Potassium alloy
- Removed post-actinoid elements from Amalgam and Ender Amalgam
- Changed default enchantability bounds for alloys
##### Elements
- Added formulas for Cesium, Gallium, Indium, Rhodium, Rubidium, Ruthenium, Scandium
- Manganese now has an adjusted harvest level formula
- Significantly changed stats or added new stats for Osmium, Iridium
- Iron now gives attack damage (max 2) as composition percentage increases
- Copper now gives attack damage (max 1.2) as composition percentage increases
- Netherite has a new attack damage max of 4 (from 2)
- Vanadium has a new attack damage max of 2 (from 1)
- Fixed Barium damage and attack speed formulas

### COMPAT
- Added Biomes O' Plenty, Biomes You Go, Botania, Immersive Engineering, Mekanism, Quark, Thermal Series, and Tinker's Construct as optional dependencies 
- Added crushing recipes for Quark stones and cobblestones
- Added alloying recipes for Thermal Series alloys
- Added crucible recipes for Thermal Series glass
- Added alloying recipes for Tinker's Construct alloys
- Added alloying recipe for Andesite Alloy from Create
- Added relevant elements for compatibility with certain alloys

### COMMUNITY CONTRIBUTIONS
- Added Russian (ru_ru) translation (liottan)

### Alpha 2 [2021-10-26]

### BLOCKS
#### --- Additions ---
- Added the Fusion Furnace, a new tile entity that uses battery power for chemical reactions involving fluids, gases, and items
- Added Ammonia Gas Block
- Added Invar Ladder
- Added Mud, Podzol and Mycellium variants for new soil types
- Added Distillation Tower and Air Distillation Packing
- Implemented Borosilicate Glass
- Implemented Ground Tap
- Texture updates
#### --- Changes ---
- Ground Tap: Model exists, Requires a pipe network to a flood gate
- Removed "Wet" blockstate from soils. Soil blocks no longer tick
- Renamed Grassy Soils to Soil Grass Blocks
- Added missing lootables

### ENCHANTMENTS
#### ---Additions---
- Added Enderbiotic, an Ender Amalgam Armor enchantment which teleports the user out of danger when they are about to be hit by a projectile and negates damage, but causes extra damage to durability if corrosion resistance fails
#### --- Changes ---
- Changed Endpoint to an enchantment that can only be applied to Ender Amalgam Spears
- Fixed Ghastly Regeneration and Shape Memory enchantment restrictions

### FLUIDS
- Added Aqua Regia
- Added Hydrobromic Acid
- Added Red Mud

### ITEMS
- Added Lorandite (Thallium ore mineral), Pollucite (Caesium/Rubidium ore mineral), Boron Trioxide, Sodium Fluorosilicate, Iron Chloride, Magnesium Chloride, Hafnia
- Added Buckets for all added fluids
- Added Ammonia Gas Bottle
- Added Bleach
- Added a Meteoric Iron-type item for natural Osmiridium
- Added Silver-Zinc Battery
- Implemented Vanadium Pentoxide, Gwihabaite, Stishovite, and Zirconia 

### RECIPES
#### --- Fusion Furnace Recipes ---
- Added Fusion Furnace JSON recipe type, which takes two item inputs, a gas bottle input, and a fluid input to produce up to two item outputs, a gas bottle output, and a fluid output (all inputs and outputs are optional)
#### ---Additions---
- Added Fusion Furnace recipes for Boron Trioxide, Calcium Chloride, Calcium Silicate, Enstatite, Iron Chloride, Quicklime, Silicon Carbide, Sodium Carbonate, Sodium Hydroxide, Sodium Sulfide, Vanadium Pentoxide
- Added Fusion Furnace "smelting" recipes for Acanthite, Barite, Bauxite, Borax, Boron Trioxide, Calcite, Chalcocite, Cinnabar, Emerald, Galena, Hafnia, Malachite, Molybdenite, Lepidolite, Pentlandite, Potash, Pyrite, Quicklime, Realgar, Sphalerite, Stibnite, Zirconia
- Added Fusion Furnace recipe to produce Aqua Regia, Hydrogen Chloride, Hydrobromic Acid, Sulfuric Acid, and Sulfur Dioxide
- Added Fusion Furnace recipe for Endositum
- Added bone ash to the campfire
- Added recipes for dowsing rod, ground tap, flood gate
#### ---Changes---
- Evaporation recipe no longer requires a "bucket" parameter, changed input to Fluid parameter
- Changed HL2 of Evaporite crushing from Gypsum to Magnesium Chloride
- Moved Spodumene to HL4 and added Pollucite to HL5 for Pegmatite crushing
- Moved Opal to HL4 and added Lorandite to HL5 for Cinnabar Ore crushing
- Added "cooldown" variable to Sluicing JSON, which determines the cooldown given to the item after sluicing
- Sodium Sulfur Battery and Zinc Bromine Battery now use Alumina instead of Bauxite
- Ultra High Refractory Bricks now require Zirconia or Hafnia
- Fixed Sediment Fan recipe
#### ---Removals---
- Removed Indium Tin Oxide crafting recipe
- Removed blasting and beehive oven recipes for Azurite, Barite, Cinnabar, Magnesite, Malachite, and Zircon
### MISC
#### --- Changes ---
- Added more blocks/items to the remapping event
- Increased energy capacity for Magnesium Battery from 64 to 96
- Mud, Podzols and Myceliums added to worldgen
- Added sounds to all fluids
- Changed the formula for calculating the water table height
- Batteries are no longer enchantable or repairable
- Some additions to the journal
#### --- Alloying ---
- Condensed Nickel Superalloy into a single alloy recipe
- Added Enderbiotic to Ender Amalgam as an Enchantment
#### --- Config ---
- Merged configs into one file
- Removed unused config settings (alloy bonus stats, duplicate worldgen parameters)
- Added config for fumarole generation
### FIXES
- Fixed incorrect tagging for Endositum items
- Fixed Armor Stand not working in the Material Testing Table
- Fixed bug where crushing alloy item outputs had mismatched nbt data with alloy items in inventory
- Fixed recipes for tools that used Steel Rods (Nickel/Cobalt Superalloy, Tungsten Heavy Alloy, Stainless Steel, Ender Amalgam)
- Fixed custom alloy components for JEI
- Fixed recipe type for Tree Tap and Glass Cutter
- Fixed alloy tools pointing to the wrong alloy recipes
- Fixed incorrect formula for max-type variables in alloys (harvest level, attack damage, attack speed)
- Added tags for Netherite Nugget and fixed missing crafting


### Alpha 1 [2021-10-18]
### REGISTRY CHANGES
- All *_alloy to *_ingot
- feldspar to orthoclase_feldspar
- feldspar_block to orthoclase_feldspar_block
- meteorite_paver to meteorite_bricks
- enstatite_paver to enstatite_bricks
- uncolored_concrete_* to plain_concrete_*
- pyroxene_gabbro to gabbro
- tufalimestone to limestone
- arkose_sandstone to arkose
- quart_sandstone to itacolumite
- carbonaceous_shale to shale
- ringwoodite to ringwoodine
- wadsleyite to wadsleyone
- perovskite to post-perovskite
- ferropericlase to sommanite
- bridgmandine to bridgmanham
- salt to sodium_chloride
- pozzolan to pozzolana
- kaolinite to kaolin
- All metalloids, reactive nonmetals (that are not gases), and mercury have been changed from *_ingot to only *


### BLOCKS
#### --- Removals ---
- Removed all variants of Pumice and Scoria
- Reduced the variants of Skarn and Breccia to just slab, vertical slab, stairs and walls
- Removed Checkered Dacite and Checkered Porphyry and all variants
- Removed Columbite Ore, Pink Salt Ore, Tantalite Ore, Aquamarine Ore, Majorite Ore, Opal Ore, Vanadinite Ore, Native ALuminum Ore, Native Copper Ore, Moissanite Ore
- Removed Mud (replaced by soil variants)
- Removed Etched Glasses (replaced with other variants - WIP)
- Removed Rope Coil (functionality was migrated to rope block)
- Removed Aluminum Bars, Nickel Bars, and Magnesium Bars (replaced by alloy bars - WIP)
- Removed Tap Barrel, Fluid Drain, Charred Wood, Laser Quarry, Laser Pylon Top, Laser Pylon Base, High Refractory Beehive Oven, Dacitic Tuff, Bamboo Wall and Banboo Culms Wall
#### --- Additions ---
- Added slab, stairs, vertical slab, and wall variant for various blocks without a complete set
- Added Rose Marble, Gray Marble, Serpetinite, Marlstone, Norite, Shonkinite, Pyroxenite, Soul Sandstone, Blueschist, Greenschist, Diabase, Eclogite, Honeystone as stones with all associated variants
- Added Komatiitic Tuff, Kimberlitic Tuff
- Added Clay Loam, Sandy Loam, Silty Loam, Loamy Sand, Sandy Clay Loam, Silty Clay Loam, Sandy Clay, Silty Clay, Loam, Humus as soil varieties
- Added Grassy Soil version of each soil (grass blocks with additional functionality)
- Added Grassy Paths for each Grass Soil
- Added Tilled Soil (farmland block for all soils)
- Added Goldenrod, Red Lily, Orange Lily, White Lily, Purple Morning Glory, Black Morning Glory, Blue Morning Glory as double block flowers
- Added White Clover, Red Clover, Crimson Clover, and Yellow Clover as ground flowers
- Added Green, Brown, Gray, and Black Tektite
- Added Rheniite Ore, Coltan Ore, Chalcocite Ore as Rankine Ores
- Added Rankine variant of the vanilla ores for texture adapting 
- Added Bog Iron, Porphyry Copper, Kimberlitic Diamond Ore
- Added Pedestal variants for Galinstan Alloy and Sodium Potassium Alloy
- Added Frozen Meteorite and Frozen Meteorite Bricks
- Added Meteoric Ice (more slippery than blue ice)
- Added Metal Bars variants for all alloys (WIP)
- Added slab, stairs, vertical slab and wall variants for Fiber Block
- Added Asphalt (full block, 3/4 block, 1/2 block, 1/4 block)
- Added Erythrina as a new tree type with all variants
- Added Petrified Chorus and Charred variants of wooden blocks
- Added Lightning Glass and Fulgurite (obtained from lightning stikes)
- Added Sod Block, Cement, Fire Clay Bricks and Kaolin Bricks with variants
- Added Light Gravel and Dark Gravel
- Added Charcoal Block and Pozzolana as layer blocks
- Added White Sand and Silt
- Added Flood Gate, Metal Pipe, Ground Tap
- Added Pokeberry Bush
- Added Short Grass, Stinging Nettle
- Added Ender Shiro
- Added Stump block
- Added Sulfur Dioxide Fumarole, Carbon Dioxide Fumarole, Hydrogen Chloride Fumarole, Hydrogen Sulfide Fumarole
- Added Sulfur Dioxide Gas Block, Carbon Dioxide Gas Block, Hydrogen Chloride Gas Block, Hydrogen Sulfide Gas Block
#### --- Changes ---
- Ores now drop their ore-related item by default and are affected by the Fortune enchantment (1.17 parity)
- Rankine Ore blocks will take the texture of the clicked block when placed
- Harvest level, hardness and resistance values adjusted for many stones and ores
- Stone Bricks, Planks, and Polished Stones have additional textures (Build Modes - WIP)
- Tree Taps will place fluid in the world using a Flood Gate through a line of Tap Lines
- Charcoal Pit produces Charcoal Blocks instead of Charred Wood
- Beehive Oven has randomized cook times based off how upgraded the structure is
- Berry bush lootables adjusted to match Sweet Berries
- Updated many block textures
- Added flamability to many blocks

### ITEMS
#### ---Removals---
- Removed Metal Scraper (functionality migrated to pumice soap)
- Removed Stone Hammer, Iron Hammer, Diamond Hammer, and Netherite Hammer
- Removed Iron Spear, Diamond Spear and Netherite Spear
- Removed Titanium Crowbar and changed Steel Crowbar to an alloy tool
- Removed Steel Rod, Cast Iron Rod, YAG Rod
- Removed Dry Mortar
- Removed Element Transmuters
#### ---Additions---
- Added Gas Bottles
- Added Cement Mix
- Added Wooden Mallet 
- Added Wooden Gold Pan and Pewter Gold Pan
- Added nuggets for alloys
- Added Alloy Knives to all existing Alloy toolsets
- Added Alloy Crowbars to all existing Alloy toolsets
- Added Rope Coil Arrow, which can deploy rope held in the off-hand
- Minerals: Added bauxite, ringwoodite, wadsleyite, forsterite, mellite, bridgmanite, ferropericlase, apatite, laurite, realgar, gypsum, rutile, chlorite, diopside, azurmalachite, goethite, monazite (Gd), monazite (Y), pyrite, spodumene, cooperite, sanidine, hedenbergite, talc, stishovite, asbestos, serpentine, potash
- Added Kaolinite
- Added Pokeberries
- Added Jams for each of the berries
- Building Tool: hold in offhand to place vertical planks and bricks
#### ---Changes---
- Wear mechanic has been removed from Alloy tools
- Corrosion Resistance and Heat Resistance now works properly on alloy armor
- Herbicide: removed ablity to create coarse dirt, increased default range, prevents Grassy Soils from growing new plants
- Functionality of Solidified Gas Ingots has been transferred over to Gas Bottles and now act as normal ingots
- Right-click functionality has been added to knives in the offhand to Parry, which causes a brief window where damage cannot be taken
- Right-click functionality has been added to crowbars, which can now be used to pull blocks towards the user based on the face of the block that they right-click
- Gold Pans (and other sluicing tools) now have a cooldown on use
- Shulker Gas Vaccuum can now make Gas Bottles if an empty bottle is held in the off-hand
- Shulker Gas Vaccum places Gas Blocks similar to Gas Bottles
- Modified alloy item NBT format significantly
- Items with tooltips now have lang text components
- Updated many item textures


### FLUIDS
- Added Sulfuric Acid

### WORLDGEN
- Reverted under water generation of sand, clay, and gravel pathches to vanilla features
- Improved config options for defining worldgen per biome (wiki page coming soon)
    - Grass / Dirt replacer (replaces 2 additional block of stone underneath grassy areas)
    - Gravel Replacer
    - Stone Layers
    - Intrusions
    - Vegetation growth
- Stone generation changed significantly
    - Default layer count reduced
    - Location of stones changed
    - Vanilla Stone intrusions spawn in all biomes of the Overworld
- Ore generation updated and config options improved (wiki page coming soon)
    - Slightly increased Native Ore generation
    - Balanced other ores around location, vein size, and vein count frequency
- Adhusted meteorite generation in the overworld and added floating meteors in the End
- Fire Clay generates under coal ores
- Kaolin, Bog Iron spawn as ore blobs
- New tall flowers spawn in world
- Fumaroles spawn in world


### ENCHANTMENTS
#### ---Additions---
- Added Backstab, a knife enchantment which deals a multiplier of the current knife's damage when attacking an enemy's back
- Added Grafting, a knife enchantment which causes one sapling to always drop from a leaf block
- Added Poison Aspect, a knife enchantment which inflicts Poison or Weak on hit depending on the type of enemy for a short duration
- Added Preparation, a knife enchantment which increases the window for a successful parry
- Added Retreat, a knife enchantment which turns you invisible for a short duration when you parry damage
- Added Retaliate, a knife enchantment which on parry returns the damage you would've taken to the attacker
- Added Leverage, a crowbar enchantment which increases the damage of the tool based on the size of the target you are attacking
- Added Lift, a crowbar enchantment which allows you to right-click a block in the air to bring the user to the top of the block if there is space
- Added Retrieval, a crowbar enchantment that allows harvesting any block provided its harvest level is less than or equal to the crowbar's harvest level and the block is not in the tag #rankine:crowbar_resistant
- Added Shape Memory


### TAGS
- Many tags switched over to #forge from #rankine
- Added Skarn and Breccia to #forge:cobblestone
- Added #rankine:crowbar_resistant block tag, for blocks which can not be harvested at all by a crowbar (with or without enchantments)
- Added #rankine:sluicing_tools, which allows an item to be used as a "tool" in a sluicing recipe
- Added missing #forge:storage_blocks/*
- Added/updated many other tags. A list will be available on the wiki soon

### RECIPES
####  --- Crafting Recipes ---
- Many recipes changed; notable ones below
- Bricks are 2x2 recipes
- Alloy Furnace uses a campfire instead of a furnace
- Sheetmetals use 3 ingots and 6 nuggets for 8
- Some recipes changed to alloy_crafting to utilize Alloy Components
- Some recipes moved to the Fusion Furnace - WIP
####  --- Crushing Recipes ---
- All ores and stones have had their crushing outputs overhauled
- Ores now have their primary crushing component at Harvest Level 0 (crushing at any level yields the ore's primary mineral)
- Mafic Igneous stones that crushed into Cobblestone now crush into Blackstone
- Sedimentary stones that crushed into Cobblestone now crush into Breccia
- Metamorphic stones that crushed into Cobblestone now crush into Skarn
- Native ores can now be crushed
####  --- Sluicing Recipes ---
- Added an ingredient arguement tool which defines the item that must be right-clicked on a block in order to sluice the block (the item must also have the tag #rankine:sluicing_tools)
- Added sluicing recipes to gravel and soil types
#### --- Alloying Recipes ---
- Removed int argument "required"
- Added boolean argument "required" to any input variable, which defines an ingredient as required to make the output
- Alloy Furnace and Induction Furnace recipes are now separated in JEI
#### --- Alloy Crafting Recipes ---
- Added inheritRecipe, an optional string argument which defines a specific recipe that will be used to define the output's composition and recipe variables
- Added additional arguements to key definitions, alloyComp and alloyRecipe, which define specific compositions and alloy recipes that can be used for that ingredient
#### --- Element Recipes ---
- Added Element JSON recipe type, to now define stats of elements in a more dynamic matter
- Added JEI support
#### --- Rock Generator Recipes ---
- Added Rock Generator JSON recipe type, to define custom generators for Cobblestone, Basalt, Stone, Obsidian, and Sediment Fan generation types
- Contains five "types": intrusive_igneous, extrusive_igneous, sedimentary, metamorphic, volcanic
- Generators no longer produce random rock outputs, each generator uses the following as its default:
    - Intrusive Igneous (cobblestone generator): Cobblestone
    - Extrusive Igneous (basalt generator): Blackstone
    - Sedimentary (sediment fan generator): Breccia
    - Metamorphic (stone generator): Skarn
    - Volcanic (obsidian generator): Obsidian
- Added JEI support


### MISC
#### --- Changes ---
- Journal updates
- Removed unecesary/broken config options and combined files into one
- Added a config to disable infinite water
- Hoeing vanilla blocks will convert them into Tilled Soil rather than farmland
- Vanilla tools are now enabled by default
- Base harvest levels adjusted to accomodate vanilla integration
- Added mixin which changes AnimalEntity spawn conditions to #forge:grass rather than only vanilla grass blocks
#### --- Alloying ---
##### Alloys
- Lead, Zinc, Arsenic, Cadmium, can now be used in Pewter
- Beryllium can now be used in all Bronze recipes
- Potential compositions of White Gold Alloy have been adjusted
- Copper can now be used in White Gold Alloy
- Cadmium and Lithium can now be used in Duralumin Alloy
- Iridium can now be used in Titanium Alloy, Blue Gold Alloy, and White Gold Alloy
- Osmium can now be used in Blue Gold Alloy and White Gold Alloy
- Tellurium can now be used in Bronze, Cupronickel, Cast Iron, Steel, and Stainless Steel Alloy
- Nitinol can be used to craft Titanium Alloy tools
- Alloys now have bonus stats again (which can be modified in Alloying JSON format)
- Element stats are now determined by the Element recipe type instead of being internally defined
##### Elements
- Element enchantments now work by associating a factor per each percent of an element for a specific enchantment, and can now be considered between multiple enchantments
- Elements can now enchant armor
- Netherite now gives Toughness as percentage increases, has rescaled harvest level formula, loses Corrosion Resistance at a quicker rate as percentage increases, and no longer modifies Attack Speed
- Cobalt, Tungsten now provide up to Harvest Level 4
- Carbon now provides toughness at small percentages
- Lithium increases in Heat Resistance as percentage increases (originally decreased), has a rescaled toughness stat, and no longer provides Unbreaking (enchantment)
- Silver, Beryllium, now receive enchantments at 10% or higher
- Nickel durability and mining speed increased at lower percentages, now gives toughness
- Modified Titanium Mining Level formula to be better at smaller percentages
- Bismuth and Lead now grant a harvest level at 12% and 15% respectively
- Boron grants a harvest level at 6%
- Copper now gives heat resistance 
- Aluminum grants harvest level at 10% and 20% and gives attack speed
- Arsenic and Cadmium now can give Poison Aspect to Knives
- Lead and Bismuth now give up to Harvest Level 2 and give corrosion resistance
- Iron now has negative corrosion resistance
- Mercury now reduces harvest level by 1, but has other stats improved
- Molybdenum, Osmium, Iridium now increases harvest level by 1
- Zinc and Calcium now give knockback resistance
- Significantly changed or newly added stats for: Calcium, Cadmium, Arsenic, Palladium, Phosphorus, Zirconium
- All other element formulas simplified and slightly modified


### FIXES
- Fixed crash with water bucket and rope interaction
- Fixed crash relating to the rendering of Bamboo Boat
- Fixed and improved advancement triggers
- Fixed flower seeds infinite use

### COMMUNITY CONTRIBUTIONS
- Fixed Alloy Template crash (Yuesha-yc)
- Added deobf and sources jar artifacts (Yuesha-yc)



## Project Rankine Version 1.2.3 Minor Changelog [2021-06-15]

### Hotfix 2 [2021-7-7]

- Added smelting recipe for Stibnite into Antimony Ingot
- Added Yellow Birch and Black Birch items to various wood tags
- Added missing metal door items to #minecraft:doors
- Added JEI information page for Rankine Box
- Fixed Yellow Birch, Black Birch and Bamboo Culms Boats
- Changed Quake to only be applied to Pickaxes and Shovels
- Changed recipe for Gyratory Crusher
- Changed Speedometer to update less frequently and be more accurate
- Changed text format for F3 tools
- Changed thermometer bounds to reflect certain conditions (snow, rain, etc.)
- Changed Path Creation config option to be disabled by default
- Fixed Rock Drill outputting its information twice
- Fixed model files for F3 tools
- Fixed permanent effects from Health Pendant and Speed Pendant
- Reduced health gain from Health Pendant from 20 to 10
- Removed extra Magnesite Block recipe
- Updated Rankine Journal


#### Community Contributions
- Added Simplified Chinese (zh_cn) translation (Yuesha-yc and Duckegg128)

### Hotfix 1 [2021-6-18]
- Added Sodium Potassium (NaK) Alloy Ingot and Block
- Modified recipe for Rankine Box
- Added Scandium to Duralumin, Titanium, and Purple Gold Alloy recipes
- Added additional recipe advancements for plant fiber
- Changed alloy spear recipes to use the shape of the Flint Spear recipe (amount of materials unchanged)
- Gyratory Crusher now stops spinning if there is no input
- Fixed Gyratory Crusher bug where not all outputs were checked
- Fluid drain texture modified to reflect its recipe
- Ghast Regeneration and Curse of Withering can now only be applied to Alloy Tools
- Added Prying entry to Enchantments in Rankine Journal
- Set axeRequired config to be false by default
- Added beehiveOvenSkylight config
- Added #rankine:foraging, which includes #forge:seeds, minecraft:potato by default
- Foraging (for tools in #rankine:foraging_tools) now uses the #rankine:foraging tag for drops
- All stone variant recipes moved to rankine/recipes/stone_variants
- Fixed #forge:cheese
- Added #forge:crops/aloe
- Removed recipe for Power Cell Fabricator
- Changed Botanist Villager trades
- Fixed Block of Chromite to Block of Chromium blasting recipe
- Cooktime of all mineral blocks increased from 100 to 800 in the blast furnace

### Blocks
- Added Sodium Vapor Lamp
- Added Cast Iron Pole
- Added Fiber Blocks: plant based glazed terracotta
- Added Fiber Mats: fiber blocks in carpet form
- Added Fluid Drain: places fluids in-world from a tap barrel
- Added Mycellium Path: work siilar to grass path
- Podzol can be converted to grass path
- Added blocks for each mineral: parity with changes in 1.17
- Added bamboo culms, yellow  birch and black birch boats
- Added Juglone Fluid
- Added Corn Stalk
- Added Aspargus Root: Grows aspargus over time, can die
- Added Corn, Rice, Asparagus, Cotton and Jute Plants
- Sawdust can be placed like snow layers
- Added Ash: can be placed like sawdust
- Added Bone Ash: can be placed like sawdust
- Blueberry, Elderberry and Cranberry bushes are two blocks high


### Items
- F3 screen tools, display info while held. Position is at head level by default, sneak to get position at feet. Config options available.
    - Compass: shows X,Z coordinates and direction
    - Clock: shows game time, 24 hour format and in ticks
    - Altimeter: shows Y value
    - Photometer: shows light level
    - Thermometer: shows temperature
    - Speedometer: shows speed in blocks per second
    - Biometer: shows current biome
- Cotton
- Jute
- Corn Seeds
- Rice Seeds
- Asparagus Seeds
- Cotton Seeds
- Jute Seeds
- New seeds can be obtained through foraging
- Added Thortveitite: 4th crushing component of pegmatite
- Plant Fiber: intermediary crafting component
- Food Related Items
    - Toasted Coconut
    - Rice
    - Asparagus
    - Ear of Corn
    - Wheat Flour
    - Corn Flour
    - Rice Flour
    - Dough

### Entities
- Added Rock Collector Villager with a POI of Sediment Fan
- Changed Gem Cutter trades

### Mechanics
- Added tree felling (logs only) to any items in #rankine:tree_choppers. Config options available.
- Tree Taps will only produce fluid from living trees
- Knives are effective at mining through leaves
- While walking on grass, podzol, or mycelium, there is a chance for a path block to form under the player. Config options available.

### Recipes
- Added JSON recipe type for Crucible (rankine:crucible) and Evaporation Tower (rankine:evaporation)
- Organized recipes folder into sub folders
- Added recipes for alloy rods, alloy gears and alloy plates using default alloys
- Updated recipes to include tags
- Totem of Cobbling durability from 64 to 1 on craft
- Crucible recipe for glowstone changed to glowstone dust
- Adjusted all Crucible recipes
- Added recipes for plant fiber
- Crafting Rope now requires plant fiber
- Changed Gun Cotton recipe
- Changed Bandage recipe
- Changed Pumice Soap recipe
- Changed Thermometer recipe
- Added recipe for podzol from biomass
- Added a recipes for bread from flour
- Added a recipe for slime balls from resin
- Added a recipes for bonemeal
- Added a recipe for Bottle o' Enchanting
- Added a recipe for String from cotton
- Adjusted Evaporation Tower outputs
- All wood variant recipes should be implemented
- Slag can be blasted to iron nugget
- Added a recipe for Dry Mortar using slag
- Lodestone can be blasted to iron ingot for more XP
- Dyes
    - Juglone to brown dye in evap tower
    - Changed output of Strawberries to dye from red to pink
    - Changed output of Elderberries to dye from black to purple
    - Added recipe for lime dye from Aloe
    - Added recipe for gray dye from ash
    - Added recipe for light gray dye from bone ash

### Tags
- Tagged cupronickel in #forge:ingots/constantan
- Tagged rope in #forge:string
- Added missing items to various stone variants tags
- Added #rankine:tree_choppers for tree chopping
- Added tags for crops
    - #forge:crops/cotton
    - #forge:crops/jute
    - #forge:crops/corn
    - #forge:crops/cotton
    - #forge:crops/rice
    - #forge:crops/coconut
    - #forge:crops/walnut
- Added Cheese to #forge:cheese
- Added wheat flour, rice flour, corn flour to #forge:flour
- Added minecraft:bread to #forge:bread
- Added minecraft:egg to #forge:eggs
- Added ash and bone_ash to forge:ash
- Added forge:storage_blocks/pewter
- Added forge:storage_blocks/tungsten_heavy_alloy
- forge:storage_blocks/cobalt_superalloy
- Added rankine:geodes
- Added #rankine:foraging_tools for foraging purposes
- Added tool category tags
    - #forge:wooden_tools
    - #forge:stone_tools
    - #forge:iron_tools
    - #forge:diamond_tools
    - #forge:golden_tools
    - #forge:netherite_tools
    - #rankine:amalgam_tools
    - #rankine:alloy_tools
    - #rankine:tungsten_heavy_alloy_tools
    - #rankine:cobalt_superalloy_tools
    - #rankine:nickel_superalloy_tools
    - #rankine:stainless_steel_tools


### Misc
- Crucible no longer has set cook time and set ingredients that reduce cook time
- Crucible liquid texture changed and can be modified slightly by color attribute from recipes
- Fixed offsets of Crucible GUI and Evaporation Tower GUI
- Fixed config option for requiredAxe
- Added config options
- Journal updates
- Changed foraging drops
- Mobs are less likely to path through berry bush blocks
- Removed right click functionality of thermometer
- Decreased sapling drop rate from black walnut leaves
- Max storage for Totem of Cobbling increased from 64 to 512
- Totem of Cobbling does not function at 0 durability
- Removed lava from evaporation tower fluids
- Reduced level cost of applying snowshoes, ice skates, sandals and gas mask from 30 to 20
- Fixed server crash upon creating alloy armor
- Added loottable for template table
- Added loottable for Magnolia Planks
- Fixed loottable for pinyon pine planks
- Added advancement for collecting every geode
- Tooltips added for pendants
- JEI info pages for snowshoes, ice skates, sandals and gas mask
- Fixed Cinnamon sapling growing into a cork oak tree
- Rearrangement of creative tabs
- Renamed Alumina to Bauxite
- Fixed rope and ladders not being climbable




## Project Rankine Version 1.2.2 Minor Changelog [2021-04-10]

### Hotfix 2 [2021-4-24]
- Added simple JEI information for boiling treetap liquids
- Added advancements for treetapping
- Added recipes for polished troctolite and troctolite bricks
- Added pegmatite bricks, troctolite bricks, comendite bricks to #minecraft:stone_bricks
- Added granite, diorite, andesite bricks variants to missing tags
- Changed texture for Maple Sap Bucket to look more distinct from Sap Bucket
- Fixed incorrect version number in-game
- Fixed critical multiplayer bug

### Hotfix 1 [2021-4-11]
- Added boats for new wood types
- Added missing Steel Alloy Arrow recipe
- Added alternative recipe for Vulcanized Rubber that uses Sodium Sulfide
- Added more journal entries and information
- Changed trampoline recipe to use Cast Iron Rods
- Fixed infinite Flower seeds bug
- Fixed Alloy armor not applying armor values properly
- Fixed localization for Netherite Nugget


### Additions
#### Blocks
- Added blocks for Hydrogen Gas, Helium Gas, Nitrogen Gas, Oxygen Gas, Fluorine Gas, Neon Gas, Chlorine Gas, Argon Gas, Krypton Gas, Xenon Gas, Radon Gas, and Oganesson Gas. Gas blocks act as air blocks with special properties.


- Added Sap, Maple Sap, Latex, Resin as fluids

- Gun Cotton: creates small explosion

- Camphor Basil Plant 

- Aloe Plant

- Sharinga tree and wood variants

- Black Walnut tree and wood variants

- Cork Oak tree and wood variants

- Cinnamon tree and wood variants

- Remaining wood variants of Black Birch, Yellow Birch and Bamboo Culms

- Block of Amber

- Tree Tap: used to collect liquids from trees

- Tap Line: piping network for automated tree tapping

- Tap Barrel: fluid barrel for any tree products

- Trampoline: used to bounce entities upwards, scales with number of connected trampolines

- Thenardite, Sodium Sulfide, Sodium Hydroxide

- Added creative-only Gas Vent block that can emit a gas in an area (set via config)

#### Items
- On right click, Soldified Gas Ingots turn into their respective gas blocks

- Shulker Gas Vacuum, which can be used to transport gas blocks

- Totem of Cobbling, which places cobblestone and absorbs stones for durability when held either in the main hand or off-hand

- Amber: decorative gem

- Black Walnuts: droppped from black walnut leaves

- Juniper Berries: droppped from juniper leaves

- Added foods: Maple Syrup, Pancakes, Pancake Batter, Pancake Breakfast, Roasted Walnut, Toast, Cinnamon Toast, Trail Mix

- Added miscellaneous materials for crafting: Cinnamon, Pulp, Sawdust, Dry Rubber

- Added Buckets for tap fluids: Sap Bucket, Maple Sap Bucket, Latex Bucket, Resin Bucket, Jugalone Bucket

- Cork: can place blocks directly on water

- Synthetic Leather: used to craft leather

- Bandage: instant heals for 2hp, 3 second cooldown

- Flower Seeds: places a random flower from #rankine:flowers

#### Misc Additions

- Added recipes for new blocks/items

- Added an alternative recipe for leather

- Added an alternative recipe for sugar

- Stripping log/wood blocks will drop items

- Config options and tags for tree tapping

- Config option for cheese aging time

- Config options for various gas properties, such as dissipation rate and gas movement

- Added more items to composter

- Added crushing to Zircon
 


### Changes

- Blasting Powder will drop all items (like TNT)

- Ore Detector now requires Indium Tin Oxide to craft

- Evaporation Tower can use other liquids for processing

- Functionality for knives now applies to all items in #rankine:knives

- Galena ore now gives Thallium nuggets at level 5 crushing

#### Alloys
- Bonus tool stats can now be changed via the config for all alloys

- Reduced bonus stats for early-game alloy tools (these bonus stats have been transfered to main elements of the alloy)

- Added Beryllium to Nickel Superalloy

- Added Nitrogen to Stainless Steel


#### Elements
- Beryllium now gives the Fortune enchantment at greater than or equal to 12%

- Copper now has Heat Resistance and Toughness as percentage increases

- Tin has increased mining speed and enchantability at higher percentages (originally stopped increasing at 50%)

- Tin increases mining level by 1 at percentages greater than 80%

- Gold and Silver now has Heat Resistance as percentage increases

- Silver and Tin gives more Enchantability at small percentages

- Gold, Zinc, and Silver now has negative Toughness as percentage increases

- Netherite is now has an Atomic Number of 161 rather than 156 (puts Netherite in the same period as Gold)

- Added more Minecraft-related elements for compatibility with other modded alloys: 
    - Mana (M), with an Atomic Number of 122 for Magic-related materials
    - Slime (Sl), with an Atomic Number of 162 for Slime-related materials
    - Prismarine (Mr), with an Atomic Number of 163 for Prismarine and Aquatic-related materials
    - Wither (Wi), with an Atomic Number of 164 for Wither-related materials
    - Ghast (Gh), with an Atomic Number of 165 for Ghast-related materials
    - Phlogiston (Pg), with an Atomic Number of 166 for Blaze and Fire-related materials
    - Endite (Ed), 167 - with an Atomic Number of 167 for End-related materials
    - Dracocaeli (Dr), with an Atomic Number of 168 for Dragon-related materials 


### Misc
- Updated Forge version to 36.1.2

- Ores should generate less often (hopefully never) with the base stone texture when they aren't supposed to

- Ores now change their background stone texture if another ore is in the vacinity and has a background ore texture 

- Journal Updates

- Improved various older textures

- Updated Tags

- Updated misc textures

- Added tags for vertical slabs

- Fixed typo in localization of Block of Pewter

- Fixed alloy tools not changing enchantability based on alloy composition

- Fixed starting book config not giving the Rankine Journal if Patchouli is installed

- Fixed steel advancement referencing an older process

- Fixed bug with White Gold Spear not having its enchantment set

- Fixed spears not having access to Impaling and Loyalty in the Enchantment Table

- Fixed various bugs with incorrect values/registration for features

- Fixed anvil item-related enchantments (Snowshoes, Ice Skates, Sandals) that could not be applied via commands


## Project Rankine Version 1.2.1 Minor Changelog [2021-3-8]


### Hotfix 2 [2021-3-9]
- Reverted Harvest Level change to Alloy tools

- Crushing JEI, advancements, the Hardness Tester, and the journal now use actual harvest level values instead of the adjusted harvest level values

- Fixed config to change harvest level of ores (stone config settings will be fixed in a later update)

- Changed the default composition for Stainless Steel and Nickel Superalloy to a composition which gives Hardness Level 4

- Fixed consistency between default composition of alloy ingots and alloy tools

- Buffed Cobalt Superalloy tool bonus durability from 370 to 500

- Removed debug code from some events

### Hotfix 1 [2021-3-8]
- Fixed Amalgam Sword recipe

### Additions

- Added Gem Cutter villager

- Added the Diamond Anvil Cell, which is used as the POI for the Gem Cutter Villager

- Added Titanium Alloy, which can be made in the Alloy Furnace and the Induction Furnace, and Block of Titanium Alloy

- Added Titanium Alloy Pickaxe, Axe, Shovel, Hoe, Sword, Hammer, and Spear

- Added Atomic Number, Symbol, and Enchantment information to Element Indexer

- Conig option for Evaporation Tower max height

- Config option to enable lighting fires with two flint

- Added a chance and config for flint to be consumed when lighting a fire

- Snow Drifter, Dune Walker and Speed Skater enchantments increase step height to 1 block while active

- Added Cleanse enchantment for the Stainless Steel Sword

- Added cast iron and brass ladders

- Added Beehive Oven recipes for terracotta and glazed terracottas

- Added experimental config options for radioactive and water-reactive items

- Added challenge advancement for creating every Colored Gold Pendant

### Changes
- Netherite Blue Gold is now exclusive to the Induction Furnace, similar to other Colored Gold Netherite Alloys

- Removed default extra damage from Bronze, Blue Gold, Black Gold, and Invar tools

- Relocalized Aluminum Ladder to Duralumin ladder. Recipe reflects this change.

- Added Atomic Number and Symbol to Element Indexer

- Ultra-high Refractory Bricks require High Refractory Bricks

- Increased amount of Silicon Carbide required for Ultra-high Refractory Bricks from 1 to 2

- Induction Furnace now requires two Ultra-high refractory bricks, which replaces Quartz Blocks

- Altered the structure for Evaporation Tower where adding layers increases processing speed

- Custom alloy items (gears, plates, wire) now change their name based on their composition if there is a matching alloy or if its composition consists of only two elements

- Modified element stats for Platinum, Titanium, Hafnium, and Oxygen

- Added Hafnium to Nickel Superalloy

- Added the rest of the metallic elements (except iron, platinum, tungsten, and tantalum) to Amalgam Alloy

- Added the rest of the Lanthanide elements to Mischmetal

- Recipe for silicon to silicon carbide moved from blast furnace to beehive oven

- Other non-Lanthanide elements in Mischmetal restricted to less than or equal to 5%

- Amalgam Alloy tools are now craftable again

- Titanium and Aluminum modified to be able to go down to 5% in alternative Nickel Superalloy recipe

- Titanium Crowbar is now crafted by using Titanium Alloy

- Harvest level in information for Alloy Tools now matches Hardness Tester and Hammer crushing levels (Flint = 1, Bronze = 2, Invar = 3, Steel = 4, Superalloy = 5, Advanced = 6) 

- (Highly Experimental) All rankine element ingots and nuggets now have stats for water reactivity and radiation. Lithium, Sodium, Potassium, Rubidium, Cesium and Francium explode in water. All elements with atomic number 84 and higher cause radiation poisoning to the player over time. These options will only be enabled if the respective config is enabled.

### Misc
- Fixed issue where Native gold and bismuth genType was set to true when it was intended to be set to false

- Fixed bug with Evaporation Tower using incorrect tag

- Fixed sluicing JEI and added information about percent chances per drop

- Fixed issue where Etched Glass missing from creative menu

- Fixed Health Pendant reseting when leaving a world

- Stone layering feature for Nether and End now use their respective height config

- Modified properties of Mercury Fluid for a future update

- Rope Coil drops the correct amount of rope

- Updated JEI Info tab for Beehive Oven

- Fixed missing loottable for Cryolite Ore

- Fixed Laser Quarry Debug code and allowing shift-clicking Power Cells into the inventory

- Major Journal Updates

- Updated Crucible GUI texture with result slots

- Improved textures for sheetmetals

- Modified some misc textures

### Community Contributions
- Fixed Silver and Bismuth Sheetmetal recipes (XBagon)






## Project Rankine Version 1.2.0 Major Changelog [2021-2-28]

Since this is a major update, starting a new world and regenerating the config is suggested to prevent errors and missing items!
### Additions
#### Blocks
- Added Black Dacite, Red Dacite, Porphyry, Purple Porphyry, Chalk, Mudstone, Arkose Sandstone, Quartz Sandstone, Troctolite, Pegmatite, Comendite, Dolostone, Mariposite, Skarn, Phyllite stones and variants

- Added decorative checkered stone blocks and variants

- Added vertical slabs for most blocks

- Added cast iron support variants

- Added uncolored concrete and variants

- Added various storage blocks for minerals and gems

- Added mud that replaces dirt under water

- Added fire clay that spawns in dirt

- Added Charcoal Pit, a block which converts nearby logs into Charred Wood that can be mined to give charcoal

- Added Botanist Station, which is used as a job site for the Botanist Villager

- Added Sediment Fan, which can be used to make generators for all sedimentary blocks

- Added Rankine Box, which can be used to transmute elements into other elements

- Added Gyratory Crusher, which acts as an upgraded piston crusher that uses power cells to determine crushing level

- Added two types of electromagnets that can pull blocks

- Added monazite ore, cryolite ore, lonsdaleite ore, pyrite ore

- Added colored LEDs, a light source block that works like a redstone lamp

- Added colored variants for mineral wool

- Added enstatite and a new form of decorative block for enstatite and meteorite

- Added kaolinite block and porcelain block


#### Items
- Added Alloy Dust, Alloy Gear, Alloy Ingot, Alloy Plate, Alloy Rod, and Alloy Wire which can be used for custom alloy recipes

- Added Diving Armor and Conduit-Powered Diving Armor

- Added Magnesium Arrow, a type of arrow which causes neaby undead mobs to attack the target hit by the arrow or blinds entities periodically in an area if in the ground

- Added 6 tiers of Power Cells, which can be used in many post-steel machines

- Added Up, Down, Left, and Right Element Transmuters for use in the Rankine Box

- Added magnets that can pickup items in a radius around you on right-click

- Added prospecting stick, an early game ore detector

- Added trona, borax, rhodonite, rhodochrosite, labradorite, topaz, pearl, lonsdaleite diamond, chrome enstatite

- Added fire clay ball for creating refractory brick

- Added various crafting components; hard carbon electrode, indium tin oxide, cadmium telluride cell

- Added a coin for villager trades

- Added topaz

- Added maple boat

- Added cryolite and lepidolite

- Added lodestone, a type of magnetite that can be used to make the Lodestone Block

- Added plagioclase feldspar, mica, amphibole for stone crushing 

- Added recipes for pendants and config option



#### Misc Additions

- Added JSON recipe types: alloy_crafting, alloying, crushing, beehive_oven, and sluicing
(More information about these types will be posted to the new wiki on the GitHub page)

- Reworked the Crucible into a tile entity that uses fluxes and heat to make various products, now including glass and redstone

- Dyes can be used in world on various blocks to color them

- Added recipes for minecart, shears, bucket, rails, anvil, chain and others using #rankine:crafting_metals

- Added configs for stone generation (layers and intrusions)

- Added igneous, sedimentary and metamorphic stone tags

- Added movement speed modifiers for walking on certain blocks (with configs)

- Added Mercury Poisoning, a potion effect which disrupts the senses of most mobs and reduces attack speed

- Added Conductive, a potion which increases the likelyhood of being struck by lightning during a rainstorm


- Added stats to all elements and modified stats of many existing elements* 

- Added block breaking and hitting sounds to hammer and crowbar

- Corrosion Resistance and Heat Resistance can no longer be negative for alloys (0% guarantees a loss of durability in aquatic/high heat environments respectively)

- Added special generators for all igneous, sedimentary, and metamorphic rocks

- Added the Metallurgist Villager, which uses the Alloy Template Table as a job site

- Added the Mineralogist Villager, which uses the Piston Crusher as a job site

- Added the Botanist Villager, which uses the Botanist Station as a job site


- Adjusted fuel values of logs to match realistic heating values and adjusted wooden variants accordingly

- Added Antiquated, an enchantment specific to Pewter tools which increases the luck stat

- Added Impact, a spear enchantment which increases knockback of spears when thrown

- Added Foraging, a hoe enchantment which adds a chance to obtain crops and other items from digging in dirt

- Added Quake, a mining tool enchantment which increases mining speed based on depth

- Added Dune Walker, a boot enchantment which increases walking speed on sand

- Added Snow Drifter, a boot enchantment which increases walking speed on snow

- Added Speed Skater, a boot enchantment which increases walking speed on ice

- Added Sandals, Snowshoes, and Ice Skates which can be combined with boots in an anvil to give the enchantments above respectively

- Added Excavate, a hammer enchantment which increases the number of blocks broken by a swing of the hammer

- Hammers can now repair anvils at the cost of 100 durability when shift-right clicking on a broken anvil

- Added more challenge advancements and new reordered story advancements

- Added configs for various mechanics and blocks

- Added ore texture support for Create, Quark and Unearthed




### Changes
- Removed Triple Alloy Template, Copper Wire, Redstone Battery, Lithium Power Cell, Pig Iron, Wrought Iron, Sponge Iron, and other unused items

- Removed Coal Forge and associated templates

- Changed the registry name for various blocks and items (will make them disappear from worlds using previous versions of Project Rankine)

- All alloys can have up to 5 unique elements

- Alloy Furnace and Induction Furnace both now have 6 slots in which inputs can be placed in any order and multiple slots can have the same input

- Outputs of crushing are now based on the harvest level of the tool/machine used to crush, and there are now 6 possible outputs for a given block/item

- Piston Crusher "harvest/crushing level" has been set to 2

- By default all stones in the overworld have harvest level 0 except Peridotite with HL 2

- All other stones from Project Rankine in the Nether and End are set to HL 2

- Default stone and ore gen settings in the config have been changed significantly

- Removed the vanilla recipe for charcoal

- Changed recipes for all refractory bricks

- Changed Laser Quarry structure

- Retextured many blocks and items

- Meteoric iron ores have to be crushed to yield meteoric iron chunks

- Cinnabarite ore has to be crushed to yield redstone

- Meteoric Iron tools have been relocalized to Invar tools

- Alloy tools and items that do not have a default alloy now change color based on their composition

- Changed enchantments given by default for Black Gold Tools

- Alloy Template Table can now use any dye and colors the Alloy Template with respect to the dye used



### Misc
- #145 Fixed issue related with knife drops spawning inside the block

- Fixed bugs related to the dynamic harvest level system of alloy tools

- Fixed bugs related to the display of wear/tool efficiency

- Fixed bugs related to items that should spawn with enchantments

- Fixed bugs related to using an element that does not exist or inputting invalid alloy compositions


*Many elements that are not in current use by the mod (mainly atomic numbers past 86) have placeholder values and will be changed in future updates



## Project Rankine Version 1.1.4 Minor Changelog [2021-1-1]
### Additions
- Major modifications to the Netherite element formulas, and Netherite can now be used as an alloy ingredient in end-game tools and gold tools

- Lead can now be used as a secondary component in place of Antimony in Pewter instead of as a tertiary ingredient

- All end game tools (Cobalt Superalloy, Nickel Superalloy, Tungsten Heavy Alloy, Stainless Steel) have had their durabilities increased by +200

- Added dry mortar which is thrown in water to make mortar, making the process no longer require a bucket

- Added enable/disable config for Rankine ores

- Added initial compatability support to TerraForged

- Added tooltips for minerals

- Added Magnesite and Chromite alternative recipe for High Refractory Brick

- Added Dolomite alternative recipe for High Refractory Brick

- Added Silicon Carbide alternative recipe for Super-High Refractory Brick

- Added alternative recipes to rails, powered rails, detector rails, activator rails, hopper, minecart

- Added magnetic metals and crafting metals tag

- Added element and alloy blocks to minecraft:beacon_base_blocks tag

- Added guide category to the Rankine Journal, which contains an initial compatability and guide page to help with progression through the mod

- Added more details to pages to the Rankine Journal

### Changes

- Changed many of the crushing recipes of stones; Most stones now give cobblestone by default when crushing

- Removed previous recipes to create cobblestone

- Moved recipes that created mortar to dry mortar instead

- Using mortar on a stone converts it to it's respective brick variant

- Metal Scraper now converts stone bricks to their respective stone variant

- Ore detector outputs block found to the player, can be turned off via config

- Adjusted stone generation to spawn intrusions through all stones

- Stripping Birch Bark with an Axe has a chance to give paper

- Changed crafting recipes of mortar to yield dry mortar

- Changed Diamond and Netherite tools to be disabled by default

- Lazurite, Plumbago, Celestine, and Majorite Ore are no longer stone specific ores

- Changed Cinnabar and Block of Cinnabar to crush into redstone

- Coal ores and cinnabar ore can no longer be crushed



### Misc
- #135 Fixed the current implementation of Alloy Ingredients in JEI so that it appears more consistently on servers

- Fixed Native Gold Ore loot table

- Fixed Spawning of Molybdenite in the end

- Fixed polished roman concrete

- Fixed Lignite, Subbituminous, and Bituminous Coals crushing recipes

- Updated textures for: Ironstone, Polished Ironstone, Ironstone Bricks and Petalite Ore

- Relocalized Iron Scraper to Metal Scraper

- Relocalized Magnesia Refractory Bricks and Zircon Refractory Bricks to High Refractory Bricks and Ultra-High Refractory Bricks respectively






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

- Changed YAG Recipe with only Yttrium and Aluminum to give 1 instead of 2

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
