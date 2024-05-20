package ru.netology.cloudservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "FILES")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_CONTENT")
    private byte[] fileContent;

    @Column(name = "FILE_SIZE")
    private long fileSize;

    @Column(name = "USER_ID")
    private long userId;
}
