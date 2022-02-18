package ru.thisistails.ultimaplug;

import org.bukkit.plugin.java.JavaPlugin;
import ru.thisistails.ultimaplug.commands.*;
import ru.thisistails.ultimaplug.discord.BotLoader;
import ru.thisistails.ultimaplug.discord.discord;
import ru.thisistails.ultimaplug.events.ChatFormatter;
import ru.thisistails.ultimaplug.events.welcome;

import java.io.File;

import static org.bukkit.Bukkit.getPluginManager;

public final class UltimaPlug extends JavaPlugin {

    public static String last_update_desc = """
- Добавление команд для админов (ban, mute, punish, kick, unban)
- Добавлена команда §e/cords§r (/help cords)
- Исправлен баг с киком при свежом запуске сервера ([UltimaPlug] Param ready = false! Try connect again.)""";
    public static String version = "2.0";

    public static boolean ready = false; // Если этот параметр вылкючен никто не сможет присоедениться

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("Checking files...");

        File config = new File(getDataFolder() + "/config.yml");

        if (!config.exists()) {
            //saveResource("/config.yml", true);
            config.mkdir();
            saveDefaultConfig();
            getLogger().info("Config created");
        }

        BotLoader.connect(this);

        getPluginManager().registerEvents(new welcome(), this);
        getPluginManager().registerEvents(new ChatFormatter(this), this);
        getCommand("discord").setExecutor(new discord(this));
        getCommand("report").setExecutor(new report());
        getCommand("mute").setExecutor(new mute(this));
        getCommand("unmute").setExecutor(new unmute());
        getCommand("kick").setExecutor(new kick());
        getCommand("ban").setExecutor(new ban());
        getCommand("unban").setExecutor(new unban());
        getCommand("cords").setExecutor(new cords());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
