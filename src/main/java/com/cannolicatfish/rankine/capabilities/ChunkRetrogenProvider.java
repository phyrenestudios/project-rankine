package com.cannolicatfish.rankine.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChunkRetrogenProvider implements ICapabilitySerializable<CompoundTag> {


    public static final Capability<IChunkRetrogenHandler> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    private final LazyOptional<IChunkRetrogenHandler> instance = LazyOptional.of(CapabilityRetrogenHandler::new);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag cnbt = new CompoundTag();

        if (instance.isPresent() && instance.resolve().isPresent()) {
            cnbt.putBoolean("retrogenComplete",instance.resolve().get().getValue());
        } else {
            cnbt.putBoolean("retrogenComplete",false);
        }

        return cnbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (instance.isPresent() && instance.resolve().isPresent()) {
            instance.resolve().get().setValue(nbt.getBoolean("retrogenComplete"));
        }
    }
}
