package pw.cinque.packetapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Queue;

@Getter
@RequiredArgsConstructor
class PacketPlayer {

    private final Player player;
    private final Queue<?> incomingQueue;
    private final Queue<?> outgoingQueue;

}
