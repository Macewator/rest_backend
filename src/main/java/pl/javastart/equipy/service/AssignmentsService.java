package pl.javastart.equipy.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.javastart.equipy.model.*;
import pl.javastart.equipy.repository.AssetRepository;
import pl.javastart.equipy.repository.AssignmentsRepository;
import pl.javastart.equipy.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AssignmentsService {

    private AssignmentsRepository assignmentsRepository;
    private AssetRepository assetRepository;
    private UserRepository userRepository;

    public AssignmentsService(AssignmentsRepository assignmentsRepository, AssetRepository assetRepository, UserRepository userRepository) {
        this.assignmentsRepository = assignmentsRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    public AssignmentsDto creatAssignments(AssignmentsDto assignmentDto) {
        Optional<Assignments> activeAssignmentForAsset = assignmentsRepository
                .findByAsset_IdAndEndIsNull(assignmentDto.getAssetId());
        activeAssignmentForAsset.ifPresent((a) -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "To wyposażenie jest aktualnie do kogoś przypisane");
        });

        User user = userRepository.findById(assignmentDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "użytkonik nie istnieje"));

        Asset asset = assetRepository.findById(assignmentDto.getAssetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "zasób nie istnieje"));

        Assignments assignments = new Assignments();
        assignments.setStart(LocalDateTime.now());
        assignments.setAsset(asset);
        assignments.setUser(user);
        return mapAndSaveAssignment(assignments);
    }

    public LocalDateTime endAssignment(Long assignmentId){
        Assignments assignment = assignmentsRepository.findById(assignmentId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie istnieje przypisanie o takim ID"));

        if(assignment.getEnd() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "To przypisanie już zostało zakończone");
        }else {
            assignment.setEnd(LocalDateTime.now());
        }

        assignmentsRepository.save(assignment);
        return assignment.getEnd();
    }

    private AssignmentsDto mapAndSaveAssignment(Assignments assignments) {
        Assignments savedAssignment = assignmentsRepository
                .save(assignments);
        return AssignmentsMapper.toDto(savedAssignment);
    }
}
