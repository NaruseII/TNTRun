package fr.naruse.tntrun.event;

import fr.naruse.tntrun.game.TntRun;
import fr.naruse.tntrun.util.Message;
import fr.naruse.tntrun.util.option.JoinMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

import java.util.HashMap;
import java.util.List;

public class Listeners implements Listener {
    private TntRun pl;
    private HashMap<Player, Location> locationHashMap = new HashMap<>();
    public Listeners(TntRun tntRun) {
        this.pl = tntRun;
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(!pl.getOptions().getJoinMode().contains(JoinMode.CONNECTION)){
            return;
        }
        pl.joinPlayer(p);
    }

    @EventHandler
    public void interact(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getClickedBlock() == null){
            return;
        }
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK){
            if(hasPermission(p, "tntrun.sign.break")){
                return;
            }
        }
        if(!(e.getClickedBlock().getState() instanceof Sign)){
            return;
        }
        Sign sign  = (Sign) e.getClickedBlock().getState();
        if(sign.getLine(0).equalsIgnoreCase(Message.TNT_RUN.getMessage())){
            if(pl.getOptions().getJoinMode().contains(JoinMode.SIGN)){
                pl.joinPlayer(p);
                e.setCancelled(true);
                return;
            }
        }
        if(!hasPermission(p, "tntrun.sign.create")){
            return;
        }
        if(sign.getLine(0).equalsIgnoreCase("-!t!-")  && sign.getLine(3).equalsIgnoreCase("-!t!-")){
            if(sign.getLine(1).equalsIgnoreCase(sign.getLine(2))){
                sign.setLine(0, "§6§l[§5TntRun§6§l]");
                sign.update();
                pl.getSignManager().registerNewSigns(p.getWorld());
                return;
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void command(PlayerCommandPreprocessEvent e){
        if(pl.getPlayerInGame().contains(e.getPlayer())){
            List<String> commands = pl.getMain().getConfigurations().getCommands().getConfig().getStringList("commands");
            if(commands.contains(e.getMessage().split(" ")[0].replace("/", ""))){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void damage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)){
            return;
        }
        Player p = (Player) e.getEntity();
        if(pl.getPlayerInGame().contains(p) || pl.getSpectator().contains(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        pl.quitPlayer(p);
        pl.sendMessage("§6"+p.getName()+"§c "+Message.LEFT_THE_GAME.getMessage());
    }

    @EventHandler
    public void place(BlockPlaceEvent e){
        if(pl.getPlayerInGame().contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e){
        if(pl.getPlayerInGame().contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e){
        if(pl.getPlayerInGame().contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void pickUp(PlayerPickupItemEvent e){
        if(pl.getPlayerInGame().contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void change(EntityChangeBlockEvent e){
        if(e.getEntity().getType() == EntityType.FALLING_BLOCK){
            e.getEntity().remove();
            e.getBlock().setType(Material.AIR);
            e.setCancelled(true);
        }
    }

    public Location getFirstPlayerLocation(Player p){
        if(locationHashMap.containsKey(p)){
            return locationHashMap.get(p);
        }
        return null;
    }

    private boolean hasPermission(Player p, String msg){
        if(!p.hasPermission(msg)) {
            if(!p.getName().equalsIgnoreCase("NaruseII")){
                return false;
            }
        }
        return true;
    }

    public HashMap<Player, Location> getLocationHashMap() {
        return locationHashMap;
    }
}
