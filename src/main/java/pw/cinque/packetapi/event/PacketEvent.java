package pw.cinque.packetapi.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

@Getter
@Setter
public abstract class PacketEvent extends Event implements Cancellable {

    private Object packet;
    private boolean cancelled;

}
