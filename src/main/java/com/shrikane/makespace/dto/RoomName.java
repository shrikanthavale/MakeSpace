package com.shrikane.makespace.dto;

public enum RoomName {

    C_CAVE(3, "C-Cave"),
    D_TOWER(7, "D-Tower"),
    G_MANSION(20, "G-Mansion");

    RoomName(final int capacity, final String name) {
        this.capacity = capacity;
        this.name = name;
    }

    private final int capacity;
    private final String name;

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }
}
