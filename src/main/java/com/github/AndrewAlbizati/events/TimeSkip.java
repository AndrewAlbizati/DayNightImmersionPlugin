package com.github.AndrewAlbizati.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

/**
 * Runs every time night is skipped by players sleeping in beds.
 */
public class TimeSkip implements Listener {
    @EventHandler
    public void onTimeSkip(TimeSkipEvent e) {
        if (e.getSkipReason().equals(TimeSkipEvent.SkipReason.NIGHT_SKIP)) {
            e.setCancelled(true);
        }
    }
}