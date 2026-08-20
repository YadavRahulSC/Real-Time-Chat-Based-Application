package com.chat.app.Repository;

import com.chat.app.Entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room, String> {


    Room findByRoomId(String roomId);
}
