package com.Tingle.G4hosp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tingle.G4hosp.entity.ChatRoomAccess;
import com.Tingle.G4hosp.entity.Med;

public interface ChatRoomAccessRepository extends JpaRepository<ChatRoomAccess, Long> {
	ChatRoomAccess findByChatRoomAccessMedId(Med medId);
}
