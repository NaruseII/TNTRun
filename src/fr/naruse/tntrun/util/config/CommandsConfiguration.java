package fr.naruse.tntrun.util.config;

import fr.naruse.tntrun.main.TntRunPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class CommandsConfiguration {
    private TntRunPlugin pl;
    private File commandsFile;
    private FileConfiguration commands;
    public CommandsConfiguration(TntRunPlugin tntRunPlugin) {
        this.pl = tntRunPlugin;
        createConfig();
    }

    private void createConfig(){
        commandsFile = new File(pl.getDataFolder(), "commands.yml");
        commands = new YamlConfiguration();
        try {
            if(!commandsFile.exists()){
                commandsFile.createNewFile();
            }
            Reader defConfigStream;
            defConfigStream = new InputStreamReader(pl.getResource("commands.yml"), "UTF8");
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            commands.setDefaults(defConfig);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§3[TntRun] §C There is an error with the configuration Commands.yml. You should perform a reload.");
            e.printStackTrace();
        }
        try{
            commands.load(commandsFile);
        }catch(Exception e){
            e.printStackTrace();
        }
        setDefault();
        saveConfig();
    }

    public void saveConfig(){
        try {
            commands.save(commandsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDefault() {
        commands.set("commands", commands.getStringList("commands"));
    }

    public FileConfiguration getConfig(){
        return this.commands;
    }
}
