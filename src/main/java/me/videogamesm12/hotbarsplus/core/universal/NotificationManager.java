/*
 * Copyright (c) 2022 Video
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.videogamesm12.hotbarsplus.core.universal;

import com.google.common.eventbus.Subscribe;
import me.videogamesm12.hotbarsplus.api.event.failures.NBackupFailEvent;
import me.videogamesm12.hotbarsplus.api.event.navigation.NHotbarNavigateEvent;
import me.videogamesm12.hotbarsplus.api.event.success.NBackupSuccessEvent;
import me.videogamesm12.hotbarsplus.api.util.Util;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.core.notifications.ActionBarNotification;
import net.kyori.adventure.text.Component;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1></h1>
 */
public class NotificationManager
{
    private final Map<Class<? extends NotificationType>, NotificationType> types = new HashMap<>();
    private final EventListener listener = new EventListener();

    public NotificationManager()
    {
        register(ActionBarNotification.class);
    }

    /**
     * Show a message to all active NotificationTypes.
     * @param title         Component
     * @param description   Component
     * @param miniature     Component
     */
    public void showNotification(Component title, Component description, Component miniature)
    {
        types.values().stream().filter(NotificationType::isEnabled).forEach((type) ->
                type.display(Util.advToNative(title), Util.advToNative(description), Util.advToNative(miniature)));
    }

    /**
     * Registers a NotificationType.
     * @param typeClass A class that implements NotificationType.
     */
    public void register(Class<? extends NotificationType> typeClass)
    {
        try
        {
            types.put(typeClass, typeClass.getDeclaredConstructor().newInstance());
        }
        catch (Exception ex)
        {
            HBPCore.LOGGER.error("Failed to register notification type");
            HBPCore.LOGGER.error(ex);
        }
    }

    public interface NotificationType
    {
        /**
         * Display the notification in-game.
         * @param text  A series of text components to display depending on the type of notification.
         *              The first component is the title, the second component is the description, and the third
         *              component is a miniature combination of the first two.
         */
        void display(Text... text);

        /**
         * Returns the current status of the Notification type.
         * @return  boolean
         */
        boolean isEnabled();
    }

    public class EventListener
    {
        public EventListener()
        {
            HBPCore.EVENTS.register(this);
        }

        @Subscribe
        public void onBackupFailure(NBackupFailEvent event)
        {
            showNotification(
                    Component.translatable("notif.hotbarsplus.backup.failed"),
                    Component.text(event.getException().getLocalizedMessage()),
                    Component.translatable("notif.hotbarsplus.backup.failed.mini",
                            Component.text(event.getException().getClass().getSimpleName()))
            );
        }

        @Subscribe
        public void onBackupSuccess(NBackupSuccessEvent event)
        {
            showNotification(
                    Component.translatable("notif.hotbarsplus.backup.success"),
                    Component.text(event.getTo().getName()),
                    Component.translatable("notif.hotbarsplus.backup.success.mini",
                            Component.text(event.getFrom().getName()),
                            Component.text(event.getTo().getName()))
            );
        }

        @Subscribe
        public void onNavigate(NHotbarNavigateEvent event)
        {
            showNotification(
                    Component.translatable("notif.hotbarsplus.navigation.selected"),
                    Component.text(Util.getHotbarFilename(event.getPage())),
                    Component.translatable("notif.hotbarsplus.navigation.selected.mini",
                            Component.text(Util.getHotbarFilename(event.getPage())))
            );
        }
    }
}
