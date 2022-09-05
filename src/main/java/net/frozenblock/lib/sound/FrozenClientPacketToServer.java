package net.frozenblock.lib.sound;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.frozenblock.lib.FrozenMain;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

@Environment(EnvType.CLIENT)
public class FrozenClientPacketToServer {

    public static void sendFrozenSoundSyncRequest(int id, ResourceKey<Level> level) {
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(id);
        byteBuf.writeResourceKey(level);
        ClientPlayNetworking.send(FrozenMain.REQUEST_LOOPING_SOUND_SYNC_PACKET, byteBuf);
    }

}