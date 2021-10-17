package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.elements.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public final class PeriodicTableUtils {

    private static final PeriodicTableUtils INSTANCE = new PeriodicTableUtils();

    public static PeriodicTableUtils getInstance() {
        return INSTANCE;
    }

    public final List<String> names = Arrays.asList("None","Hydrogen","Helium","Lithium","Beryllium","Boron","Carbon","Nitrogen","Oxygen","Fluorine","Neon","Sodium","Magnesium","Aluminum","Silicon","Phosphorus","Sulfur","Chlorine",
            "Argon", "Potassium","Calcium","Scandium", "Titanium","Vanadium","Chromium","Manganese","Iron","Cobalt","Nickel","Copper","Zinc","Gallium","Germanium","Arsenic","Selenium","Bromine","Krypton","Rubidium","Strontium",
            "Yttrium", "Zirconium","Niobium","Molybdenum", "Technetium","Ruthenium","Rhodium","Palladium","Silver","Cadmium","Indium","Tin","Antimony","Tellurium","Iodine","Xenon","Cesium","Barium","Lanthanum","Cerium",
            "Praseodymium", "Neodymium","Promethium","Samarium", "Europium","Gadolinium","Terbium","Dysprosium","Holmium","Erbium","Thulium","Ytterbium","Lutetium","Hafnium","Tantalum","Tungsten","Rhenium","Osmium","Iridium",
            "Platinum","Gold", "Mercury","Thallium","Lead","Bismuth", "Polonium","Astatine","Radon","Francium","Radium","Actinium","Thorium","Protactinium","Uranium","Neptunium","Plutonium","Americium","Curium","Berkelium",
            "Californium","Einsteinium", "Fermium","Mendelevium","Nobelium", "Lawrencium","Rutherfordium","Dubnium","Seaborgium","Bohrium","Hassium","Meitnerium","Darmstadtium","Roentgenium","Copernicium","Nihonium","Flerovium",
            "Moscovium","Livermorium", "Tennessine","Oganesson","Netherite"); //Index 0 for all lists are "None" and should not be used or appear

    public final List<String> symbols = Arrays.asList("","H","He","Li","Be","B","C","N","O","F","Ne","Na","Mg","Al","Si","P","S","Cl","Ar","K","Ca","Sc","Ti","V","Cr","Mn","Fe","Co","Ni","Cu","Zn","Ga","Ge","As","Se","Br","Kr","Rb",
            "Sr","Y","Zr","Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn","Sb","Te","I","Xe","Cs","Ba","La","Ce","Pr","Nd","pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb","Lu","Hf","Ta","W","Re","Os ","Ir","Pt","Au","Hg","Tl",
            "Pb","Bi","Po","At","Rn","Fr","Ra","Ac","Th","Pa","U","Np","Pu","Am","Cm","Bk","Cf","Es","Fm","Md","No","Lr","Rf","Db","Sg","Bh","Hs","Mt","Ds","Rg","Cn","Nh","Fl","Mc","Lv","Ts","Og","Nr");

    public final List<Element> amalgamExtras = Arrays.asList(Element.IRON,Element.PLATINUM,Element.TUNGSTEN,Element.TANTALUM);
    public final List<Element> amalgamNonmetals = Arrays.asList(Element.BORON,Element.CARBON,Element.SILICON,Element.PHOSPHORUS,Element.SULFUR,Element.ARSENIC,Element.ANTIMONY);
    public List<String> getElementNames() {
        return names;
    }

    public List<String> getImplementedElementNames() {
        List<String> elements = new ArrayList<>();
        for (Element e: Element.values())
        {
            elements.add(e.toString().toLowerCase());
        }
        //System.out.println(elements);
        return elements;
    }

    public Element getElementFromItem(Item item) {
        for (ResourceLocation tag: item.getTags())
        {
            if (tag.toString().contains("rankine:elements/") || tag.toString().contains("forge:ingots/") || tag.toString().contains("forge:storage_blocks/") || tag.toString().contains("forge:nuggets/"))
            {
                String temp = tag.getPath().split("/")[1];
                if (getImplementedElementNames().contains(temp))
                {
                    return Element.valueOfCaseIgnored(temp);
                }
            }
        }
        return Element.NONE;
    }

    public Element getElementFromIngotItem(Item item) {
        for (ResourceLocation tag: item.getTags())
        {
            if (tag.toString().contains("rankine:elements/") || tag.toString().contains("forge:ingots/") || tag.toString().contains("forge:storage_blocks/") || tag.toString().contains("forge:nuggets/"))
            {
                String temp = tag.getPath().split("/")[1];
                if (getImplementedElementNames().contains(temp) && (item.getTags().contains(new ResourceLocation("forge:ingots")) || (item.getRegistryName() != null && item.getRegistryName().toString().contains("ingot"))))
                {
                    return Element.valueOfCaseIgnored(temp);
                }
            }
        }
        return Element.NONE;
    }

    public boolean hasElement(Item item) {

        return getElementFromItem(item) != Element.NONE;
    }

    public boolean hasElementRecipe(ItemStack stack, World world) {
        return world != null && getElementRecipe(stack,world) != null;
    }

    public ElementRecipe getElementRecipe(ItemStack stack, World world) {
        Inventory temp = new Inventory(stack);
        return world.getRecipeManager().getRecipe(RankineRecipeTypes.ELEMENT, temp, world).orElse(null);
    }

    public AlloyingRecipe getAlloyRecipe(ItemStack stack, World world) {
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            IRecipe<?> recipe = world.getRecipeManager().getRecipe(rs).orElse(null);
            if (recipe instanceof AlloyingRecipe) {
                return (AlloyingRecipe) recipe;
            }
        }
        return null;
    }
    public List<String> getElementSymbols() {
        return symbols;
    }

    public Element getElementbyNumber(int x)
    {
        for (Element i: Element.values())
        {
            if (i.getAtomicNumber() == x)
            {
                return i;
            }
        }
        return Element.NONE;
    }

    public Item getElementIngot(Element element) {
        String namespace = "rankine";
        if (element == Element.IRON || element == Element.GOLD || element == Element.NETHERITE) {
            namespace = "minecraft";
        }
        Item elementIngot = ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace, element.name().toLowerCase(Locale.ROOT) + "_ingot"));
        if (elementIngot != null) {
            return elementIngot;
        }

        return Items.AIR;
    }

    public Element getAdjacentElement(Element element, int direction) {
        if (element == Element.NONE) {
            return Element.NONE;
        }
        int atom = element.getAtomicNumber();
        switch (direction) {
            case 3: // UP
                int up = 0;
                if (atom == 3) {
                    up = 1;
                } else if (atom >= 10 && atom <= 20) {
                    up = 8;
                } else if (atom >= 31 && atom <= 57) {
                    up = 18;
                } else if (atom >= 72 && atom <= 121) {
                    up = 32;
                } else if (atom >= 140 && atom <= 218) {
                    up = 50;
                }
                if (Config.MACHINES.RANKINE_BOX_UP.get() && up != 0) {
                    return getElementbyNumber(atom - up);
                } else {
                    return Element.NONE;
                }
            case 4: // RIGHT
                if (Config.MACHINES.RANKINE_BOX_RIGHT.get()) {
                    return getElementbyNumber(atom + 1);
                } else {
                    return Element.NONE;
                }
            case 5: // DOWN
                int down = 0;
                if (atom == 1) {
                    down = 1;
                } else if (atom >= 3 && atom <= 12) {
                    down = 8;
                } else if (atom >= 19 && atom <= 39) {
                    down = 18;
                } else if (atom >= 40 && atom <= 89) {
                    down = 32;
                } else if (atom >= 90 && atom <= 168) {
                    down = 50;
                }
                if (Config.MACHINES.RANKINE_BOX_DOWN.get() && down != 0) {
                    return getElementbyNumber(atom + down);
                } else {
                    return Element.NONE;
                }
            case 6: // LEFT
                if (Config.MACHINES.RANKINE_BOX_LEFT.get()) {
                    return getElementbyNumber(atom - 1);
                } else {
                    return Element.NONE;
                }
            default:
                return Element.NONE;
        }
    }

    public String getElementNameBySymbol(String symbol)
    {
        int index = symbols.indexOf(symbol);
        return names.get(index);
    }

    public Element getElementBySymbol(String symbol)
    {
        for (Element i: Element.values())
        {
            if (i.symbol.equals(symbol))
            {
                return i;
            }
        }
        return Element.NONE;
    }

    public int calcDurability(List<Element> elements, List<Integer> percents)
    {
        int index = 0;
        int durability = 0;
        for (Element e: elements)
        {
            durability += e.element.getDurabilityFromPercent(percents.get(index));
            index++;
        }

        return durability;
    }

    public float calcMiningSpeed(List<Element> elements, List<Integer> percents)
    {
        int index = 0;
        float miningSpeed = 0f;
        for (Element e: elements)
        {
            miningSpeed += e.element.getMiningSpeedFromPercent(percents.get(index));
            index++;
        }

        return miningSpeed;
    }

    public int calcMiningLevel(List<Element> elements, List<Integer> percents)
    {
        int index = 0;
        List<Integer> levels = new ArrayList<>();
        for (Element e: elements)
        {
            levels.add(e.element.getMiningLevelFromPercent(percents.get(index)));
            index++;
        }

        return Collections.max(levels);
    }

    public float calcArrowDamage(List<Element> elements, List<Integer> percents)
    {
        int hl = calcMiningLevel(elements,percents);
        float dmg = calcDamage(elements,percents);

        return 2f + 0.5f*hl + 0.5f*dmg;
    }

    public int calcDamageReduceAmount(List<Element> elements, List<Integer> percents, EquipmentSlotType slotType)
    {
        int base = slotType == EquipmentSlotType.CHEST ? 3 : slotType == EquipmentSlotType.LEGS ? 2 : 1;
        int index = 0;
        List<Integer> levels = new ArrayList<>();
        for (Element e: elements)
        {
            levels.add(e.element.getMiningLevelFromPercent(percents.get(index)));
            index++;
        }
        int dr = Collections.max(levels);
        if (slotType == EquipmentSlotType.CHEST || slotType == EquipmentSlotType.LEGS) {
            return Math.min(base + dr,10);
        } else if (slotType == EquipmentSlotType.FEET) {
            if (dr >= 5) {
                return base + 2;
            } else if (dr >= 3) {
                return base + 1;
            } else {
                return base;
            }
        } else {
            if (dr >= 5) {
                return base + 3;
            } else if (dr >= 3) {
                return base + 2;
            } else if (dr >= 1) {
                return base + 1;
            } else {
                return base;
            }
        }
    }

    public float calcDamage(List<Element> elements, List<Integer> percents) // takes max; 0 -> 4 (strongest)
    {
        int index = 0;
        List<Float> levels = new ArrayList<>();
        for (Element e: elements)
        {
            levels.add(e.element.getDamageFromPercent(percents.get(index)));
            index++;
        }

        return Collections.max(levels);
    }

    public float calcAttackSpeed(List<Element> elements, List<Integer> percents) // takes max; 0 -> 3 adds to preexisting attack speed (-3 -> 0 maximum attack speed);
    {
        int index = 0;
        List<Float> levels = new ArrayList<>();
        for (Element e: elements)
        {
            levels.add(e.element.getAttackSpeedFromPercent(percents.get(index)));
            index++;
        }

        return Collections.max(levels);
    }

    public int calcEnchantability(List<Element> elements, List<Integer> percents)
    {
        int index = 0;
        int enchant = 0;
        for (Element e: elements)
        {
            enchant += e.element.getEnchantabilityFromPercent(percents.get(index));
            index++;
        }

        return enchant;
    }

    public float calcCorrResist(List<Element> elements, List<Integer> percents)
    {
        int index = 0;
        float corrResist = 0f;
        for (Element e: elements)
        {
            corrResist += e.element.getCorrResistFromPercent(percents.get(index));
            index++;
        }

        return corrResist;
    }

    public float calcHeatResist(List<Element> elements, List<Integer> percents)
    {
        int index = 0;
        float heatResist = 0f;
        for (Element e: elements)
        {
            heatResist += e.element.getHeatResistFromPercent(percents.get(index));
            index++;
        }

        return heatResist;
    }

    public float calcToughness(List<Element> elements, List<Integer> percents)
    {
        int index = 0;
        float tough = 0f;
        for (Element e: elements)
        {
            tough += e.element.getToughnessFromPercent(percents.get(index));
            index++;
        }

        return tough;
    }


    public int calcArmorDurability(List<Element> elements, List<Integer> percents, int extra, EquipmentSlotType slotType)
    {
        final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
        int durability = calcDurability(elements,percents) + extra;
        if (durability <= 100) {
            return Math.round(MAX_DAMAGE_ARRAY[slotType.getIndex()] * durability/10f);
        } else {
            return Math.round(MAX_DAMAGE_ARRAY[slotType.getIndex()] * (10 + (durability-100)/50f));
        }
    }

    public int calcArmorToughness(List<Element> elements, List<Integer> percents)
    {
        int index = 0;
        float tough = 0f;
        for (Element e: elements)
        {
            tough += e.element.getToughnessFromPercent(percents.get(index));
            index++;
        }
        if (tough >= 0.4) {
            return 4;
        } else if (tough >= 0.3) {
            return 3;
        } else if (tough >= 0.2){
            return 2;
        } else if (tough >= 0.1){
           return 1;
        } else {
            return 0;
        }
    }

    public List<Enchantment> getEnchantments(List<Element> elements, List<Integer> percents)
    {
        int index = 0;
        List<Enchantment> enchantments = new ArrayList<>();
        for (Element e: elements)
        {
            Enchantment en = e.element.getEnchantments(percents.get(index));
            if (en != null)
            {
                enchantments.add(en);
            }
            index++;
        }

        return enchantments;
    }

    public enum Element {
        NONE(-1,"X",new NoneElement()),
        HYDROGEN(1,"H",new HydrogenElement()),
        HELIUM(2,"He",new HeliumElement()),
        LITHIUM(3,"Li",new LithiumElement(),14087935),
        BERYLLIUM(4,"Be",new BerylliumElement(),6672537),
        BORON(5,"B",new BoronElement(),10194328),
        CARBON(6,"C",new CarbonElement(),6513507),
        NITROGEN(7,"N",new NitrogenElement()),
        OXYGEN(8,"O",new OxygenElement()),
        FLUORINE(9,"F",new FluorineElement()),
        NEON(10,"Ne",new NeonElement()),
        SODIUM(11,"Na",new SodiumElement()),
        MAGNESIUM(12,"Mg",new MagnesiumElement(),15724505),
        ALUMINUM(13, "Al", new AluminumElement(),15919335),
        SILICON(14, "Si", new SiliconElement(),8684688),
        PHOSPHORUS(15, "P", new PhosphorusElement(),14202036),
        SULFUR(16,"S",new SulfurElement(),14737504),
        CHLORINE(17,"Cl",new ChlorineElement()),
        ARGON(18,"Ar",new ArgonElement()),
        POTASSIUM(19,"K",new PotassiumElement()),
        CALCIUM(20, "Ca", new CalciumElement(),15595448),
        SCANDIUM(21,"Sc",new ScandiumElement()),
        TITANIUM(22, "Ti", new TitaniumElement(),13621208),
        VANADIUM(23, "V", new VanadiumElement(),13416389),
        CHROMIUM(24, "Cr", new ChromiumElement(),15462646),
        MANGANESE(25, "Mn", new ManganeseElement(),15450555),
        IRON(26, "Fe", new IronElement(),14211288),
        COBALT(27, "Co", new CobaltElement(),11653119),
        NICKEL(28,"Ni", new NickelElement(),13625311),
        COPPER(29, "Cu", new CopperElement(),15382670),
        ZINC(30, "Zn", new ZincElement(),14870226),
        GALLIUM(31,"Ga",new GalliumElement(),9207455),
        GERMANIUM(32,"Ge",new GermaniumElement()),
        ARSENIC(33,"As",new ArsenicElement()),
        SELENIUM(34,"Se",new SeleniumElement()),
        BROMINE(35,"Br",new BromineElement()),
        KRYPTON(36,"K",new KryptonElement()),
        RUBIDIUM(37,"Rb",new RubidiumElement()),
        STRONTIUM(38,"Sr",new StrontiumElement()),
        YTTRIUM(39,"Y",new YttriumElement()),
        ZIRCONIUM(40,"Zr",new ZirconiumElement(),9018824),
        NIOBIUM(41,"Nb",new NiobiumElement(),10200279),
        MOLYBDENUM(42,"Mo",new MolybdenumElement(),13615061),
        TECHNETIUM(43,"Tc",new TechnetiumElement()),
        RUTHENIUM(44,"Ru",new RutheniumElement()),
        RHODIUM(45,"Rh",new RhodiumElement()),
        PALLADIUM(46,"Pd",new PalladiumElement(),15699840),
        SILVER(47,"Ag",new SilverElement(),15724527),
        CADMIUM(48,"Cd",new CadmiumElement()),
        INDIUM(49,"In",new IndiumElement(),6315469),
        TIN(50, "Sn", new TinElement(),11975882),
        ANTIMONY(51, "Sb", new AntimonyElement(),13683045),
        TELLURIUM(52, "Te", new TelluriumElement()),
        IODINE(53, "I", new IodineElement()),
        XENON(54, "Xe", new XenonElement()),
        CESIUM(55, "Cs", new CesiumElement()),
        BARIUM(56, "Ba", new BariumElement()),
        LANTHANUM(57, "La", new LanthanumElement()),
        CERIUM(58, "Ce", new CeriumElement()),
        PRASEODYMIUM(59,"Pr",new PraseodymiumElement()),
        NEODYMIUM(60, "Nd", new NeodymiumElement()),
        PROMETHIUM(61, "Pm", new PromethiumElement()),
        SAMARIUM(62,"Sm",new SamariumElement()),
        EUROPIUM(63,"Eu",new EuropiumElement()),
        GADOLINIUM(64,"Gd",new GadoliniumElement()),
        TERBIUM(65,"Tb",new TerbiumElement()),
        DYSPROSIUM(66,"Dy",new DysprosiumElement()),
        HOLMIUM(67, "Ho", new HolmiumElement()),
        ERBIUM(68, "Er", new ErbiumElement()),
        THULIUM(69, "Tm", new ThuliumElement()),
        YTTERBIUM(70, "Yb", new YtterbiumElement()),
        LUTETIUM(71, "Lu", new LutetiumElement()),
        HAFNIUM(72,"Hf", new HafniumElement()),
        TANTALUM(73,"Ta", new TantalumElement()),
        TUNGSTEN(74,"W", new TungstenElement(),12693657),
        RHENIUM(75,"Re", new RheniumElement()),
        OSMIUM(76,"Os", new OsmiumElement()),
        IRIDIUM(77,"Ir", new IridiumElement()),
        PLATINUM(78,"Pt", new PlatinumElement(),14016232),
        GOLD(79, "Au", new GoldElement(),16643423),
        MERCURY(80, "Hg", new MercuryElement(),15721704),
        THALLIUM(81, "Tl", new ThalliumElement()),
        LEAD(82,"Pb", new LeadElement(),10131385),
        BISMUTH(83,"Bi", new BismuthElement(),14407127),
        POLONIUM(84,"Po", new PoloniumElement()),
        ASTATINE(85,"At", new AstatineElement()),
        RADON(86,"Rn", new RadonElement()),
        FRANCIUM(87,"Fr", new FranciumElement()),
        RADIUM(88,"Ra", new RadiumElement()),
        ACTINIUM(89,"Ac", new ActiniumElement()),
        THORIUM(90,"Th", new ThoriumElement()),
        PROTACTINIUM(91,"Pa", new ProtactiniumElement()),
        URANIUM(92,"U", new UraniumElement()),
        NEPTUNIUM(93,"Np", new NeptuniumElement()),
        PLUTONIUM(94,"Pu", new PlutoniumElement()),
        AMERICIUM(95,"Am", new AmericiumElement()),
        CURIUM(96,"Cm", new CuriumElement()),
        BERKELIUM(97,"Bk", new BerkeliumElement()),
        CALIFORNIUM(98,"Cf", new CaliforniumElement()),
        EINSTEINIUM(99,"Es", new EinsteiniumElement()),
        FERMIUM(100,"Fm", new FermiumElement()),
        MENDELEVIUM(101,"Md", new MendeleviumElement()),
        NOBELIUM(102,"No", new NobeliumElement()),
        LAWRENCIUM(103,"Lr", new LawrenciumElement()),
        RUTHERFORDIUM(104,"Rf", new RutherfordiumElement()),
        DUBNIUM(105,"Db", new DubniumElement()),
        SEABORGIUM(106,"Sg", new SeaborgiumElement()),
        BOHRIUM(107,"Bh", new BohriumElement()),
        HASSIUM(108,"Hs", new HassiumElement()),
        MEITNERIUM(109,"Mt", new MeitneriumElement()),
        DARMSTADTIUM(110,"Ds", new DarmstadtiumElement()),
        ROENTGENIUM(111,"Rg", new RoentgeniumElement()),
        COPERNICIUM(112,"Cn", new CoperniciumElement()),
        NIHONIUM(113,"Nh", new NihoniumElement()),
        FLEROVIUM(114,"Fl", new FleroviumElement()),
        MOSCOVIUM(115,"Mc", new MoscoviumElement()),
        LIVERMORIUM(116,"Lv", new LivermoriumElement()),
        TENNESSINE(117,"Ts", new TennessineElement()),
        OGANESSON(118,"Og", new OganessonElement()),
        MANA(122,"M", new ManaElement(),7845107),
        NETHERITE(161,"Nr", new NetheriteElement(),4997443),
        SLIME(162,"Sl", new SlimeElement(),7781997),
        PRISMARINE(163,"Mr", new PrismarineElement(),10801602),
        WITHER(164,"Wi", new WitherElement(),1513239),
        GHASTIUM(165,"Gh", new GhastiumElement(),13627889),
        PHLOGISTON(166,"Pg", new PhlogistonElement(),16761088),
        ENDITE(167,"Ed", new EnditeElement(),2458740),
        DRACOCAELI(168,"Dr", new DracocaeliElement(),11283579);

        public final int atomicNumber;
        public final String symbol;
        public final ElementInterface element;
        public final int color;

        Element(int atomicNumber, String symbol, ElementInterface element, int color)
        {
            this.atomicNumber = atomicNumber;
            this.symbol = symbol;
            this.element = element;
            this.color = color;
        }

        Element(int atomicNumber, String symbol, ElementInterface element)
        {
            this(atomicNumber,symbol,element,16777215);
        }

        public int getAtomicNumber() {
            return atomicNumber;
        }

        public String getSymbol() {
            return symbol;
        }

        public int getColor() {
            return color;
        }

        public static PeriodicTableUtils.Element valueOfCaseIgnored(String string) {
            return valueOf(string.toUpperCase());
        }
    }
}
