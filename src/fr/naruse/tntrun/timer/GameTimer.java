package fr.naruse.tntrun.timer;

import fr.naruse.tntrun.bonus.Bonus;
import fr.naruse.tntrun.game.TntRun;
import fr.naruse.tntrun.util.Message;
import fr.naruse.tntrun.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameTimer extends BukkitRunnable {
    private TntRun pl;
    public GameTimer(TntRun tntRun) {
        this.pl = tntRun;
        this.bonus = pl.getMain().getConfig().getInt("time.bonus");
        this.runTaskTimer(tntRun.getMain(), 20, 20);
    }

    private int time = 0, bonus = 20;
    @Override
    public void run() {
        pl.getScoreboardSign().updateTimer(time);
        time++;
        if(bonus != 0){
            bonus--;
        }else{
            bonus = pl.getMain().getConfig().getInt("time.bonus");
            Location loc = pl.getPlayerInGame().get(new Random().nextInt(pl.getPlayerInGame().size())).getLocation();
            Bonus bonus = Bonus.Builder.build(pl, loc);
            bonus.playBonus();
        }
        Iterator<Player> iterator = ((ArrayList<Player>) pl.getPlayerInGame().clone()).iterator();
        while(iterator.hasNext()){
            Player p = iterator.next();
            p.setFoodLevel(20);
            if(p.getLocation().getY() < pl.getInitializer().getHeight()){
                pl.sendMessage("§e"+p.getName()+"§c "+ Message.FELL.getMessage());
                p.setGameMode(GameMode.SPECTATOR);
                p.teleport(pl.getInitializer().getSpawn());
                pl.getPlayerInGame().remove(p);
                pl.getSpectator().add(p);
                pl.getBreakerManager().unregister(p);
                pl.getSignManager().updateSigns();
            }
            iterator.remove();
        }
        if(pl.getPlayerInGame().size() == 1 && pl.getMain().getConfig().getInt("min") != 1){
            Player winner = pl.getPlayerInGame().get(0);
            pl.getBreakerManager().unregister(winner);
            if(!pl.getMain().getConfig().getString("rewards.command").equalsIgnoreCase("null")){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), pl.getMain().getConfig().getString("rewards.command").replace("{player}", winner.getName()));
            }
            if (pl.getMain().getOtherPluginSupport().getVaultPlugin().getEconomy() != null) {
                if (pl.getMain().getConfig().getInt("rewards.win") != 0) {
                    pl.getMain().getOtherPluginSupport().getVaultPlugin().getEconomy().depositPlayer(winner, pl.getMain().getConfig().getDouble("rewards.win"));
                }
            }
            pl.sendMessage("§6"+winner.getName()+" "+Message.WINS_THIS_GAME.getMessage());
            Bukkit.getScheduler().scheduleSyncDelayedTask(pl.getMain(), new Runnable() {
                @Override
                public void run() {
                    pl.getGame().GAME = false;
                    pl.getGame().WAIT = true;
                    boolean canRestartTimer = true;
                    if(pl.getMain().getConfig().getBoolean("allow.end.tpBackPlayers")){
                        for(int i = 0; i < pl.getPlayerInGame().size(); i++){
                            Player p = pl.getPlayerInGame().get(i);
                            Location location = pl.getListeners().getFirstPlayerLocation(p);
                            if(location == null){
                                p.kickPlayer("§a"+Message.GAME_ENDED.getMessage());
                            }else{
                                pl.quitPlayer(p);
                            }
                        }
                        for(int i = 0; i < pl.getSpectator().size(); i++){
                            Player p = pl.getSpectator().get(i);
                            Location location = pl.getListeners().getFirstPlayerLocation(p);
                            if(location == null){
                                p.kickPlayer("§a"+Message.GAME_ENDED.getMessage());
                            }else{
                                pl.quitPlayer(p);
                            }
                        }
                    }
                    if(pl.getMain().getConfig().getBoolean("allow.end.kickPlayers")){
                        for(int i = 0; i < pl.getPlayerInGame().size(); i++){
                            Player p = pl.getPlayerInGame().get(i);
                            p.kickPlayer("§a"+Message.GAME_ENDED.getMessage());
                        }
                        for(int i = 0; i < pl.getSpectator().size(); i++){
                            Player p = pl.getSpectator().get(i);
                            p.kickPlayer("§a"+Message.GAME_ENDED.getMessage());
                        }
                    }
                    Utils.regen(pl.getMain());
                    if(pl.getMain().getConfig().getBoolean("allow.end.shutdown")){
                        Bukkit.shutdown();
                        canRestartTimer = false;
                    }
                    if(pl.getMain().getConfig().getBoolean("allow.end.reload")){
                        Bukkit.reload();
                        canRestartTimer = false;
                    }
                    if(canRestartTimer){
                        pl.newStartTimer();
                    }
                    pl.getSignManager().updateSigns();
                }
            }, 20*5);
            this.cancel();
        }
        if(pl.getPlayerInGame().size() == 0){
            this.cancel();
            for(int i = 0; i < pl.getSpectator().size(); i++) {
                Player p = pl.getSpectator().get(i);
                pl.quitPlayer(p);
            }
            pl.getGame().GAME = false;
            pl.getGame().WAIT = true;
            pl.newStartTimer();
            Utils.regen(pl.getMain());
            pl.getSignManager().updateSigns();
        }
    }
}
