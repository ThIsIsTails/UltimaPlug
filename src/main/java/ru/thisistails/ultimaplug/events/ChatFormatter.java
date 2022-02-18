package ru.thisistails.ultimaplug.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.thisistails.ultimaplug.UltimaPlug;
import ru.thisistails.ultimaplug.commands.mute;
import ru.thisistails.ultimaplug.discord.BotLoader;

import java.util.Objects;

public class ChatFormatter implements Listener {

    private final UltimaPlug plug;
    private final FileConfiguration config;

    public ChatFormatter(UltimaPlug plug) {
        this.plug = plug;
        this.config = plug.getConfig();
    }

    @EventHandler
    public void formatter(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();
        double l_areal = config.getDouble("chat.local-chat-range"); // локальная арена в блоках

        if (message.startsWith(Objects.requireNonNull(config.getString("chat.global-prefix")))) {

                if (mute.muted.containsKey(event.getPlayer().getUniqueId())) {
                    event.getPlayer().sendMessage("[" + ChatColor.AQUA + "Г" + ChatColor.RESET + "] " + player.getName() + ": " + new StringBuilder(message).deleteCharAt(0).toString().replace('&', '§'));
                    event.setCancelled(true);
                    if(UltimaPlug.ready) {
                        MessageEmbed embed = new EmbedBuilder()
                                .setAuthor(player.getName() + " [заглушён]")
                                .setColor(0x3b0099)
                                .setDescription("[Глобальный чат]: " + new StringBuilder(message).deleteCharAt(0))
                                .build();

                        //BotLoader.pub.sendMessageEmbeds(embed).queue();
                        BotLoader.mod.sendMessageEmbeds(embed).queue();
                    }
                } else {
                    event.setFormat("[" + ChatColor.AQUA + "Г" + ChatColor.RESET + "] " + player.getName() + ": " + new StringBuilder(message).deleteCharAt(0).toString().replace('&', '§'));

                    if(UltimaPlug.ready) {
                        MessageEmbed embed = new EmbedBuilder()
                                .setAuthor(player.getName())
                                .setColor(0x3b0099)
                                .setDescription("[Глобальный чат]: " + new StringBuilder(message).deleteCharAt(0))
                                .build();

                        BotLoader.pub.sendMessageEmbeds(embed).queue();
                        BotLoader.mod.sendMessageEmbeds(embed).queue();
                    }
                }
            } if(!message.startsWith(Objects.requireNonNull(config.getString("chat.global-prefix")))) {

            event.setCancelled(true);

            StringBuilder heard = new StringBuilder(); // Игороки которые услышали сообщение.

            if (!mute.muted.containsKey(player.getUniqueId())){
                for (Player p : plug.getServer().getOnlinePlayers()) {
                    if (player.getLocation().distanceSquared(p.getLocation()) <= l_areal && p != player) {
                        p.sendMessage("[" + ChatColor.GREEN + "Л" + ChatColor.RESET + "] " + player.getName() + ": " + message.replace('&', '§'));
                        heard.append(" ").append(p.getName());
                    }
                }
            }

            player.sendMessage("[" + ChatColor.GREEN + "Л" + ChatColor.RESET + "] " + player.getName() + ": " + message.replace('&', '§'));

            if(heard.length() == 0) {
                event.getPlayer().sendMessage("§c§lНо никто не услышал...");
                heard = new StringBuilder("Никто не слышал этого сообщения...");
            }

            if(UltimaPlug.ready) {
                if (!mute.muted.containsKey(player.getUniqueId())) {
                    MessageEmbed embed = new EmbedBuilder()
                            .setAuthor(player.getName())
                            .setColor(0x3b0099)
                            .setDescription("[Локальный чат]: " + message)
                            .addField("Услышали", heard.toString(), false)
                            .build();

                    BotLoader.mod.sendMessageEmbeds(embed).queue();
                } else {
                    MessageEmbed embed = new EmbedBuilder()
                            .setAuthor(player.getName() + " [заглушён]")
                            .setColor(0x3b0099)
                            .setDescription("[Локальный чат]: " + message)
                            .addField("Услышали", "Никто, т.к заглушён", false)
                            .build();

                    BotLoader.mod.sendMessageEmbeds(embed).queue();
                }
            }
        }

}
    }
