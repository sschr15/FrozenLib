package net.frozenblock.lib.config.api.instance;

public abstract class Config<T> {

	private final String modId;
	private final Class<T> config;
	private T configInstance;
	private final T defaultInstance;

	public Config(String modId, Class<T> config) {
		this.modId = modId;
		this.config = config;
		try {
			this.defaultInstance = this.configInstance = config.getConstructor().newInstance();
		} catch (Throwable e) {
			throw new IllegalStateException("No default constructor for default config instance.");
		}
	}

	public String modId() {
		return this.modId;
	}

	public T config() {
		return this.configInstance;
	}

	public void setConfig(T configInstance) {
		this.configInstance = configInstance;
	}

	public T defaultInstance() {
		return this.defaultInstance;
	}

	public Class<T> configClass() {
		return this.config;
	}

	public abstract void save();
	public abstract void load();
}