package com.shrikane.makespace.dto;

public class VacancyRequestDTO extends RequestDTO {
    public VacancyRequestDTO(
            final String startTime,
            final String endTime) {
        super(ActionRequested.VACANCY, startTime, endTime);
    }
}
