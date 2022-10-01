package me.videogamesm12.hotbarsplus.core.notifications;

import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.core.universal.NotificationManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class ToastNotification implements NotificationManager.NotificationRoute
{
    @Override
    public void display(NotificationManager.NotificationType type, Text... text)
    {
        if (HBPCore.TOASTS != null)
            HBPCore.TOASTS.showToast(type, text);
    }

    @Override
    public @NotNull Identifier getId()
    {
        return new Identifier("hotbarsplus", "toast");
    }
}
