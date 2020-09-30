package com.danilojakob.application.repository;

import com.danilojakob.application.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Override
    Optional<Room> findById(Integer integer);
}
