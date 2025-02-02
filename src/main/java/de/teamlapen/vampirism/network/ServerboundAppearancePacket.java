package de.teamlapen.vampirism.network;

import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.api.util.VResourceLocation;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public record ServerboundAppearancePacket(int entityId, String name, List<Integer> data) implements CustomPacketPayload {

    public static final Type<ServerboundAppearancePacket> TYPE = new Type<>(VResourceLocation.mod("appearance"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundAppearancePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, ServerboundAppearancePacket::entityId,
            ByteBufCodecs.STRING_UTF8, ServerboundAppearancePacket::name,
            ByteBufCodecs.VAR_INT.apply(ByteBufCodecs.list()), ServerboundAppearancePacket::data,
            ServerboundAppearancePacket::new
    );

    public ServerboundAppearancePacket(int entityId, String name, int... data) {
        this(entityId, name, Arrays.stream(data).boxed().toList());
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
