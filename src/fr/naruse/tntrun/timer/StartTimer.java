package fr.naruse.tntrun.timer;

import fr.naruse.tntrun.game.TntRun;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StartTimer extends BukkitRunnable {
    private TntRun pl;
    public StartTimer(TntRun tntRun) {
        this.pl = tntRun;
        this.time = pl.getMain().getConfig().getInt("time.wait");
        this.runTaskTimer(tntRun.getMain(), 20, 20);
    }

    private int time = 60;
    @Override
    public void run() {
        pl.getScoreboardSign().updateTimer(time);
        for(Player p : pl.getPlayerInGame()){
            p.setFoodLevel(20);
        }
        if(pl.getPlayerInGame().size() >= pl.getMain().getConfig().getInt("min")){
            if(time != 0){
                time--;
            }else{
                pl.start();
                this.cancel();
            }
        }else{
            time = 60;
        }
    }
}
