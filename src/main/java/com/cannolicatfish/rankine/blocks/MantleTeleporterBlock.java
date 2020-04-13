package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.dimension.ModDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.hooks.BasicEventHooks;

import java.util.function.Function;

public class MantleTeleporterBlock extends Block {
    public MantleTeleporterBlock(Properties properties) {
        super(properties);
    }

    public static void changeDim(ServerPlayerEntity player, BlockPos pos, DimensionType type) { // copy from ServerPlayerEntity#changeDimension
        if (!ForgeHooks.onTravelToDimension(player, type)) return;
        DimensionType dimensiontype = player.dimension;

        ServerWorld serverworld = player.server.getWorld(dimensiontype);
        player.dimension = type;
        player.setInvulnerable(true);
        ServerWorld serverworld1 = player.server.getWorld(type);
        WorldInfo worldinfo = player.world.getWorldInfo();
        player.connection.sendPacket(new SRespawnPacket(type, WorldInfo.byHashing(worldinfo.getSeed()), worldinfo.getGenerator(), player.interactionManager.getGameType()));
        player.connection.sendPacket(new SServerDifficultyPacket(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
        PlayerList playerlist = player.server.getPlayerList();
        playerlist.updatePermissionLevel(player);
        serverworld.removeEntity(player, true);
        player.revive();
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;

        player.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
        serverworld.getProfiler().endSection();
        serverworld.getProfiler().startSection("placing");
        player.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);

        serverworld.getProfiler().endSection();
        player.setWorld(serverworld1);
        serverworld1.addDuringPortalTeleport(player);
        player.connection.setPlayerLocation(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
        player.interactionManager.setWorld(serverworld1);
        player.connection.sendPacket(new SPlayerAbilitiesPacket(player.abilities));
        playerlist.sendWorldInfo(player, serverworld1);
        playerlist.sendInventory(player);

        for (EffectInstance effectinstance : player.getActivePotionEffects()) {
            player.connection.sendPacket(new SPlayEntityEffectPacket(player.getEntityId(), effectinstance));
        }

        player.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
        player.setExperienceLevel(-1);
        player.setHealth(-1F);
        player.setInvulnerable(false);
        BasicEventHooks.firePlayerChangedDimensionEvent(player, dimensiontype, type);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        System.out.println(player.getPosition().down());
        System.out.println(pos);
        if (!worldIn.isRemote && !player.isPassenger() && !player.isBeingRidden() && player.isNonBoss() && player.getPosition().down().equals(new BlockPos(pos.getX(), pos.getY(), pos.getZ()))) {
            player.changeDimension(worldIn.dimension.getType() == ModDimensions.MANTLE_DIMENSION ? DimensionType.OVERWORLD : ModDimensions.MANTLE_DIMENSION, new ITeleporter() {
                @Override
                public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                    return repositionEntity.apply(false);
                }
            });
            BlockPos x = new BlockPos(player.getPosX(), player.getPosY() - 1, player.getPosZ());
            IWorld newworld = player.getEntityWorld();
            if (newworld.getBlockState(x) != this.getDefaultState() && newworld.getDimension().getType() == ModDimensions.MANTLE_DIMENSION) {

                BlockPos x2 = new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ());
                BlockPos x3 = new BlockPos(player.getPosX(), player.getPosY() + 1, player.getPosZ());
                BlockPos x4 = new BlockPos(player.getPosX(), player.getPosY() + 2, player.getPosZ());
                /*
                for (int k = 0; k < 3; k++) {
                    for (int s = 0; s < 3; s++) {
                        newworld.setBlockState(new BlockPos(player.getPosX() + k, player.getPosY() - 1, player.getPosZ() + s), ModBlocks.PERIDOTITE.getDefaultState(), 2);
                        newworld.setBlockState(new BlockPos(player.getPosX() - k, player.getPosY() - 1, player.getPosZ() - s), ModBlocks.PERIDOTITE.getDefaultState(), 2);
                    }
                }
                for (int k = 0; k < 3; k++) {
                    for (int s = 0; s < 3; s++) {
                        for (int y = 0; y < 3; y++) {
                            newworld.setBlockState(new BlockPos(player.getPosX() + k, player.getPosY() + y, player.getPosZ() + k), Blocks.AIR.getDefaultState(), 2);
                            newworld.setBlockState(new BlockPos(player.getPosX() - k, player.getPosY() + y, player.getPosZ() - k), Blocks.AIR.getDefaultState(), 2);
                        }
                    }
                }
                */
                newworld.setBlockState(x2, Blocks.AIR.getDefaultState(), 2);
                newworld.setBlockState(x3, Blocks.AIR.getDefaultState(), 2);
                newworld.setBlockState(x4, Blocks.AIR.getDefaultState(), 2);
                newworld.setBlockState(pos, this.getDefaultState(), 2);
            }
            return ActionResultType.SUCCESS;

        }
        return ActionResultType.FAIL;
    }
}
