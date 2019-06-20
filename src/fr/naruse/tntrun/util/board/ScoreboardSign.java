package fr.naruse.tntrun.util.board;

import fr.naruse.tntrun.game.TntRun;
import fr.naruse.tntrun.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardSign {
    private Scoreboard sb;
    private Objective obj;
    private TntRun pl;
    public ScoreboardSign(TntRun pl){
        this.sb = Bukkit.getScoreboardManager().getNewScoreboard();
        this.obj = sb.registerNewObjective("hs", "dummy");
        this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.pl = pl;
        this.obj.setDisplayName("§c§lTnt§f§lRun");
        update();
    }

    public void update(){
        clearLines();
        setLine(7, "§6 ");
        setLine(6, Message.ScoreboardColorTag.TIME_RAMAINING_TAG.getColorTag()+ Message.TIME_REMAINING.getMessage());
        setLine(5, ""+lastTimer);
        setLine(4, "§8 ");
        setLine(3, Message.ScoreboardColorTag.PLAYERS_TAG.getColorTag()+Message.PLAYERS.getMessage()+": "+Message.ScoreboardColorTag.PLAYERS_COUNT_TAG.getColorTag()+pl.getPlayerInGame().size());
    }

    private String lastTimer = Message.ScoreboardColorTag.TIME_TAG.getColorTag();
    private boolean wasRed = false;
    public void updateTimer(int time){
        if(wasRed){
            wasRed = false;
            obj.setDisplayName(Message.ScoreboardColorTag.DISPLAY_NAME_FIRST.getColorTag());
        }else{
            wasRed = true;
            obj.setDisplayName(Message.ScoreboardColorTag.DISPLAY_NAME_SECOND.getColorTag());
        }
        sb.resetScores(lastTimer);
        setLine(5, Message.ScoreboardColorTag.TIME_TAG.getColorTag()+getTimer(time));
        lastTimer = Message.ScoreboardColorTag.TIME_TAG.getColorTag()+getTimer(time);
    }

    public void setLine(int line, String msg){
        obj.getScore(msg).setScore(line);
    }

    public void clearLines(){
        for(String line : sb.getEntries()){
            sb.resetScores(line);
        }
    }

    public Objective getObjective() {
        return obj;
    }

    public Scoreboard getScoreboard() {
        return sb;
    }

    private String getTimer(int time){
        int mins = time/60;
        int secondes = time-(mins*60);
        if(secondes < 10){
            return mins+":0"+secondes;
        }
        return mins+":"+secondes;
    }
}

