package biz.advanceitgroup.rdvserver.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostFilesDto {
	
	
	private int docType;
	private long jobID;
	private long userCategoryID;
	private long bidID;
	
	

}
