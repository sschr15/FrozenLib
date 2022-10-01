/*
 * Copyright 2022 QuiltMC
 * Modified to work on Fabric
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quiltmc.qsl.frozenblock.misc.datafixerupper.impl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.frozenblock.lib.FrozenMain;
import org.jetbrains.annotations.ApiStatus;

/**
 * Modified to work on Fabric
 */
@ApiStatus.Internal
public final class ServerFreezer implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            FrozenMain.log("[Quilt DFU API] Serverside DataFixer Registry is about to freeze", true);
            QuiltDataFixesInternals.get().freeze();
            FrozenMain.log("[Quilt DFU API] Serverside DataFixer Registry was frozen", true);
        });
    }
}