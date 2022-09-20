package me.videogamesm12.hotbarsplus.api.event.failures;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NBackupFailEvent
{
    private final Exception exception;
}