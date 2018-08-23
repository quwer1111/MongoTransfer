package com.bi.service.repositoriesMariaDB;

import com.bi.service.model.mariadb.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {

    Gender findByName(String name);

}
