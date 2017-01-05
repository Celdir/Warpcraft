package Warpcraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.HandlerList;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.UUID;
import java.util.logging.Level;

public class WarpDriveBreakListener implements Listener {
    private String name;

    public WarpDriveBreakListener(String name) {
        this.name = name;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        WarpDrive w;
        synchronized(Warpcraft.registry) {
            w = Warpcraft.registry.get(name);
        }
        
        if (w == null) {
            HandlerList.unregisterAll(this);
            return;
        }

        if (w.isBlock(e.getBlock())) {
            if (e.getPlayer().getUniqueId().equals(w.getOwner())) {
                synchronized(Warpcraft.registry) {
                    Warpcraft.registry.remove(w.getName());
                }
                synchronized(Warpcraft.placements) {
                    Warpcraft.placements.remove(w.getLocation());
                }
                HandlerList.unregisterAll(this);
            } else {
                e.getPlayer().sendMessage(ChatColor.YELLOW + "That warpdrive is not yours.");
                e.setCancelled(true);
            }
        }
    }
}
