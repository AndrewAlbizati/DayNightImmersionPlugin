package com.github.AndrewAlbizati.commands;

import com.github.AndrewAlbizati.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetTimezoneTab implements TabCompleter {
    private final Plugin plugin;
    public SetTimezoneTab(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        if (!p.isOp()) {
            return null;
        }

        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            for(String a : plugin.getTimezones()) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(a);
                }
            }
            return result;
        }

        return null;
    }
}