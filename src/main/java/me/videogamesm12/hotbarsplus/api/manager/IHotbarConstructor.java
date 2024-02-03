package me.videogamesm12.hotbarsplus.api.manager;

import me.videogamesm12.hotbarsplus.api.IHotbarsPlusStorage;

import java.math.BigInteger;

public interface IHotbarConstructor
{
    public IHotbarsPlusStorage createHotbarStorage(BigInteger page);
}
