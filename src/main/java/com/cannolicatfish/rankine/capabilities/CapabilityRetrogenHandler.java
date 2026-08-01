package com.cannolicatfish.rankine.capabilities;

import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.capabilities.CapabilityManager;
import net.neoforged.neoforge.common.capabilities.CapabilityToken;

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
