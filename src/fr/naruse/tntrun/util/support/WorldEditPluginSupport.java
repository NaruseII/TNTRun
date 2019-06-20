package fr.naruse.tntrun.util.support;


import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;

public class WorldEditPluginSupport {
    private WorldEditPlugin we;
    private boolean isPresent;
    public WorldEditPluginSupport(){
        this.we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        this.isPresent = we != null;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public WorldEditPlugin getWorldEdit() {
        return we;
    }
}
