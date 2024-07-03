package com.project.psdstartinformationservice.repository;

import com.project.psdstartinformationservice.entity.StartInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartInformationRepository extends JpaRepository<StartInformation,Short> {
}
