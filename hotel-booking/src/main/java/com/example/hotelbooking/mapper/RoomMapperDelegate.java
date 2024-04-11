package com.example.hotelbooking.mapper;


import com.example.hotelbooking.entities.Hotel;
import com.example.hotelbooking.entities.Room;
import com.example.hotelbooking.services.HotelService;
import com.example.hotelbooking.web.model.UpsertRoomRequest;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class RoomMapperDelegate implements RoomMapper {

    @Autowired
    private HotelService hotelService;

    @Override
    public Room roomRequestToRoom(UpsertRoomRequest request) {
        Room room = new Room();
        room.setDescription(request.getDescription());
        room.setName(request.getName());
        room.setNumber(request.getNumber());
        room.setPrice(request.getPrice());
        room.setMaximumNumberOfPeople(request.getMaximumNumberOfPeople());
        room.setHotel(hotelService.getHotelById(request.getHotelId()));
        return room;
    }

    @Override
    public Room roomRequestToRoom(Long roomId, UpsertRoomRequest request) {
        Room room = roomRequestToRoom(request);
        room.setId(roomId);
        return room;
    }

    @Override
    public UpsertRoomRequest roomToRequest(Room room) {
        UpsertRoomRequest request = new UpsertRoomRequest();
        request.setDescription(room.getDescription());
        request.setName(room.getName());
        request.setNumber(room.getNumber());
        request.setPrice(room.getPrice());
        request.setMaximumNumberOfPeople(room.getMaximumNumberOfPeople());
        request.setHotelId(room.getHotel().getId());
        return request;
    }

}
