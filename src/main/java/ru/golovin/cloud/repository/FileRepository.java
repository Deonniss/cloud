package ru.golovin.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.golovin.cloud.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
