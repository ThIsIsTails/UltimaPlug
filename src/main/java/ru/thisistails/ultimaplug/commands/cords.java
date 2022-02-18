package ru.thisistails.ultimaplug.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class cords implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) return false;

        Player friend = Bukkit.getPlayer(args[0]);
        Player player = Bukkit.getPlayer(sender.getName());
        Location loc = player.getLocation();
        if (friend == null || friend == player) return false;

        friend.sendMessage("[§eUltima§bPlug§r] Игрок " + sender.getName() + " отослал вам свои координаты: §b" + loc.getWorld().getName() + " §e" + loc.getBlockX() + "§8x §e" + loc.getBlockY() + "§8y §e" + loc.getBlockZ() + "§8z");
        sender.sendMessage("[§eUltima§bPlug§r] Игроку " + friend.getName() + " отосланы ваши координаты");

        return true;
    }
}
