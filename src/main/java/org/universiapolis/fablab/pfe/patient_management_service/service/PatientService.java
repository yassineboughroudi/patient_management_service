package org.universiapolis.fablab.pfe.patient_management_service.service;

import org.springframework.stereotype.Service;
import org.universiapolis.fablab.pfe.patient_management_service.dao.PatientRepository;
import org.universiapolis.fablab.pfe.patient_management_service.model.Patient;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID " + id));
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        // Check existence
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            throw new RuntimeException("Patient not found with ID " + id);
        }

        Patient existingPatient = optionalPatient.get();
//        // Update fields
//        existingPatient.setFirstName(updatedPatient.getFirstName());
//        existingPatient.setLastName(updatedPatient.getLastName());
//        existingPatient.setGender(updatedPatient.getGender());
//        existingPatient.setDateOfBirth(updatedPatient.getDateOfBirth());
//        existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
//        existingPatient.setEmail(updatedPatient.getEmail());
//        existingPatient.setInsuranceProvider(updatedPatient.getInsuranceProvider());

        return patientRepository.save(existingPatient);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with ID " + id);
        }
        patientRepository.deleteById(id);
    }
}