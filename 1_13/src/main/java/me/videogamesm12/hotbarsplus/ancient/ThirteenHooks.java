package me.videogamesm12.hotbarsplus.ancient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import me.videogamesm12.hotbarsplus.api.IVersionHook;
import net.minecraft.text.Text;

public class ThirteenHooks implements IVersionHook
{
    @Override
    public Text convertFromJson(JsonElement tree)
    {
        return Text.Serializer.deserializeText(new Gson().toJson(tree));
    }
}
