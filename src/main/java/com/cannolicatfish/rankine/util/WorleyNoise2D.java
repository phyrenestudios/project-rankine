package com.cannolicatfish.rankine.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import org.joml.Vector2f;

public class WorleyNoise2D {

    public static Vector2f closestPoint(PerlinSimplexNoise noiseIn, float noiseScale, boolean curvyEdges, BlockPos posIn) {
        Vector2f inputRegion = getInputRegion(noiseScale, posIn);
        Vector2f inputVec = getInputOffsetVec(noiseIn, noiseScale, curvyEdges, posIn).sub(inputRegion);
        Vector2f closestPoint = null;
        float minDist = 5.0f;
        for (short i=-1; i<=1; i+=1) {
            for (short j = -1; j <= 1; j += 1) {
                Vector2f neighborOffset = new Vector2f(i,j);
                Vector2f neighborPoint = randomVec2XY(noiseIn, add(neighborOffset, inputRegion));
                float dist = add(neighborPoint, neighborOffset).sub(inputVec).length();
                if (dist < minDist) {
                    minDist = dist;
                    closestPoint = add(neighborPoint, add(neighborOffset, inputRegion));
                }
            }
        }
        return closestPoint;
    }

    private static Vector2f getInputOffsetVec(PerlinSimplexNoise noiseIn, float noiseScale, boolean curvyEdges, BlockPos posIn) {
        int xshift = curvyEdges ? (int) (noiseScale*0.1*(noiseIn.getValue(posIn.getX()*0.02+42, posIn.getZ()*0.02+42, false)+1)/2) : 0;
        int zshift =  curvyEdges ? (int) (noiseScale*0.1*(noiseIn.getValue(posIn.getX()*0.02-42, posIn.getZ()*0.02-42, false)+1)/2) : 0;
        return new Vector2f((posIn.getX()+xshift)/noiseScale, (posIn.getZ()+zshift)/noiseScale);
    }
    private static Vector2f getInputRegion(float noiseScale, BlockPos posIn) {
        return new Vector2f((float) Math.floor(posIn.getX()/noiseScale), (float) Math.floor(posIn.getZ()/noiseScale));
    }
    private static Vector2f randomVec2XY(PerlinSimplexNoise noiseIn, Vector2f vecIn) {
        return new Vector2f((float) (noiseIn.getValue(vecIn.x()+16, vecIn.y()+16, false)+1f)/2f, (float) (noiseIn.getValue(vecIn.x()-16, vecIn.y()-16, false)+1f)/2f);
    }
    private static Vector2f add(Vector2f vec1, Vector2f vec2) {
        return new Vector2f(vec1.x()+vec2.x(), vec1.y()+vec2.y());
    }

}
