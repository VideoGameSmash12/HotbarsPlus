package me.videogamesm12.hotbarsplus.api.config;

public interface ConfigMeta
{
    @interface ConfigEntry
    {
        String getter();

        String setter();
    }
}
