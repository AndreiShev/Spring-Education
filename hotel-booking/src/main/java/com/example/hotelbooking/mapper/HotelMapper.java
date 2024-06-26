package com.example.hotelbooking.mapper;

import com.example.hotelbooking.entities.Hotel;
import com.example.hotelbooking.web.model.HotelResponse;
import com.example.hotelbooking.web.model.HotelResponseList;
import com.example.hotelbooking.web.model.UpsertHotelRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(HotelMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE, uses = {RoomMapper.class})
public interface HotelMapper {

    Hotel requestToHotel(UpsertHotelRequest request);

    @Mapping(source = "roomId", target = "id")
    Hotel requestToHotel(Long roomId, UpsertHotelRequest request);

    HotelResponse hotelToResponse(Hotel hotel);

    default HotelResponseList hotelListToHotelResponseList(List<Hotel> hotels) {
        HotelResponseList response = new HotelResponseList();

        response.setHotelResponseList(hotels.stream()
                .map(this::hotelToResponse).collect(Collectors.toList()));

        return response;
    }
}
