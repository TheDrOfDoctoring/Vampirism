package de.teamlapen.vampirism.network;

import de.teamlapen.lib.network.IMessage;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.world.MultiBossInfo;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraftforge.fml.network.NetworkEvent;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.Supplier;

public class UpdateMultiBossInfoPacket implements IMessage {
    static void encode(UpdateMultiBossInfoPacket msg, PacketBuffer buf) {
        buf.writeUniqueId(msg.uniqueId);
        buf.writeEnumValue(msg.operation);
        switch (msg.operation) {
            case ADD:
                buf.writeTextComponent(msg.name);
                buf.writeVarInt(msg.colors.size());
                msg.colors.forEach(color -> buf.writeVarInt(color.getRGB()));
                buf.writeEnumValue(msg.overlay);
            case UPDATE_PCT:
                buf.writeVarInt(msg.entries.size());
                for (Map.Entry<Color, Float> value : msg.entries.entrySet()) {
                    buf.writeVarInt(value.getKey().getRGB());
                    buf.writeFloat(value.getValue());
                }
                break;
            case UPDATE_NAME:
                buf.writeTextComponent(msg.name);
                break;
            case UPDATE_STYLE:
                buf.writeEnumValue(msg.overlay);
                break;
        }
    }

    static UpdateMultiBossInfoPacket decode(PacketBuffer buf) {
        UUID uuid = buf.readUniqueId();
        SUpdateBossInfoPacket.Operation operation = buf.readEnumValue(SUpdateBossInfoPacket.Operation.class);
        UpdateMultiBossInfoPacket packet = new UpdateMultiBossInfoPacket(operation, uuid);
        switch (operation) {
            case ADD:
                packet.name = buf.readTextComponent();
                int size = buf.readVarInt();
                List<Color> colors = new LinkedList<>();
                for (int i = 0; i < size; i++) {
                    colors.add(new Color(buf.readVarInt(), true));
                }
                packet.colors = colors;
                packet.overlay = buf.readEnumValue(BossInfo.Overlay.class);
            case UPDATE_PCT:
                Map<Color, Float> entries = new LinkedHashMap<>();
                int size2 = buf.readVarInt();
                for (int i = 0; i < size2; i++) {
                    Color color = new Color(buf.readVarInt(), true);
                    float perc = buf.readFloat();
                    entries.put(color, perc);
                }
                packet.entries = entries;
                break;
            case UPDATE_NAME:
                packet.name = buf.readTextComponent();
                break;
            case UPDATE_STYLE:
                packet.overlay = buf.readEnumValue(BossInfo.Overlay.class);
                break;
        }
        return packet;
    }

    public static void handle(final UpdateMultiBossInfoPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> VampirismMod.proxy.handleUpdateMultiBossInfoPacket(msg));
        ctx.setPacketHandled(true);
    }
    private final UUID uniqueId;
    private final SUpdateBossInfoPacket.Operation operation;
    protected List<Color> colors;
    protected Map<Color, Float> entries;
    private ITextComponent name;
    private BossInfo.Overlay overlay;

    public UpdateMultiBossInfoPacket(SUpdateBossInfoPacket.Operation operation, MultiBossInfo data) {
        this.uniqueId = data.getUniqueId();
        this.operation = operation;
        this.name = data.getName();
        this.colors = data.getColors();
        this.entries = data.getEntries();
        this.overlay = data.getOverlay();
    }

    private UpdateMultiBossInfoPacket(SUpdateBossInfoPacket.Operation operation, UUID uuid) {
        this.uniqueId = uuid;
        this.operation = operation;
        this.entries = new LinkedHashMap<>();
    }

    public List<Color> getColors() {
        return colors;
    }

    public Map<Color, Float> getEntries() {
        return entries;
    }

    public ITextComponent getName() {
        return name;
    }

    public SUpdateBossInfoPacket.Operation getOperation() {
        return operation;
    }

    public BossInfo.Overlay getOverlay() {
        return overlay;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }


}
