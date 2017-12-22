package pw.cinque.packetapi;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PacketAPI extends JavaPlugin implements Listener {

    @Getter
    private final PacketProcessorTask processorTask = new PacketProcessorTask(this);
    @Getter
    private Reflection reflection;

    @Override
    public void onEnable() {
        try {
            reflection = new Reflection();
        } catch (Exception e) {
            getLogger().severe("Failed to initialize PlayerAPI, disabling plugin!");
            Bukkit.getPluginManager().disablePlugin(this);

            e.printStackTrace();
            return;
        }

        Bukkit.getOnlinePlayers().forEach(this::registerPlayer); // only necessary when the reload command is used
        Bukkit.getPluginManager().registerEvents(this, this);
        processorTask.runTaskTimer(this, 1L, 1L);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        registerPlayer(event.getPlayer());
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        unregisterPlayer(event.getPlayer());
    }

    private void registerPlayer(Player player) {
        try {
            processorTask.getPlayers().add(reflection.createPacketPlayer(player));
        } catch (Exception e) {
            getLogger().severe("Failed to register player!");
            e.printStackTrace();
        }
    }

    private void unregisterPlayer(Player player) {
        processorTask.getPlayers().removeIf(packetPlayer -> packetPlayer.getPlayer() == player);
    }

}
