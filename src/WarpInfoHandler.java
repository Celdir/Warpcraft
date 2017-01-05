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

public class WarpInfoHandler extends CommandHandler implements Listener {
    private Warpcraft parent;

    public WarpInfoHandler(Player p, String cmd, String[] args) {
        super(p, cmd, args);

        if (args.length != 0) {
            target.sendMessage(ChatColor.YELLOW + "Too many arguments.");
            this.cancel();
            HandlerList.unregisterAll(this);
            return;
        }
    }

    protected void execute() {
        warpInfo(block);
        HandlerList.unregisterAll(this);
    }

    private void warpInfo(Block b) {
        WarpDrive w;
        synchronized(Warpcraft.placements) {
            if (!Warpcraft.placements.containsKey(b.getLocation())) {
                target.sendMessage(ChatColor.YELLOW + "This block is not a WarpDrive");
                return;
            }
            
            w = Warpcraft.placements.get(b.getLocation());
        }
        target.sendMessage(ChatColor.LIGHT_PURPLE + "Name: " + w.getName());
        target.sendMessage(ChatColor.LIGHT_PURPLE + "Owner: " + Bukkit.getServer().getPlayer(w.getOwner()).getName());
        String locked = w.isLocked() ? "locked" : "unlocked";
        target.sendMessage(ChatColor.LIGHT_PURPLE + "Lock status: " + locked);
    }
}
