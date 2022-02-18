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

public class report implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 2) return false;

        Player enemy = Bukkit.getPlayer(args[0]);

        if (enemy == null) return false;

        String report_msg = "";

        for (int i = 1; i < args.length; i++) {
            report_msg = report_msg + args[i] + " ";
        }

        Player reporter = Bukkit.getPlayer(sender.getName());

        if (reporter == enemy) {
            reporter.sendMessage("Нельзя зарепортить себя!");
            return true;
        }

        MessageEmbed embed = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("Репорт от " + sender.getName())
                .addField("Информация о " + sender.getName(), "UUID: " + reporter.getUniqueId().toString() +
                        "\nМир: " + reporter.getLocation().getWorld().getName() +
                        "\nHP: " + reporter.getHealth(), false)
                .addField("Репорт на игорока " + enemy.getName(), "UUID: " + enemy.getUniqueId().toString() +
                        "\nМир: " + enemy.getLocation().getWorld().getName() +
                        "\nHP: " + enemy.getHealth(), false)
                .addField("Детали репорта", report_msg, false)
                .build();

        BotLoader.mod.sendMessage("@here").setEmbeds(embed).queue();

        sender.sendMessage("[§eUltima§bPlug§r] Репорт отослан модераторам. Спасибо что стараетесь помочь нам!");

        return true;
    }
}
