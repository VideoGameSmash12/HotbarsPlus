package me.videogamesm12.hotbarsplus.api.event.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;

@Getter
@RequiredArgsConstructor
public class NBackupSuccessEvent
{
    private final File from;

    private final File to;
}
