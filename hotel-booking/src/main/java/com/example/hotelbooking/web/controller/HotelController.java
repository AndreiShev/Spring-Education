package com.example.hotelbooking.web.controller;

import com.example.hotelbooking.mapper.HotelMapper;
import com.example.hotelbooking.services.HotelService;
import com.example.hotelbooking.web.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    private final HotelMapper hotelMapper;

    @GetMapping
    public ResponseEntity<HotelResponseList> findAll(@RequestBody @Valid HotelFilter filter) {
        return ResponseEntity.ok(
            hotelMapper.hotelListToHotelResponseList(hotelService.getAllHotel(filter))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable @NotNull @Positive Long id) {
        return ResponseEntity.ok(
                hotelMapper.hotelToResponse(hotelService.getHotelById(id))
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> save(@RequestBody @Valid UpsertHotelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            hotelMapper.hotelToResponse(hotelService.save(hotelMapper.requestToHotel(request)))
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> update(@PathVariable("id") @NotNull @Positive Long hotelId,
                                                @RequestBody @Valid UpsertHotelRequest request) {
        return ResponseEntity.ok(
            hotelMapper.hotelToResponse(hotelService.update(hotelId, hotelMapper.requestToHotel(request)))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<HotelResponse> changeRating(@RequestParam(name = "id") Long id,
                                             @RequestParam(name = "newMark") Integer newMark) {
        return ResponseEntity.ok(
                hotelMapper.hotelToResponse(hotelService.changeRating(id, newMark))
        );
    }
}
