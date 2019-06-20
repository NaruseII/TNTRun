package fr.naruse.tntrun.bonus;

import com.google.common.collect.Lists;
import fr.naruse.tntrun.bonus.type.ExchangeBonus;
import fr.naruse.tntrun.bonus.type.HoleBonus;
import fr.naruse.tntrun.bonus.type.JumpBonus;
import fr.naruse.tntrun.game.TntRun;
import fr.naruse.tntrun.main.TntRunPlugin;
import org.bukkit.Location;

import java.util.List;
import java.util.Random;

public enum BonusType {

    HOLE("hole"),
    JUMP("jump"),
    EXCHANGE("exchange"),
    ;

    public static List<BonusType> types = Lists.newArrayList();
    static {
        for(BonusType bonusType : BonusType.values()){
            if(TntRunPlugin.INSTANCE.getConfig().getBoolean("bonus."+bonusType.getName()+".enable")){
                types.add(bonusType);
            }
        }
    }
    private String name;
    BonusType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BonusType random(){
        return types.get(new Random().nextInt(types.size()));
    }

    public Bonus build(TntRun pl, Location loc){
        switch (this){
            case HOLE: return new HoleBonus(pl, loc);
            case JUMP: return new JumpBonus(pl, loc);
            case EXCHANGE: return new ExchangeBonus(pl, loc);
        }
       return null;
    }
}
