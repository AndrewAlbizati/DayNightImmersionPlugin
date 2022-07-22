package com.github.AndrewAlbizati.commands;

import com.github.AndrewAlbizati.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Sets the server's timezone.
 * (Only OP players or console can use this command)
 */
public class SetTimezone implements CommandExecutor {
    private final Plugin plugin;
    public SetTimezone(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!label.equalsIgnoreCase("settimezone")) {
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Please provide a valid timezone.");
            return true;
        }

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (!sender.isOp()) {
                p.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is a mistake.");
                return true;
            }
        }

        for (String timezone : plugin.getTimezones()) {
            if (timezone.equalsIgnoreCase(args[0])) {
                plugin.setTimezone(timezone);

                plugin.getConfig().set("timezone", timezone);
                plugin.saveConfig();

                sender.sendMessage(ChatColor.GREEN + "Timezone set to " + timezone + ".");
                return true;
            }
        }

        sender.sendMessage(ChatColor.RED + "Timezone not found.");
        return true;
    }
}