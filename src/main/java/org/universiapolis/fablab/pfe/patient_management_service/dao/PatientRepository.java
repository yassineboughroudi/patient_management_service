package org.universiapolis.fablab.pfe.patient_management_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.universiapolis.fablab.pfe.patient_management_service.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
