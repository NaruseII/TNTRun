package fr.naruse.tntrun.util.board;

import com.google.common.collect.Lists;
import fr.naruse.tntrun.game.TntRun;
import fr.naruse.tntrun.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

import java.util.List;

public class SignManager {
    private TntRun pl;
    private List<Sign> signs = Lists.newArrayList();
    public SignManager(TntRun tntRun) {
        this.pl = tntRun;
        for(World world : Bukkit.getWorlds()){
            registerNewSigns(world);
        }
    }

    public void updateSigns(){
        for(Sign sign : signs){
            if(pl.getGame().WAIT){
                sign.setLine(0, Message.TNT_RUN.getMessage());
                sign.setLine(1, Message.SignColorTag.OPEN_WAIT_LINE2.getColorTag()+pl.getPlayerInGame().size() + " "+ Message.PLAYERS.getMessage().toLowerCase());
                if(pl.getPlayerInGame().size() >= pl.getMain().getConfig().getInt("min")){
                    sign.setLine(2, Message.SignColorTag.OPEN_WAIT_LINE3_0.getColorTag()+Message.READY.getMessage());
                }else{
                    sign.setLine(2, Message.SignColorTag.OPEN_WAIT_LINE3_1.getColorTag()+(pl.getMain().getConfig().getInt("min")-pl.getPlayerInGame().size())+" "+Message.MISSING.getMessage());
                }
                sign.setLine(3, Message.SignColorTag.OPEN_WAIT_LINE4.getColorTag()+Message.JOIN.getMessage());
                sign.update();
            }else if(pl.getGame().GAME){
                sign.setLine(0, Message.TNT_RUN.getMessage());
                sign.setLine(1, Message.SignColorTag.OPEN_GAME_LINE2.getColorTag()+pl.getPlayerInGame().size() + " "+ Message.PLAYERS.getMessage().toLowerCase());
                sign.setLine(2, Message.SignColorTag.OPEN_GAME_LINE3.getColorTag()+pl.getSpectator().size() + " "+ Message.SPECTATORS.getMessage().toLowerCase());
                sign.setLine(3, Message.SignColorTag.OPEN_GAME_LINE4.getColorTag()+Message.IN_GAME.getMessage());
                sign.update();
            }
        }
    }

    public void registerNewSigns(World world) {
        for(Chunk c : world.getLoadedChunks()){
            for(BlockState state : c.getTileEntities()){
                if(state instanceof Sign){
                    Sign sign = (Sign) state;
                    if(sign.getLine(0).equalsIgnoreCase("§6§l[§5TntRun§6§l]")){
                        if(!signs.contains(sign)){
                            signs.add(sign);
                        }
                    }
                }
            }
        }
        updateSigns();
    }

    public void disable(){
        for(Sign sign : signs){
            sign.setLine(0, "§6§l[§5TntRun§6§l]");
            sign.setLine(1, "");
            sign.setLine(2, "");
            sign.setLine(3, "");
            sign.update();
        }
    }
}
