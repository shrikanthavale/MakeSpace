package com.shrikane.makespace.dto;

public class BookRequestDTO extends RequestDTO {
    private final String personCapacity;

    public BookRequestDTO(
            final String startTime,
            final String endTime,
            final String personCapacity) {
        super(ActionRequested.BOOK, startTime, endTime);
        this.personCapacity = personCapacity;
    }

    public String getPersonCapacity() {
        return personCapacity;
    }
}
