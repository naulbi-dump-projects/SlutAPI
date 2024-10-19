package naulbimix.slutmine.slutapi.utils;

import java.util.*;
import org.bukkit.*;
import java.util.logging.*;
import org.bukkit.entity.*;
import naulbimix.slutmine.slutapi.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;

public class ServerUtils {

    private static Main plugin = Main.getInstance();

    public static String s(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static boolean notAllowedSymbol(String message) {
        String allowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-!?&§ ";
        for (int i = 0; i < message.length(); ++i) {
            if (!allowed.contains(String.valueOf(message.charAt(i)))) {
                return true;
            }
        }
        return false;
    }

    public static void sendTitle(Player p, String title, int i, int i1, int i2, String subtitle, int i3, int i4, int i5) {
        PacketPlayOutTitle t1 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + s(title) +"\"}"),i, i1,i2);
        PacketPlayOutTitle t2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + s(subtitle) + "\"}"),i3,i4,i5);

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(t1);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(t2);
    }

    public static void randomTeleport(Player player, Type type) {
        Random random = new Random();
        int x = random.nextInt(plugin.getConfig().getInt("settings." + type.toString().toLowerCase() + ".coords"));
        int z = random.nextInt(plugin.getConfig().getInt("settings." + type.toString().toLowerCase() + ".coords"));
        x = randomType(x);
        z = randomType(z);
        int y = 150;
        Location loc = new Location(player.getWorld(), x, y, z);
        loc.getWorld().getHighestBlockYAt(loc);
        while (loc.getBlock().getType() != Material.AIR) {
            loc.setX(loc.getX() + 1.0D);
            loc.setZ(loc.getZ() + 1.0D);
            loc.setY(loc.getY() + 1.0D);
        }
        loc.getWorld().getHighestBlockYAt(loc);
        player.getEyeLocation().getBlock().setType(Material.AIR);
        player.teleport(loc);
        if (plugin.getConfig().getBoolean("settings." + type.toString().toLowerCase() + ".message")) {
            player.sendMessage(s(plugin.getConfig().getString("messages.commands." + type.toString().toLowerCase() + ".teleported").replace("$x", x + "").replace("$y", y + "").replace("$z", z + "")));
        }
        if (plugin.getConfig().getBoolean("settings." + type.toString().toLowerCase() + ".title")) {
            sendTitle(player, plugin.getConfig().getString("messages.commands." + type.toString().toLowerCase() + ".title").replace("$x", x + "").replace("$y", y + "").replace("$z", z + ""), 40, 20, 20, plugin.getConfig().getString("messages.commands." + type.toString().toLowerCase() + ".subtitle").replace("$x", x + "").replace("$y", y + "").replace("$z", z + ""), 40, 20, 20);

        }
    }

    public static int randomType(int i) {
        Random random = new Random();
        int r = random.nextInt(2);
        switch (r) {
            case 0:
                return i *= -1;
            case 1:
                return i;
        }
        return i *= -1;
    }

    public static void Logger(String type, String message) {
        type = type.toUpperCase();
        type = type.equals("ERROR") ? "SEVERE" : type;
        plugin.getLogger().log(Level.parse(type), ServerUtils.s("&7" + message));
    }

}
