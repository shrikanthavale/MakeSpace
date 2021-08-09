package com.shrikane.makespace.dto;

public class RequestDTO {
    private final String startTime;
    private final String endTime;
    private final ActionRequested actionRequested;

    public RequestDTO(
            final ActionRequested actionRequested,
            final String startTime,
            final String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.actionRequested = actionRequested;
    }

    public ActionRequested getActionRequested() {
        return actionRequested;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "RequestDTO { " +
                " startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", actionRequested=" + actionRequested +
                '}';
    }
}
