package pl.javastart.equipy.model;

public class UserAssignmentsMapper {

    public static UserAssignmentsDto toDto(Assignments assignments){
        UserAssignmentsDto dto = new UserAssignmentsDto();
        dto.setId(assignments.getId());
        dto.setStart(assignments.getStart());
        dto.setEnd(assignments.getEnd());
        dto.setAssetId(assignments.getAsset().getId());
        dto.setAssetName(assignments.getAsset().getName());
        dto.setSerialNumber(assignments.getAsset().getSerialNumber());
        return dto;
    }
}
