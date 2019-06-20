package fr.naruse.tntrun.floor;

import fr.naruse.tntrun.game.TntRun;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class BreakerManager extends BukkitRunnable {
    private TntRun pl;
    private HashMap<Player, BreakerPlayer> breakerPlayerHashMap = new HashMap<>();
    public BreakerManager(TntRun tntRun) {
        this.pl = tntRun;
        this.runTaskTimer(pl.getMain(), 2, 2);
    }

    public void register(Player p){
        BreakerPlayer breakerPlayer = new BreakerPlayer(pl, p);
        breakerPlayerHashMap.put(p, breakerPlayer);
    }

    public void movePlayers(){
        for(Player p : pl.getPlayerInGame()){
            if(breakerPlayerHashMap.containsKey(p)){
                breakerPlayerHashMap.get(p).move();
            }
        }
    }

    public void unregister(Player p) {
        breakerPlayerHashMap.remove(p);
    }

    @Override
    public void run() {
        movePlayers();
    }

    public void movePlayer(Player p) {
        if(breakerPlayerHashMap.containsKey(p)){
            breakerPlayerHashMap.get(p).move();
        }
    }
}
