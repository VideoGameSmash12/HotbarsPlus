package me.videogamesm12.hotbarsplus.legacy.manager;

import com.mojang.blaze3d.platform.GlStateManager;
import lombok.Getter;
import lombok.Setter;
import me.videogamesm12.hotbarsplus.api.manager.IToastManager;
import me.videogamesm12.hotbarsplus.core.universal.NotificationManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.Text;

public class CustomToastManager implements IToastManager
{
    @Override
    public HotbarToast getToastFrom(NotificationManager.NotificationType type, Text... texts)
    {
        Text title;
        Text description;

        switch (texts.length)
        {
            case 0:
            {
                throw new IllegalArgumentException("Fuck you");
            }
            case 1:
            {
                title = texts[0];
                description = null;
                break;
            }
            default:
            case 2:
            {
                title = texts[0];
                description = texts[1];
                break;
            }
        }

        return new HotbarToast(title, description, type);
    }

    @Override
    public void showToast(IHotbarToast toast)
    {
        HotbarToast instance = MinecraftClient.getInstance().getToastManager().getToast(HotbarToast.class, toast.getType());
        if (instance == null)
        {
            MinecraftClient.getInstance().getToastManager().add((HotbarToast) toast);
        }
        else
        {
            HotbarToast hToast = (HotbarToast) toast;
            //--
            instance.setTitle(hToast.getTitle());
            instance.setDescription(hToast.getDescription());
            instance.setJustUpdated(true);
        }
    }

    @Getter
    @Setter
    public static class HotbarToast implements IHotbarToast, Toast
    {
        private Text title;
        private Text description;
        //--
        private NotificationManager.NotificationType type;
        private long time;
        //--
        private boolean justUpdated = true;

        public HotbarToast(Text title, Text description, NotificationManager.NotificationType type)
        {
            this.title = title;
            this.description = description;
            this.type = type;
        }

        @Override
        public Visibility draw(ToastManager manager, long currentTime)
        {
            if (justUpdated)
            {
                this.time = currentTime;
                justUpdated = false;
            }

            MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
            GlStateManager.color3f(1, 1, 1);
            DrawableHelper.blit(0, 0, 0, 20, 160, 32, 160, 52);
            //--
            GlStateManager.enableBlend();
            DrawableHelper.blit(6, 6, 20 * type.ordinal(), 0, 20, 20, 160, 52);
            GlStateManager.disableBlend();
            //--
            int titleY = description == null ? 12 : 7;
            MinecraftClient.getInstance().textRenderer.draw(title.asString(), 30, titleY, type.getColor());

            if (description != null)
            {
                MinecraftClient.getInstance().textRenderer.draw(description.asString(), 30, 18, 0xFFFFFF);
            }

            return currentTime - time >= 5000 ? Visibility.HIDE : Visibility.SHOW;
        }

        @Override
        public NotificationManager.NotificationType getType()
        {
            return type;
        }
    }
}
