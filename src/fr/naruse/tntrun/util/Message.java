package fr.naruse.tntrun.util;

import fr.naruse.tntrun.main.TntRunPlugin;

public enum Message {

    TNT_RUN(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("tntRun"), "tntRun"),
    JUMP_BONUS(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("jumpBonus"), "jumpBonus"),
    A_BOMB_ARRIVED(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("bombArrived"), "bombArrived"),
    NEED_A_NUMBER(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("needANumber"), "needANumber"),
    PAGE_NOT_FOUND(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("pageNotFound"), "pageNotFound"),
    LOCATION_SAVED(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("locationSaved"), "locationSaved"),
    SETTING_SAVED(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("settingSaved"), "settingSaved"),
    WHERE_IS_WE(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("whereIsWE"), "whereIsWE"),
    YOU_DONT_HAVE_REGION(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("youDontHaveRegion"), "youDontHaveRegion"),
    MAP_SAVED(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("mapSaved"), "mapSaved"),
    GAME_STARTED(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("gameStarted"), "gameStarted"),
    JOINED_THE_GAME(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("joinedTheGame"), "joinedTheGame"),
    LEFT_THE_GAME(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("leftTheGame"), "leftTheGame"),
    GAME_STARTS(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("gameStarts"), "gameStarts"),
    FELL(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("fell"), "fell"),
    WINS_THIS_GAME(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("wins"), "wins"),
    TIME_REMAINING(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("timeRemaining"), "timeRemaining"),
    PLAYERS(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("players"), "players"),
    GAME_ENDED(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("gameEnded"), "gameEnded"),
    YOU_DONT_HAVE_THIS_PERMISSION(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("youDontHaveThisPermission"), "youDontHaveThisPermission"),
    CANT_JOIN_WITH_COMMAND(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("cantJoinWithCommand"), "cantJoinWithCommand"),
    GAME_IN_PROGRESS(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("gameInProgress"), "gameInProgress"),
    READY(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("ready"), "ready"),
    MISSING(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("missing"), "missing"),
    JOIN(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("join"), "join"),
    IN_GAME(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("inGame"), "inGame"),
    SPECTATORS(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("spectators"), "spectators"),
    BLOCK_LIST(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("blockList"), "blockList"),
    EXCHANGE_BONUS(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("exchangeBonus"), "exchangeBonus"),
    YOU_EXCHANGE_WITH(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("youExchangeWith"), "youExchangeWith"),
    ;

    private String msg;
    private String path;
    Message(String s, String path) {
        this.msg = s.replace("&", "§");
        this.path = path;
    }

    public String getMessage(){
        return msg;
    }

    public String getPath() {
        return path;
    }

    public enum SignColorTag{

        OPEN_WAIT_LINE2(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("signColorTag.open.wait.line2"), "signColorTag.open.wait.line2"),
        OPEN_WAIT_LINE3_0(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("signColorTag.open.wait.line3.ifPlayersSize>=min"), "signColorTag.open.wait.line3.ifPlayersSize>=min"),
        OPEN_WAIT_LINE3_1(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("signColorTag.open.wait.line3.else"), "signColorTag.open.wait.line3.else"),
        OPEN_WAIT_LINE4(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("signColorTag.open.wait.line4"), "signColorTag.open.wait.line4"),
        OPEN_GAME_LINE2(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("signColorTag.open.game.line2"), "signColorTag.open.game.line2"),
        OPEN_GAME_LINE3(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("signColorTag.open.game.line3"), "signColorTag.open.game.line3"),
        OPEN_GAME_LINE4(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("signColorTag.open.game.line4"), "signColorTag.open.game.line4"),
        ;

        private String colorTag;
        private String path;
        SignColorTag(String string, String path) {
            this.colorTag = string.replace("&", "§");
            this.path = path;
        }

        public String getColorTag() {
            return colorTag;
        }

        public String getPath() {
            return path;
        }
    }

    public enum ScoreboardColorTag{

        TIME_RAMAINING_TAG(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("scoreboardColorTag.timeRemainingTag"), "scoreboardColorTag.timeRemainingTag"),
        TIME_TAG(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("scoreboardColorTag.timeTag"), "scoreboardColorTag.timeTag"),
        PLAYERS_TAG(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("scoreboardColorTag.playersTag"), "scoreboardColorTag.playersTag"),
        PLAYERS_COUNT_TAG(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("scoreboardColorTag.playersCountTag"), "scoreboardColorTag.playersCountTag"),
        DISPLAY_NAME_FIRST(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("scoreboardColorTag.displayName.first"), "scoreboardColorTag.displayName.first"),
        DISPLAY_NAME_SECOND(TntRunPlugin.INSTANCE.getConfigurations().getMessages().getConfig().getString("scoreboardColorTag.displayName.second"), "scoreboardColorTag.displayName.second"),
        ;

        private String colorTag;
        private String path;
        ScoreboardColorTag(String string, String path) {
            this.colorTag = string.replace("&", "§");
            this.path = path;
        }

        public String getColorTag() {
            return colorTag;
        }

        public String getPath() {
            return path;
        }
    }
}
