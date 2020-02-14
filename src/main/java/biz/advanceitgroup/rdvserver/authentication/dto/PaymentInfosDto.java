package biz.advanceitgroup.rdvserver.authentication.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfosDto {
	
	
	
	@NotNull
    @NotEmpty
	private String paymentMode;
	@NotNull
    @NotEmpty
	private String paymentAccount;
	private String codeIsoLang;

}
