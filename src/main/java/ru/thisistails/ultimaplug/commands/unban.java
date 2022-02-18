package ru.thisistails.ultimaplug.commands;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class unban implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 1) return false;

        Bukkit.getBanList(BanList.Type.NAME).pardon(args[0]);

        sender.sendMessage("[§eUltima§bPlug§r] Игрок §c§l" + args[0] + "§r был разбанен");

        return true;
    }
}
