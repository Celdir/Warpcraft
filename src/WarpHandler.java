package Warpcraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.HandlerList;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.UUID;
import java.util.logging.Level;

public class WarpHandler extends CommandHandler implements Listener {
    public WarpHandler(Player p, String cmd, String[] args) {
        super(p, cmd, args);
        execute();
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent e) {}

    protected void execute() {
        if (args.length < 1) {
            target.sendMessage("Please specify a WarpDrive to warp to.");
        } else if (args.length > 1) {
            target.sendMessage("You may only warp to one WarpDrive at a time.");
        } else {
            warpTo(args[0]);
        }

        HandlerList.unregisterAll(this);
    }

    protected void warpTo(String dest) {
        WarpDrive src;
        WarpDrive dst;
        synchronized(Warpcraft.registry) {
            dst = Warpcraft.registry.get(dest);
        }
        synchronized(Warpcraft.placements) {
            // We getLocation() again because the first location has the wrong rotation and is not considered equal.
            if (Warpcraft.placements.containsKey(target.getLocation().subtract(0,1,0).getBlock().getLocation())) { 
                src = Warpcraft.placements.get(target.getLocation().subtract(0,1,0).getBlock().getLocation());
            } else {
                src = null;
            }
        }
        if (dst == null) {
            target.sendMessage(dest + " is not a valid WarpDrive.");
        } else if (src == null) {
            target.sendMessage("You must be standing above a WarpDrive to warp.");
        } else {
            dst.warp(target);

            synchronized(Warpcraft.backlog) {
                if (!Warpcraft.backlog.containsKey(target.getUniqueId())) {
                    Warpcraft.backlog.put(target.getUniqueId(), new Stack<WarpDrive>());
                }
                Warpcraft.backlog.get(target.getUniqueId()).push(src);
            }
        }
    }
}
