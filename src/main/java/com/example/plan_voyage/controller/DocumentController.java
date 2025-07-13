package com.example.plan_voyage.controller;

import com.example.plan_voyage.dto.DocumentDetailsDto;
import com.example.plan_voyage.entity.DocumentDetails;
import com.example.plan_voyage.services.DocumentService;
import com.example.plan_voyage.util.BaseController;
import com.example.plan_voyage.util.SuccessMessageResponse;
import com.example.plan_voyage.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/document")
public class DocumentController extends BaseController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload/{tripId}/{uploaderId}")
    public ResponseEntity<SuccessResponse<DocumentDetails>> uploadDocument(@PathVariable UUID tripId, @PathVariable String uploaderId, @RequestParam("file") MultipartFile file) {
        DocumentDetails documentDetails;
        try {
            documentDetails = documentService.documentUpload(file, tripId, uploaderId);
        } catch (IOException e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST, "/v1/upload/"+tripId);
        }
        return success(documentDetails, "Document has been uploaded successfully.");
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<SuccessResponse<List<DocumentDetailsDto>>> filesList(@PathVariable UUID tripId) {
        List<DocumentDetailsDto> files;
        try {
            files = documentService.listFilesForTrip(tripId);
        } catch (IOException e) {
            return error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "/v1/document" + tripId);
        }
        return success(files, "List of files");
    }
}
