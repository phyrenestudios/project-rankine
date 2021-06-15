
# Changelog
All notable changes to the mod, Project Rankine, will be kept in this file.
## Project Rankine Version 1.2.3 Minor Changelog [2021-06-15]

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

- Gallium ore now gives Thallium nuggets at level 5 crushing

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
