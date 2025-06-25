package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ListItemRepository extends JpaRepository<ListItem, UUID> {
}
