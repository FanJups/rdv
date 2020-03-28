package biz.advanceitgroup.rdvserver.jobs.dto;

import biz.advanceitgroup.rdvserver.jobs.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private Long id;
	private String name;
	private String description;
	private boolean requireEval;
	private String codeIsoLang;

	public CategoryDto(Category category) {
		this.id = category.getId();
		this.name = category.getName_En() != null ? category.getName_En() : category.getName_Fr();
		this.description = category.getDescription_En() != null ? category.getDescription_En() : category.getDescription_Fr();
		this.requireEval = category.getRequireEval();
	}

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isRequireEval() {
		return requireEval;
	}

	public void setRequireEval(boolean requireEval) {
		this.requireEval = requireEval;
	}

	public String getCodeIsoLang() {
		return codeIsoLang;
	}

	public void setCodeIsoLang(String codeIsoLang) {
		this.codeIsoLang = codeIsoLang;
	}

	@Override
	public String toString() {
		return "CategoryDto{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", requireEval=" + requireEval +
				", codeIsoLang='" + codeIsoLang + '\'' +
				'}';
	}
}
