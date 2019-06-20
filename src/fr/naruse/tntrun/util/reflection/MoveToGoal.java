package fr.naruse.tntrun.util.reflection;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MoveToGoal {
    private Entity entity;
    private Location location;
    public MoveToGoal(Entity entity, Location location){
        this.entity = entity;
        this.location = location;
    }

    private Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "net.minecraft.server." + version + nmsClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }

    private Class<?> getCBClass(String cbClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "org.bukkit.craftbukkit." + version +cbClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }

    private Object getConnection(Player player) throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method getHandle = player.getClass().getMethod("getHandle");
        Object nmsPlayer = getHandle.invoke(player);
        Field conField = nmsPlayer.getClass().getField("playerConnection");
        Object con = conField.get(nmsPlayer);
        return con;
    }

    public void execute(double speed){
        try {
            Class<?> craftEntityClass = this.getCBClass("entity.CraftEntity");
            Method getHandle = craftEntityClass.getMethod("getHandle");
            Object craftEntity = getHandle.invoke(entity);
            Class<?> craftEntityInsentient = this.getNMSClass("EntityInsentient");
            Object entityInsentient = craftEntityInsentient.cast(craftEntity);
            Method getNavigation = craftEntityInsentient.getMethod("getNavigation");
            Object nav = getNavigation.invoke(entityInsentient);
            Method a = nav.getClass().getMethod("a", double.class, double.class, double.class);
            Object path = a.invoke(nav, location.getX(), location.getY(), location.getZ());
            if(path != null){
                a = nav.getClass().getMethod("a", path.getClass(), double.class);
                a.invoke(nav, path, speed);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
