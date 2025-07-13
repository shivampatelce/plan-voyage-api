package com.example.plan_voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "document_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"trip_id", "file_name"})
})
public class DocumentDetails {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID documentId;

    private String uploaderId;

    private String uploaderFullName;

    private Date uploadDate;

    @Column(unique = true)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonProperty("trip")
    @JsonIgnore
    private Trip trip;

    public DocumentDetails() {
    }

    public DocumentDetails(String uploaderId, String uploaderFullName, Date uploadDate, String fileName, Trip trip) {
        this.uploaderId = uploaderId;
        this.uploaderFullName = uploaderFullName;
        this.uploadDate = uploadDate;
        this.fileName = fileName;
        this.trip = trip;
    }

    public DocumentDetails(UUID documentId, String uploaderId, String uploaderFullName, Date uploadDate, String fileName, Trip trip) {
        this.documentId = documentId;
        this.uploaderId = uploaderId;
        this.uploaderFullName = uploaderFullName;
        this.uploadDate = uploadDate;
        this.fileName = fileName;
        this.trip = trip;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getUploaderFullName() {
        return uploaderFullName;
    }

    public void setUploaderFullName(String uploaderFullName) {
        this.uploaderFullName = uploaderFullName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
