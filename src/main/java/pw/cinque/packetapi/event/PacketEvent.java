package pw.cinque.packetapi.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import pw.cinque.packetapi.WrappedPacket;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class PacketEvent extends Event implements Cancellable {

    private final WrappedPacket packet;
    private boolean cancelled;

}
