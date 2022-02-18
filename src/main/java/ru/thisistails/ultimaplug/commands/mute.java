package ru.thisistails.ultimaplug.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import ru.thisistails.ultimaplug.UltimaPlug;
import ru.thisistails.ultimaplug.discord.BotLoader;
import ru.thisistails.ultimaplug.tools.arguments;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class mute implements CommandExecutor {

    private final UltimaPlug plug;
    public static HashMap<UUID, BukkitTask> muted = new HashMap<>();

    public mute(UltimaPlug plug) {
        this.plug = plug;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length > 3)
            return false;

        Player defaulter = Bukkit.getPlayer(args[0]);
        Player admin = null;
        String rule = args[1];

        if (sender instanceof Player) {
            admin = Bukkit.getPlayer(sender.getName());

            if (defaulter == admin) {
                admin.sendMessage("Вы не можете замутить себя.");
                return false;
            }
        }

        if (defaulter == null) return false;

        if (muted.containsKey(defaulter.getUniqueId())) {
            admin.sendMessage("[§eUltima§bPlug§r] Игрок §c§l" + defaulter.getName() + "§r уже в муте.");
            return true;
        }

        if (args[2].equals("-f")) {
            muted.put(defaulter.getUniqueId(), null);
            admin.sendMessage("[§eUltima§bPlug§r] Игрок §c§l" + defaulter.getName() + "§r заглушен навечно по правилу " + rule + ".");
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Игрок " + defaulter.getName() + " заглушён")
                    .setColor(Color.RED)
                    .setDescription("Администратор " + admin.getName() + " (" +
                            BotLoader.pub.getGuild().getMembersByName(admin.getName(), true) + ") заглушил игорока " + defaulter.getName() +
                            " по причине (правилу) " + rule + " навечно.")
                    .build();

            BotLoader.mod.sendMessageEmbeds(embed).queue();

        } else {
            int days = 0;
            int minutes = 0;

            if(args[2].startsWith("d")) {
                days = Integer.parseInt(args[2].split(":")[1]);
            } else if (args[2].startsWith("m")) {
                minutes = Integer.parseInt(args[2].split(":")[1]);
            } else {
                return false;
            }

            if (args.length == 4) {
                if(args[3].startsWith("d")) {
                    days = Integer.parseInt(args[3].split(":")[1]);
                } else if (args[3].startsWith("m")) {
                    minutes = Integer.parseInt(args[3].split(":")[1]);
                } else {
                    return false;
                }
            }

            long seconds = (days * 3600L) + (minutes * 60L);

            admin.sendMessage("[§eUltima§bPlug§r] Игрок §c§l" + defaulter.getName() + "§r был заглушён на " + seconds + "с");

            String mention = "";

            mention = BotLoader.pub.getGuild().getMembersByName(admin.getName(), true).get(0).getAsMention();

            if (mention.isEmpty()) mention = "Неудалось найти админа...";

            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Игрок " + defaulter.getName() + " заглушён")
                    .setColor(Color.RED)
                    .setDescription("Администратор " + admin.getName() + " (" +
                            mention  + ") заглушил игорока " + defaulter.getName() +
                            " по причине (правилу) " + rule + " на " + seconds + " секунд.")
                    .build();

            BotLoader.mod.sendMessageEmbeds(embed).queue();

            BukkitTask task = Bukkit.getScheduler().runTaskLater(plug, new Runnable() {
                @Override
                public void run() {
                    MessageEmbed embed = new EmbedBuilder()
                            .setTitle("Игрок " + defaulter.getName() + " разглушен")
                            .setDescription("Теперь " + defaulter.getName() + " может писать в чат.")
                            .setColor(Color.GRAY)
                            .build();

                    BotLoader.mod.sendMessageEmbeds(embed).queue();
                    muted.remove(defaulter.getUniqueId());
                }
            }, seconds * 20);

            muted.put(defaulter.getUniqueId(), task);
        }

        return true;
    }
}
