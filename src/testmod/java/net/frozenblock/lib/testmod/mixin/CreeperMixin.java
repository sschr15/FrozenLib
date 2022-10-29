package net.frozenblock.lib.testmod.mixin;

import net.frozenblock.lib.FrozenMain;
import net.frozenblock.lib.spotting_icons.impl.EntitySpottingIconInterface;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public class CreeperMixin {

	@Inject(method = "<init>", at = @At(value = "TAIL"))
	public void initWithIcon(EntityType<? extends Creeper> entityType, Level level, CallbackInfo info) {
		Creeper creeper = Creeper.class.cast(this);
		((EntitySpottingIconInterface)creeper).getSpottingIconManager().setIcon(FrozenMain.id("textures/spotting_icons/creeper.png"), 12, 20, FrozenMain.id("default"));
	}

}
