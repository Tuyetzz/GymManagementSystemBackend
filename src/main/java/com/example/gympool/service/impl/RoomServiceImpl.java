package com.example.gympool.service.impl;

import com.example.gympool.entity.Room;
import com.example.gympool.repository.RoomRepository;
import com.example.gympool.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Not Found"));
    }

    @Override
    public Room updateRoom(Long id, Room roomDetails) {
        Room existingRoom = getRoomById(id);

        existingRoom.setName(roomDetails.getName());
        existingRoom.setLocation(roomDetails.getLocation());
        existingRoom.setNote(roomDetails.getNote());

        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        Room roomToDelete = getRoomById(id);
        roomRepository.delete(roomToDelete);
    }
}