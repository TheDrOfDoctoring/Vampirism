package de.teamlapen.vampirism.network;

import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.api.util.VResourceLocation;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;


public record ServerboundNameItemPacket(Optional<String> name) implements CustomPacketPayload {

    public static final Type<ServerboundNameItemPacket> TYPE = new Type<>(VResourceLocation.mod("name_item"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundNameItemPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8), ServerboundNameItemPacket::name,
            ServerboundNameItemPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
