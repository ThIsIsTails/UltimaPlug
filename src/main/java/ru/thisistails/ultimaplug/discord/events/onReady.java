package ru.thisistails.ultimaplug.discord.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import ru.thisistails.ultimaplug.UltimaPlug;
import ru.thisistails.ultimaplug.discord.BotLoader;

import java.awt.*;

public class onReady extends ListenerAdapter {

    private final UltimaPlug plug;

    public onReady(UltimaPlug plug) {
        this.plug = plug;
    }

    @Override
    public void onReady(ReadyEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Подключено")
                .setColor(Color.GREEN)
                .addField("SDK", Runtime.version().toString(), false)
                .setDescription("Бот подключен и готов к работе.")
                .setFooter("UltimaPlug by ThIsIsTails | ПОДКЛЮЧЕНО")
                .build();

        UltimaPlug.ready = true;

        BotLoader.getClient().getPresence().setPresence(OnlineStatus.IDLE, Activity.listening("Онлайн: " + Bukkit.getOnlinePlayers().size()), false);

        BotLoader.pub = event.getJDA().getTextChannelById(plug.getConfig().getLong("discord.pub-channel"));
        BotLoader.mod = event.getJDA().getTextChannelById(plug.getConfig().getLong("discord.mod-channel"));

        //BotLoader.pub.sendMessageEmbeds(embed).queue();
        BotLoader.mod.sendMessageEmbeds(embed).queue();
    }

}
