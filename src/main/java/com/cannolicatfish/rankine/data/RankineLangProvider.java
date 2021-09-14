package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineLangProvider extends LanguageProvider {
    private final String locale;
    public RankineLangProvider(DataGenerator gen, String locale) {
        super(gen, ProjectRankine.MODID, locale);
        this.locale = locale;
    }

    @Override
    public String getName() {
        return "Rankine Lang: " + locale;
    }

    @Override
    protected void addTranslations() {
        for (Block blk : Stream.of(RankineLists.STONE, RankineLists.PLANKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String LangName = "";
            for (String s : blk.getRegistryName().getPath().split("_")) {
                if (LangName.equals("")) {
                    LangName = s.toUpperCase();
                } else {
                    LangName = LangName + " " + s.toUpperCase();
                }
            }
            add(blk, LangName);
        }
    }




}
