package com.chat.app.Repository;

import com.chat.app.Entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {


    Room findByRoomId(String roomId);
}
