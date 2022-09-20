package me.videogamesm12.hotbarsplus.api.event.keybind;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NBindPressEvent
{
    private final Bind bind;

    public enum Bind
    {
        BACKUP,
        NEXT,
        PREVIOUS;
    }
}
