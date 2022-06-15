package org.yakou.lang.api;

import org.jetbrains.annotations.Nullable;

import java.io.File;

public abstract class AbstractPreference {
    public @Nullable File sourceFile = null;
    public @Nullable File outputFolder = new File("out");
    public boolean enableColor = false;
    public boolean useAscii = false;
}
