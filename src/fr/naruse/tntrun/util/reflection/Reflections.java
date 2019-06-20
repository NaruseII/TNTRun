package fr.naruse.tntrun.util.reflection;

import com.sk89q.worldedit.bukkit.selections.Selection;
import fr.naruse.tntrun.main.TntRunPlugin;
import net.minecraft.server.v1_12_R1.EnumColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class Reflections {


    private static Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "net.minecraft.server." + version + nmsClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }

    private static Class<?> getCBClass(String cbClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "org.bukkit.craftbukkit." + version +cbClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }

    public static Selection getSelection(TntRunPlugin pl, Player p) {
        try{
            Class worldEditPluginClass = pl.getOtherPluginSupport().getWorldEditPlugin().getWorldEdit().getClass();
            Method getSelectionMethod = worldEditPluginClass.getDeclaredMethod("getSelection", Player.class);
            Selection selection = (Selection) getSelectionMethod.invoke(pl.getOtherPluginSupport().getWorldEditPlugin().getWorldEdit(), p);
            return selection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
