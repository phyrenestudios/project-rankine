package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class RankineBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(RankineBoatEntity.class, EntityDataSerializers.INT);
    public RankineBoatEntity(EntityType<? extends Boat> entityType, Level worldIn)
    {
        super(entityType, worldIn);
    }

    public RankineBoatEntity(Level worldIn, double x, double y, double z) {
        this(RankineEntityTypes.RANKINE_BOAT.get(), worldIn);
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

    public Packet<ClientGamePacketListener> getAddEntityPacket()
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
                return RankineBlocks.CEDAR.getBoat();
            case COCONUT_PALM:
                return RankineBlocks.COCONUT_PALM.getBoat();
            case PINYON_PINE:
                return RankineBlocks.PINYON_PINE.getBoat();
            case JUNIPER:
                return RankineBlocks.JUNIPER.getBoat();
            case BALSAM_FIR:
                return RankineBlocks.BALSAM_FIR.getBoat();
            case MAGNOLIA:
                return RankineBlocks.MAGNOLIA.getBoat();
            case EASTERN_HEMLOCK:
                return RankineBlocks.EASTERN_HEMLOCK.getBoat();
            case WESTERN_HEMLOCK:
                return RankineBlocks.WESTERN_HEMLOCK.getBoat();
            case MAPLE:
                return RankineBlocks.MAPLE.getBoat();
            case BLACK_BIRCH:
                return RankineBlocks.BLACK_BIRCH.getBoat();
            case YELLOW_BIRCH:
                return RankineBlocks.YELLOW_BIRCH.getBoat();
            case BLACK_WALNUT:
                return RankineBlocks.BLACK_WALNUT.getBoat();
            case SHARINGA:
                return RankineBlocks.SHARINGA.getBoat();
            case CORK_OAK:
                return RankineBlocks.CORK_OAK.getBoat();
            case CINNAMON:
                return RankineBlocks.CINNAMON.getBoat();
            case BAMBOO:
                return RankineBlocks.BAMBOO.getBoat();
            case BAMBOO_CULMS:
                return RankineBlocks.BAMBOO_CULMS.getBoat();
            case PETRIFIED_CHORUS:
                return RankineBlocks.PETRIFIED_CHORUS.getBoat();
            case ERYTHRINA:
                return RankineBlocks.ERYTHRINA.getBoat();
            case CHARRED:
                return RankineBlocks.CHARRED.getBoat();
            case RED_BIRCH:
                return RankineBlocks.RED_BIRCH.getBoat();
            case WEEPING_WILLOW:
                return RankineBlocks.WEEPING_WILLOW.getBoat();
            case HONEY_LOCUST:
                return RankineBlocks.HONEY_LOCUST.getBoat();
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
        CEDAR(RankineBlocks.CEDAR.getPlanks(), "cedar"),
        BALSAM_FIR(RankineBlocks.BALSAM_FIR.getPlanks(), "balsam_fir"),
        EASTERN_HEMLOCK(RankineBlocks.EASTERN_HEMLOCK.getPlanks(), "eastern_hemlock"),
        WESTERN_HEMLOCK(RankineBlocks.WESTERN_HEMLOCK.getPlanks(), "western_hemlock"),
        PINYON_PINE(RankineBlocks.PINYON_PINE.getPlanks(), "pinyon_pine"),
        JUNIPER(RankineBlocks.JUNIPER.getPlanks(), "juniper"),
        BLACK_BIRCH(RankineBlocks.BLACK_BIRCH.getPlanks(), "black_birch"),
        YELLOW_BIRCH(RankineBlocks.YELLOW_BIRCH.getPlanks(), "yellow_birch"),
        RED_BIRCH(RankineBlocks.RED_BIRCH.getPlanks(), "red_birch"),
        MAGNOLIA(RankineBlocks.MAGNOLIA.getPlanks(), "magnolia"),
        MAPLE(RankineBlocks.MAPLE.getPlanks(), "maple"),
        BLACK_WALNUT(RankineBlocks.BLACK_WALNUT.getPlanks(), "black_walnut"),
        COCONUT_PALM(RankineBlocks.COCONUT_PALM.getPlanks(), "coconut_palm"),
        CORK_OAK(RankineBlocks.CORK_OAK.getPlanks(), "cork_oak"),
        SHARINGA(RankineBlocks.SHARINGA.getPlanks(), "sharinga"),
        CINNAMON(RankineBlocks.CINNAMON.getPlanks(), "cinnamon"),
        HONEY_LOCUST(RankineBlocks.HONEY_LOCUST.getPlanks(), "honey_locust"),
        WEEPING_WILLOW(RankineBlocks.WEEPING_WILLOW.getPlanks(), "weeping_willow"),
        ERYTHRINA(RankineBlocks.ERYTHRINA.getPlanks(), "erythrina"),
        PETRIFIED_CHORUS(RankineBlocks.PETRIFIED_CHORUS.getPlanks(), "petrified_chorus"),
        CHARRED(RankineBlocks.CHARRED.getPlanks(), "charred"),
        BAMBOO(RankineBlocks.BAMBOO.getPlanks(), "bamboo"),
        BAMBOO_CULMS(RankineBlocks.BAMBOO_CULMS.getPlanks(), "bamboo_culms");


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
