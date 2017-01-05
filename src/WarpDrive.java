package Warpcraft;

import org.bukkit.block.Block;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

public class WarpDrive {
    private String name;
    private UUID owner;
    private Location location;
    
    private boolean locked;
    private Set<UUID> whitelist;

    public WarpDrive(String name, UUID owner, Location location) {
        this.name = name;
        this.owner = owner;
        this.location = location;
        locked = false;
        whitelist = new HashSet<UUID>();
    }

    public WarpDrive(String name, UUID owner, Block block) {
        this(name, owner, block.getLocation());
    }

    public String getName() {
        return name;
    }

    public UUID getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isLocked() {
        return locked;
    }

    public Set<UUID> getWhitelist() {
        return whitelist;
    }

    public boolean isBlock(Block b) {
        return b.getLocation().equals(location);
    }

    public void lock(Collection<UUID> allowedPlayers) {
        locked = true;
        for (UUID id : allowedPlayers) {
            whitelist.add(id);
        }
    }

    public void lockremove(Collection<UUID> disallowedPlayers) {
        for (UUID id : disallowedPlayers) {
            whitelist.remove(id);
        }
    }

    public void unlock() {
        locked = false;
        whitelist.clear();
    }

    public void warp(Player p) {
        if (!locked || whitelist.contains(p.getUniqueId())) {
            p.teleport(new Location(location.getWorld(), location.getX(), location.getY() + 2, location.getZ()));
            p.sendMessage(ChatColor.DARK_PURPLE + "Welcome to " + name + "!");
        } else {
            p.sendMessage(ChatColor.YELLOW + "You do not have access to the WarpDrive \"" + name + "\".");
        }
    }
}
