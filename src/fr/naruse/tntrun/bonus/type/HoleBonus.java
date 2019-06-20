package fr.naruse.tntrun.bonus.type;

import fr.naruse.tntrun.bonus.Bonus;
import fr.naruse.tntrun.game.TntRun;
import fr.naruse.tntrun.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;


public class HoleBonus extends Bonus {
    private TntRun pl;
    private Location loc;
    public HoleBonus(TntRun pl, Location loc){
        super(pl, loc);
        this.pl = pl;
        this.loc = loc.clone();
    }

    @Override
    public void playBonus() {
        int i = 0;
        while(loc.getBlock().getType() == Material.AIR){
            loc.add(0, -1, 0);
            i++;
            if(i == 40){
                break;
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(pl.getMain(), new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i != 6; i++){
                    for(Block b : Utils.getCircle(loc, i)){
                        Location loc = b.getLocation();
                        Utils.setAir(loc);
                        loc.getWorld().strikeLightningEffect(loc);
                    }
                }
            }
        }, 20);
    }
}
