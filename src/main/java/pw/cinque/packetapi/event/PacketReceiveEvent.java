package pw.cinque.packetapi.event;

import org.bukkit.event.HandlerList;
import pw.cinque.packetapi.WrappedPacket;

public class PacketReceiveEvent extends PacketEvent {

    private static final HandlerList handlers = new HandlerList();

    public PacketReceiveEvent(WrappedPacket packet) {
        super(packet);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
