package ru.thisistails.ultimaplug.discord.events;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.thisistails.ultimaplug.UltimaPlug;

public class chatToServer extends ListenerAdapter {

    private final UltimaPlug plug;

    public chatToServer(UltimaPlug plug) {
        this.plug = plug;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (UltimaPlug.ready) {
            if (!(event.isFromGuild()) ||
                    event.getMessage().getChannel().getIdLong() != 941006289801216100L ||
                    event.getAuthor().isBot()) return;


            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("§8[§3Discord§8] %sender: §r%message".replace("%sender", event.getMember().getEffectiveName()).replace("%message", event.getMessage().getContentDisplay().replace('&', '§')));
            }

            event.getMessage().addReaction("✅").queue();
        }
    }

}
