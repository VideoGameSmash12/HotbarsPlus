package me.videogamesm12.hotbarsplus.api.event.notification;

import me.videogamesm12.hotbarsplus.core.universal.NotificationManager;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.Arrays;

public interface NotificationTypeRegistered
{
    Event<NotificationTypeRegistered> EVENT = EventFactory.createArrayBacked(NotificationTypeRegistered.class,
            (listeners) -> (type) -> Arrays.stream(listeners).forEach(listener -> listener.onTypeRegistered(type)));

    void onTypeRegistered(NotificationManager.NotificationRoute type);
}
