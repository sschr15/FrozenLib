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

package net.frozenblock.lib.entity.api.rendering;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.function.BiFunction;
import net.frozenblock.lib.FrozenMain;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL32C;

public final class FrozenRenderType {

    public static final BiFunction<ResourceLocation, Boolean, RenderType> ENTITY_TRANSLUCENT_EMISSIVE_FIXED = Util.memoize(
            ((identifier, affectsOutline) -> {
                RenderType.CompositeState multiPhaseParameters = RenderType.CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(identifier, false, false))
                        .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(affectsOutline);
                return create(
                        FrozenMain.string("entity_translucent_emissive_fixed"),
                        DefaultVertexFormat.NEW_ENTITY,
                        VertexFormat.Mode.QUADS,
                        256,
                        true,
                        true,
                        multiPhaseParameters
                );
            })
    );

	public static final BiFunction<ResourceLocation, Boolean, RenderType> ENTITY_TRANSLUCENT_EMISSIVE_ALWAYS_RENDER = Util.memoize(
			((texture, affectsOutline) -> create(
					FrozenMain.string("entity_translucent_emissive_always_render"),
					DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
					VertexFormat.Mode.QUADS,
					256,
					false,
					true,
					RenderType.CompositeState.builder()
							.setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
							.setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
							.setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
							.setCullState(RenderStateShard.NO_CULL)
							.setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
							.setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
							//.setLayeringState(RenderStateShard.POLYGON_OFFSET_LAYERING)
							//.setOutputState(RenderStateShard.TRANSLUCENT_TARGET)
							.createCompositeState(affectsOutline)
			))
	);

    public static RenderType entityTranslucentEmissiveFixed(ResourceLocation resourceLocation) {
        return ENTITY_TRANSLUCENT_EMISSIVE_FIXED.apply(resourceLocation, true);
    }

	public static RenderType entityTranslucentEmissiveFixedNoOutline(ResourceLocation resourceLocation) {
		return ENTITY_TRANSLUCENT_EMISSIVE_FIXED.apply(resourceLocation, false);
	}

	public static RenderType entityTranslucentEmissiveAlwaysRender(ResourceLocation resourceLocation) {
		return ENTITY_TRANSLUCENT_EMISSIVE_ALWAYS_RENDER.apply(resourceLocation, false);
	}

    /*@Nullable
    public static ShaderInstance renderTypeTranslucentCutoutShader;

    public static final RenderStateShard.ShaderStateShard RENDERTYPE_TRANSLUCENT_CUTOUT_SHADER = new RenderStateShard.ShaderStateShard(
            WilderWildClient::getRenderTypeTranslucentCutoutShader
    );

    @Nullable
    public static ShaderInstance getRenderTypeTranslucentCutoutShader() {
        return renderTypeTranslucentCutoutShader;
    }

    public static final RenderType TRANSLUCENT_CUTOUT = create(
            "translucent_cutout_wilderwild", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 2097152, true, true, RenderType.translucentState(RENDERTYPE_TRANSLUCENT_CUTOUT_SHADER)
    );

    public static RenderType translucentCutout() {
        return TRANSLUCENT_CUTOUT;
    }*/

	public static RenderType dynamicLightStencil() {
		return DYNAMIC_LIGHT_STENCIL;
	}

	public static RenderType dynamicLightColor() {
		return DYNAMIC_LIGHT_COLOR;
	}

	public static RenderStateShard.OutputStateShard STENCIL_SETUP_AND_LEAK = new RenderStateShard.OutputStateShard("stencil_setup_and_leak", () -> {
		GL32C.glClear(1024);
		GL32C.glColorMask(false, false, false, false);
		GL32C.glEnable(2929);
		GL32C.glDepthMask(true);
		GL32C.glEnable(2960);
		GL32C.glDepthMask(false);
		GL32C.glEnable(34383);
		GL32C.glDisable(2884);
		GL32C.glStencilFunc(519, 0, 255);
		GL32C.glStencilOpSeparate(1029, 7680, 34055, 7680);
		GL32C.glStencilOpSeparate(1028, 7680, 34056, 7680);
	}, () -> {
		GL32C.glDisable(34383);
		GL32C.glEnable(2884);
		GL32C.glColorMask(true, true, true, true);
	});

	public static RenderStateShard.OutputStateShard STENCIL_RENDER_AND_CLEAR = new RenderStateShard.OutputStateShard("stencil_render_and_clear", () -> {
		GL32C.glStencilFunc(514, 1, 255);
		GL32C.glDepthFunc(516);
		GL32C.glCullFace(1028);
		GL32C.glStencilOpSeparate(1028, 7680, 7680, 7680);
	}, () -> {
		GL32C.glDepthFunc(515);
		GL32C.glCullFace(1029);
		GL32C.glDepthMask(true);
		GL32C.glDisable(2960);
	});

	private static final RenderType DYNAMIC_LIGHT_STENCIL = RenderType.create("dynamic_light_stencil", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES, 256, RenderType.CompositeState.builder().setShaderState(RenderStateShard.POSITION_COLOR_SHADER).setOutputState(STENCIL_SETUP_AND_LEAK).createCompositeState(false));

	private static final RenderType DYNAMIC_LIGHT_COLOR = RenderType.create("dynamic_light_color", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES, 256, RenderType.CompositeState.builder().setShaderState(RenderStateShard.POSITION_COLOR_SHADER).setOutputState(STENCIL_RENDER_AND_CLEAR).setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY).createCompositeState(false));


	public static RenderType.CompositeRenderType create(
            String name,
            VertexFormat vertexFormat,
            VertexFormat.Mode drawMode,
            int expectedBufferSize,
            boolean hasCrumbling,
            boolean translucent,
            RenderType.CompositeState phases
    ) {
        return new RenderType.CompositeRenderType(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, phases);
    }
}
