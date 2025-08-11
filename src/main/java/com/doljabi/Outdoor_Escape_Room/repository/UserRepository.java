package com.doljabi.Outdoor_Escape_Room.repository;

import com.doljabi.Outdoor_Escape_Room.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
