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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.HandlerList;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.UUID;
import java.util.logging.Level;

public class WarpDriveHandler extends CommandHandler implements Listener {
    private Warpcraft parent;

    public WarpDriveHandler(Player p, String cmd, String[] args, Warpcraft parent) {
        super(p, cmd, args);
        this.parent = parent;

        if (args.length != 1) {
            String msg;
            msg = args.length < 1 ? "Please specify a name for the WarpDrive." : "Too many arguments. WarpDrive names can only be one word without spaces.";
            target.sendMessage(msg);
            this.cancel();
            HandlerList.unregisterAll(this);
            return;
        }

        synchronized(Warpcraft.registry) {
            if (Warpcraft.registry.containsKey(args[0])) {
                target.sendMessage("The name " + args[0] + " is already in use as a WarpDrive. Please pick another name.");
                this.cancel();
            }
        }
    }

    protected void execute() {
        if (block.getType() == Material.LAPIS_BLOCK) {
            makeDrive(block);
            parent.getServer().getPluginManager().registerEvents(new WarpDriveBreakListener(args[0]), parent);
        } else {
            target.sendMessage("I'm sorry, WarpDrives can only be made out of lapis blocks.");
        }

        HandlerList.unregisterAll(this);
    }

    private void makeDrive(Block b) {
        synchronized(Warpcraft.registry) {
            synchronized(Warpcraft.placements) {
                if (Warpcraft.placements.containsKey(b.getLocation())) {
                    target.sendMessage("This block is already a WarpDrive.");
                    return;
                }
                Warpcraft.registry.put(args[0], new WarpDrive(args[0], target.getUniqueId(), b));
                Warpcraft.placements.put(b.getLocation(), Warpcraft.registry.get(args[0]));
            }
            target.sendMessage("WarpDrive created successfully!");
        }
    }
}
