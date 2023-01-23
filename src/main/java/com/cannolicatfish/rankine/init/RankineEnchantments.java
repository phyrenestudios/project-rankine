package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.enchantment.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ProjectRankine.MODID);

    static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    static final EquipmentSlot[] HAND_SLOTS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

    public static final RegistryObject<Enchantment> PUNCTURE = ENCHANTMENTS.register("puncture", () -> new PunctureEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> SWING = ENCHANTMENTS.register("swing", () -> new SwingEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> DAZE = ENCHANTMENTS.register("daze", () -> new DazeEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ATOMIZE = ENCHANTMENTS.register("atomize", () -> new AtomizeEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> EXCAVATE = ENCHANTMENTS.register("excavate", () -> new ExcavateEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> IMPACT = ENCHANTMENTS.register("impact", () -> new ImpactEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> QUAKE = ENCHANTMENTS.register("quake", () -> new QuakeEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> FORAGING = ENCHANTMENTS.register("foraging", () -> new ForagingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> LIGHTNING_ASPECT = ENCHANTMENTS.register("lightning_aspect", () -> new LightningAspectEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ANTIQUATED = ENCHANTMENTS.register("antiquated", () -> new AntiquatedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> CLEANSE = ENCHANTMENTS.register("cleanse", () -> new CleanseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> DUNE_WALKER = ENCHANTMENTS.register("dune_walker", () -> new DuneWalkerEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment> SNOW_DRIFTER = ENCHANTMENTS.register("snow_drifter", () -> new SnowDrifterEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment> SPEED_SKATER = ENCHANTMENTS.register("speed_skater", () -> new SpeedSkaterEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment> SWIFT_SWIMMER = ENCHANTMENTS.register("swift_swimmer", () -> new SwiftSwimmersEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment> GAS_PROTECTION = ENCHANTMENTS.register("gas_protection", () -> new GasProtectionEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.HEAD));
    public static final RegistryObject<Enchantment> AQUA_LENSE = ENCHANTMENTS.register("aqua_lense", () -> new AquaLenseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.HEAD));

    public static final RegistryObject<Enchantment> ENDOBIOTIC = ENCHANTMENTS.register("endobiotic", () -> new EndobioticEnchantment(Enchantment.Rarity.VERY_RARE, HAND_SLOTS));
    public static final RegistryObject<Enchantment> ENDOTOXIN = ENCHANTMENTS.register("endotoxin", () -> new EndotoxinEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ENDPOINT = ENCHANTMENTS.register("endpoint", () -> new EndpointEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ENDOSPORE = ENCHANTMENTS.register("endospore", () -> new EndosporeEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ENDURE = ENCHANTMENTS.register("endure", () -> new EndureEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ENDGAME = ENCHANTMENTS.register("endgame", () -> new EndgameEnchantment(Enchantment.Rarity.VERY_RARE, HAND_SLOTS));
    public static final RegistryObject<Enchantment> ENDOLITHIC = ENCHANTMENTS.register("endolithic", () -> new EndolithicEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ENDLESS = ENCHANTMENTS.register("endless", () -> new EndlessEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ENDEAVOR = ENCHANTMENTS.register("endeavor", () -> new EndeavorEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ENDOTHERMIC = ENCHANTMENTS.register("endothermic", () -> new EndothermicEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ENDPLAY = ENCHANTMENTS.register("endplay", () -> new EndplayEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> GHASTLY_REGENERATION = ENCHANTMENTS.register("ghastly_regeneration", () -> new GhastRegenerationEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> WITHERING_CURSE = ENCHANTMENTS.register("withering_curse", () -> new WitheringCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> SHAPE_MEMORY = ENCHANTMENTS.register("shape_memory", () -> new ShapeMemoryEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> GUARD = ENCHANTMENTS.register("guard", () -> new GuardEnchantment(Enchantment.Rarity.UNCOMMON, ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> ACCURACY = ENCHANTMENTS.register("accuracy", () -> new AccuracyEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> PREPARATION = ENCHANTMENTS.register("preparation", () -> new PreparationEnchantment(Enchantment.Rarity.COMMON, HAND_SLOTS));
    public static final RegistryObject<Enchantment> POISON_ASPECT = ENCHANTMENTS.register("poison_aspect", () -> new PoisonAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> BACKSTAB = ENCHANTMENTS.register("backstab", () -> new BackstabEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> GRAFTING = ENCHANTMENTS.register("grafting", () -> new GraftingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> RETALIATE = ENCHANTMENTS.register("retaliate", () -> new RetaliateEnchantment(Enchantment.Rarity.VERY_RARE, HAND_SLOTS));
    public static final RegistryObject<Enchantment> RETREAT = ENCHANTMENTS.register("retreat", () -> new RetreatEnchantment(Enchantment.Rarity.VERY_RARE, HAND_SLOTS));

    public static final RegistryObject<Enchantment> LEVERAGE = ENCHANTMENTS.register("leverage", () -> new LeverageEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> PRYING = ENCHANTMENTS.register("prying", () -> new PryingEnchantment(Enchantment.Rarity.RARE, HAND_SLOTS));
    public static final RegistryObject<Enchantment> LIFT = ENCHANTMENTS.register("lift", () -> new LiftEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> RETRIEVAL = ENCHANTMENTS.register("retrieval", () -> new RetrievalEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

}
