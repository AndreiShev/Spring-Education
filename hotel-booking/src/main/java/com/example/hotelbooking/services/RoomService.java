package com.example.hotelbooking.services;

import com.example.hotelbooking.entities.Room;

import java.util.List;

public interface RoomService {

    List<Room> getAllRoom();

    Room getById(Long id);

    Room save(Room room);

    Room update(Long id, Room room);

    void delete(Long id);
}
