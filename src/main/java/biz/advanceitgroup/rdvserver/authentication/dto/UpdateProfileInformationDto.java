package biz.advanceitgroup.rdvserver.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProfileInformationDto {
	
	private String firstName;
	
	private String lastName;
	
	private String nickName; 
	private String phoneNumber;
	private String personnalAddress;
	private String professionnalAddress;
	private long activityRadius;
	private String businessDescription;
	private String paymentMode;
	private String paymentAccount;
	private String codeIsoLang;

}
