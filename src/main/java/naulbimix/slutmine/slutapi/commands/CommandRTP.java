package naulbimix.slutmine.slutapi.commands;

import naulbimix.slutmine.slutapi.Main;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import naulbimix.slutmine.slutapi.utils.*;

public class CommandRTP implements CommandExecutor {

    private static Main plugin = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String l, String[] args) {
        if(s instanceof Player){
            Player p = ((Player) s).getPlayer();
            if(args.length == 0) {
                    if(p.hasPermission(plugin.getConfig().getString("settings.perms.rtp"))) {
                        ServerUtils.randomTeleport(p, Type.RTP);
                        return true;
                    }
                    p.sendMessage(ServerUtils.s(plugin.getConfig().getString("messages.noPermission")));
                    return true;
            }
            p.sendMessage(ServerUtils.s(plugin.getConfig().getString("messages.commands.rtp.usage")));
            return true;
        }
        s.sendMessage(ServerUtils.s(plugin.getConfig().getString("messages.useOnlyPlayers")));
        return true;
    }
}
