package fr.naruse.tntrun.util;

import com.google.common.collect.Lists;
import fr.naruse.tntrun.main.TntRunPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.List;

public class Initializer {
    private TntRunPlugin pl;
    private Location spawn, lobby = null, end = null;
    private List<Material> materials = Lists.newArrayList();
    private int height;
    public Initializer(TntRunPlugin pl) {
        this.pl = pl;
    }

    public boolean init(){
        if(pl.getConfig().getString("location.spawn.x") == null){
            Bukkit.getConsoleSender().sendMessage(Message.TNT_RUN.getMessage()+" §cThe location SPAWN is not found.");
            return false;
        }
        if(pl.getConfig().getString("height") == null){
            Bukkit.getConsoleSender().sendMessage(Message.TNT_RUN.getMessage()+" §cThe height HEIGHT is not found.");
            return false;
        }
        if(pl.getConfig().getString("map.a.x") == null){
            Bukkit.getConsoleSender().sendMessage(Message.TNT_RUN.getMessage()+" §cThe region MAP is not found.");
            return false;
        }
        if(pl.getConfig().getString("map.b.x") == null){
            Bukkit.getConsoleSender().sendMessage(Message.TNT_RUN.getMessage()+" §cThe region MAP is not found.");
            return false;
        }
        this.spawn = new Location(Bukkit.getWorld(pl.getConfig().getString("location.spawn.world")), pl.getConfig().getDouble("location.spawn.x"),
                pl.getConfig().getDouble("location.spawn.y"),  pl.getConfig().getDouble("location.spawn.z"),  pl.getConfig().getInt("location.spawn.yaw")
                , pl.getConfig().getInt("location.spawn.pitch"));
        if(pl.getConfig().getString("location.lobby.x") != null){
            this.lobby = new Location(Bukkit.getWorld(pl.getConfig().getString("location.lobby.world")), pl.getConfig().getDouble("location.lobby.x"),
                    pl.getConfig().getDouble("location.lobby.y"),  pl.getConfig().getDouble("location.lobby.z"),  pl.getConfig().getInt("location.lobby.yaw")
                    , pl.getConfig().getInt("location.lobby.pitch"));
        }
        if(pl.getConfig().getString("location.end.x") != null){
            this.lobby = new Location(Bukkit.getWorld(pl.getConfig().getString("location.end.world")), pl.getConfig().getDouble("location.end.x"),
                    pl.getConfig().getDouble("location.end.y"),  pl.getConfig().getDouble("location.end.z"),  pl.getConfig().getInt("location.end.yaw")
                    , pl.getConfig().getInt("location.end.pitch"));
        }
        this.height = pl.getConfig().getInt("height");
        for(String s : pl.getConfig().getStringList("block")){
            Material material = Material.valueOf(s);
            if(material != null){
                materials.add(material);
            }
        }
        return true;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getLobby() {
        return lobby;
    }

    public Location getEnd() {
        return end;
    }

    public int getHeight() {
        return height;
    }

    public List<Material> getMaterials() {
        return materials;
    }
}
