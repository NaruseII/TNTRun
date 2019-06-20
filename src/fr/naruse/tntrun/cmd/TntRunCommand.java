package fr.naruse.tntrun.cmd;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import fr.naruse.tntrun.main.TntRunPlugin;
import fr.naruse.tntrun.util.Message;
import fr.naruse.tntrun.util.option.JoinMode;
import fr.naruse.tntrun.util.reflection.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TntRunCommand implements CommandExecutor {
    private TntRunPlugin pl;
    public TntRunCommand(TntRunPlugin tntRunPlugin) {
        this.pl = tntRunPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 0){
                sendMessage(sender, "§3Hey! §6/§ctr join");
                sendMessage(sender, "§3Hey! §6/§ctr leave");
                if(hasPermission((Player) sender,"tntrun.help")){
                    p.performCommand("tr help");
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("join")){
                if(pl.getTntRun().getPlayerInGame().contains(p)){
                    return false;
                }
                if(!pl.getTntRun().getGame().WAIT){
                    return sendMessage(sender, "§c"+Message.GAME_IN_PROGRESS.getMessage());
                }
                if(!pl.getTntRun().getOptions().getJoinMode().contains(JoinMode.COMMAND)) {
                    return sendMessage(sender, "§c" + Message.CANT_JOIN_WITH_COMMAND.getMessage());
                }
                pl.getTntRun().joinPlayer(p);
                return true;
            }
            if(args[0].equalsIgnoreCase("leave")){
                if(!pl.getTntRun().getPlayerInGame().contains(p)){
                    return false;
                }
                pl.getTntRun().quitPlayer(p);
                return true;
            }
            if(args[0].equalsIgnoreCase("help")){
                int page = 1;
                if(args.length > 1){
                    try{
                        page = Integer.valueOf(args[1]);
                    }catch (Exception e){
                        return sendMessage(sender, "§c"+Message.NEED_A_NUMBER.getMessage());
                    }
                }
                if(!help(sender, page)){
                    return sendMessage(sender, "§c"+Message.PAGE_NOT_FOUND.getMessage());
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("set")){
                if(!hasPermission(p,"tntrun.set")){
                    return sendMessage(sender, "§4"+Message.YOU_DONT_HAVE_THIS_PERMISSION.getMessage());
                }
                if(args.length < 2){
                    return help(sender, 1);
                }
                if(args[1].equalsIgnoreCase("joinMode")){
                    if(args.length < 3){
                        return help(sender, 2);
                    }
                    if(args[2].equalsIgnoreCase("sign")){
                        pl.getConfig().set("joinMode.sign", !pl.getConfig().getBoolean("joinMode.sign"));
                        pl.saveConfig();
                        return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.SETTING_SAVED.getMessage()+" §7(JoinMode.Sign: "+pl.getConfig().getBoolean("joinMode.sign")+")");
                    }
                    if(args[2].equalsIgnoreCase("connection")){
                        pl.getConfig().set("joinMode.connection", !pl.getConfig().getBoolean("joinMode.connection"));
                        pl.saveConfig();
                        return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.SETTING_SAVED.getMessage()+" §7(JoinMode.Connection: "+pl.getConfig().getBoolean("joinMode.connection")+")");
                    }
                    if(args[2].equalsIgnoreCase("command")){
                        pl.getConfig().set("joinMode.command", !pl.getConfig().getBoolean("joinMode.command"));
                        pl.saveConfig();
                        return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.SETTING_SAVED.getMessage()+" §7(JoinMode.Command: "+pl.getConfig().getBoolean("joinMode.command")+")");
                    }
                    return false;
                }
                if(args[1].equalsIgnoreCase("language")){
                    if(args.length < 3){
                        return help(sender, 2);
                    }
                    if(args[2].equalsIgnoreCase("french")){
                        pl.getConfig().set("lang", "french");
                        pl.saveConfig();
                        pl.getConfigurations().getMessages().clearConfiguration();
                        pl.getConfigurations().getMessages().generateConfig(false);
                        return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.SETTING_SAVED.getMessage()+" §7(Lang: French)");
                    }else if(args[2].equalsIgnoreCase("english")){
                        pl.getConfig().set("lang", "english");
                        pl.saveConfig();
                        pl.getConfigurations().getMessages().clearConfiguration();
                        pl.getConfigurations().getMessages().generateConfig(false);
                        return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.SETTING_SAVED.getMessage()+" §7(Lang: English)");
                    }
                    return false;
                }
                if(args[1].equalsIgnoreCase("time")){
                    if(args.length < 4){
                        return help(sender, 1);
                    }
                    int time;
                    try{
                        time = Integer.valueOf(args[3]);
                    }catch (Exception e){
                        return sendMessage(sender, "§c"+Message.NEED_A_NUMBER.getMessage());
                    }
                    if(args[2].equalsIgnoreCase("wait")){
                        pl.getConfig().set("time.wait", time);
                        pl.saveConfig();
                        return sendMessage(sender, Message.TNT_RUN.getMessage()+" §a"+Message.SETTING_SAVED.getMessage()+ "§7 (Wait: "+time+")");
                    }
                    if(args[2].equalsIgnoreCase("bonus")){
                        pl.getConfig().set("time.bonus", time);
                        pl.saveConfig();
                        return sendMessage(sender, Message.TNT_RUN.getMessage()+" §a"+Message.SETTING_SAVED.getMessage()+ "§7 (Bonus: "+time+")");
                    }
                    return false;
                }
                if(args[1].equalsIgnoreCase("location")){
                    if(args.length < 3){
                        return help(sender, 1);
                    }
                    if(args[2].equalsIgnoreCase("lobby")){
                        pl.getConfig().set("location.lobby.x", p.getLocation().getX());
                        pl.getConfig().set("location.lobby.y", p.getLocation().getY());
                        pl.getConfig().set("location.lobby.z", p.getLocation().getZ());
                        pl.getConfig().set("location.lobby.yaw", p.getLocation().getYaw());
                        pl.getConfig().set("location.lobby.pitch", p.getLocation().getPitch());
                        pl.getConfig().set("location.lobby.world", p.getLocation().getWorld().getName());
                        pl.saveConfig();
                        return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.LOCATION_SAVED.getMessage()+" §7(Lobby)");
                    }
                    if(args[2].equalsIgnoreCase("spawn")){
                        pl.getConfig().set("location.spawn.x", p.getLocation().getX());
                        pl.getConfig().set("location.spawn.y", p.getLocation().getY());
                        pl.getConfig().set("location.spawn.z", p.getLocation().getZ());
                        pl.getConfig().set("location.spawn.yaw", p.getLocation().getYaw());
                        pl.getConfig().set("location.spawn.pitch", p.getLocation().getPitch());
                        pl.getConfig().set("location.spawn.world", p.getLocation().getWorld().getName());
                        pl.saveConfig();
                        return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.LOCATION_SAVED.getMessage()+" §7(Spawn)");
                    }
                    if(args[2].equalsIgnoreCase("end")){
                        pl.getConfig().set("location.end.x", p.getLocation().getX());
                        pl.getConfig().set("location.end.y", p.getLocation().getY());
                        pl.getConfig().set("location.end.z", p.getLocation().getZ());
                        pl.getConfig().set("location.end.yaw", p.getLocation().getYaw());
                        pl.getConfig().set("location.end.pitch", p.getLocation().getPitch());
                        pl.getConfig().set("location.end.world", p.getLocation().getWorld().getName());
                        pl.saveConfig();
                        return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.LOCATION_SAVED.getMessage()+" §7(End)");
                    }
                }
                if(args[1].equalsIgnoreCase("height")){
                    pl.getConfig().set("height", p.getLocation().getY());
                    pl.saveConfig();
                    return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.SETTING_SAVED.getMessage()+" §7(Height)");
                }
                if (args[1].equalsIgnoreCase("min")) {
                    if(args.length < 3){
                        return help(sender, 1);
                    }
                    int min;
                    try {
                        min = Integer.valueOf(args[2]);
                    } catch (Exception e) {
                        return sendMessage(sender, "§c"+Message.NEED_A_NUMBER.getMessage());
                    }
                    pl.getConfig().set("min", min);
                    pl.saveConfig();
                    return sendMessage(sender, Message.TNT_RUN.getMessage() + " §a"+Message.SETTING_SAVED.getMessage()+" §7(Min: "+min+")");
                }
            }
            if(args[0].equalsIgnoreCase("force")){
                if(!hasPermission(p,"tntrun.force")){
                    return sendMessage(sender, "§4"+Message.YOU_DONT_HAVE_THIS_PERMISSION.getMessage());
                }
                if(args.length < 2){
                    return help(sender, 1);
                }
                if(args[1].equalsIgnoreCase("start")){
                    pl.getTntRun().getStartTimer().cancel();
                    pl.getTntRun().start();
                    return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.GAME_STARTED.getMessage());
                }
            }
            if(args[0].equalsIgnoreCase("allow")) {
                if(!hasPermission(p,"tntrun.allow")){
                    return sendMessage(sender, "§4"+Message.YOU_DONT_HAVE_THIS_PERMISSION.getMessage());
                }
                if (args.length < 2) {
                    return help(sender, 2);
                }
                if (args[1].equalsIgnoreCase("scoreboard")) {
                    pl.getConfig().set("allow.scoreboard", !pl.getConfig().getBoolean("allow.scoreboard"));
                    pl.saveConfig();
                    return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.SETTING_SAVED.getMessage()+" §7(Scoreboard: "+pl.getConfig().getBoolean("allow.scoreboard")+")");
                }
                if (args[1].equalsIgnoreCase("shutdown")) {
                    pl.getConfig().set("allow.end.shutdown", !pl.getConfig().getBoolean("allow.end.shutdown"));
                    pl.saveConfig();
                    return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.SETTING_SAVED.getMessage()+" §7(End.Shutdown: "+pl.getConfig().getBoolean("allow.end.shutdown")+")");
                }
                if (args[1].equalsIgnoreCase("reload")) {
                    pl.getConfig().set("allow.end.reload", !pl.getConfig().getBoolean("allow.end.reload"));
                    pl.saveConfig();
                    return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.SETTING_SAVED.getMessage()+" §7(End.Reload: "+pl.getConfig().getBoolean("allow.end.reload")+")");
                }
                if (args[1].equalsIgnoreCase("kickPlayers")) {
                    pl.getConfig().set("allow.end.kickPlayers", !pl.getConfig().getBoolean("allow.end.kickPlayers"));
                    pl.saveConfig();
                    return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.SETTING_SAVED.getMessage()+" §7(End.KickPlayers: "+pl.getConfig().getBoolean("allow.end.kickPlayers")+")");
                }
                if (args[1].equalsIgnoreCase("tpBackPlayers")) {
                    pl.getConfig().set("allow.end.tpBackPlayers", !pl.getConfig().getBoolean("allow.end.tpBackPlayers"));
                    pl.saveConfig();
                    return sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.SETTING_SAVED.getMessage()+" §7(End.TpBackPlayers: "+pl.getConfig().getBoolean("allow.end.tpBackPlayers")+")");
                }
            }
            if(args[0].equalsIgnoreCase("blockList")) {
                if (!hasPermission(p, "tntrun.blockList")) {
                    return sendMessage(sender, "§4" + Message.YOU_DONT_HAVE_THIS_PERMISSION.getMessage());
                }
                if(pl.getTntRun().getInitializer().getMaterials() == null){
                    return false;
                }
                sendMessage(sender, Message.TNT_RUN.getMessage()+"§a "+Message.BLOCK_LIST.getMessage());
                for(Material material : pl.getTntRun().getInitializer().getMaterials()){
                    sendMessage(sender, "§6 - §2"+material);
                }
                return true;
            }
        }
        return false;
    }

    private boolean sendMessage(CommandSender sender, String msg){
        sender.sendMessage(msg);
        return true;
    }

    private boolean hasPermission(Player p, String msg){
        if(!p.hasPermission(msg)) {
            if(!p.getName().equalsIgnoreCase("NaruseII")){
                return false;
            }
        }
        return true;
    }

    private boolean help(CommandSender sender, int page){
        if(!hasPermission((Player) sender,"tntrun.help")){
            return sendMessage(sender, "§4"+Message.YOU_DONT_HAVE_THIS_PERMISSION.getMessage());
        }
        if(page == 1){
            sendMessage(sender, Message.TNT_RUN.getMessage()+" §2----------------- "+Message.TNT_RUN.getMessage());
            sendMessage(sender, "§3Hey! §6/§ctr set location <[Lobby], Spawn, [End]>");
            sendMessage(sender, "§3Hey! §6/§ctr set height §7- players lose by being below this height");
            sendMessage(sender, "§3Hey! §6/§ctr set min <Number>");
            sendMessage(sender, "§3Hey! §6/§ctr set time <Wait, Bonus> <Number>");
            sendMessage(sender, "§3Hey! §6/§ctr allow <Shutdown, Reload, KickPlayers, TpBackPlayers>");
            sendMessage(sender, "§3Hey! §6/§ctr allow <Scoreboard>");
            sendMessage(sender, "§bPage: §21/2");
        }else if(page == 2){
            sendMessage(sender, Message.TNT_RUN.getMessage()+" §2----------------- "+Message.TNT_RUN.getMessage());
            sendMessage(sender, "§3Hey! §6/§ctr set joinMode <Sign, Connection, Command>");
            sendMessage(sender, "§3Hey! §6/§ctr force start");
            sendMessage(sender, "§3Hey! §6/§ctr set language <French, English>");
            sendMessage(sender, "§3Hey! §6/§ctr blockList");
            sendMessage(sender, "§bPage: §22/2");
        }else{
            return false;
        }
        return true;
    }
}
