package com.example.hotelbooking.services;

import com.example.hotelbooking.entities.Room;
import com.example.hotelbooking.web.model.RoomFilter;

import java.util.List;

public interface RoomService {

    List<Room> getAllRoom(RoomFilter filter);

    Room getById(Long id);

    Room save(Room room);

    Room update(Long id, Room room);

    void delete(Long id);
}
