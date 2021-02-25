package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.elements.*;
import net.minecraft.enchantment.Enchantment;

import java.util.*;

public final class PeriodicTableUtils {

    public final List<String> names = Arrays.asList("None","Hydrogen","Helium","Lithium","Beryllium","Boron","Carbon","Nitrogen","Oxygen","Flourine","Neon","Sodium","Magnesium","Aluminum","Silicon","Phosphorus","Sulfur","Chlorine",
            "Argon", "Potassium","Calcium","Scandium", "Titanium","Vanadium","Chromium","Manganese","Iron","Cobalt","Nickel","Copper","Zinc","Gallium","Germanium","Arsenic","Selenium","Bromine","Krypton","Rubidium","Strontium",
            "Yttrium", "Zirconium","Niobium","Molybdenum", "Technetium","Ruthenium","Rhodium","Palladium","Silver","Cadmium","Indium","Tin","Antimony","Tellurium","Iodine","Xenon","Cesium","Barium","Lanthanum","Cerium",
            "Praseodymium", "Neodymium","Promethium","Samarium", "Europium","Gadolinium","Terbium","Dysprosium","Holmium","Erbium","Thulium","Ytterbium","Lutetium","Hafnium","Tantalum","Tungsten","Rhenium","Osmium","Iridium",
            "Platinum","Gold", "Mercury","Thallium","Lead","Bismuth", "Polonium","Astatine","Radon","Francium","Radium","Actinium","Thorium","Proactinium","Uranium","Neptunium","Plutonium ","Americium","Curium","Berkelium",
            "Californium","Einsteinium", "Fermium","Mendelevium","Nobelium", "Lawrencium","Rutherfordium","Dubnium","Seaborgium","Bohrium","Hassium","Meiterium","Darmstadtium","Roentgenium","Copernicium","Nihonium","Flerovium",
            "Moscovium","Livermorium", "Tennessine","Oganesson","Netherite"); //Index 0 for all lists are "None" and should not be used or appear

    public final List<String> symbols = Arrays.asList("","H","He","Li","Be","B","C","N","O","F","Ne","Na","Mg","Al","Si","P","S","Cl","Ar","K","Ca","Sc","Ti","V","Cr","Mn","Fe","Co","Ni","Cu","Zn","Ga","Ge","As","Se","Br","Kr","Rb",
            "Sr","Y","Zr","Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn","Sb","Te","I","Xe","Cs","Ba","La","Ce","Pr","Nd","pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb","Lu","Hf","Ta","W","Re","Os ","Ir","Pt","Au","Hg","Tl",
            "Pb","Bi","Po","At","Rn","Fr","Ra","Ac","Th","Pa","U","Np","Pu","Am","Cm","Bk","Cf","Es","Fm","md","No","Lr","Rf","Db","Sg","Bh","Hs","Mt","Ds","Rg","Cn","Uut","Ft","Uup","Lv","Uus","Uuo","Nr");

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

    public List<String> getAmalgamNames() {
        List<String> elements = new ArrayList<>();
        for (Element e: Element.values())
        {
            if (e != Element.MERCURY && e != Element.GOLD && !amalgamNonmetals.contains(e))
            {
                if (Config.ALLOYS.AMALGAM_EXTRAS.get() || (!amalgamExtras.contains(e)))
                {
                    elements.add(e.toString().toLowerCase());
                }
            }
        }
        //System.out.println(elements);
        return elements;
    }

    public List<String> getElementSymbols() {
        return symbols;
    }

    public String getElementbyNumber(int x, Boolean symbol)
    {
        if (symbol)
        {
            return symbols.get(x);
        }
        else
        {
            return names.get(x);
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
        return null;
    }

    public String getElementByMaterial(String material)
    {
        if (material.equals("none") || material.equals("nope"))
        {
            return "";
        }
        if (material.equals("pure_carbon"))
        {
            return "C";
        }
        if (material.equals("unref_iron") || material.equals("ref_iron"))
        {
            return "Fe";
        }
        int index = 0;
        for (String e:names) // change to use implemented elements?
        {
            if (material.equalsIgnoreCase(e))
            {
                return symbols.get(index);
            }
            index++;
        }
        return "";
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

    public int calcMiningLevel(List<Element> elements, List<Integer> percents) // takes max; 0 -> 4 (best)
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
        LITHIUM(3,"Li",new LithiumElement(),14087935),
        BERYLLIUM(4,"Be",new BerylliumElement(),6672537),
        BORON(5,"B",new BoronElement(),10194328),
        CARBON(6,"C",new CarbonElement(),6513507),
        MAGNESIUM(12,"Mg",new MagnesiumElement(),15724505),
        ALUMINUM(13, "Al", new AluminumElement(),15919335),
        SILICON(14, "Si", new SiliconElement(),8684688),
        PHOSPHORUS(15, "P", new PhosphorusElement(),14202036),
        SULFUR(16,"S",new SulfurElement(),14737504),
        CALCIUM(20, "Ca", new CalciumElement(),15595448),
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
        STRONTIUM(38,"Sr",new StrontiumElement()),
        YTTRIUM(39,"Y",new YttriumElement()),
        ZIRCONIUM(40,"Zr",new ZirconiumElement(),9018824),
        NIOBIUM(41,"Nb",new NiobiumElement(),10200279),
        MOLYBDENUM(42,"Mo",new MolybdenumElement(),13615061),
        RUTHENIUM(44,"Ru",new RutheniumElement()),
        RHODIUM(45,"Rh",new RhodiumElement()),
        PALLADIUM(46,"Pd",new PalladiumElement(),15699840),
        SILVER(47,"Ag",new SilverElement(),15724527),
        CADMIUM(48,"Cd",new CadmiumElement()),
        INDIUM(49,"In",new IndiumElement(),6315469),
        TIN(50, "Sn", new TinElement(),11975882),
        ANTIMONY(51, "Sb", new AntimonyElement(),13683045),
        LANTHANUM(57, "La", new LanthanumElement()),
        CERIUM(58, "Ce", new CeriumElement()),
        PRASEODYMIUM(59,"Pr",new PraseodymiumElement()),
        NEODYMIUM(60, "Nd", new NeodymiumElement()),
        SAMARIUM(62,"Sm",new SamariumElement()),
        EUROPIUM(63,"Eu",new EuropiumElement()),
        GADOLINIUM(64,"Gd",new GadoliniumElement()),
        TERBIUM(65,"Tb",new TerbiumElement()),
        DYSPROSIUM(66,"Dy",new DysprosiumElement()),
        HAFNIUM(72,"Hf", new HafniumElement()),
        TANTALUM(73,"Ta", new TantalumElement()),
        TUNGSTEN(74,"W", new TungstenElement(),12693657),
        RHENIUM(75,"Re", new RheniumElement()),
        IRIDIUM(77,"Ir", new IridiumElement()),
        PLATINUM(78,"Pt", new PlatinumElement(),14016232),
        GOLD(79, "Au", new GoldElement(),16643423),
        MERCURY(80, "Hg", new MercuryElement(),15721704),
        LEAD(82,"Pb", new LeadElement(),10131385),
        BISMUTH(83,"Bi", new BismuthElement(),14407127),
        THORIUM(90,"Th", new ThoriumElement()),
        NETHERITE(156,"Nr", new NetheriteElement(),4997443);

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
