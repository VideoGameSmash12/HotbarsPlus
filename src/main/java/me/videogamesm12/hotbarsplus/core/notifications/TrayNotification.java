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

package me.videogamesm12.hotbarsplus.core.notifications;

import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.core.universal.NotificationManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TrayNotification implements NotificationManager.NotificationRoute
{
    private SystemTray tray = null;
    private TrayIcon icon = null;

    public TrayNotification()
    {
        if (!isEnabled())
        {
            return;
        }

        setup();
    }

    public void setup()
    {
        try
        {
            // Disables headless mode if enabled.
            System.setProperty("java.awt.headless", "false");

            // Sets up the tray
            tray = SystemTray.getSystemTray();
            icon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(
                    HBPCore.class.getClassLoader().getResource("assets/hotbarsplus/icon.png")), "Hotbars+");
            icon.setImageAutoSize(true);
            icon.setPopupMenu(new HPopupMenu());
            tray.add(icon);
        }
        catch (Exception ex)
        {
            HBPCore.LOGGER.error("Failed to set up the System Tray integration");
            HBPCore.LOGGER.error(ex);
        }
    }

    @Override
    public void display(NotificationManager.NotificationType type, Text... text)
    {
        icon.displayMessage(text[0].getString(), text[1].getString(), TrayIcon.MessageType.INFO);
    }

    @Override
    public @NotNull Identifier getId()
    {
        return new Identifier("hotbarsplus", "systemtray");
    }

    @Override
    public boolean isEnabled()
    {
        return HBPCore.UCL.getConfig().getIntegrationConfig().isTrayIntegrationEnabled()
                && HBPCore.UCL.getConfig().getNotificationConfig().isTypeEnabled(getId());
    }

    public static class HPopupMenu extends PopupMenu
    {
        private final MenuItem nextPage = new MenuItem("Next page");
        private final MenuItem backupPg = new MenuItem("Backup current page");
        private final MenuItem prevPage = new MenuItem("Previous page");

        public HPopupMenu()
        {
            super();
            //--
            nextPage.addActionListener((e) -> HBPCore.UPL.incrementPage());
            backupPg.addActionListener((e) -> HBPCore.UBL.backupHotbar());
            prevPage.addActionListener((e) -> HBPCore.UPL.decrementPage());
            //--
            add(nextPage);
            add(backupPg);
            add(prevPage);
        }
    }
}
