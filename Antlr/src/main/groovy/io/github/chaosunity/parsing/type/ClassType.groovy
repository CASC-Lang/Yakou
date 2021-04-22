package io.github.chaosunity.parsing.type

class ClassType implements Type {
    private String name

    ClassType(String name) {
        this.name = name
    }

    @Override
    String getName() {
        return name
    }

    @Override
    Class<?> getTypeClass() {
        try {
            return Class.forName(name)
        } catch (ClassNotFoundException ignored) {
            throw new RuntimeException()
        }
    }

    @Override
    String getDescriptor() {
        return "L" + getInternalName() + ";"
    }

    @Override
    String getInternalName() {
        return name.replace(".", "/")
    }
}
