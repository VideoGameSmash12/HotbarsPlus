package me.videogamesm12.hotbarsplus.v1_21_2.manager;

import lombok.Getter;
import lombok.Setter;
import me.videogamesm12.hotbarsplus.api.manager.IToastManager;
import me.videogamesm12.hotbarsplus.core.universal.NotificationManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CustomToastManager implements IToastManager
{
    private static final Identifier TEXTURE = Identifier.of("hotbarsplus", "toasts/hotbarsplus");

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
        private long startTime;
        //--
        private boolean justUpdated = true;
        private Visibility visibility = Visibility.HIDE;

        public HotbarToast(Text title, Text description, NotificationManager.NotificationType type)
        {
            this.title = title;
            this.description = description;
            this.type = type;
        }

        @Override
        public void update(ToastManager manager, long time)
        {
            if (justUpdated)
            {
                this.startTime = time;
                justUpdated = false;
            }

            visibility = time - this.startTime >= 5000 ? Visibility.HIDE : Visibility.SHOW;
        }

        @Override
        public void draw(DrawContext context, TextRenderer textRenderer, long startTime)
        {
            context.drawGuiTexture(RenderLayer::getGuiTextured, TEXTURE, 0, 0, getWidth(), getHeight());
            context.drawGuiTexture(RenderLayer::getGuiTextured, type.getIcon(), 6, 6, 20, 20);
            //--
            int titleY = description == null ? 12 : 7;
            context.drawText(MinecraftClient.getInstance().textRenderer, title, 30, titleY, type.getColor(), false);

            if (description != null)
            {
                context.drawText(MinecraftClient.getInstance().textRenderer, description.getString(), 30, 18, 0xFFFFFF, false);
            }
        }

        @Override
        public NotificationManager.NotificationType getType()
        {
            return type;
        }
    }
}
