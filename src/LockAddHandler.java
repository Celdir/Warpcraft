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
import org.bukkit.event.HandlerList;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

public class LockAddHandler extends LockHandler implements Listener {
    public LockAddHandler(Player p, String cmd, String[] args) {
        super(p, cmd, args);

        if (args.length < 1) {
            target.sendMessage(ChatColor.YELLOW + "You must specify names to be added to the lock.");
            this.cancel();
            HandlerList.unregisterAll(this);
        }
    }

    protected void lock(WarpDrive tar) {
        if (!tar.isLocked()) {
            target.sendMessage(ChatColor.YELLOW + "This WarpDrive is not locked.");
            return;
        }
        tar.lock(players);
        target.sendMessage(ChatColor.GREEN + "Successfully added players to lock!");
    }
}
