package io.github.chaosunity.parsing.type

enum BuiltInType implements Type {
    BOOLEAN("bool", boolean.class, "Z"),
    INT("int", int.class, "I"),
    CHAR("char", char.class, "C"),
    BYTE("byte", byte.class, "B"),
    SHORT("short", short.class, "S"),
    LONG("long", long.class, "J"),
    FLOAT("float", float.class, "F"),
    DOUBLE("double", double.class, "D"),
    STRING("string", String.class, "Ljava/lang/String;"),
    BOOLEAN_ARR("bool[]", boolean[].class, "[B"),
    INT_ARR("int[]", int[].class, "[I"),
    CHAR_ARR("char[]", char[].class, "[C"),
    BYTE_ARR("byte[]", byte[].class, "[B"),
    SHORT_ARR("short[]", short[].class, "[S"),
    LONG_ARR("long[]", long[].class, "[J"),
    FLOAT_ARR("float[]", float[].class, "[F"),
    DOUBLE_ARR("double[]", double[].class, "[D"),
    STRING_ARR("string[]", String[].class, "[Ljava/lang/String;"),
    NONE("", null, ""),
    VOID("void", void.class, "V");

    String name
    Class<?> typeClass
    String descriptor

    BuiltInType(String name, Class<?> typeClass, String descriptor) {
        this.name = name
        this.typeClass = typeClass
        this.descriptor = descriptor
    }

    @Override
    String getName() {
        return name
    }

    @Override
    Class<?> getTypeClass() {
        return typeClass
    }

    @Override
    String getDescriptor() {
        return descriptor
    }

    @Override
    String getInternalName() {
        return getDescriptor()
    }
}