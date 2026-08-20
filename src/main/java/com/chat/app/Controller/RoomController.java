package com.chat.app.Controller;

import com.chat.app.Entity.Message;
import com.chat.app.Entity.Room;
import com.chat.app.Repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class RoomController {

    //Constructor Based dependency field injection
    private RoomRepository RoomRepo;

    RoomController(RoomRepository RoomRepo) {
        this.RoomRepo = RoomRepo;
    }


    //create room
    @PostMapping()
    public ResponseEntity<?> createRoom(@RequestBody String roomId) {

        if (RoomRepo.findByRoomId(roomId)!=null) {
            //Room is already there

            return ResponseEntity.badRequest().body("Room already exits");
        }

        //create a room
        Room room = new Room();
        room.setRoomId(roomId);
        Room roomSaved = RoomRepo.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    // get room:Join
    @GetMapping("{roomId}")
    public ResponseEntity<?> RoomJoining(@PathVariable String roomId) {
        Room room = RoomRepo.findByRoomId(roomId);

        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found");
        }
        return ResponseEntity.ok(room);
    }

    //get messages of room

    public ResponseEntity<List<Message>> getMsg(@PathVariable String roomId,
                                                @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                @RequestParam(value = "size", defaultValue = "20", required = false) int size) {

        Room room = RoomRepo.findByRoomId(roomId);

        if (room == null) {

            return ResponseEntity.badRequest().build();
        }
        //get messages:
        //Pagination

        List<Message> msg = room.getMsg();
        int start = Math.max(0, msg.size() - page * size);
        int end = Math.min(msg.size(), msg.size() - (page - 1) * size);
        List<Message> paginatedmsg = msg.subList(start, end);
        return ResponseEntity.ok(paginatedmsg);


    }


}
