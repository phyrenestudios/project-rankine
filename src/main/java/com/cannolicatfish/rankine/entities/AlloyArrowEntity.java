package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class AlloyArrowEntity extends AbstractArrowEntity {
    private double damage = 2.0D;
    private ItemStack arrowStack = new ItemStack(RankineItems.ALLOY_ARROW.get());

    protected AlloyArrowEntity(EntityType<? extends ArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AlloyArrowEntity(World worldIn, ItemStack arrowStackIn, LivingEntity shooter, float damageIn) {
        super(RankineEntityTypes.ALLOY_ARROW, shooter, worldIn);
        this.arrowStack = arrowStackIn.copy();
        this.damage = damageIn;
    }

    @OnlyIn(Dist.CLIENT)
    public AlloyArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world, EntityType<AlloyArrowEntity> e) {
        super(e, world);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ItemStack getArrowStack() {
        return this.arrowStack.copy();
    }
}
