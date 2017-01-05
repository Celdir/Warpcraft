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

public abstract class CommandHandler implements Listener {
    protected Player target;
    protected String cmd;
    protected String[] args;
    protected Block block;
    protected boolean cancelled;

    public CommandHandler(Player p, String cmd, String[] args) {
        target = p;
        this.cmd = cmd;
        this.args = args;
        cancelled = false;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            block = e.getClickedBlock();
            if (!cancelled) {
                execute();
            }
        }
    }

    protected void cancel() {
        cancelled = true;
    }

    protected abstract void execute();
}
