package fr.naruse.tntrun.main;

import fr.naruse.tntrun.cmd.TntRunCommand;
import fr.naruse.tntrun.game.TntRun;
import fr.naruse.tntrun.util.Logs;
import fr.naruse.tntrun.util.Utils;
import fr.naruse.tntrun.util.config.Configurations;
import fr.naruse.tntrun.util.support.OtherPluginSupport;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TntRunPlugin extends JavaPlugin {
    private TntRun tntRun;
    public static TntRunPlugin INSTANCE;
    private OtherPluginSupport otherPluginSupport;
    private Configurations configurations;
    @Override
    public void onEnable(){
        super.onEnable();
        saveConfig();
        this.INSTANCE = this;
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                Logs logs = new Logs();
                getCommand("tr").setExecutor(new TntRunCommand(INSTANCE));
                getCommand("tntrun").setExecutor(new TntRunCommand(INSTANCE));
                configurations = new Configurations(INSTANCE);
                otherPluginSupport = new OtherPluginSupport();
                tntRun = new TntRun(INSTANCE);
                logs.stop();
            }
        }, 2);
    }

    @Override
    public void onDisable(){
        super.onDisable();
        tntRun.disable();
        Utils.regen(this);
    }

    public TntRun getTntRun() {
        return tntRun;
    }

    public OtherPluginSupport getOtherPluginSupport() {
        return otherPluginSupport;
    }

    public Configurations getConfigurations() {
        return configurations;
    }
}
