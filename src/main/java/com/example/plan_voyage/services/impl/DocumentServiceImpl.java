package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.dto.DocumentDetailsDto;
import com.example.plan_voyage.entity.DocumentDetails;
import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.repository.DocumentDetailsRepository;
import com.example.plan_voyage.repository.TripRepository;
import com.example.plan_voyage.services.DocumentService;
import com.example.plan_voyage.services.KeycloakService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DocumentServiceImpl implements DocumentService {

    private String BASE_DIR = "Documents";

    @Autowired
    private DocumentDetailsRepository documentDetailsRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private KeycloakService keycloakService;

    @Override
    public DocumentDetails documentUpload(MultipartFile file, UUID tripId, String uploaderId) throws UnsupportedMediaTypeStatusException, IOException {
        if (!isValidFileType(file.getContentType())) {
            throw new UnsupportedMediaTypeStatusException("Only PDF and image files are allowed.");
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        // Base directory
        Path baseDirPath = Paths.get(BASE_DIR);
        if(!Files.exists(baseDirPath)) {
            Files.createDirectories(baseDirPath);
        }

        // Create directory with tripId inside base directory
        Path uploadPath = baseDirPath.resolve(tripId.toString());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Store file
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        UserRepresentation userRepresentation = keycloakService.getUserById(uploaderId);
        String fullName = userRepresentation.getFirstName() + " " + userRepresentation.getLastName();

        Trip trip = tripRepository.findById(tripId).orElseThrow(()->new RuntimeException("Invalid trip id."));

        List<DocumentDetails> documentList = documentDetailsRepository.findAllByTrip(trip).stream().filter(document -> document.getFileName()
                .equals(filename)).toList();

        DocumentDetails documentMetaData = null;

        if(!documentList.isEmpty()) {
            documentMetaData = documentList.get(0);
        }

        if(documentMetaData != null) {
            return documentDetailsRepository.save(new DocumentDetails(documentMetaData.getDocumentId(), uploaderId, fullName, new Date(), filename, trip));
        }

        return documentDetailsRepository.save(new DocumentDetails(uploaderId, fullName, new Date(), filename, trip));
    }

    private boolean isValidFileType(String contentType) {
        return contentType.equals("application/pdf") ||
                contentType.startsWith("image/");
    }

    @Override
    public List<DocumentDetailsDto> listFilesForTrip(UUID tripId) throws IOException {
        Path tripDirPath = Paths.get(BASE_DIR).resolve(tripId.toString());

        if (!Files.exists(tripDirPath) || !Files.isDirectory(tripDirPath)) {
            return Collections.emptyList();
        }

        Trip trip = tripRepository.findById(tripId).orElseThrow(()->new RuntimeException("Invalid trip id."));

        List<DocumentDetails> documentDetails = documentDetailsRepository.findAllByTrip(trip);

        Stream<Path> files = Files.list(tripDirPath);

        return files
                .filter(Files::isRegularFile)
                .map(path -> {
                    try {
                        String fileName = path.getFileName().toString();
                        String fileType = Files.probeContentType(path);
                        long fileSize = Files.size(path);

                        DocumentDetails documentMetaData = documentDetails.stream().filter(document -> document.getFileName().equals(fileName)).toList().get(0);

                        return new DocumentDetailsDto(documentMetaData.getDocumentId(), fileName, fileType, fileSize, documentMetaData.getUploaderFullName(), documentMetaData.getUploaderId(), documentMetaData.getUploadDate());
                    } catch (IOException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDocument(UUID documentId) {
        DocumentDetails documentDetails = documentDetailsRepository.findById(documentId)
                .orElseThrow(()-> new RuntimeException("Invalid document id: " + documentId));

        Path filePath = Paths.get(BASE_DIR)
                .resolve(documentDetails.getTrip().getTripId().toString())
                .resolve(documentDetails.getFileName());

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file from disk: " + e.getMessage(), e);
        }

        documentDetailsRepository.delete(documentDetails);
    }

    @Override
    public Resource downloadDocument(UUID documentId) throws IOException {
        DocumentDetails documentDetails = documentDetailsRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Invalid document id: " + documentId));

        Path filePath = Paths.get(BASE_DIR)
                .resolve(documentDetails.getTrip().getTripId().toString())
                .resolve(documentDetails.getFileName());

        if (!Files.exists(filePath)) {
            throw new IOException("File not found on disk: " + filePath);
        }

        return new PathResource(filePath);
    }


}
