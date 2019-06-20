package fr.naruse.tntrun.util.player;

import fr.naruse.tntrun.game.TntRun;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TntRunPlayer {
    private Inventory inv;
    private Player p;
    private TntRun pl;
    private GameMode gameMode;
    private boolean isFlying = false;
    public TntRunPlayer(TntRun pl, Player p){
        this.pl = pl;
        this.p = p;
    }

    public void registerInventory(){
        inv = Bukkit.createInventory(null, 9*6, p.getInventory().getName());
        for(int i = 0; i < inv.getSize(); i++){
            try{
                if(p.getInventory().getItem(i) != null){
                    inv.setItem(i, p.getInventory().getItem(i));
                }
            }catch (Exception e){
                break;
            }
        }
    }

    public void setPlayerInventory(){
        if(inv == null){
            return;
        }
        for(int i = 0; i < 9*6; i++){
            if(inv.getItem(i) != null){
                p.getInventory().setItem(i, inv.getItem(i));
            }
        }
    }

    public void registerGameMode(){
        this.gameMode = p.getGameMode();
    }

    public void setPlayerGameMode(){
        if(gameMode != null){
            p.setGameMode(gameMode);
        }
    }

    public void setIsFlying(){
        p.setFlying(isFlying);
    }

    public void registerIsFlying(){
        this.isFlying = p.isFlying();
    }
}
