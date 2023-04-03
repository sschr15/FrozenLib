package net.frozenblock.lib.entrypoint.api;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public abstract class FrozenModInitializer implements ModInitializer {

	private final String modId;
	private final ModContainer container;

	public FrozenModInitializer(String modId) {
		this.modId = modId;
		this.container = FabricLoader.getInstance().getModContainer(modId).orElseThrow(() -> new IllegalStateException("Mod container not found for mod id " + modId));
	}

	@Override
	public void onInitialize() {
		this.onInitialize(this.modId, this.container);
	}

	public abstract void onInitialize(String modId, ModContainer container);

	public String modId() {
		return this.modId;
	}

	public ModContainer container() {
		return this.container;
	}

	public ResourceLocation id(String path) {
		return new ResourceLocation(this.modId, path);
	}

	public <T> T register(Registry<T> registry, String path, T value) {
		return Registry.register(registry, this.id(path), value);
	}
}