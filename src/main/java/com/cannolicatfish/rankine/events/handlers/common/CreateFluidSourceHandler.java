package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;

public class CreateFluidSourceHandler {
    public static void noWater( BlockEvent.CreateFluidSourceEvent event) {
        /*
        List<ResourceLocation> waterBiomes = WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), true);

        for (String b : Config.GENERAL.INFI_WATER_BIOMES.get()) {
            List<String> biomeName = Arrays.asList(b.split(":"));
            if (biomeName.size() > 1) {
                waterBiomes.add(ResourceLocation.tryCreate(b));
            } else {
                waterBiomes.addAll(WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.byName(b)), true));
            }
        }
        if (Config.GENERAL.DISABLE_WATER.get() && !waterBiomes.contains(event.getWorld().getBiome(event.getPos()).toString())) {
            event.setResult(Event.Result.DENY);
        }

         */
        if (Config.GENERAL.DISABLE_WATER.get() && event.getPos().getY() > WorldgenUtils.waterTableHeight((World) event.getWorld(), event.getPos())) {
            event.setResult(Event.Result.DENY);
        }
    }

}
