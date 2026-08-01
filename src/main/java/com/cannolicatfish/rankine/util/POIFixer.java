package com.cannolicatfish.rankine.util;

/**
 * PORT-NOTE (1.21.1): this class has been fully commented out since 1.18.2 — it used to reflectively
 * call the private {@code PoiType#registerBlockStates}. That workaround is obsolete (POI types are a
 * datapack registry now and their block states are derived on registration), and the porting guide
 * forbids reflection hacks, so the dead body and its imports are removed rather than revived.
 * Kept as an empty class so nothing that still references the type breaks.
 */
public class POIFixer {
}
