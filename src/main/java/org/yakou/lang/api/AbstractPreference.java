package org.yakou.lang.api;

import chaos.unity.nenggao.FileReportBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.PrintStream;

public abstract class AbstractPreference {
    public @Nullable File sourceFile = null;
    public @Nullable File outputFolder = new File("out");
    public @NotNull PrintStream outputStream = System.out;
    public boolean enableColor = false;
    public boolean useAscii = false;
    public boolean enableTiming = false;

    public abstract FileReportBuilder reportBuilder(@NotNull File currentFile);
}
