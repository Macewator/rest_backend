package pl.javastart.equipy.model;

public class AssignmentsMapper {

    public static AssignmentsDto toDto(Assignments assignments) {
        AssignmentsDto dto = new AssignmentsDto();
        dto.setId(assignments.getId());
        dto.setStart(assignments.getStart());
        dto.setEnd(assignments.getEnd());
        dto.setUserId(assignments.getUser().getId());
        dto.setAssetId(assignments.getAsset().getId());
        return dto;
    }
}
