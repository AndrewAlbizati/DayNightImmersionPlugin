package com.github.AndrewAlbizati;

import com.github.AndrewAlbizati.commands.SetTimezone;
import com.github.AndrewAlbizati.commands.SetTimezoneTab;
import com.github.AndrewAlbizati.events.TimeSkip;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Plugin extends JavaPlugin {
    private String timezone;
    public String getTimezone() {
        return timezone;
    }
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    private final List<String> timezones = new ArrayList();
    public List<String> getTimezones() {
        return timezones;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig(); // Create the config file if it doesn't exist

        // Stops daylight cycle
        for (World world : getServer().getWorlds()) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        }

        // Load timezone names from file
        try {
            InputStream inputStream = Plugin.class.getResourceAsStream("/timezones.txt");

            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";

            for (String timezone : result.split("\\r?\\n")) {
                timezones.add(timezone);
            }
            System.out.println(timezones.size());
        } catch (Exception e) {
            System.out.println("Timezones couldn't be loaded correctly: " + e.getMessage());
        }

        // Get timezone if already assigned
        if (getConfig().contains("timezone")) {
            timezone = getConfig().getString("timezone");
        } else {
            System.out.println("Please provide a timezone by typing /settimezone <time zone>");
        }

        // Register /settimezone command
        getCommand("settimezone").setExecutor(new SetTimezone(this));
        getCommand("settimezone").setTabCompleter(new SetTimezoneTab(this));

        // Register events
        getServer().getPluginManager().registerEvents(new TimeSkip(), this);

        new Runnable(this).runTaskTimer(this, 0L,  1200); // Runs once every minute
    }
}

/**
 * Runnable class that changes the Minecraft world time according to the time in real life.
 */
class Runnable extends BukkitRunnable {
    private final Plugin plugin;
    public Runnable(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.getTimezone() == null) {
            return;
        }

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH mm");

        // Set time zone
        df.setTimeZone(TimeZone.getTimeZone(plugin.getTimezone()));
        String formatted = df.format(date);

        // Convert real life hour to Minecraft time
        int hour = Integer.parseInt(formatted.split(" ")[0]);
        long minecraftTime;

        if (hour == 0) {
            minecraftTime = 18000;
        } else if (hour > 5) {
            minecraftTime = (hour - 6) * 1000L;
        } else {
            minecraftTime = (hour + 18) * 1000L;
        }

        // Convert real life minute to Minecraft time
        int minute = Integer.parseInt(formatted.split(" ")[1]);
        minecraftTime += (long) ((minute / 60.0) * 1000);

        // Set time in all worlds
        for (World world : plugin.getServer().getWorlds()) {
            world.setTime(minecraftTime);
        }
    }
}