package naulbimix.slutmine.slutapi;

import java.io.*;

import org.bukkit.plugin.java.*;
import naulbimix.slutmine.slutapi.utils.*;
import naulbimix.slutmine.slutapi.events.*;
import naulbimix.slutmine.slutapi.commands.*;

public class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        ServerUtils.Logger("info", "&8-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= &eSlut&cAPI &8-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        ServerUtils.Logger("info", "&6| &fВерсия: &6" + getDescription().getVersion());
        ServerUtils.Logger("info", "&6| &fАвтор: &6NaulbiMIX");
        ServerUtils.Logger("info", "&6| &fВеб-сайт: &6" + getDescription().getWebsite());
        ServerUtils.Logger("info", "&8-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= &eSlut&cAPI &8-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if(!config.exists()){
            saveDefaultConfig();
            ServerUtils.Logger("info", "&6| &fСоздание нового конфигурационного файла плагина...");
        }
        getServer().getPluginManager().registerEvents(new Listeners(), getInstance());
        getCommand("rtp").setExecutor(new CommandRTP());
        getCommand("vrtp").setExecutor(new CommandVRTP());
        ServerUtils.Logger("info", "&6| &fПлагин был &eуспешно &aвключён&7!");
    }

    @Override
    public void onDisable() {
        ServerUtils.Logger("info", "&8-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= &eSlut&cAPI &8-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        ServerUtils.Logger("info", "&6| &fВерсия: &6" + getDescription().getVersion());
        ServerUtils.Logger("info", "&6| &fАвтор: &6NaulbiMIX");
        ServerUtils.Logger("info", "&6| &fВеб-сайт: &6" + getDescription().getWebsite());
        ServerUtils.Logger("info", "&8-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= &eSlut&cAPI &8-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        ServerUtils.Logger("info", "&6| &fПлагин был &eуспешно &cвыключен&7!");
    }

    public static Main getInstance(){
        return plugin;
    }

}
