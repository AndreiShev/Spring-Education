package com.example.hotelbooking.web.controller;

import com.example.hotelbooking.mapper.RoomMapper;
import com.example.hotelbooking.services.RoomService;
import com.example.hotelbooking.web.model.RoomResponse;
import com.example.hotelbooking.web.model.RoomResponseList;
import com.example.hotelbooking.web.model.UpsertRoomRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @GetMapping
    public ResponseEntity<RoomResponseList> getAll() {
        return ResponseEntity.ok(
                roomMapper.roomListToResponseList(roomService.getAllRoom())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomBiId(@PathVariable @NotNull @Positive Long id) {
        return ResponseEntity.ok(roomMapper.roomToResponseRoom(roomService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> saveRoom(@RequestBody @Valid UpsertRoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            roomMapper.roomToResponseRoom(roomService.save(roomMapper.roomRequestToRoom(request)))
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable @NotNull @Positive Long id,
                                                   @RequestBody @Valid UpsertRoomRequest request) {
        return ResponseEntity.ok(
            roomMapper.roomToResponseRoom(roomService.update(id, roomMapper.roomRequestToRoom(id, request)))
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable @NotNull @Positive Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
