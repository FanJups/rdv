package biz.advanceitgroup.rdvserver.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportTemplateDto {
	
	private String code;
	private String description;
	private String path;
	private String codeIsoLang;
	
	

}
