package biz.advanceitgroup.rdvserver.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizzDto {
	
	private String name;
	private String description;
	private String codeIsoLang;
	private long categoryID;

}
