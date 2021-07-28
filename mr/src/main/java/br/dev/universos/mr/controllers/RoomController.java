package br.dev.universos.mr.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.universos.mr.repositories.RoomRepository;
import br.dev.universos.mr.exceptions.ResourceNotFoundException;
import br.dev.universos.mr.models.Room;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/")
public class RoomController {
    
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable("id") Long id) 
    throws ResourceNotFoundException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada:" + id));
        return ResponseEntity.ok().body(room);
    }

    @PostMapping("/rooms")
    public Room createRoom(@Valid @RequestBody Room room) {
        return roomRepository.save(room);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable("id") Long id, @Valid @RequestBody Room roomDetails) throws ResourceNotFoundException {

        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada" + id));
        room.setId(id);
        room.setName(roomDetails.getName());
        room.setDate(roomDetails.getDate());
        room.setStartHour(roomDetails.getStartHour());
        room.setEndHour(roomDetails.getEndHour());

        final Room updateRoom = roomRepository.save(room);
        return ResponseEntity.ok(updateRoom);
    }

    @DeleteMapping("/rooms/{id}")
    public Map<String, Boolean> deleteRoom(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada" + id));
        roomRepository.delete(room);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
