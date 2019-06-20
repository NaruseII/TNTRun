package fr.naruse.tntrun.util.config;

import fr.naruse.tntrun.main.TntRunPlugin;
import fr.naruse.tntrun.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class MessagesConfiguration {
    private TntRunPlugin pl;
    private File messagesFile;
    private FileConfiguration messages;
    public MessagesConfiguration(TntRunPlugin tntRunPlugin) {
        this.pl = tntRunPlugin;
        createConfig(false);
    }

    private void createConfig(boolean empty){
        messagesFile = new File(pl.getDataFolder(), "messages.yml");
        messages = new YamlConfiguration();
        try{
            if(!messagesFile.exists()){
                messagesFile.createNewFile();
            }
            if(!empty){
                Reader defConfigStream;
                defConfigStream = new InputStreamReader(pl.getResource(langFileName()), "UTF8");
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                messages.setDefaults(defConfig);
            }
        } catch (UnsupportedEncodingException e) {
            Bukkit.getConsoleSender().sendMessage("§3[TntRun] §cThere is an error with the configuration Messages.yml. You should perform a reload.");
            e.printStackTrace();
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§3[TntRun] §cThere is an error with the configuration Messages.yml. You should perform a reload.");
            e.printStackTrace();
        }
        try{
            messages.load(messagesFile);
        }catch(Exception e){
            e.printStackTrace();
        }
        saveConfig();
        Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
            @Override
            public void run() {
                setDefault();
            }
        },1);
    }

    public void saveConfig(){
        try {
            messages.save(messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String langFileName(){
        if(pl.getConfig().getString("lang").equalsIgnoreCase("french")){
            return "languages/french.yml";
        }
        return "languages/english.yml";
    }

    public FileConfiguration getConfig(){
        return this.messages;
    }

    private void setDefault() {
        for(Message msg : Message.values()){
            messages.set(msg.getPath(), messages.getString(msg.getPath()));
        }
        for(Message.SignColorTag sct : Message.SignColorTag.values()){
            messages.set(sct.getPath(), messages.getString(sct.getPath()));
        }
        for(Message.ScoreboardColorTag sct : Message.ScoreboardColorTag.values()){
            messages.set(sct.getPath(), messages.getString(sct.getPath()));
        }
        saveConfig();
    }

    public void clearConfiguration(){
        messagesFile.delete();
    }

    public void generateConfig(boolean empty){
        createConfig(empty);
    }
}
