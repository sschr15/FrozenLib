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

package net.frozenblock.lib.config.frozenlib_config.getter;

public class FrozenLibConfigValues {
	public static FrozenConfigGetter CONFIG = new FrozenConfigGetter(
			new ConfigInterface() {
				@Override
				public boolean useWindOnNonFrozenServers() {
					return DefaultFrozenLibConfigValues.USE_WIND_ON_NON_FROZENLIB_SERVERS;
				}

				@Override
				public boolean saveItemCooldowns() {
					return DefaultFrozenLibConfigValues.SAVE_ITEM_COOLDOWNS;
				}
			}
	);


	public record FrozenConfigGetter(ConfigInterface getter) {
	}

	public static class DefaultFrozenLibConfigValues {
		public static final boolean USE_WIND_ON_NON_FROZENLIB_SERVERS = true;
		public static final boolean SAVE_ITEM_COOLDOWNS = true;
	}

	public interface ConfigInterface {
		boolean useWindOnNonFrozenServers();
		boolean saveItemCooldowns();
	}
}
