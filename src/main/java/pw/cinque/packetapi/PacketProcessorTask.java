package pw.cinque.packetapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pw.cinque.packetapi.event.PacketReceiveEvent;
import pw.cinque.packetapi.event.PacketSendEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
class PacketProcessorTask extends BukkitRunnable {

    private final PacketAPI plugin;
    @Getter
    private List<PacketPlayer> players = new ArrayList<>();

    @Override
    public void run() {
        players.forEach(this::processQueues);
    }

    private void processQueues(PacketPlayer packetPlayer) {
        packetPlayer.getIncomingQueue().removeIf(packet -> {

            WrappedPacket wrappedPacket = wrapPacket(packet);

            if (wrappedPacket != null) {
                PacketReceiveEvent event = new PacketReceiveEvent(wrappedPacket);
                Bukkit.getPluginManager().callEvent(event);
                return event.isCancelled();
            } else {
                return false;
            }

        });

        packetPlayer.getOutgoingQueue().removeIf(packet -> {

            WrappedPacket wrappedPacket = wrapPacket(packet);

            if (wrappedPacket != null) {
                PacketSendEvent event = new PacketSendEvent(wrappedPacket);
                Bukkit.getPluginManager().callEvent(event);
                return event.isCancelled();
            } else {
                return false;
            }

        });
    }

    private WrappedPacket wrapPacket(Object packet) {
        try {
            List<Object> values = new ArrayList<>();

            Class<?> packetClass = packet.getClass();

            do { // scan the superclass(es) for fields as well
                for (Field field : packetClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    values.add(field.get(packet));
                }
            } while ((packetClass = packetClass.getSuperclass()) != plugin.getReflection().getPacketClass());

            return new WrappedPacket(packet.getClass().getSimpleName(), packet, new PacketValues(values));
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to wrap packet " + packet + "");
            e.printStackTrace();
            return null;
        }
    }

}
