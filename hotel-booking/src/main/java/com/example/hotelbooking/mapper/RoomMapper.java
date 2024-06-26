package com.example.hotelbooking.mapper;

import com.example.hotelbooking.entities.Hotel;
import com.example.hotelbooking.entities.Room;
import com.example.hotelbooking.web.model.RoomResponse;
import com.example.hotelbooking.web.model.RoomResponseList;
import com.example.hotelbooking.web.model.UpsertRoomRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(RoomMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {


    Room roomRequestToRoom(UpsertRoomRequest request);

    @Mapping(source = "roomId", target = "id")
    Room roomRequestToRoom(Long roomId, UpsertRoomRequest request);

    RoomResponse roomToResponseRoom(Room room);

    UpsertRoomRequest roomToRequest(Room room);

    default RoomResponseList roomListToResponseList(List<Room> roomList) {
        RoomResponseList responseList = new RoomResponseList();
        responseList.setRoomList(roomList.stream().map(room -> roomToResponseRoom(room)).toList());
        return responseList;
    }

}
