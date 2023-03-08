package net.frozenblock.lib.worldgen.feature.api;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FrozenPlacedFeature {

	/**
	 * Can be used for setting all bootstrap contexts on 1.19.3
	 */
	public static final List<FrozenPlacedFeature> FEATURES = new ArrayList<>();

	private final ResourceKey<PlacedFeature> key;

	private Holder<ConfiguredFeature<?, ?>> configuredHolder;
	private Holder<PlacedFeature> holder;

	public FrozenPlacedFeature(ResourceLocation key) {
		this.key = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, key);
		FEATURES.add(this);
	}
	public ResourceKey<PlacedFeature> getKey() {
		return key;
	}

	public Holder<@Nullable ConfiguredFeature<?, ?>> getConfiguredHolder() {
		if (this.configuredHolder == null)
			return Holder.direct(null);
		return this.configuredHolder;
	}

	@SuppressWarnings("unchecked")
	public <FC extends FeatureConfiguration> FrozenPlacedFeature setConfiguredHolder(Holder<ConfiguredFeature<FC, ?>> configuredHolder) {
		this.configuredHolder = (Holder) configuredHolder;
		return this;
	}

	public Holder<@Nullable PlacedFeature> getHolder() {
		if (this.holder == null)
			return Holder.direct(null);
		return this.holder;
	}

	public FrozenPlacedFeature setHolder(Holder<PlacedFeature> holder) {
		this.holder = holder;
		return this;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public <FC extends FeatureConfiguration> FrozenPlacedFeature makeAndSetHolder(Holder<ConfiguredFeature<FC, ?>> configuredHolder, List<PlacementModifier> modifiers) {
		this.configuredHolder = (Holder) configuredHolder;
		Holder<PlacedFeature> holder = PlacementUtils.register(this.getKey().location().toString(), configuredHolder, modifiers);
		return this.setHolder(holder);
	}

	public <FC extends FeatureConfiguration> FrozenPlacedFeature makeAndSetHolder(Holder<ConfiguredFeature<FC, ?>> configuredHolder, PlacementModifier... modifiers) {
		return this.makeAndSetHolder(configuredHolder, List.of(modifiers));
	}
}
