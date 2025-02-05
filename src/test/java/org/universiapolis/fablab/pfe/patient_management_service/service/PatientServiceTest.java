package org.universiapolis.fablab.pfe.patient_management_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.universiapolis.fablab.pfe.patient_management_service.dao.PatientRepository;
import org.universiapolis.fablab.pfe.patient_management_service.model.Patient;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    public void testGetAllPatients() {
        // Arrange
        Patient patient1 = Patient.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender("M")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .build();

        Patient patient2 = Patient.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .gender("F")
                .dateOfBirth(LocalDate.of(1995, 5, 5))
                .email("jane.doe@example.com")
                .build();

        when(patientRepository.findAll()).thenReturn(List.of(patient1, patient2));

        // Act
        List<Patient> patients = patientService.getAllPatients();

        // Assert
        assertEquals(2, patients.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    public void testGetPatientById() {
        // Arrange
        Patient patient = Patient.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender("M")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        // Act
        Patient foundPatient = patientService.getPatientById(1L);

        // Assert
        assertNotNull(foundPatient);
        assertEquals("John", foundPatient.getFirstName());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddPatient() {
        // Arrange
        Patient patient = Patient.builder()
                .firstName("John")
                .lastName("Doe")
                .gender("M")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .build();

        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Act
        Patient savedPatient = patientService.addPatient(patient);

        // Assert
        assertNotNull(savedPatient);
        assertEquals("John", savedPatient.getFirstName());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    public void testUpdatePatient() {
        // Arrange
        Patient existingPatient = Patient.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender("M")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .build();

        Patient updatedPatient = Patient.builder()
                .firstName("Johnny")
                .lastName("Doe")
                .gender("M")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .email("johnny.doe@example.com")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);

        // Act
        Patient result = patientService.updatePatient(1L, updatedPatient);

        // Assert
        assertNotNull(result);
        assertEquals("Johnny", result.getFirstName());
        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    public void testDeletePatient() {
        // Arrange
        when(patientRepository.existsById(1L)).thenReturn(true);

        // Act
        patientService.deletePatient(1L);

        // Assert
        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeletePatient_NotFound() {
        // Arrange
        when(patientRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> patientService.deletePatient(1L));
        verify(patientRepository, times(1)).existsById(1L);
    }
}