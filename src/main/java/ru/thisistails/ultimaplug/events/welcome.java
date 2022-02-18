package ru.thisistails.ultimaplug.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.thisistails.ultimaplug.UltimaPlug;
import ru.thisistails.ultimaplug.discord.BotLoader;

import java.awt.*;
import java.util.Random;

public class welcome implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {

        String[] messages = {
                "§e%player запрыгивает на сервер!",
                "§e%player только что вернулся!",
                "§e%player появился!",
                "§e%player телепортировался прямо на сервер!",
                "§e%player появился! Не забыл ли он подарки?",
                "§eДикий %player появился!"
        };

        Random random = new Random();

        event.setJoinMessage(messages[random.nextInt(messages.length)]
                .replace("%player", event.getPlayer().getName()));

        event.getPlayer().sendMessage(
                """
§8-------------------------------
§rДобро пожаловать, §b%player§r!

Коротко об обновлении %version:
%update

Удачного дня!
§8-------------------------------"""
                        .replace("%player", event.getPlayer().getName())
                        .replace("%update", UltimaPlug.last_update_desc)
                        .replace("%version", UltimaPlug.version)
        );

        MessageEmbed embed = new EmbedBuilder()
                .setAuthor("Игрок " +
                        event.getPlayer().getName() + " подключился [" + event.getPlayer().getServer().getOnlinePlayers().size() +
                        "/20]")
                .setColor(Color.YELLOW)
                .build();

        BotLoader.getClient().getPresence().setPresence(OnlineStatus.IDLE, Activity.listening("Онлайн: " + event.getPlayer().getServer().getOnlinePlayers().size()), false);

        BotLoader.mod.sendMessageEmbeds(embed).queue();
        BotLoader.pub.sendMessageEmbeds(embed).queue();

    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        String[] messages = {
                "§e%player упрыгал с сервера!",
                "§e%player ушёл, но обещал вернуться!",
                "§e%player исчез!",
                "§e%player телепортировался пить чай!",
                "§e%player ушёл! Не забудет ли он принести подарки?",
                "§eДикий %player скрылся!"
        };

        Random random = new Random();

        event.setQuitMessage(messages[random.nextInt(messages.length)]
                .replace("%player", event.getPlayer().getName()));

        MessageEmbed embed = new EmbedBuilder()
                .setAuthor("Игрок " +
                        event.getPlayer().getName() + " отключился [" + (event.getPlayer().getServer().getOnlinePlayers().size()-1) +
                        "/20]")
                .setColor(Color.YELLOW)
                .build();

        BotLoader.getClient().getPresence().setPresence(OnlineStatus.IDLE, Activity.listening("Онлайн: " + (event.getPlayer().getServer().getOnlinePlayers().size()-1)), false);
        BotLoader.mod.sendMessageEmbeds(embed).queue();
        BotLoader.pub.sendMessageEmbeds(embed).queue();
    }

    @EventHandler
    public void check(AsyncPlayerPreLoginEvent event) {
        if (!UltimaPlug.ready) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "[UltimaPlug] Param readyn = false! Try conect again.");
        } else event.allow();
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        event.setLeaveMessage("§e" +event.getPlayer().getName() + " был кикнут по причине " + event.getReason());

        MessageEmbed embed = new EmbedBuilder()
                .setAuthor("Игрок " +
                        event.getPlayer().getName() + " кикнут по причине " + event.getReason() +" [" + (event.getPlayer().getServer().getOnlinePlayers().size()-1) +
                        "/20]")
                .setColor(Color.YELLOW)
                .build();

        BotLoader.getClient().getPresence().setPresence(OnlineStatus.IDLE, Activity.listening("Онлайн: " + (event.getPlayer().getServer().getOnlinePlayers().size()-1)), false);
        BotLoader.mod.sendMessageEmbeds(embed).queue();
        BotLoader.pub.sendMessageEmbeds(embed).queue();
    }

}
