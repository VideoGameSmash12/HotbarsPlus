package me.videogamesm12.hotbarsplus.api.event.navigation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@RequiredArgsConstructor
public class NHotbarNavigateEvent
{
    private final BigInteger page;
}
