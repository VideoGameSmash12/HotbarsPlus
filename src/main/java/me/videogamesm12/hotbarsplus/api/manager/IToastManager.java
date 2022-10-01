package me.videogamesm12.hotbarsplus.api.manager;

import me.videogamesm12.hotbarsplus.core.universal.NotificationManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public interface IToastManager
{
    Identifier TEXTURE = new Identifier("hotbarsplus", "textures/toasts.png");

    IHotbarToast getToastFrom(NotificationManager.NotificationType type, Text... texts);

    void showToast(IHotbarToast toast);

    default void showToast(NotificationManager.NotificationType type, Text... texts)
    {
        showToast(getToastFrom(type, texts));
    }

    interface IHotbarToast
    {
        NotificationManager.NotificationType getType();
    }
}
