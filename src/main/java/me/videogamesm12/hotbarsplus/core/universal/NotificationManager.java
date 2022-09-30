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

import me.videogamesm12.hotbarsplus.api.event.failures.BackupFailEvent;
import me.videogamesm12.hotbarsplus.api.event.navigation.HotbarNavigateEvent;
import me.videogamesm12.hotbarsplus.api.event.notification.NotificationTypeRegistered;
import me.videogamesm12.hotbarsplus.api.event.success.BackupSuccessEvent;
import me.videogamesm12.hotbarsplus.api.util.Util;
import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.core.notifications.ActionBarNotification;
import net.kyori.adventure.text.Component;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.math.BigInteger;
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
        // Don't show notifications if they are disabled
        if (!HBPCore.UCL.getConfig().getNotificationConfig().isEnabled())
            return;

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
            NotificationType type = typeClass.getDeclaredConstructor().newInstance();
            types.put(typeClass, type);
            //--
            NotificationTypeRegistered.EVENT.invoker().onTypeRegistered(type);
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
         * Returns an ID unique to this notification type.
         * @return  String
         */
        @NotNull
        Identifier getId();

        /**
         * Returns the current status of the Notification type.
         * @return  boolean
         */
        default boolean isEnabled()
        {
            return HBPCore.UCL.getConfig().getNotificationConfig().isTypeEnabled(getId());
        }
    }

    public class EventListener implements BackupFailEvent, BackupSuccessEvent, HotbarNavigateEvent
    {
        public EventListener()
        {
            BackupFailEvent.EVENT.register(this);
            BackupSuccessEvent.EVENT.register(this);
            HotbarNavigateEvent.EVENT.register(this);
        }

        @Override
        public ActionResult onNavigate(BigInteger page)
        {
            showNotification(
                    Component.translatable("notif.hotbarsplus.navigation.selected"),
                    Component.text(Util.getHotbarFilename(page)),
                    Component.translatable("notif.hotbarsplus.navigation.selected.mini",
                            Component.text(Util.getHotbarFilename(page)))
            );

            return ActionResult.PASS;
        }

        /********** FAILURES **********/

        @Override
        public ActionResult onBackupFailure(Exception ex)
        {
            showNotification(
                    Component.translatable("notif.hotbarsplus.backup.failed"),
                    Component.text(ex.getLocalizedMessage()),
                    Component.translatable("notif.hotbarsplus.backup.failed.mini",
                            Component.text(ex.getClass().getSimpleName()))
            );

            return ActionResult.PASS;
        }

        /********** SUCCESSES **********/

        @Override
        public ActionResult onBackupSuccess(File from, File to)
        {
            showNotification(
                    Component.translatable("notif.hotbarsplus.backup.success"),
                    Component.text(to.getName()),
                    Component.translatable("notif.hotbarsplus.backup.success.mini",
                            Component.text(from.getName()),
                            Component.text(to.getName()))
            );
            return ActionResult.PASS;
        }
    }
}
