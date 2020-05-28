package com.cannolicatfish.rankine.util;

import com.sun.jna.platform.mac.Carbon;

import java.util.Arrays;
import java.util.List;

public final class ElementUtils {

    public final List<String> names = Arrays.asList("None","Hydrogen","Helium","Lithium","Beryllium","Boron","Carbon","Nitrogen ","Oxygen","Flourine","Neon","Sodium","Magnesium","Aluminum","Silicon","Phosphorus","Sulfur","Chlorine",
            "Argon", "Potassium","Calcium","Scandium", "Titanium","Vanadium","Chromium","Manganese","Iron","Cobalt","Nickel","Copper","Zinc","Gallium","Germanium","Arsenic","Selenium","Bromine","Krypton","Rubidium","Strontium",
            "Yttrium", "Zirconium","Niobium","Molybdenum", "Technetium","Ruthenium","Rhodium","Palladium","Silver","Cadmium","Indium","Tin","Antimony","Tellurium","Iodine","Xenon","Cesium","Barium","Lanthanum","Cerium",
            "Praseodymium", "Neodymium","Promethium","Samarium", "Europium","Gadolinium","Tebrium","Dysprosium","Holmium","Eribium","Thulium","Ytterbium","Lutetium","Hafnium","Tantalum","Tungsten","Rhenium","Osmium","Iridium",
            "Platinum","Gold", "Mercury","Thalium","Lead","Bismuth", "Polonium","Astatine","Radon","Francium","Radium","Actinium","Thorium","Proactinium","Uranium","Neptunium","Plutonium ","Americium","Curium","Berkelium",
            "Californium","Einsteinium", "Fermium","Mendelevium","Nobelium", "Lawrencium","Rutherfordium","Dubnium","Seaborgium","Bohrium","Hassium","Meiterium","Darmstadtium","Roentgenium","Copernicium","Nihonium","Flerovium",
            "Moscovium","Livermorium", "Tennessine","Oganesson"); //Index 0 for all lists are "None" and should not be used or appear

    public final List<String> symbols = Arrays.asList("","H","He","Li","Be","B","C","N","O","F","Ne","Na","Mg","Al","Si","P","S","Cl","Ar","K","Ca","Sc","Ti","V","Cr","Mn","Fe","Co","Ni","Cu","Zn","Ga","Ge","As","Se","Br","Kr","Rb",
            "Sr","Y","Zr","Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn","Sb","Te","I","Xe","Cs","Ba","La","Ce","Pr","Nd","pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb","Lu","Hf","Ta","W","Re","Os ","Ir","Pt","Au","Hg","Tl",
            "Pb","Bi","Po","At","Rn","Fr","Ra","Ac","Th","Pa","U","Np","Pu","Am","Cm","Bk","Cf","Es","Fm","md","No","Lr","Rf","Db","Sg","Bh","Hs","Mt","Ds","Rg","Cn","Uut","Ft","Uup","Lv","Uus","Uuo");

    public List<String> getElementNames() {
        return names;
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

    public String getElementNamebySymbol(String symbol)
    {
        int index = symbols.indexOf(symbol);
        return names.get(index);
    }

    public String getElementbyMaterial(String material)
    {
        if (material.equals("none") || material.equals("nope"))
        {
            return "";
        }
        if (material.equals("pure_carbon"))
        {
            return "C";
        }
        int index = 0;
        for (String e:names)
        {
            if (material.equalsIgnoreCase(e))
            {
                return symbols.get(index);
            }
            index++;
        }
        return "";
    }
}
