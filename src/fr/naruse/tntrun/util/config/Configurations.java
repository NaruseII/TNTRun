package fr.naruse.tntrun.util.config;

import fr.naruse.tntrun.main.TntRunPlugin;

public class Configurations {
    private MessagesConfiguration messages;
    private CommandsConfiguration commands;
    public Configurations(TntRunPlugin pl){
        this.messages = new MessagesConfiguration(pl);
        this.commands = new CommandsConfiguration(pl);
        setDefault(pl);
    }

    private void setDefault(TntRunPlugin pl) {
        pl.getConfig().set("min", pl.getConfig().getInt("min"));
        pl.getConfig().set("lang", pl.getConfig().getString("lang"));
        pl.getConfig().set("rewards.win", pl.getConfig().getDouble("rewards.win"));
        pl.getConfig().set("rewards.lose", pl.getConfig().getDouble("rewards.lose"));
        pl.getConfig().set("rewards.command", pl.getConfig().getString("rewards.command"));
        pl.getConfig().set("time.bonus", pl.getConfig().getInt("time.bonus"));
        pl.getConfig().set("time.wait", pl.getConfig().getInt("time.wait"));
        pl.getConfig().set("allow.end.shutdown", pl.getConfig().getBoolean("allow.end.shutdown"));
        pl.getConfig().set("allow.end.reload", pl.getConfig().getBoolean("allow.end.reload"));
        pl.getConfig().set("allow.end.restart", pl.getConfig().getBoolean("allow.end.restart"));
        pl.getConfig().set("allow.end.kickPlayers", pl.getConfig().getBoolean("allow.end.kickPlayers"));
        pl.getConfig().set("allow.end.tpBackPlayers", pl.getConfig().getBoolean("allow.end.tpBackPlayers"));
        pl.getConfig().set("block", pl.getConfig().getStringList("block"));
        pl.getConfig().set("allow.scoreboard", pl.getConfig().getBoolean("allow.scoreboard"));
        pl.getConfig().set("joinMode.sign", pl.getConfig().getBoolean("joinMode.sign"));
        pl.getConfig().set("joinMode.connection", pl.getConfig().getBoolean("joinMode.connection"));
        pl.getConfig().set("joinMode.command", pl.getConfig().getBoolean("joinMode.command"));
        pl.getConfig().set("bonus.hole.enable", pl.getConfig().getBoolean("bonus.hole.enable"));
        pl.getConfig().set("bonus.jump.enable", pl.getConfig().getBoolean("bonus.jump.enable"));
        pl.getConfig().set("bonus.exchange.enable", pl.getConfig().getBoolean("bonus.exchange.enable"));
        pl.saveConfig();
    }

    public MessagesConfiguration getMessages() {
        return messages;
    }

    public CommandsConfiguration getCommands() {
        return commands;
    }
}
