package biz.advanceitgroup.rdvserver.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private String name;
	private String description;
	private boolean requireEval;
	private String codeIsoLang;

}
