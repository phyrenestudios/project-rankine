package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class RankineBoatEntity extends BoatEntity {
    private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.createKey(RankineBoatEntity.class, DataSerializers.VARINT);
    public RankineBoatEntity(EntityType<? extends BoatEntity> entityType, World worldIn)
    {
        super(entityType, worldIn);
    }

    public RankineBoatEntity(World worldIn, double x, double y, double z) {
        this(ModEntityTypes.RANKINE_BOAT, worldIn);
        this.setPosition(x, y, z);
        this.setMotion(Vec3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    protected void registerData()
    {
        super.registerData();
        this.dataManager.register(BOAT_TYPE, RankineBoatEntity.Type.OAK.ordinal());
    }

    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void writeAdditional(CompoundNBT compound) {
        compound.putString("Type", this.getRankineBoatType().getName());
    }

    protected void readAdditional(CompoundNBT compound) {
        if (compound.contains("Type", 8)) {
            this.setRankineBoatType(RankineBoatEntity.Type.getTypeFromString(compound.getString("Type")));
        }

    }

    public Item getItemBoat() {
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
                return ModItems.CEDAR_BOAT;
            case COCONUT_PALM:
                return ModItems.COCONUT_PALM_BOAT;
            case PINYON_PINE:
                return ModItems.PINYON_PINE_BOAT;
            case JUNIPER:
                return ModItems.JUNIPER_BOAT;
            case BALSAM_FIR:
                return ModItems.BALSAM_FIR_BOAT;
            case MAGNOLIA:
                return ModItems.MAGNOLIA_BOAT;
        }
    }


    public RankineBoatEntity.Type getRankineBoatType() {
        return RankineBoatEntity.Type.byId(this.dataManager.get(BOAT_TYPE));
    }

    public void setRankineBoatType(RankineBoatEntity.Type boatType) {
        this.dataManager.set(BOAT_TYPE, boatType.ordinal());
    }

    public enum Type {
        OAK(Blocks.OAK_PLANKS, "oak"),
        SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"),
        BIRCH(Blocks.BIRCH_PLANKS, "birch"),
        JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
        ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
        DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak"),
        CEDAR(ModBlocks.CEDAR_PLANKS, "cedar"),
        COCONUT_PALM(ModBlocks.COCONUT_PALM_PLANKS, "coconut_palm"),
        PINYON_PINE(ModBlocks.PINYON_PINE_PLANKS, "pinyon_pine"),
        JUNIPER(ModBlocks.JUNIPER_PLANKS, "juniper"),
        BALSAM_FIR(ModBlocks.BALSAM_FIR_PLANKS, "balsam_fir"),
        MAGNOLIA(ModBlocks.MAGNOLIA_PLANKS, "magnolia");


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
