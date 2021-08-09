package com.shrikane.makespace.entity;

import com.shrikane.makespace.dto.RoomName;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private RoomName roomName;
    private String startTime;
    private String endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomName getRoomName() {
        return roomName;
    }

    public void setRoomName(RoomName roomName) {
        this.roomName = roomName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Transient
    public LocalDateTime getStartTimeInLocalDate() {
        return LocalDate.now().atTime(LocalTime.parse(startTime));
    }

    @Transient
    public LocalDateTime getEndTimeInLocalDate() {
        return LocalDate.now().atTime(LocalTime.parse(endTime));
    }

}
