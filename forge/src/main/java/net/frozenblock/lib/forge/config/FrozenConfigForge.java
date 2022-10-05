package net.frozenblock.lib.forge.config;

import com.google.common.base.Preconditions;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;

import java.util.Arrays;

/**
 * CLIENT ONLY
 */
public class FrozenConfigForge {

    public static ConfigCategory createSubCategory(ConfigEntryBuilder entryBuilder, ConfigCategory parentCategory, Component key, boolean expanded, Component tooltip, AbstractConfigListEntry... entries) {
        Preconditions.checkArgument(entryBuilder != null, "ConfigEntryBuilder is null");
        Preconditions.checkArgument(parentCategory != null, "Parent Category is null");
        Preconditions.checkArgument(key != null, "Sub Category key is null");
        Arrays.stream(entries).forEach(entry -> Preconditions.checkArgument(entry != null, "Config List Entry is null"));

        var subCategory = entryBuilder.startSubCategory(key, Arrays.stream(entries).toList());

        subCategory.setExpanded(expanded);
        if (tooltip != null) {
            subCategory.setTooltip(tooltip);
        }

        return parentCategory.addEntry(entryBuilder.startSubCategory(key, Arrays.stream(entries).toList())
                .setExpanded(expanded)
                .setTooltip(tooltip)
                .build()
        );
    }
}
