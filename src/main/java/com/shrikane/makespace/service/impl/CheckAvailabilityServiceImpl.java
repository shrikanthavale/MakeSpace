package com.shrikane.makespace.service.impl;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.dto.VacancyRequestDTO;
import com.shrikane.makespace.entity.BookedRoom;
import com.shrikane.makespace.repository.BookedRoomRepository;
import com.shrikane.makespace.service.CheckAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckAvailabilityServiceImpl implements CheckAvailabilityService {

    private final BookedRoomRepository bookedRoomRepository;

    @Autowired
    public CheckAvailabilityServiceImpl(final BookedRoomRepository bookedRoomRepository) {
        this.bookedRoomRepository = bookedRoomRepository;
    }

    @Override
    public List<RoomName> checkAvailableRooms(VacancyRequestDTO vacancyRequestDTO) {
        final List<BookedRoom> bookedRooms = bookedRoomRepository.findAll();
        final LocalDateTime startTime = LocalDate.now().atTime(LocalTime.parse(vacancyRequestDTO.getStartTime()));
        final LocalDateTime endTime = LocalDate.now().atTime(LocalTime.parse(vacancyRequestDTO.getEndTime()));
        final List<RoomName> availableRooms = new ArrayList<>(3);

        if (noSlotsBookedInRoom(bookedRooms, RoomName.C_CAVE)
                || !isSlotTaken(bookedRooms, RoomName.C_CAVE, startTime, endTime)) {
            availableRooms.add(RoomName.C_CAVE);
        }

        if (noSlotsBookedInRoom(bookedRooms, RoomName.D_TOWER)
                || !isSlotTaken(bookedRooms, RoomName.D_TOWER, startTime, endTime)) {
            availableRooms.add(RoomName.D_TOWER);
        }

        if (noSlotsBookedInRoom(bookedRooms, RoomName.G_MANSION)
                || !isSlotTaken(bookedRooms, RoomName.G_MANSION, startTime, endTime)) {
            availableRooms.add(RoomName.G_MANSION);
        }

        return availableRooms;
    }

    @Override
    public RoomName findBestRoomAvailable(final BookRequestDTO bookRequestDTO) {
        final VacancyRequestDTO vacancyRequestDTO = new VacancyRequestDTO(bookRequestDTO.getStartTime(), bookRequestDTO.getEndTime());
        List<RoomName> roomNames = checkAvailableRooms(vacancyRequestDTO);
        if (CollectionUtils.isEmpty(roomNames)) {
            return null;
        }

        final int personCapacity = Integer.parseInt(bookRequestDTO.getPersonCapacity());
        if (personCapacity <= 3) {
            return roomNames.get(0);
        }

        if (personCapacity > 3 && personCapacity <= 7) {
            if (roomNames.contains(RoomName.D_TOWER)) {
                return RoomName.D_TOWER;
            } else if (roomNames.contains(RoomName.G_MANSION)) {
                return RoomName.G_MANSION;
            } else {
                return null;
            }
        }

        if (personCapacity > 7 && personCapacity <= 20 && roomNames.contains(RoomName.G_MANSION)) {
            return RoomName.G_MANSION;
        }

        return null;
    }

    private boolean isSlotTaken(
            final List<BookedRoom> bookedRooms,
            final RoomName roomName,
            final LocalDateTime startTime,
            final LocalDateTime endTime) {
        return bookedRooms.stream()
                .filter(bookedRoom -> bookedRoom.getRoomName() == roomName)
                .anyMatch(bookedRoom -> isSlotOverlapping(startTime, endTime, bookedRoom.getStartTimeInLocalDate(), bookedRoom.getEndTimeInLocalDate()));
    }

    private boolean isSlotOverlapping(
            final LocalDateTime startDateToCheck,
            final LocalDateTime endDateToCheck,
            final LocalDateTime startDate,
            final LocalDateTime endDate) {
        return startDateToCheck.isBefore(endDate) && startDate.isBefore(endDateToCheck);
    }

    private boolean noSlotsBookedInRoom(final List<BookedRoom> bookedRooms, final RoomName roomName) {
        return bookedRooms.stream().noneMatch(bookedRoom -> bookedRoom.getRoomName() == roomName);
    }
}
