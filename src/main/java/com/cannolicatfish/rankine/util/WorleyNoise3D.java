package com.cannolicatfish.rankine.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import org.joml.Vector3f;
import oshi.util.tuples.Pair;

public class WorleyNoise3D {

    public static Vector3f closestPoint(PerlinSimplexNoise noiseIn, float noiseScale, boolean curvyEdges, BlockPos posIn, Pair<Float,Short> layerNums) {
        Vector3f inputRegion = getInputRegion(noiseScale, posIn);
        Vector3f inputVec = getInputOffsetVec(noiseIn, noiseScale, curvyEdges, posIn).add(0f, layerNums.getA(),0f).sub(inputRegion);
        Vector3f closestPoint = null;
        float minDist = 5.0f;
        for (short i=-1; i<=1; i+=1) {
            for (short j = -1; j <= 1; j += 1) {
                Vector3f neighborOffset = new Vector3f(i,0,j);
                Vector3f neighborPoint = randomVec3XZ(noiseIn, add(neighborOffset, inputRegion), layerNums.getB());
                float dist = add(neighborPoint, neighborOffset).sub(inputVec).length();
                if (dist < minDist) {
                    minDist = dist;
                    closestPoint = add(neighborPoint, add(neighborOffset, inputRegion));
                }
            }
        }
        return closestPoint;
    }

    private static Vector3f getInputOffsetVec(PerlinSimplexNoise noiseIn, float noiseScale, boolean curvyEdges, BlockPos posIn) {
        int xshift = curvyEdges ? (int) (noiseScale*0.1*(noiseIn.getValue(posIn.getX()*0.02+42, posIn.getZ()*0.02+42, false)+1)/2) : 0;
        int zshift =  curvyEdges ? (int) (noiseScale*0.1*(noiseIn.getValue(posIn.getX()*0.02-42, posIn.getZ()*0.02-42, false)+1)/2) : 0;
        return new Vector3f((posIn.getX()+xshift)/noiseScale, 0, (posIn.getZ()+zshift)/noiseScale);
    }
    private static Vector3f getInputRegion(float noiseScale, BlockPos posIn) {
        return new Vector3f((float) Math.floor(posIn.getX()/noiseScale), 0, (float) Math.floor(posIn.getZ()/noiseScale));
    }
    private static Vector3f randomVec3XZ(PerlinSimplexNoise noiseIn, Vector3f vecIn, int layer) {
        return new Vector3f((float) (noiseIn.getValue(vecIn.x()+16*layer, vecIn.z()+16*layer, false)+1f)/2f,(float) (noiseIn.getValue(vecIn.x()+160*layer, vecIn.z()+160*layer, false)+1f)/4f + 0.5f,(float) (noiseIn.getValue(vecIn.x()+16*layer, vecIn.z()+16*layer, false)+1f)/2f);
    }
    private static Vector3f add(Vector3f vec1, Vector3f vec2) {
        return new Vector3f(vec1.x()+vec2.x(), vec1.y()+vec2.y(), vec1.z()+vec2.z());
    }

}
