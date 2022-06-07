package com.cannolicatfish.rankine.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityRetrogenHandler implements IChunkRetrogenHandler {

    private boolean isComplete;

    @Override
    public boolean getValue() {
        return isComplete;
    }

    @Override
    public void setValue(boolean value) {
        this.isComplete = value;
    }
}
