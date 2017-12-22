# PacketAPI
PacketAPI is a very basic Bukkit plugin that can be used by other plugins to listen for incoming and outgoing packets. It has only been tested on 1.7.10, however it should work on all Minecraft versions as it doesn't import any NMS/CraftBukkit classes directly.

### Limitations
- The packet events are sent synchronously, I might add async events in the future
- PacketAPI cannot be used to listen for packets that are sent before the PlayerJoinEvent has been called, such as handshake packets
- PacketAPI cannot be used to listen for 'high-priority' packets, such as the keep alive packet

### Example usage
```java
@EventHandler
private void onPacketReceive(PacketReceiveEvent event) {
    WrappedPacket packet = event.getPacket();
    PacketValues values = packet.getValues();

    if (packet.getType().equals("PacketPlayInCustomPayload")) {
        String channel = values.get(0, String.class);
        int length = values.get(1, int.class);
        byte[] message = values.get(2, byte[].class);

        getLogger().info(String.format("Received message '%s' with length %d on channel %s!",
                new String(message),
                length,
                channel));
    }
}
```
