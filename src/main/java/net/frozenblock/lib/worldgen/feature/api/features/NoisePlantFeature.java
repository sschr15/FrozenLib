/*
 * Copyright 2023 FrozenBlock
 * This file is part of FrozenLib.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.lib.worldgen.feature.api.features;

import com.mojang.serialization.Codec;
import net.frozenblock.lib.worldgen.feature.api.features.config.PathFeatureConfig;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

public class NoisePlantFeature extends Feature<PathFeatureConfig> {
    public NoisePlantFeature(Codec<PathFeatureConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<PathFeatureConfig> context) {
        boolean generated = false;
        PathFeatureConfig config = context.config();
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();
        int radiusSquared = config.radius * config.radius;
        RandomSource random = level.getRandom();
        ImprovedNoise sampler = config.noise == 1 ? EasyNoiseSampler.perlinLocal : config.noise == 2 ? EasyNoiseSampler.perlinChecked : config.noise == 3 ? EasyNoiseSampler.perlinThreadSafe : EasyNoiseSampler.perlinXoro;
        int bx = blockPos.getX();
        int bz = blockPos.getZ();
        BlockPos.MutableBlockPos mutable = blockPos.mutable();

        for (int x = bx - config.radius; x <= bx + config.radius; x++) {
            for (int z = bz - config.radius; z <= bz + config.radius; z++) {
                double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
                if (distance < radiusSquared) {
                    mutable.set(x, level.getHeight(Types.OCEAN_FLOOR, x, z), z);
                    double sample = EasyNoiseSampler.sample(level, sampler, mutable, config.multiplier, config.multiplyY, config.useY);
                    if (sample > config.minThresh && sample < config.maxThresh && level.getBlockState(mutable).is(config.replaceable) && level.getBlockState(mutable.below()).is(BlockTags.DIRT)) {
                        generated = true;
                        level.setBlock(mutable, config.pathBlock.getState(random, mutable), 3);
                    }
                }
            }
        }
        return generated;
    }

}
