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

package net.frozenblock.lib.worldgen.feature.api.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class PathSwapUnderWaterFeatureConfig implements FeatureConfiguration {
    public static final Codec<PathSwapUnderWaterFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BlockStateProvider.CODEC.fieldOf("block").forGetter((config) -> config.pathBlock),
			BlockStateProvider.CODEC.fieldOf("waterPathBlock").forGetter((config) -> config.waterPathBlock),
			Codec.intRange(1, 64).fieldOf("radius").orElse(10).forGetter((config) -> config.radius),
			Codec.intRange(1, 4).fieldOf("noise").orElse(4).forGetter((config) -> config.noise),
			Codec.doubleRange(0.0001, 128).fieldOf("multiplier").orElse(0.05).forGetter((config) -> config.multiplier),
			Codec.doubleRange(-1, 1).fieldOf("minThresh").orElse(0.2).forGetter((config) -> config.minThresh),
			Codec.doubleRange(-1, 1).fieldOf("maxThresh").orElse(1D).forGetter((config) -> config.maxThresh),
			Codec.BOOL.fieldOf("useY").orElse(false).forGetter((config) -> config.useY),
			Codec.BOOL.fieldOf("multiplyY").orElse(false).forGetter((config) -> config.multiplyY),
			Codec.BOOL.fieldOf("is3D").orElse(false).forGetter((config) -> config.is3D),
			Codec.BOOL.fieldOf("onlyExposed").orElse(false).forGetter((config) -> config.onlyExposed),
			RegistryCodecs.homogeneousList(Registry.BLOCK_REGISTRY).fieldOf("replaceable").forGetter((config) -> config.replaceable)
	).apply(instance, PathSwapUnderWaterFeatureConfig::new));

    public final BlockStateProvider pathBlock;
	public final BlockStateProvider waterPathBlock;
    public final int radius;
    public final int noise;
    public final double multiplier;
    public final double minThresh;
    public final double maxThresh;
    public final boolean useY;
    public final boolean multiplyY;
	public final boolean is3D;
	public final boolean onlyExposed;
    public final HolderSet<Block> replaceable;

    public PathSwapUnderWaterFeatureConfig(BlockStateProvider pathBlock, BlockStateProvider waterPathBlock, int radius, int noise, double multiplier, double minThresh, double maxThresh, boolean useY, boolean multiplyY, boolean is3D, boolean onlyExposed, HolderSet<Block> replaceable) {
        this.pathBlock = pathBlock;
		this.waterPathBlock = waterPathBlock;
        this.radius = radius;
        this.noise = noise;
        this.multiplier = multiplier;
        this.minThresh = minThresh;
        this.maxThresh = maxThresh;
        this.useY = useY;
        this.multiplyY = multiplyY;
		this.is3D = is3D;
		this.onlyExposed = onlyExposed;
        this.replaceable = replaceable;
    }

}
