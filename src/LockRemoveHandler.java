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
import java.util.Arrays;

public class LockRemoveHandler extends LockHandler implements Listener {
    public LockRemoveHandler(Player p, String cmd, String[] args) {
        super(p, cmd, args);

        if (args.length < 1) {
            target.sendMessage(ChatColor.YELLOW + "You must specify names to be removed from the lock.");
            this.cancel();
            HandlerList.unregisterAll(this);
            return;
        }

        if(!Arrays.asList(args).contains(target.getPlayerListName())) {
            players.remove(target.getUniqueId());
        }
    }

    protected void lock(WarpDrive tar) {
        if (!tar.isLocked()) {
            target.sendMessage(ChatColor.YELLOW + "This WarpDrive is not locked.");
            return;
        }
        tar.lockremove(players);
        target.sendMessage(ChatColor.GREEN + "Successfully removed players from lock!");
    }
}
