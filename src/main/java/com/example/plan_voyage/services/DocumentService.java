package com.example.plan_voyage.services;

import com.example.plan_voyage.dto.DocumentDetailsDto;
import com.example.plan_voyage.entity.DocumentDetails;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface DocumentService {
    DocumentDetails documentUpload(MultipartFile file, UUID tripId, String uploaderId) throws UnsupportedMediaTypeStatusException, IOException;

    List<DocumentDetailsDto> listFilesForTrip(UUID tripId) throws IOException;

    void deleteDocument(UUID documentId);

    Resource downloadDocument(UUID documentId) throws IOException;
}
