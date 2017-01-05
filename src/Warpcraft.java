package Warpcraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.block.Block;
import org.bukkit.Location;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;

public class Warpcraft extends JavaPlugin implements CommandExecutor {
    protected static Map<String, WarpDrive> registry;
    protected static Map<Location, WarpDrive> placements;
    protected static Map<UUID, Stack<WarpDrive>> backlog;

    @Override
    public void onEnable() {
        try {
            load();

            backlog = new HashMap<UUID, Stack<WarpDrive>>();

            getCommand("warpdrive").setExecutor(this);
            getCommand("warp").setExecutor(this);
            getCommand("back").setExecutor(this);
            getCommand("lock").setExecutor(this);
            getCommand("lockadd").setExecutor(this);
            getCommand("lockremove").setExecutor(this);
            getCommand("unlock").setExecutor(this);

            getServer().getPluginManager().enablePlugin(this);
            getLogger().info("Enabled Warpcraft plugin!");
        } catch (Exception e) {
            getLogger().log(Level.WARNING, "Error: could not enable Warpcraft plugin.\n" + e.getMessage());
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        File saveData = new File("warpcraftSave.dat");
        try {
            if (Warpcraft.registry != null) {
                save();
            }
            getLogger().info("Disabled Warpcraft plugin.");
        } catch (Exception e) {
            getLogger().log(Level.WARNING, "Error: could not disable Warpcraft plugin.\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void load() throws IOException, ClassNotFoundException {
        registry = new HashMap<String, WarpDrive>();
        placements = new HashMap<Location, WarpDrive>();

        File saveData = new File("warpcraftSave.dat");
        if (saveData.exists() && saveData.length() != 0) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveData));
            
            int numWarpDrives = (int) in.readObject();
            for (int i = 0; i < numWarpDrives; i++) {
                String name = (String) in.readObject();
                UUID owner = (UUID) in.readObject();
                Location location = Location.deserialize((Map<String, Object>) in.readObject());
                boolean locked = (boolean) in.readObject();
                Set<UUID> whitelist = (Set<UUID>) in.readObject();

                WarpDrive w = new WarpDrive(name, owner, location);
                if (locked) {
                    w.lock(whitelist);
                }

                registry.put(name, w);
                placements.put(location, w);
                getServer().getPluginManager().registerEvents(new WarpDriveBreakListener(name), this);
            }

            in.close();
        }
    }

    private void save() throws IOException {
        File saveData = new File("warpcraftSave.dat");
        if (!saveData.exists()) {
            saveData.createNewFile();
        }
        synchronized(registry) {
            if (registry != null && placements != null && registry.size() != 0) {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveData));
                out.writeObject(registry.size());
                for (WarpDrive w : registry.values()) {
                    out.writeObject(w.getName());
                    out.writeObject(w.getOwner());
                    out.writeObject(w.getLocation().serialize());
                    out.writeObject(w.isLocked());
                    out.writeObject(w.getWhitelist());
                }
                out.close();
            } else {
                saveData.delete();
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("/" + label + " is only available to players.");
        }
        Player player = (Player) sender;

        CommandHandler c;
        if (label.equalsIgnoreCase("warp")) {
            c = new WarpHandler(player, label, args);
        } else if (label.equalsIgnoreCase("warpdrive")) {
            c = new WarpDriveHandler(player, label, args, this);
        } else if (label.equalsIgnoreCase("back")) {
            c = new BackHandler(player, label, args);
        } else if (label.equalsIgnoreCase("lock")) {
            c = new LockHandler(player, label, args);
        } else if (label.equalsIgnoreCase("lockadd")) {
            c = new LockAddHandler(player, label, args);
        } else if (label.equalsIgnoreCase("lockremove")) {
            c = new LockRemoveHandler(player, label, args);
        } else if (label.equalsIgnoreCase("unlock")) {
            c = new UnlockHandler(player, label, args);
        } else {
            return false;
        }
        getServer().getPluginManager().registerEvents(c, this);

        return true;
    }
}
