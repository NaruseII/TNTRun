package fr.naruse.tntrun.game;

import com.google.common.collect.Lists;
import fr.naruse.tntrun.event.Listeners;
import fr.naruse.tntrun.floor.BreakerManager;
import fr.naruse.tntrun.main.TntRunPlugin;
import fr.naruse.tntrun.timer.GameTimer;
import fr.naruse.tntrun.timer.StartTimer;
import fr.naruse.tntrun.util.board.SignManager;
import fr.naruse.tntrun.util.option.Options;
import fr.naruse.tntrun.util.Game;
import fr.naruse.tntrun.util.Initializer;
import fr.naruse.tntrun.util.Message;
import fr.naruse.tntrun.util.board.ScoreboardSign;
import fr.naruse.tntrun.util.player.TntRunPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TntRun {
    private TntRunPlugin pl;
    private Initializer initializer;
    private Game game;
    private ArrayList<Player> playerInGame = Lists.newArrayList();
    private ArrayList<Player> spectator = Lists.newArrayList();
    private StartTimer startTimer;
    private ScoreboardSign scoreboardSign;
    private BreakerManager breakerManager;
    private Listeners listeners;
    private Options options;
    private SignManager signManager;
    private TntRunPlayerManager tntRunPlayerManager;
    public TntRun(TntRunPlugin pl) {
        this.pl = pl;
        this.initializer = new Initializer(pl);
        if (!initializer.init()) {
            return;
        }
        this.game = new Game();
        this.signManager = new SignManager(this);
        this.options = new Options(pl);
        this.scoreboardSign = new ScoreboardSign(this);
        this.startTimer = new StartTimer(this);
        this.breakerManager = new BreakerManager(this);
        this.tntRunPlayerManager = new TntRunPlayerManager(this);
        Bukkit.getPluginManager().registerEvents(this.listeners = new Listeners(this), pl);
    }

    public void start(){
        sendMessage("§6"+ Message.GAME_STARTS.getMessage());
        game.WAIT = false;
        game.GAME = true;
        for(Player p : playerInGame){
            breakerManager.register(p);
        }
        new GameTimer(this);
        signManager.updateSigns();
    }

    public void sendMessage(String msg){
        for(Player p : playerInGame){
            p.sendMessage(msg);
        }
        for(Player p : spectator){
            p.sendMessage(msg);
        }
    }

    public void disable() {
        for(Player p : playerInGame){
            p.setGameMode(GameMode.SURVIVAL);
            getBreakerManager().unregister(p);
        }
        signManager.disable();
    }

    public void joinPlayer(Player p){
        if(getPlayerInGame().contains(p)){
            return;
        }
        if(!getGame().WAIT){
            return;
        }
        tntRunPlayerManager.registerPlayer(p);
        listeners.getLocationHashMap().put(p, p.getLocation());
        getPlayerInGame().add(p);
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setFoodLevel(20);
        if(initializer.getLobby() != null){
            p.teleport(getInitializer().getLobby());
        }else{
            p.teleport(getInitializer().getSpawn());
        }
        p.getInventory().clear();
        sendMessage("§6"+p.getName()+"§a "+ Message.JOINED_THE_GAME.getMessage()+" §7("+getPlayerInGame().size()+"/"+getMain().getConfig().getInt("min")+")");
        if(getOptions().isAllowingScoreboard()){
            p.setScoreboard(getScoreboardSign().getScoreboard());
        }
        getScoreboardSign().update();
        getSignManager().updateSigns();
    }

    public void quitPlayer(Player p){
        if(!getPlayerInGame().contains(p) && !getSpectator().contains(p)){
            return;
        }
        p.setGameMode(GameMode.SURVIVAL);
        getPlayerInGame().remove(p);
        getSpectator().remove(p);
        getBreakerManager().unregister(p);
        getSignManager().updateSigns();
        tntRunPlayerManager.setPlayerProperties(p);
        if(getOptions().isAllowingScoreboard()){
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        if(initializer.getEnd() != null){
            p.teleport(getInitializer().getEnd());
        }else{
            Location location = getListeners().getFirstPlayerLocation(p);
            if(location == null){
                p.kickPlayer("§a"+Message.GAME_ENDED.getMessage());
            }else{
                p.teleport(location);
                getSpectator().remove(p);
            }
        }
    }

    public void newStartTimer() {
        this.startTimer = new StartTimer(this);
    }

    public Initializer getInitializer() {
        return initializer;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<Player> getPlayerInGame() {
        return playerInGame;
    }

    public TntRunPlugin getMain() {
        return pl;
    }

    public ScoreboardSign getScoreboardSign() {
        return scoreboardSign;
    }

    public BreakerManager getBreakerManager() {
        return breakerManager;
    }

    public ArrayList<Player> getSpectator() {
        return spectator;
    }

    public StartTimer getStartTimer() {
        return startTimer;
    }

    public Listeners getListeners() {
        return listeners;
    }

    public Options getOptions() {
        return options;
    }

    public SignManager getSignManager() {
        return signManager;
    }

    public TntRunPlayerManager getTntRunPlayerManager() {
        return tntRunPlayerManager;
    }
}
