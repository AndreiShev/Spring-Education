package com.example.hotelbooking.services.impl;

import com.example.hotelbooking.entities.Room;
import com.example.hotelbooking.exception.EntityNotFoundException;
import com.example.hotelbooking.repository.HotelSpecification;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.repository.RoomSpecification;
import com.example.hotelbooking.services.HotelService;
import com.example.hotelbooking.services.RoomService;
import com.example.hotelbooking.utils.Utils;
import com.example.hotelbooking.web.model.RoomFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository repository;
    private final HotelService hotelService;
    @Override
    public List<Room> getAllRoom(RoomFilter filter) {
        return repository.findAll(
                RoomSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent();
    }

    @Override
    public Room getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Комната с таким id {0} не была найдена", id))
        );
    }

    @Override
    public Room save(Room room) {
        Room newRoom = repository.save(room);
        return newRoom;
    }

    @Override
    public Room update(Long id, Room room) {
        Room existedRoom = getById(id);
        Utils.copyNonNullValues(room, existedRoom);

        return repository.save(existedRoom);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
