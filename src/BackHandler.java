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

public class BackHandler extends WarpHandler implements Listener {
    public BackHandler(Player p, String cmd, String[] args) {
        super(p, cmd, args);
    }

    protected void execute() {
        if (args.length > 0) {
            target.sendMessage("Too many arguments.");
        } else {
            back();
        }

        HandlerList.unregisterAll(this);
    }

    private void back() {
        Stack<WarpDrive> previous;
        synchronized(Warpcraft.backlog) {
            previous = Warpcraft.backlog.get(target.getUniqueId());
        }

        WarpDrive src;
        synchronized(Warpcraft.placements) {
            // We getLocation() again because the first location has the wrong rotation and is not considered equal.
            if (Warpcraft.placements.containsKey(target.getLocation().subtract(0,1,0).getBlock().getLocation())) { 
                src = Warpcraft.placements.get(target.getLocation().subtract(0,1,0).getBlock().getLocation());
            } else {
                src = null;
            }
        }
        if (src == null) {
            target.sendMessage("You must be standing above a WarpDrive to warp.");
        } else if (previous == null || previous.isEmpty()) {
            target.sendMessage("You have no previous warps on record.");
        } else {
            synchronized(Warpcraft.registry) {
                if (Warpcraft.registry.containsKey(previous.peek().getName())) {
                    previous.pop().warp(target);
                } else { // WarpDrive no longer exists.
                    previous.pop();
                    back();
                }
            }
        }
    }
}
