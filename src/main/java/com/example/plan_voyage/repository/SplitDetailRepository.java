package com.example.plan_voyage.repository;

import com.example.plan_voyage.entity.SplitDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SplitDetailRepository extends JpaRepository<SplitDetail, UUID> {
}
