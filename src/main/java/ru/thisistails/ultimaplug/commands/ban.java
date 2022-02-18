package ru.thisistails.ultimaplug.commands;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.thisistails.ultimaplug.tools.arguments;

public class ban implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 2) return false;

        Player defaulter = Bukkit.getPlayer(args[0]);

        String message = arguments.getMessageFromArgs(args, 1);

        Bukkit.getBanList(BanList.Type.NAME).addBan(args[0], "§c§lЗАБАНЕН§r\nМодератор: " + sender.getName() + "\nПричина: " + message, null, null);
        if (defaulter != null) {
            defaulter.kickPlayer(message);
        }

        sender.sendMessage("[§eUltima§bPlug§r] Игрок §c§l" + args[0] + "§r забанен");

        return true;
    }
}
