package fr.naruse.tntrun.bonus;

import fr.naruse.tntrun.game.TntRun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;

public abstract class Bonus implements Listener {
    private Location location;
    private TntRun pl;
    public Bonus(TntRun pl, Location loc) {
        this.pl = pl;
        this.location = loc;
    }

    public abstract void playBonus();

    public static class Builder{

        public static Bonus build(TntRun pl, Location loc){
            Bonus bonus = BonusType.random().build(pl, loc);
            Bukkit.getPluginManager().registerEvents(bonus, pl.getMain());
            return bonus;
        }

    }

}
