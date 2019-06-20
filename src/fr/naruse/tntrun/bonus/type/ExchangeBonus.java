package fr.naruse.tntrun.bonus.type;

import fr.naruse.tntrun.bonus.Bonus;
import fr.naruse.tntrun.game.TntRun;
import fr.naruse.tntrun.util.Message;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class ExchangeBonus extends Bonus {
    private TntRun pl;
    private Location loc;
    public ExchangeBonus(TntRun pl, Location loc){
        super(pl, loc);
        this.pl = pl;
        this.loc = loc;
    }

    @Override
    public void playBonus() {
        pl.sendMessage("§6Bonus: §3"+ Message.EXCHANGE_BONUS.getMessage());
        int r = new Random().nextInt(pl.getPlayerInGame().size());
        Player p = pl.getPlayerInGame().get(r);
        if(r == 0){
            if(pl.getPlayerInGame().size() > 1){
                r++;
            }
        }else{
            r--;
        }
        Player p1 = pl.getPlayerInGame().get(r);
        pl.sendMessage(" §6-§2 "+p.getName()+" "+Message.YOU_EXCHANGE_WITH.getMessage()+ " "+p1.getName()+".");
        Location location = p.getLocation().clone();
        p.teleport(p1.getLocation());
        p1.teleport(location);
    }
}
