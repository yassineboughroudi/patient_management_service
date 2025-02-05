package org.universiapolis.fablab.pfe.patient_management_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.universiapolis.fablab.pfe.patient_management_service.model.Patient;
import org.universiapolis.fablab.pfe.patient_management_service.service.PatientService;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPatients() throws Exception {
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

        when(patientService.getAllPatients()).thenReturn(List.of(patient1, patient2));

        // Act & Assert
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    public void testGetPatientById() throws Exception {
        // Arrange
        Patient patient = Patient.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender("M")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .build();

        when(patientService.getPatientById(1L)).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void testAddPatient() throws Exception {
        // Arrange
        Patient patient = Patient.builder()
                .firstName("John")
                .lastName("Doe")
                .gender("M")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .build();

        when(patientService.addPatient(any(Patient.class))).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void testUpdatePatient() throws Exception {
        // Arrange
        Patient updatedPatient = Patient.builder()
                .firstName("Johnny")
                .lastName("Doe")
                .gender("M")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .email("johnny.doe@example.com")
                .build();

        when(patientService.updatePatient(anyLong(), any(Patient.class))).thenReturn(updatedPatient);

        // Act & Assert
        mockMvc.perform(put("/api/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Johnny"));
    }

    @Test
    public void testDeletePatient() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Patient with ID 1 deleted successfully."));
    }
}