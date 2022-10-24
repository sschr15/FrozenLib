package net.frozenblock.lib.sound;

import net.frozenblock.lib.sound.SoundPredicate.SoundPredicate;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class MovingSoundWithRestriction extends AbstractTickableSoundInstance {

    private final Entity entity;
    private final SoundPredicate.LoopPredicate<?> predicate;

    public MovingSoundWithRestriction(Entity entity, SoundEvent sound, SoundSource category, float volume, float pitch, SoundPredicate.LoopPredicate<?> predicate) {
        super(sound, category, SoundInstance.createUnseededRandom());
        this.entity = entity;
        this.looping = false;
        this.volume = volume;
        this.pitch = pitch;
        this.x = (float) entity.getX();
        this.y = (float) entity.getY();
        this.z = (float) entity.getZ();
        this.predicate = predicate;
    }

    public boolean canPlaySound() {
        return !this.entity.isSilent();
    }

    public boolean canStartSilent() {
        return true;
    }

    public void tick() {
        if (this.entity.isRemoved()) {
            this.stop();
        } else {
            if (!this.predicate.test(this.entity)) {
                this.stop();
            } else {
                this.x = (float) this.entity.getX();
                this.y = (float) this.entity.getY();
                this.z = (float) this.entity.getZ();
            }
        }
    }

}
