package com.example.gympool.service;

import com.example.gympool.entity.Room;
import java.util.List;

public interface RoomService {
    Room createRoom(Room room);
    List<Room> getAllRooms();
    Room getRoomById(Long id);
    Room updateRoom(Long id, Room roomDetails);
    void deleteRoom(Long id);
}