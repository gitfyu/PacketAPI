package pw.cinque.packetapi.event;

import lombok.Getter;
import org.bukkit.event.HandlerList;

public class PacketSendEvent extends PacketEvent {

    @Getter
    private static final HandlerList handlers = new HandlerList();

}
