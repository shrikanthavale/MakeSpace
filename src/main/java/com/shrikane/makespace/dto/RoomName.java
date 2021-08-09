package com.shrikane.makespace.dto;

public enum RoomName {

    C_CAVE(3),
    D_TOWER(7),
    G_MANSION(20);

    RoomName(final int capacity) {
        this.capacity = capacity;
    }

    private final int capacity;

    public int getCapacity() {
        return capacity;
    }
}
