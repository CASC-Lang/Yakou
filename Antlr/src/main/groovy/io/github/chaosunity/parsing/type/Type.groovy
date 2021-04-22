package io.github.chaosunity.parsing.type

interface Type {
    String getName()

    Class<?> getTypeClass()

    String getDescriptor()

    String getInternalName()
}