package pl.javastart.equipy.model;

public class AssetAssignmentsMapper {

    public static AssetAssignmentsDto toDto(Assignments assignments){
        AssetAssignmentsDto dto = new AssetAssignmentsDto();
        dto.setId(assignments.getId());
        dto.setStart(assignments.getStart());
        dto.setEnd(assignments.getEnd());
        dto.setUserId(assignments.getUser().getId());
        dto.setFirstName(assignments.getUser().getFirstName());
        dto.setLastName(assignments.getUser().getLastName());
        dto.setPesel(assignments.getUser().getPesel());
        return dto;
    }
}
