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
import java.util.Optional;

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
                || isSlotAvailable(bookedRooms, RoomName.C_CAVE, startTime, endTime).isPresent()) {
            availableRooms.add(RoomName.C_CAVE);
        }

        if (noSlotsBookedInRoom(bookedRooms, RoomName.D_TOWER)
                || isSlotAvailable(bookedRooms, RoomName.D_TOWER, startTime, endTime).isPresent()) {
            availableRooms.add(RoomName.D_TOWER);
        }

        if (noSlotsBookedInRoom(bookedRooms, RoomName.G_MANSION)
                || isSlotAvailable(bookedRooms, RoomName.G_MANSION, startTime, endTime).isPresent()) {
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

    private Optional<BookedRoom> isSlotAvailable(
            final List<BookedRoom> bookedRooms,
            final RoomName roomName,
            final LocalDateTime startTime,
            final LocalDateTime endTime) {
        return bookedRooms.stream()
                .filter(bookedRoom -> bookedRoom.getRoomName() == roomName)
                .filter(bookedRoom -> !inBetweenInclusive(bookedRoom.getStartTimeInLocalDate(), startTime, endTime))
                .filter(bookedRoom -> !inBetweenInclusive(bookedRoom.getEndTimeInLocalDate(), startTime, endTime))
                .findAny();
    }

    private boolean inBetweenInclusive(
            final LocalDateTime dateToCheck,
            final LocalDateTime startDate,
            final LocalDateTime endDate) {
        return dateToCheck.isAfter(startDate) && dateToCheck.isBefore(endDate);
    }

    private boolean noSlotsBookedInRoom(final List<BookedRoom> bookedRooms, final RoomName roomName) {
        return bookedRooms.stream().noneMatch(bookedRoom -> bookedRoom.getRoomName() == roomName);
    }
}
