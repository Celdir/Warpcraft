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
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

public class LockHandler extends CommandHandler implements Listener {
    protected Set<UUID> players;

    public LockHandler(Player p, String cmd, String[] args) {
        super(p, cmd, args);

        players = new HashSet<UUID>();
        players.add(target.getUniqueId());
        for (String s : args) {
            Player n = Bukkit.getServer().getPlayer(s);
            if (n == null) {
                target.sendMessage(s + " is not a valid player name.");
                this.cancel();
                HandlerList.unregisterAll(this);
                break;
            } else {
                players.add(n.getUniqueId());
            }
        }
    }

    protected void execute() {
        WarpDrive tar = null;
        synchronized(Warpcraft.registry) {
            for (WarpDrive w : Warpcraft.registry.values()) {
                if (w.isBlock(block)) {
                    tar = w;
                }
            }
        }

        if (tar == null) {
            target.sendMessage("That block is not a WarpDrive.");
        } else {
            if (!target.getUniqueId().equals(tar.getOwner())) {
                target.sendMessage("You are not the owner of this WarpDrive.");
            } else {
                lock(tar);
            }
        }

        HandlerList.unregisterAll(this);
    }

    protected void lock(WarpDrive tar) {
        tar.unlock();
        tar.lock(players);
        target.sendMessage("Successfully locked!");
    }
}
