package br.dev.universos.mr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.universos.mr.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
}
