package pl.javastart.equipy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javastart.equipy.model.AssignmentsDto;
import pl.javastart.equipy.service.AssignmentsService;

import java.net.URI;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentsController {

    private AssignmentsService assignmentsService;

    public AssignmentsController(AssignmentsService assignmentsService) {
        this.assignmentsService = assignmentsService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssignmentsDto> createAssignment(@RequestBody AssignmentsDto assignment) {
        if (assignment.getUserId() == null || assignment.getAssetId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id nie może być puste");
        AssignmentsDto savedAssignment = assignmentsService.creatAssignments(assignment);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(assignment.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedAssignment);
    }

    @PostMapping("{assignmentId}/end")
    public ResponseEntity<?> endAssignment(@PathVariable Long assignmentId) {
        return ResponseEntity.ok(assignmentsService.endAssignment(assignmentId));
    }
}
