package fr.naruse.tntrun.floor;

import fr.naruse.tntrun.game.TntRun;
import fr.naruse.tntrun.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class BreakerPlayer {
    private TntRun pl;
    private Player p;
    private boolean inSchedule = false;
    public BreakerPlayer(TntRun pl, Player p) {
        this.pl = pl;
        this.p = p;
    }

    private Location loc;
    public void move() {
        loc = p.getLocation().clone().add(0, -1, 0);
        if(loc.getBlock().getType() == Material.AIR){
            loc = getLocation(loc);
        }
        if(loc.clone().add(0, -1, 0).getBlock().getType() != Material.TNT){
            return;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(pl.getMain(), new Runnable() {
            Location location = loc.clone();
            @Override
            public void run() {
                Utils.setAir(location);
            }
        }, 5);
    }

    private Location getLocation(Location loc){
        if(loc.getBlock().getRelative(BlockFace.NORTH).getType() != Material.AIR){
            return loc = loc.getBlock().getRelative(BlockFace.NORTH).getLocation();
        }
        if(loc.getBlock().getRelative(BlockFace.EAST).getType() != Material.AIR){
            return loc = loc.getBlock().getRelative(BlockFace.EAST).getLocation();
        }
        if(loc.getBlock().getRelative(BlockFace.WEST).getType() != Material.AIR){
            return loc = loc.getBlock().getRelative(BlockFace.WEST).getLocation();
        }
        if(loc.getBlock().getRelative(BlockFace.SOUTH).getType() != Material.AIR){
            return loc = loc.getBlock().getRelative(BlockFace.SOUTH).getLocation();
        }
        if(loc.getBlock().getRelative(BlockFace.NORTH_EAST).getType() != Material.AIR){
            return loc = loc.getBlock().getRelative(BlockFace.NORTH_EAST).getLocation();
        }
        if(loc.getBlock().getRelative(BlockFace.NORTH_WEST).getType() != Material.AIR){
            return loc = loc.getBlock().getRelative(BlockFace.NORTH_WEST).getLocation();
        }
        if(loc.getBlock().getRelative(BlockFace.SOUTH_EAST).getType() != Material.AIR){
            return loc = loc.getBlock().getRelative(BlockFace.SOUTH_EAST).getLocation();
        }
        if(loc.getBlock().getRelative(BlockFace.SOUTH_WEST).getType() != Material.AIR){
            return loc = loc.getBlock().getRelative(BlockFace.SOUTH_WEST).getLocation();
        }
        return loc;
    }

}
