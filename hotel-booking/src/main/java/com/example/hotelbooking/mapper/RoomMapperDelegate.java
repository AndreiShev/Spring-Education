package com.example.hotelbooking.mapper;


import com.example.hotelbooking.entities.Room;
import com.example.hotelbooking.services.HotelService;
import com.example.hotelbooking.web.model.UpsertRoomRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class RoomMapperDelegate implements RoomMapper {
    private final HotelService hotelService;
    @Override
    public Room roomRequestToRoom(UpsertRoomRequest request) {
        Room room = new Room();
        room.setDescription(room.getDescription());
        room.setName(room.getName());
        room.setNumber(room.getNumber());
        room.setPrice(room.getPrice());
        room.setMaximumNumberOfPeople(room.getMaximumNumberOfPeople());
        room.setBookingFrom(room.getBookingFrom());
        room.setBookingTo(room.getBookingTo());
        room.setHotel(hotelService.getHotelById(request.getHotel()));
        return room;
    }

    @Override
    public Room roomRequestToRoom(Long id, UpsertRoomRequest request) {
        Room room = roomRequestToRoom(request);
        room.setId(id);
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
        request.setBookingFrom(room.getBookingFrom());
        request.setBookingTo(room.getBookingTo());
        request.setHotel(room.getHotel().getId());
        return request;
    }

}
