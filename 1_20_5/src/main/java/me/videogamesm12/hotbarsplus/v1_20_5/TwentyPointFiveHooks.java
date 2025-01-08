package me.videogamesm12.hotbarsplus.v1_20_5;

import com.google.gson.JsonElement;
import lombok.Getter;
import me.videogamesm12.hotbarsplus.api.IVersionHook;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;

public class TwentyPointFiveHooks implements IVersionHook
{
	@Getter
	private static final RegistryWrapper.WrapperLookup wrapperLookup = BuiltinRegistries.createWrapperLookup();

	@Override
	public Text convertFromJson(JsonElement tree)
	{
		return Text.Serialization.fromJsonTree(tree, wrapperLookup);
	}
}
