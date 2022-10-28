/*
 * Copyright 2022 FrozenBlock
 * This file is part of FrozenLib.
 *
 * FrozenLib is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * FrozenLib is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with FrozenLib. If not, see <https://www.gnu.org/licenses/>.
 */

package org.quiltmc.qsl.frozenblock.misc.datafixerupper.api;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.frozenblock.lib.datafix.BlockStateRenameFix;
import net.frozenblock.lib.datafix.FrozenEntityRenameFix;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.*;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Provides methods to add common {@link DataFix}es to {@link DataFixerBuilder}s.
 * <p>
 * Modified to work on Fabric
 */
public final class SimpleFixes {
    private SimpleFixes() {
        throw new RuntimeException("SimpleFixes contains only static declarations.");
    }

    /**
     * Adds a block rename fix to the builder, in case a block's identifier is changed.
     *
     * @param builder the builder
     * @param name    the fix's name
     * @param oldId   the block's old identifier
     * @param newId   the block's new identifier
     * @param schema  the schema this fixer should be a part of
     * @see BlockRenameFix
     */
    public static void addBlockRenameFix(@NotNull DataFixerBuilder builder, @NotNull String name,
                                         @NotNull ResourceLocation oldId, @NotNull ResourceLocation newId,
                                         @NotNull Schema schema) {
        requireNonNull(builder, "DataFixerBuilder cannot be null");
        requireNonNull(name, "Fix name cannot be null");
        requireNonNull(oldId, "Old identifier cannot be null");
        requireNonNull(newId, "New identifier cannot be null");
        requireNonNull(schema, "Schema cannot be null");

        final String oldIdStr = oldId.toString(), newIdStr = newId.toString();
        builder.addFixer(BlockRenameFix.create(schema, name, (inputName) ->
                Objects.equals(NamespacedSchema.ensureNamespaced(inputName), oldIdStr) ? newIdStr : inputName));
    }

    /**
     * Adds an entity rename fix to the builder, in case an entity's identifier is changed.
     *
     * @param builder the builder
     * @param name    the fix's name
     * @param oldId   the entity's old identifier
     * @param newId   the entity's new identifier
     * @param schema  the schema this fixer should be a part of
     * @see SimpleEntityRenameFix
     */
    public static void addEntityRenameFix(@NotNull DataFixerBuilder builder, @NotNull String name,
                                        @NotNull ResourceLocation oldId, @NotNull ResourceLocation newId,
                                        @NotNull Schema schema) {
        requireNonNull(builder, "DataFixerBuilder cannot be null");
        requireNonNull(name, "Fix name cannot be null");
        requireNonNull(oldId, "Old identifier cannot be null");
        requireNonNull(newId, "New identifier cannot be null");
        requireNonNull(schema, "Schema cannot be null");

        final String oldIdStr = oldId.toString(), newIdStr = newId.toString();
        builder.addFixer(FrozenEntityRenameFix.create(schema, name, (inputName) ->
                Objects.equals(NamespacedSchema.ensureNamespaced(inputName), oldIdStr) ? newIdStr : inputName));
    }

    /**
     * Adds an item rename fix to the builder, in case an item's identifier is changed.
     *
     * @param builder the builder
     * @param name    the fix's name
     * @param oldId   the item's old identifier
     * @param newId   the item's new identifier
     * @param schema  the schema this fix should be a part of
     * @see ItemRenameFix
     */
    public static void addItemRenameFix(@NotNull DataFixerBuilder builder, @NotNull String name,
                                        @NotNull ResourceLocation oldId, @NotNull ResourceLocation newId,
                                        @NotNull Schema schema) {
        requireNonNull(builder, "DataFixerBuilder cannot be null");
        requireNonNull(name, "Fix name cannot be null");
        requireNonNull(oldId, "Old identifier cannot be null");
        requireNonNull(newId, "New identifier cannot be null");
        requireNonNull(schema, "Schema cannot be null");

        final String oldIdStr = oldId.toString(), newIdStr = newId.toString();
        builder.addFixer(ItemRenameFix.create(schema, name, (inputName) ->
                Objects.equals(NamespacedSchema.ensureNamespaced(inputName), oldIdStr) ? newIdStr : inputName));
    }

    /**
     * Adds a blockstate rename fix to the builder, in case a blockstate's name is changed.
     *
     * @param builder       the builder
     * @param name          the fix's name
     * @param blockId       the block's identifier
     * @param oldState      the blockstate's old name
     * @param defaultValue  the blockstate's default value
     * @param newState      the blockstates's new name
     * @param schema        the schema this fixer should be a part of
     * @see BlockStateRenameFix
     */
    public static void addBlockStateRenameFix(@NotNull DataFixerBuilder builder, @NotNull String name,
                                         @NotNull ResourceLocation blockId, @NotNull String oldState,
                                         @NotNull String defaultValue, @NotNull String newState,
                                         @NotNull Schema schema) {
        requireNonNull(builder, "DataFixerBuilder cannot be null");
        requireNonNull(name, "Fix name cannot be null");
        requireNonNull(blockId, "Block Id cannot be null");
        requireNonNull(oldState, "Old BlockState cannot be null");
        requireNonNull(defaultValue, "Default value cannot be null");
        requireNonNull(newState, "New BlockState cannot be null");
        requireNonNull(schema, "Schema cannot be null");

        final String blockIdStr = blockId.toString();
        builder.addFixer(new BlockStateRenameFix(schema, name, blockIdStr, oldState, defaultValue, newState));
    }

    /**
     * Adds a biome rename fix to the builder, in case biome identifiers are changed.
     *
     * @param builder the builder
     * @param name    the fix's name
     * @param changes a map of old biome identifiers to new biome identifiers
     * @param schema  the schema this fixer should be a part of
     * @see RenameBiomesFix
     */
    public static void addBiomeRenameFix(@NotNull DataFixerBuilder builder, @NotNull String name,
                                         @NotNull Map<ResourceLocation, ResourceLocation> changes,
                                         @NotNull Schema schema) {
        requireNonNull(builder, "DataFixerBuilder cannot be null");
        requireNonNull(name, "Fix name cannot be null");
        requireNonNull(changes, "Changes cannot be null");
        requireNonNull(schema, "Schema cannot be null");

        var mapBuilder = ImmutableMap.<String, String>builder();
        for (var entry : changes.entrySet()) {
            mapBuilder.put(entry.getKey().toString(), entry.getValue().toString());
        }
        builder.addFixer(new RenameBiomesFix(schema, false, name, mapBuilder.build()));
    }
}
