package ru.thisistails.ultimaplug.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.thisistails.ultimaplug.discord.BotLoader;

import java.awt.*;

public class unmute implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 1)
            return false;

        Player defaulter = Bukkit.getPlayer(args[0]);
        Player admin = null;
        //String rule = args[1];

        if (sender instanceof Player) {
            admin = Bukkit.getPlayer(sender.getName());
        }

        if (defaulter == null) return false;

        if (!mute.muted.containsKey(defaulter.getUniqueId())) {
            admin.sendMessage("[§eUltima§bPlug§r] Игрок §b§l" + defaulter.getName() + "§r не в муте.");
            return true;
        }
        try {
            mute.muted.get(defaulter.getUniqueId()).cancel();
        } catch(NullPointerException ignore) {}

        mute.muted.remove(defaulter.getUniqueId());
        admin.sendMessage("[§eUltima§bPlug§r] Игрок §b§l" + defaulter.getName() + "§r больше не в муте.");

        String mention = "";

        mention = BotLoader.pub.getGuild().getMembersByName(admin.getName(), true).get(0).getAsMention();

        if (mention.isEmpty()) mention = "Неудалось найти админа...";

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Игрок " + defaulter.getName() + " разглушен")
                .setColor(Color.RED)
                .setDescription("Администратор " + admin.getName() + " (" +
                        mention  + ") разглушил игорока " + defaulter.getName())
                .build();

        BotLoader.mod.sendMessageEmbeds(embed).queue();

        return true;
    }
}
