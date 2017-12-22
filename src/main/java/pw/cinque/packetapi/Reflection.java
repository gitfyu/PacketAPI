package pw.cinque.packetapi;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

class Reflection {

    @Getter
    private final Class<?> packetClass;
    private final Method getHandleMethod;
    private final Field playerConnectionField;
    private final Field networkManagerField;
    private final Field incomingQueueField;
    private final Field outgoingQueueField;

    Reflection() throws Exception {
        String nmsVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];

        Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
        Class<?> entityPlayerClass = Class.forName("net.minecraft.server." + nmsVersion + ".EntityPlayer");
        Class<?> playerConnectionClass = Class.forName("net.minecraft.server." + nmsVersion + ".PlayerConnection");
        Class<?> networkManagerClass = Class.forName("net.minecraft.server." + nmsVersion + ".NetworkManager");

        List<Field> queues = Arrays.stream(networkManagerClass.getDeclaredFields()).filter(field -> field.getType() == Queue.class).collect(Collectors.toList());

        if (queues.size() != 2) {
            throw new RuntimeException("Queue fields not found!");
        }

        this.packetClass = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
        this.getHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
        this.playerConnectionField = entityPlayerClass.getDeclaredField("playerConnection");
        this.networkManagerField = playerConnectionClass.getDeclaredField("networkManager");
        this.incomingQueueField = queues.get(0);
        this.outgoingQueueField = queues.get(1);

        this.incomingQueueField.setAccessible(true);
        this.outgoingQueueField.setAccessible(true);
    }

    PacketPlayer createPacketPlayer(Player player) throws Exception {
        Object entityPlayer = getHandleMethod.invoke(player);
        Object playerConnection = playerConnectionField.get(entityPlayer);
        Object networkManager = networkManagerField.get(playerConnection);

        Queue incomingQueue = (Queue) incomingQueueField.get(networkManager);
        Queue outgoingQueue = (Queue) outgoingQueueField.get(networkManager);

        return new PacketPlayer(player, incomingQueue, outgoingQueue);
    }

}
