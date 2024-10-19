package naulbimix.slutmine.slutapi.events;

import java.util.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import naulbimix.slutmine.slutapi.Main;
import naulbimix.slutmine.slutapi.utils.*;
import org.bukkit.event.world.WorldLoadEvent;

public class Listeners implements Listener {

    private static Main plugin = Main.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFixPistonExtend(BlockPistonExtendEvent e) {
        e.setCancelled(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFixPistonRetract(BlockPistonRetractEvent e) {
        e.setCancelled(false);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFixDupePortal(PlayerPortalEvent e) {
        e.getPlayer().closeInventory();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFixDupeInteractBlock(PlayerInteractEvent e) {
        if(e.getPlayer().isInsideVehicle() && e.hasBlock()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if(e.getPlayer() instanceof Player) {
            Player p = e.getPlayer();
            if(ServerUtils.notAllowedSymbol(e.getMessage())) {
                e.setCancelled(true);
                p.sendMessage(ServerUtils.s(plugin.getConfig().getString("messages.chat.unallowsymbols")));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = e.getPlayer();
            if (ServerUtils.notAllowedSymbol(e.getMessage().replace("/", ""))) {
                e.setCancelled(true);
                p.sendMessage(ServerUtils.s(plugin.getConfig().getString("messages.chat.unallowsymbols")));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickInventory(InventoryClickEvent e) {
        if(e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if(e.getInventory() instanceof AnvilInventory && e.getRawSlot() == e.getView().convertSlot(e.getRawSlot()) && e.getRawSlot() == 2 && e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                boolean unallowsymbols = false;
                List<String> lore = e.getCurrentItem().getItemMeta().getLore();
                String displayName = e.getCurrentItem().getItemMeta().getDisplayName();
                if(ServerUtils.notAllowedSymbol(displayName) || ServerUtils.notAllowedSymbol(lore.toString())) {
                    unallowsymbols = true;
                }
                for(String message : lore) {
                    if(ServerUtils.notAllowedSymbol(message)) {
                        unallowsymbols = true;
                        e.setCancelled(true);
                    }
                }
                if(unallowsymbols) {
                    e.setCancelled(true);
                    p.sendMessage(ServerUtils.s(plugin.getConfig().getString("messages.inventory.unallowsymbols")));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSighChange(SignChangeEvent e) {
        for (int i = 0; i < 4; i++) {
            if (e.getLine(i).matches("^[a-zA-Z0-9_]*$")) {
                if (e.getLine(i).length() > 20) {
                    e.setCancelled(true);
                }
            } else if (e.getLine(i).length() > 50) {
                e.setCancelled(true);
            }

            if(ServerUtils.notAllowedSymbol(e.getLine(i))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldLoad(WorldLoadEvent e) {
        e.getWorld().setGameRuleValue("announceAdvancements", "false");
    }

}
