package fr.naruse.tntrun.bonus.type;

import fr.naruse.tntrun.bonus.Bonus;
import fr.naruse.tntrun.game.TntRun;
import fr.naruse.tntrun.util.Message;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class JumpBonus extends Bonus {
    private TntRun pl;
    private Location loc;
    public JumpBonus(TntRun pl, Location loc){
        super(pl, loc);
        this.pl = pl;
        this.loc = loc;
    }

    @Override
    public void playBonus() {
        pl.sendMessage("§6Bonus: §3"+ Message.JUMP_BONUS.getMessage());
        for(Player p : pl.getPlayerInGame()){
            p.setVelocity(new Vector(0, 2, 0));
        }
    }
}
