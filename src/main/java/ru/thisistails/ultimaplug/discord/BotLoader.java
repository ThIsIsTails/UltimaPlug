package ru.thisistails.ultimaplug.discord;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import ru.thisistails.ultimaplug.UltimaPlug;
import ru.thisistails.ultimaplug.discord.events.chatToServer;
import ru.thisistails.ultimaplug.discord.events.onReady;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class BotLoader {

    private static JDA client;
    public static TextChannel pub = null;
    public static TextChannel mod = null;

    protected static void reconnect(UltimaPlug plugin) {

        UltimaPlug.ready = false;

        plugin.getLogger().info("Reconnecting bot...");

        client.shutdownNow();

        try {
            plugin.getLogger().info("Connecting...");
            client = JDABuilder
                    .createDefault(plugin.getConfig().getString("discord.token"), EnumSet.allOf(GatewayIntent.class))
                    .build();
            client.addEventListener(new onReady(plugin));
            client.addEventListener(new chatToServer(plugin));
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

    public static void connect(UltimaPlug plugin) {

        try {
            plugin.getLogger().info("Connecting...");
            client = JDABuilder
                    .createDefault(plugin.getConfig().getString("discord.token"), EnumSet.allOf(GatewayIntent.class))
                    .build();
            client.addEventListener(new onReady(plugin));
            client.addEventListener(new chatToServer(plugin));
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

    public static JDA getClient() {
        return client;
    }
}
