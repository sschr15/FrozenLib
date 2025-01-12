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

package net.frozenblock.lib.config.clothconfig;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public final class FrozenClothConfig {

	/**
	 * Creates a subcategory in the parent config category with the specified key and adds entries to it.
	 *
	 * @param entryBuilder the ConfigEntryBuilder instance
	 * @param parentCategory the parent config category
	 * @param key the key for the subcategory
	 * @param expanded if the subcategory is expanded or not
	 * @param tooltip the tooltip for the subcategory
	 * @param entries the entries to be added to the subcategory
	 * @return the newly created subcategory
	 */
	public static ConfigCategory createSubCategory(ConfigEntryBuilder entryBuilder, ConfigCategory parentCategory, Component key, boolean expanded, Component tooltip, AbstractConfigListEntry... entries) {
		// Check if the required parameters are not null
		Preconditions.checkArgument(entryBuilder != null, "ConfigEntryBuilder is null");
		Preconditions.checkArgument(parentCategory != null, "Parent Category is null");
		Preconditions.checkArgument(key != null, "Sub Category key is null");
		Arrays.stream(entries).forEach(entry -> Preconditions.checkArgument(entry != null, "Config List Entry is null"));

		// Create the subcategory
		var subCategory = entryBuilder.startSubCategory(key, Arrays.stream(entries).toList());

		// Set the expanded status
		subCategory.setExpanded(expanded);
		// If the tooltip is not null, set the tooltip for the subcategory
		if (tooltip != null) {
			subCategory.setTooltip(tooltip);
		}

		// Add the subcategory to the parent category and return it
		return parentCategory.addEntry(entryBuilder.startSubCategory(key, Arrays.stream(entries).toList())
				.setExpanded(expanded)
				.setTooltip(tooltip)
				.build()
		);
	}

}
