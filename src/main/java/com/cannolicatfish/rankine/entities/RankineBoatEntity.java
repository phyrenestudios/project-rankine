package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class RankineBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(RankineBoatEntity.class, EntityDataSerializers.INT);
    public RankineBoatEntity(EntityType<? extends Boat> entityType, Level worldIn)
    {
        super(entityType, worldIn);
    }

    public RankineBoatEntity(Level worldIn, double x, double y, double z) {
        this(RankineEntityTypes.RANKINE_BOAT, worldIn);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(BOAT_TYPE, RankineBoatEntity.Type.OAK.ordinal());
    }

    public Packet<?> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putString("Type", this.getRankineBoatType().getName());
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("Type", 8)) {
            this.setRankineBoatType(RankineBoatEntity.Type.getTypeFromString(compound.getString("Type")));
        }

    }

    public Item getDropItem() {
        switch(this.getRankineBoatType()) {
            case OAK:
            default:
                return Items.OAK_BOAT;
            case SPRUCE:
                return Items.SPRUCE_BOAT;
            case BIRCH:
                return Items.BIRCH_BOAT;
            case JUNGLE:
                return Items.JUNGLE_BOAT;
            case ACACIA:
                return Items.ACACIA_BOAT;
            case DARK_OAK:
                return Items.DARK_OAK_BOAT;
            case CEDAR:
                return RankineItems.CEDAR_BOAT.get();
            case COCONUT_PALM:
                return RankineItems.COCONUT_PALM_BOAT.get();
            case PINYON_PINE:
                return RankineItems.PINYON_PINE_BOAT.get();
            case JUNIPER:
                return RankineItems.JUNIPER_BOAT.get();
            case BALSAM_FIR:
                return RankineItems.BALSAM_FIR_BOAT.get();
            case MAGNOLIA:
                return RankineItems.MAGNOLIA_BOAT.get();
            case HEMLOCK:
                return RankineItems.EASTERN_HEMLOCK_BOAT.get();
            case MAPLE:
                return RankineItems.MAPLE_BOAT.get();
            case BLACK_BIRCH:
                return RankineItems.BLACK_BIRCH_BOAT.get();
            case YELLOW_BIRCH:
                return RankineItems.YELLOW_BIRCH_BOAT.get();
            case BLACK_WALNUT:
                return RankineItems.BLACK_WALNUT_BOAT.get();
            case SHARINGA:
                return RankineItems.SHARINGA_BOAT.get();
            case CORK_OAK:
                return RankineItems.CORK_OAK_BOAT.get();
            case CINNAMON:
                return RankineItems.CINNAMON_BOAT.get();
            case BAMBOO:
                return RankineItems.BAMBOO_BOAT.get();
            case BAMBOO_CULMS:
                return RankineItems.BAMBOO_CULMS_BOAT.get();
            case PETRIFIED_CHORUS:
                return RankineItems.PETRIFIED_CHORUS_BOAT.get();
            case ERYTHRINA:
                return RankineItems.ERYTHRINA_BOAT.get();
            case CHARRED:
                return RankineItems.CHARRED_BOAT.get();
            case RED_BIRCH:
                return RankineItems.RED_BIRCH_BOAT.get();
            case WEEPING_WILLOW:
                return RankineItems.WEEPING_WILLOW_BOAT.get();
            case HONEY_LOCUST:
                return RankineItems.HONEY_LOCUST_BOAT.get();
        }
    }


    public RankineBoatEntity.Type getRankineBoatType() {
        return RankineBoatEntity.Type.byId(this.entityData.get(BOAT_TYPE));
    }

    public void setRankineBoatType(RankineBoatEntity.Type boatType) {
        this.entityData.set(BOAT_TYPE, boatType.ordinal());
    }

    public enum Type {
        OAK(Blocks.OAK_PLANKS, "oak"),
        SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"),
        BIRCH(Blocks.BIRCH_PLANKS, "birch"),
        JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
        ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
        DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak"),
        CEDAR(RankineBlocks.CEDAR_PLANKS.get(), "cedar"),
        COCONUT_PALM(RankineBlocks.COCONUT_PALM_PLANKS.get(), "coconut_palm"),
        PINYON_PINE(RankineBlocks.PINYON_PINE_PLANKS.get(), "pinyon_pine"),
        JUNIPER(RankineBlocks.JUNIPER_PLANKS.get(), "juniper"),
        BALSAM_FIR(RankineBlocks.BALSAM_FIR_PLANKS.get(), "balsam_fir"),
        MAGNOLIA(RankineBlocks.MAGNOLIA_PLANKS.get(), "magnolia"),
        HEMLOCK(RankineBlocks.EASTERN_HEMLOCK_PLANKS.get(), "hemlock"),
        MAPLE(RankineBlocks.MAPLE_PLANKS.get(), "maple"),
        BLACK_BIRCH(RankineBlocks.BLACK_BIRCH_PLANKS.get(), "black_birch"),
        YELLOW_BIRCH(RankineBlocks.YELLOW_BIRCH_PLANKS.get(), "yellow_birch"),
        RED_BIRCH(RankineBlocks.RED_BIRCH_PLANKS.get(), "red_birch"),
        BLACK_WALNUT(RankineBlocks.BLACK_WALNUT_PLANKS.get(), "black_walnut"),
        SHARINGA(RankineBlocks.SHARINGA_PLANKS.get(), "sharinga"),
        HONEY_LOCUST(RankineBlocks.HONEY_LOCUST_PLANKS.get(), "honey_locust"),
        WEEPING_WILLOW(RankineBlocks.WEEPING_WILLOW_PLANKS.get(), "weeping_willow"),
        CORK_OAK(RankineBlocks.CORK_OAK_PLANKS.get(), "cork_oak"),
        CINNAMON(RankineBlocks.CINNAMON_PLANKS.get(), "cinnamon"),
        BAMBOO(RankineBlocks.BAMBOO_PLANKS.get(), "bamboo"),
        BAMBOO_CULMS(RankineBlocks.BAMBOO_CULMS.get(), "bamboo_culms"),
        PETRIFIED_CHORUS(RankineBlocks.PETRIFIED_CHORUS_PLANKS.get(), "petrified_chorus"),
        ERYTHRINA(RankineBlocks.ERYTHRINA_PLANKS.get(), "erythrina"),
        CHARRED(RankineBlocks.CHARRED_PLANKS.get(), "charred");


        private final String name;
        private final Block block;

        private Type(Block p_i48146_3_, String p_i48146_4_) {
            this.name = p_i48146_4_;
            this.block = p_i48146_3_;
        }

        public String getName() {
            return this.name;
        }

        public Block asPlank() {
            return this.block;
        }

        public String toString() {
            return this.name;
        }

        /**
         * Get a boat type by it's enum ordinal
         */
        public static RankineBoatEntity.Type byId(int id) {
            RankineBoatEntity.Type[] aboatentity$type = values();
            if (id < 0 || id >= aboatentity$type.length) {
                id = 0;
            }

            return aboatentity$type[id];
        }

        public static RankineBoatEntity.Type getTypeFromString(String nameIn) {
            RankineBoatEntity.Type[] aboatentity$type = values();

            for(int i = 0; i < aboatentity$type.length; ++i) {
                if (aboatentity$type[i].getName().equals(nameIn)) {
                    return aboatentity$type[i];
                }
            }

            return aboatentity$type[0];
        }
    }
}
