package com.ironhack.WTWAPI.repository;

import com.ironhack.WTWAPI.model.WatchItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchItemRepository extends JpaRepository<WatchItem,Long> {
}

