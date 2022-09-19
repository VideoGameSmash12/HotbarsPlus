package me.videogamesm12.hotbarsplus.legacy;

import com.google.gson.JsonElement;
import me.videogamesm12.hotbarsplus.api.IVersionHook;
import net.minecraft.text.Text;

public class FourteenHooks implements IVersionHook
{
    @Override
    public Text convertFromJson(JsonElement tree)
    {
        return Text.Serializer.fromJson(tree);
    }
}
