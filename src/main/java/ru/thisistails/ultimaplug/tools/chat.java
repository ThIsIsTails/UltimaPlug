package ru.thisistails.ultimaplug.tools;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class chat {

    public static void sendAll(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

}
