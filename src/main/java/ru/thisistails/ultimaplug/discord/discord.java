package ru.thisistails.ultimaplug.discord;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.thisistails.ultimaplug.UltimaPlug;

public class discord implements CommandExecutor {

    private final UltimaPlug plug;

    public discord(UltimaPlug plug) {
        this.plug = plug;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0)
            return false;

        if (args[0].equals("help")) {

            sender.sendMessage(
                    """
§8---------------->
§bhelp§r - Показывает этот список
§breload§r - Перезапуск бота
§8---------------->"""
            );

        }

        if (args[0].equals("reload")) {
            BotLoader.reconnect(plug);
            sender.sendMessage("§aПерезапуск бота...");
        }

        return true;
    }
}
