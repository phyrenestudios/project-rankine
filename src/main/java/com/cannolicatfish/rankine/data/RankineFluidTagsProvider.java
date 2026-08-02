package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineFluids;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RankineFluidTagsProvider extends FluidTagsProvider {

    public RankineFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ProjectRankine.MODID, existingFileHelper);
    }

    public String getName() {
        return "Project Rankine - Fluid Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(RankineTags.Fluids.LATEX).add(RankineFluids.LATEX.get(), RankineFluids.FLOWING_LATEX.get());
        this.tag(RankineTags.Fluids.RESIN).add(RankineFluids.RESIN.get(), RankineFluids.FLOWING_RESIN.get());
        this.tag(RankineTags.Fluids.SAP).add(RankineFluids.SAP.get(), RankineFluids.FLOWING_SAP.get());
        this.tag(RankineTags.Fluids.MAPLE_SAP).add(RankineFluids.MAPLE_SAP.get(), RankineFluids.FLOWING_MAPLE_SAP.get());
        this.tag(RankineTags.Fluids.JUGLONE).add(RankineFluids.JUGLONE.get(), RankineFluids.FLOWING_JUGLONE.get());
        this.tag(RankineTags.Fluids.SULFURIC_ACID).add(RankineFluids.SULFURIC_ACID.get(), RankineFluids.FLOWING_SULFURIC_ACID.get());
    }
}
