package com.example.examplemod.entity.bus;

import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum BusState implements StringRepresentable {
    IDLE("idle"), ANGRY("angry");

    private final String name;

    BusState(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
    private static final Map<String, BusState> BY_NAME = Arrays.stream(values())
            .collect(Collectors.toMap(BusState::getSerializedName, Function.identity()));

    public static BusState byName(String name) {
        return BY_NAME.getOrDefault(name, IDLE);
    }
}
