package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class RankineFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ProjectRankine.MODID);

    public static final RegistryObject<FluidType> LIQUID_MERCURY_FLUID_TYPE = FLUID_TYPES.register("liquid_mercury",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)) {
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/liquid_mercury_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/liquid_mercury_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/liquid_mercury_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/liquid_mercury_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> SAP_FLUID_TYPE = FLUID_TYPES.register("sap",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/sap_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/sap_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/sap_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/sap_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> MAPLE_SAP_FLUID_TYPE = FLUID_TYPES.register("maple_sap",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/maple_sap_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/maple_sap_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/maple_sap_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/maple_sap_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> LATEX_FLUID_TYPE = FLUID_TYPES.register("latex",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/latex_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/latex_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/latex_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/latex_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> RESIN_FLUID_TYPE = FLUID_TYPES.register("resin",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/resin_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/resin_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/resin_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/resin_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> JUGLONE_FLUID_TYPE = FLUID_TYPES.register("juglone",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/juglone_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/juglone_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/juglone_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/juglone_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> AQUA_REGIA_FLUID_TYPE = FLUID_TYPES.register("aqua_regia",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/aqua_regia_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/aqua_regia_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/aqua_regia_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/aqua_regia_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> CARBON_DISULFIDE_FLUID_TYPE = FLUID_TYPES.register("carbon_disulfide",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/carbon_disulfide_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/carbon_disulfide_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/carbon_disulfide_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/carbon_disulfide_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> HEXAFLUOROSILICIC_ACID_FLUID_TYPE = FLUID_TYPES.register("hexafluorosilicic_acid",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/hexafluorosilicic_acid_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/hexafluorosilicic_acid_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/hexafluorosilicic_acid_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/hexafluorosilicic_acid_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> HYDROBROMIC_ACID_FLUID_TYPE = FLUID_TYPES.register("hydrobromic_acid",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/hydrobromic_acid_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/hydrobromic_acid_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/hydrobromic_acid_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/hydrobromic_acid_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> GRAY_MUD_FLUID_TYPE = FLUID_TYPES.register("gray_mud",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/gray_mud_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/gray_mud_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/gray_mud_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/gray_mud_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> RED_MUD_FLUID_TYPE = FLUID_TYPES.register("red_mud",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/red_mud_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/red_mud_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/red_mud_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/red_mud_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> SULFURIC_ACID_FLUID_TYPE = FLUID_TYPES.register("sulfuric_acid",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/sulfuric_acid_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/sulfuric_acid_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/sulfuric_acid_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/sulfuric_acid_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> BLACK_LIQUOR_FLUID_TYPE = FLUID_TYPES.register("black_liquor",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/black_liquor_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/black_liquor_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/black_liquor_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/black_liquor_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> GREEN_LIQUOR_FLUID_TYPE = FLUID_TYPES.register("green_liquor",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/green_liquor_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/green_liquor_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/green_liquor_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/green_liquor_overlay");
                        }
                    });
                }
            });

    public static final RegistryObject<FluidType> WHITE_LIQUOR_FLUID_TYPE = FLUID_TYPES.register("white_liquor",
            () -> new FluidType(FluidType.Properties.create().
                    density(14).viscosity(5).temperature(1)
                    .canHydrate(false).canExtinguish(false).canConvertToSource(false)
                    .canSwim(false).canDrown(true).canPushEntity(true).supportsBoating(false)){
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/white_liquor_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/white_liquor_flow");
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/white_liquor_overlay");
                        }

                        @Override
                        public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                            return new ResourceLocation(ProjectRankine.MODID,"blocks/white_liquor_overlay");
                        }
                    });
                }
            });
}
