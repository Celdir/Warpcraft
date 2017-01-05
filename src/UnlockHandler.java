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

public class UnlockHandler extends LockHandler implements Listener {
    public UnlockHandler(Player p, String cmd, String[] args) {
        super(p, cmd, args);
    }

    protected void lock(WarpDrive tar) {
        if (!tar.isLocked()) {
            target.sendMessage(ChatColor.YELLOW + "This WarpDrive is not locked.");
            return;
        }
        tar.unlock();
        target.sendMessage(ChatColor.GREEN + "Successfully unlocked!");
    }
}
