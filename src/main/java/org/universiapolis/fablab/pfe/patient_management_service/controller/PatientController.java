package org.universiapolis.fablab.pfe.patient_management_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.universiapolis.fablab.pfe.patient_management_service.model.Patient;
import org.universiapolis.fablab.pfe.patient_management_service.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "Retrieve all patients", description = "Fetches all registered patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of patients retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Patient.class)))
    })
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @Operation(summary = "Retrieve patient by ID", description = "Fetches a single patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Patient.class))),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @Operation(summary = "Add a new patient", description = "Registers a new patient in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Patient.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.addPatient(patient));
    }

    @Operation(summary = "Update a patient's details", description = "Updates an existing patient's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.updatePatient(id, patient));
    }

    @Operation(summary = "Delete a patient", description = "Deletes a patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient with ID " + id + " deleted successfully.");
    }
}
