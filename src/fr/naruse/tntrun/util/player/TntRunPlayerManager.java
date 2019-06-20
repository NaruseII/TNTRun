package fr.naruse.tntrun.util.player;

import fr.naruse.tntrun.game.TntRun;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TntRunPlayerManager {
    private TntRun pl;
    private HashMap<Player, TntRunPlayer> tntRunPlayerHashMap = new HashMap<>();
    public TntRunPlayerManager(TntRun tntRun) {
        this.pl = tntRun;
    }

    public void registerPlayer(Player p){
        TntRunPlayer tntRunPlayer = new TntRunPlayer(pl, p);
        tntRunPlayer.registerGameMode();
        tntRunPlayer.registerInventory();
        tntRunPlayer.registerIsFlying();
        tntRunPlayerHashMap.put(p, tntRunPlayer);
    }

    public void setPlayerProperties(Player p){
        TntRunPlayer tntRunPlayer = getTntRunPlayer(p);
        if(tntRunPlayer == null){
            return;
        }
        tntRunPlayer.setPlayerGameMode();
        tntRunPlayer.setPlayerInventory();
        tntRunPlayer.setIsFlying();
    }

    public TntRunPlayer getTntRunPlayer(Player p){
        if(tntRunPlayerHashMap.containsKey(p)){
            return tntRunPlayerHashMap.get(p);
        }
        return null;
    }
}
