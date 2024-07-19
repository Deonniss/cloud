package ru.golovin.cloud.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String name;
    private String path;
    private String md5;
    private String extension;
    private Long weight;
}
