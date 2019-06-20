package fr.naruse.tntrun.util.option;

import com.google.common.collect.Lists;
import fr.naruse.tntrun.main.TntRunPlugin;

import java.util.List;

public class Options {
    private TntRunPlugin pl;
    public Options(TntRunPlugin pl) {
        this.pl = pl;
    }

    public boolean isAllowingScoreboard(){
        return pl.getConfig().getBoolean("allow.scoreboard");
    }

    public boolean isAllowing(String allow){
        return pl.getConfig().getBoolean("allow."+allow);
    }

    public boolean getOption(String option){
        return pl.getConfig().getBoolean(option);
    }

    public List<JoinMode> getJoinMode(){
        List<JoinMode> joinModes = Lists.newArrayList();
        if(getOption("joinMode.sign")){
            joinModes.add(JoinMode.SIGN);
        }
        if(getOption("joinMode.connection")){
            joinModes.add(JoinMode.CONNECTION);
        }
        if(getOption("joinMode.command")){
            joinModes.add(JoinMode.COMMAND);
        }
        return joinModes;
    }
}
