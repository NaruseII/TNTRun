package fr.naruse.tntrun.util;

import com.google.common.collect.Lists;
import fr.naruse.tntrun.main.TntRunPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.List;

public class Utils {
    public static List<Block> blocksFromTwoPoints(Location loc1, Location loc2) {
        List<Block> blocks = Lists.newArrayList();
        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++) {
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }

    public static List<Block> getCircle(Location center, int r){
        final List<Block> list = Lists.newArrayList();
        for(double x = -r; x <= r; x++){
            for(double z = -r; z <= r; z++){
                if((int) center.clone().add(x, 0, z).distance(center) == r){
                    list.add(center.clone().add(x, 0, z).getBlock());
                }
            }
        }
        return list;
    }

    private static HashMap<Block, Material> materialHashMap = new HashMap<>();
    private static HashMap<Block, Byte> dataHashMap = new HashMap<>();
    public static void setAir(Location loc) {
        if(!TntRunPlugin.INSTANCE.getTntRun().getInitializer().getMaterials().contains(loc.getBlock().getType())){
            return;
        }
        loc = loc.add(0, -2, 0);
        for(int i = 0; i < 4; i++){
            materialHashMap.put(loc.getBlock(), loc.getBlock().getType());
            dataHashMap.put(loc.getBlock(), loc.getBlock().getData());
            loc.getBlock().setType(Material.AIR);
            loc = loc.add(0, 1, 0);
        }
    }

    public static void regen(TntRunPlugin tntRunPlugin) {
        for(Block block : materialHashMap.keySet()){
            block.setType(materialHashMap.get(block));
            block.setData(dataHashMap.get(block));
        }
        materialHashMap.clear();
        dataHashMap.clear();
    }
}
