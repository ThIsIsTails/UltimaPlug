package ru.thisistails.ultimaplug.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.thisistails.ultimaplug.tools.arguments;

public class kick implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 2) return false;

        Player defaulter = Bukkit.getPlayer(args[0]);

        if (defaulter == null) {
            return false;
        }

        String message = arguments.getMessageFromArgs(args, 1);

        defaulter.kickPlayer(message);

        return true;
    }
}
