package biz.advanceitgroup.rdvserver.authentication.webs;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import biz.advanceitgroup.rdvserver.admins.dto.AdminRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.dto.ForgottenPasswordEmailDto;
import biz.advanceitgroup.rdvserver.authentication.dto.ResetPasswordDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UpdateProfileInformationDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UserClientDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UserLoginDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UserRegistrationDto;
import biz.advanceitgroup.rdvserver.jobs.entities.Category;
import biz.advanceitgroup.rdvserver.quizz.entities.Quizz;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.entities.enumeration.AuthProvider;
import biz.advanceitgroup.rdvserver.authentication.event.OnForgottenPasswordEvent;
import biz.advanceitgroup.rdvserver.authentication.event.OnRegistrationCompleteEvent;
import biz.advanceitgroup.rdvserver.authentication.exceptions.EmailDoesntExistException;
import biz.advanceitgroup.rdvserver.authentication.exceptions.NotEnabledException;
import biz.advanceitgroup.rdvserver.authentication.exceptions.ProviderException;
import biz.advanceitgroup.rdvserver.authentication.exceptions.RoleDoesntExistException;
import biz.advanceitgroup.rdvserver.authentication.exceptions.UpdatingSamePasswordException;
import biz.advanceitgroup.rdvserver.authentication.exceptions.UserAlreadyExistException;
import biz.advanceitgroup.rdvserver.authentication.repositories.UserRepository;
import biz.advanceitgroup.rdvserver.authentication.security.TokenProvider;
import biz.advanceitgroup.rdvserver.authentication.security.oauth2.OAuth2AuthenticationSuccessHandler;
import biz.advanceitgroup.rdvserver.authentication.services.impl.UserDetailsServiceImpl;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;
import biz.advanceitgroup.rdvserver.commons.dto.PostFilesDto;
import biz.advanceitgroup.rdvserver.commons.dto.ReportTemplateDto;
import biz.advanceitgroup.rdvserver.commons.entities.ApiResponse;
import biz.advanceitgroup.rdvserver.commons.entities.AuthResponse;
import biz.advanceitgroup.rdvserver.commons.entities.ReportTemplate;
import biz.advanceitgroup.rdvserver.commons.repositories.FileRepository;
import biz.advanceitgroup.rdvserver.commons.repositories.ReportTemplateRepository;
import biz.advanceitgroup.rdvserver.commons.services.interfaces.FileService;
import biz.advanceitgroup.rdvserver.jobs.dto.CategoryDto;
import biz.advanceitgroup.rdvserver.jobs.repositories.CategoryRepository;
import biz.advanceitgroup.rdvserver.payments.dto.PaymentInfosDto;
import biz.advanceitgroup.rdvserver.quizz.dto.QuizzDto;
import biz.advanceitgroup.rdvserver.quizz.repositories.QuizzQuestionRepository;
import biz.advanceitgroup.rdvserver.quizz.repositories.QuizzQuestionResponseRepository;
import biz.advanceitgroup.rdvserver.quizz.repositories.QuizzRepository;
import io.swagger.annotations.Api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationEventPublisher;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;



@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin(origins = "*")
@Api(value = "Authentication Rest API", description = "Définition des API d'authentification.")
public class AuthRestController {
	
	
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private ReportTemplateRepository reportTemplateRepository;
	
	@Autowired
	private QuizzRepository quizzRepository;
	
	@Autowired
	private QuizzQuestionRepository quizzQuestionRepository;
	
	@Autowired
	private QuizzQuestionResponseRepository quizzQuestionResponseRepository;
	
	
	
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();


	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private TokenProvider tokenProvider;
	
	
	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	
	
	
	//Manual test : OK
	
	@PostMapping("/user/registerUserAccount")
	public ApiResponse registerUserAccount (@Valid @RequestBody UserRegistrationDto userRegistrationDto,HttpServletRequest request)
	{
		
		
		
		try
		{
			User user = userService.registerNewUserAccount(userRegistrationDto);
			
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
			
			UserClientDto userClientDto = new UserClientDto();
			
			userClientDto.setEmail(user.getEmail());
			
			
			
			userClientDto.setEnabled(user.isEnabled());
			
			userClientDto.setValidated(user.isValidated());
			
			userClientDto.setAccountStatus(user.getAccountStatus());
			
			userClientDto.setRoles(user.getRoles()); 
			
			return new ApiResponse(HttpStatus.OK," Successful Registration. Now, just active your email !",userClientDto);

			
		}catch(UserAlreadyExistException e) {
			
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());

			
			
		}catch(RoleDoesntExistException e) {
			
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());

			
			
		}catch(Exception e) {
			
			return new ApiResponse("An UnHandledException occured; To learn more, please see the message.", e.getMessage());

		}
		
		
	}
	
	
	//Manual test : OK
	
	/*
	 * IDEAS :
	 * 1- Send email to inform the user about the success of email activation
	 */
	
	@GetMapping("/user/activateEmailAccount")
	public ApiResponse activateEmailAccount (@RequestParam("token") String token)
	{
		
		String result = userService.validateVerificationToken(token);
		
		
		
        if (result.equals("valid")) {
        	
            User user = userService.getUserFromVerificationToken(token);
      
            
            return new ApiResponse(HttpStatus.OK,"Email activation : Success",user);
            
            	
        }

 	
        return new ApiResponse("STATUS : FAILURE"," Email activation failed");
		
	}
	

	//Manual test : OK
	
	@PostMapping("/user/login")
	public ResponseEntity<?> login (@Valid @RequestBody UserLoginDto userLoginDto)
	{
		/*
			Optional<Authentication> authenticationOpt = authService.authenticateUser(userLoginDto.getEmail(), userLoginDto.getPassword());
			authenticationOpt.orElseThrow(() -> new UserLoginException("Couldn't login user [" + userLoginDto + "]"));
			Authentication authentication = authenticationOpt.get();
			//UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			
			
			SecurityContext sc = SecurityContextHolder.getContext();
			    
			sc.setAuthentication(authentication);
			
			*/
		
		
		try
		{
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDto.getEmail());
			
			if(userDetails.isEnabled())
			{
				if(passwordEncoder.matches(userLoginDto.getPassword(), userDetails.getPassword()))
				 {
					Authentication authentication = authenticationManager.authenticate(
			                new UsernamePasswordAuthenticationToken(
			                		userLoginDto.getEmail(),
			                		userLoginDto.getPassword()
			                )
			        );
					
					SecurityContextHolder.getContext().setAuthentication(authentication);

			        String token = tokenProvider.createToken(authentication);
			        
			        long id = tokenProvider.getUserIdFromToken(token);
			        
			        return new ResponseEntity<>(new AuthResponse(token,id,userLoginDto.getRole(),tokenProvider.getExpirationDate(token)),HttpStatus.OK);
					
					
					
						
				 }else {
					 
					 return new ResponseEntity<>(new ApiResponse("FAILURE","Wrong Password"),HttpStatus.EXPECTATION_FAILED);
						
					 
					 
						 
					 }

				
			}else {
				
			
				return new ResponseEntity<>(new ApiResponse("You are not authorized to login. Just activate your email first."),HttpStatus.UNAUTHORIZED);
				
			
			}

			
		}catch(UsernameNotFoundException e) {
			
		
			return new ResponseEntity<>(new ApiResponse(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
			
	
	}
	
	/*  Google
	
	"/oauth2/authorization/google?role=ROLE_WORKER" -> href
	
		user/loginGG
	
		user/registerUserAccountByGG
	
	
	*/
	
	/* Facebook
	
	"/oauth2/authorization/facebook?role=ROLE_WORKER" -> href
	
		user/loginFB
	
		user/registerUserAccountByFB
	
	
	
	*/
	
	/*
	 
	 Les API Facebook & Google ne peuvent qu'être testées de manière manuelle.
	 Il suffit de mettre les url mentionnées plus haut dans le href correspendant.
	  
	 */
	
	/*
	 
	 
	  
	 
	 */
	
	
	
	/*
	
	@GetMapping("/oauth2/authorization/google")
	public String getRoleWithGoogleRegisterOrLogin (@RequestParam("role") String role)
	{
		
		
		return role;
		
		 
	}
	
	*/
	
	@GetMapping("/user/authResponseFromSocialLogin")
    public ResponseEntity<?> authResponseFromSocialLogin() {
		
        
        return new ResponseEntity<>(oAuth2AuthenticationSuccessHandler.getAuthResponse(),HttpStatus.OK);

    }
	
	
	//Manual test : NONE
	@PostMapping("/user/registerAdminUser")
	public ResponseEntity<?> registerAdminUser (@Valid @RequestBody AdminRegistrationDto adminRegistrationDto,HttpServletRequest request)
	{
		
		User user = userService.registerNewAdminAccount(adminRegistrationDto);
		
		UserClientDto userClientDto = new UserClientDto();
		
		userClientDto.setEmail(user.getEmail());
		userClientDto.setFirstName(user.getFirstName());
		userClientDto.setLastName(user.getLastName());
		
		userClientDto.setNickName(user.getNickName());
		
		userClientDto.setRoles(user.getRoles());
		
		userClientDto.setRegistrationRole(user.getRegistrationRole());
		
		
		
		return new ResponseEntity<>(userClientDto,HttpStatus.OK);
		
	}
	
	@PostMapping("/user/forgottenPassword")
	public ApiResponse forgottenPassword (@Valid @RequestBody ForgottenPasswordEmailDto forgottenPasswordEmailDto,HttpServletRequest request)
	{
		
		try
		{
			
			if(userService.emailExists(forgottenPasswordEmailDto.getEmail()))
			{
				
				User user = userService.findByEmail(forgottenPasswordEmailDto.getEmail());
				
				if(user.getProvider()==AuthProvider.local)
				{
					
					if(user.isEnabled())
					{
						eventPublisher.publishEvent(new OnForgottenPasswordEvent(user, request.getLocale(), getAppUrl(request)));
						
						
					}else {
						
						throw new NotEnabledException("Please, your account is not activated ! Activate your account first !");
						
						
						
					}
					
					
					
				}else {
					
					throw new ProviderException("Please log in with : "+user.getProvider()+" ! No need to use a password. Use the provider you choose when you signed up.");
					
					
				}
				
				
			}else {
				
				throw new EmailDoesntExistException("No account with this email address ! ");

			}
			
			return new ApiResponse(HttpStatus.OK," Check your email to reset password !");

			
		}catch(EmailDoesntExistException | ProviderException | NotEnabledException e) {
			
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());

		}
		
	}
	
	@GetMapping("/user/resetPassword")
	public ApiResponse resetPassword (@RequestParam("token") String token)
	{
		
		String result = userService.validateForgottenPasswordToken(token);
		
		
		
        if (result.equals("valid")) {
        	
            User user = userService.getUserFromForgottenPasswordToken(token);
            
            //String email = user.getEmail();
      
            
            return new ApiResponse(HttpStatus.OK,"It's time to reset your password !",user);
            
            	
        }

 	
        return new ApiResponse("STATUS : FAILURE"," Token doesn't work ! ");
		
	}
	
	@PutMapping("/user/confirmResetPassword")
	public ApiResponse confirmResetPassword (@Valid @RequestBody ResetPasswordDto resetPasswordDto,HttpServletRequest request)
	{
		try
		{
			
			
			if(userService.emailExists(resetPasswordDto.getEmail()))
			{
				User user = userService.findByEmail(resetPasswordDto.getEmail());
				
				if(user.getProvider()==AuthProvider.local)
				{
					
					if(user.isEnabled())
					{
						
						if(passwordEncoder.matches(resetPasswordDto.getPassword(), user.getEncryptPwd()))
						{
							//We don't update same password
							
							throw new UpdatingSamePasswordException("Can't update same password");
							
							
						}else {
							
							userService.updatePassword(resetPasswordDto.getPassword(), resetPasswordDto.getEmail());
							

							return new ApiResponse(HttpStatus.OK," Successful Password Update !");
						
						
						}
							
					
					}else {
						
						throw new NotEnabledException("Please, your account is not activated ! Activate your account first !");
						
					}
					
				}else {
					
					throw new ProviderException("Please log in with : "+user.getProvider()+" ! No need to use a password. Use the provider you choose when you signed up.");
					
				}
				
			}else {
				
				throw new EmailDoesntExistException("No account with this email address ! ");
			}
			
		
		}catch(EmailDoesntExistException | ProviderException | NotEnabledException | UpdatingSamePasswordException e) {
			
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());

		}
		
		
	}
	

	
	//Manual test : NONE
	
	@GetMapping("/user/setPaymentInfos/{userId}")
	public void setPaymentInfos (@PathVariable Long userId,@Valid @RequestBody PaymentInfosDto paymentInfosDto)
	{
		
		 userService.setPaymentInfosById(paymentInfosDto.getPaymentMode(), paymentInfosDto.getPaymentAccount(), userId);
	
	}
	
	
	/*
	 
	 https://stackoverflow.com/questions/11881479/how-do-i-update-an-entity-using-spring-data-jpa
	 
	 */
	//Manual test : NONE
	
	@PutMapping("/user/updateProfileInformation/{userId}")
	public void updateProfileInformation (@PathVariable Long userId,@RequestBody UpdateProfileInformationDto updateProfileInformationDto)
	{
		    
		 userService.updateProfileInformation(userId, updateProfileInformationDto);
		
	}
	
	//Manual test : NONE
	
	@GetMapping("/user/getProfileInformation/{userId}")
	public ResponseEntity<?> getProfileInformation (@PathVariable Long userId)
	{
		    
		 User user = userService.findById(userId);
		    
		 return new ResponseEntity<>(user,HttpStatus.OK) ;
	
		
	}
	
	//Manual test : NONE
	
		@GetMapping("/user/{userId}")
		public ResponseEntity<?> getUser (@PathVariable Long userId)
		{
			    
			 User user = userService.findById(userId);
			    
			 return new ResponseEntity<>(user,HttpStatus.OK) ;
		
			
		}
		
		//Manual test : NONE
		
	
		
		@PostMapping(value="/files/postFiles ",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<?> postFiles (@RequestParam("file") MultipartFile file, @RequestBody PostFilesDto postFilesDto) throws IOException
		{
			
			
			File convertFile = new File("/var/tmp/"+file.getOriginalFilename());
		      convertFile.createNewFile();
		   long id =   fileService.postFiles(postFilesDto).getFileID();
		   
		      FileOutputStream fout = new FileOutputStream(convertFile);
		      fout.write(file.getBytes());
		      fout.close();
		    
		      
		      return new ResponseEntity<>("File is upload successfully",HttpStatus.OK) ;
			
		}
		
		
		@PutMapping(value="/files/putFiles ",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<?> putFiles (@RequestParam("file") MultipartFile file, @RequestBody PostFilesDto postFilesDto) throws IOException
		{
			
			
			File convertFile = new File("/var/tmp/"+file.getOriginalFilename());
		      convertFile.createNewFile();
		   
		      FileOutputStream fout = new FileOutputStream(convertFile);
		      fout.write(file.getBytes());
		      fout.close();
		    
		      
		      return new ResponseEntity<>("File is updated successfully",HttpStatus.OK) ;
			
		}
		
		@GetMapping(value="/files/getFileById/{fileID} ")
		public ResponseEntity<?> getFileById ( @PathVariable Long fileID) 
		{
			  
		      return new ResponseEntity<>(fileRepository.getOne(fileID).getFileURL(),HttpStatus.OK) ;
			
		}
		
		@DeleteMapping(value="/files/deleteFileById/{fileID} ")
		public ResponseEntity<?> deleteFileById ( @PathVariable Long fileID) 
		{
			  
			fileRepository.deleteById(fileID); 
			
			return new ResponseEntity<>("File is deleted successfully",HttpStatus.OK) ;
			
		}
		
		@PostMapping(value="/addCategory/{categoryID}/{userID}")
		public ResponseEntity<?> addCategory ( @PathVariable Long categoryID,@PathVariable Long userID) 
		{
			
			
			Category category = categoryRepository.getOne(categoryID);
			
			User user = userRepository.getOne(userID);
			
			
			
			user.getCategories().add(category);
			
			
			
			userRepository.save(user);
			
			
			return new ResponseEntity<>("Category is added successfully",HttpStatus.OK) ;
			
		}
		
		
		@PutMapping(value="/category/{categoryID}")
		public ResponseEntity<?> updateCategory ( @PathVariable Long categoryID, @RequestBody CategoryDto categoryRegistrationDto) 
		{
			
			
			Category category = categoryRepository.getOne(categoryID);
			
			
			
			if(categoryRegistrationDto.getCodeIsoLang().equals("en"))
			{
				
				category.setDescription_En(categoryRegistrationDto.getDescription());
				
				category.setName_En(categoryRegistrationDto.getName());
				
			}
			
			if(categoryRegistrationDto.getCodeIsoLang().equals("fr"))
			{
				category.setDescription_Fr(categoryRegistrationDto.getDescription());
				
				category.setName_Fr(categoryRegistrationDto.getName());
				
				
			}
			
			category.setRequireEval(categoryRegistrationDto.isRequireEval());
			
			categoryRepository.save(category);
			
		
			
			return new ResponseEntity<>("Category is updated successfully",HttpStatus.OK) ;
			
		}
		
		@GetMapping(value="/category/{categoryID}")
		public ResponseEntity<?> getCategory ( @PathVariable Long categoryID) 
		{
		
			Category category = categoryRepository.getOne(categoryID);
	
			return new ResponseEntity<>(category,HttpStatus.OK) ;
			
		}
		
		@GetMapping(value="/category/all")
		public ResponseEntity<List<CategoryDto>> getAllCategory ()
		{
		
			List<Category> categories = categoryRepository.findAll();
			List<CategoryDto> categoriesDto = categories.stream().map(CategoryDto::new).collect(Collectors.toList());
	
			return new ResponseEntity<>(categoriesDto,HttpStatus.OK) ;
			
		}
		
		@DeleteMapping(value="/category/{categoryID}")
		public ResponseEntity<?> deleteCategory ( @PathVariable Long categoryID) 
		{
		
			categoryRepository.deleteById(categoryID);
	
			return new ResponseEntity<>("Category deleted succesfully",HttpStatus.OK) ;
			
		}
		
		@PostMapping(value="/category")
		public ResponseEntity<?> registerCategory ( @Valid @RequestBody CategoryDto categoryRegistrationDto) 
		{
			
			
			Category category = new Category();
			
			if(categoryRegistrationDto.getCodeIsoLang().equals("en"))
			{
				
				category.setDescription_En(categoryRegistrationDto.getDescription());
				
				category.setName_En(categoryRegistrationDto.getName());
				
			}
			
			if(categoryRegistrationDto.getCodeIsoLang().equals("fr"))
			{
				category.setDescription_Fr(categoryRegistrationDto.getDescription());
				
				category.setName_Fr(categoryRegistrationDto.getName());
				
				
			}
			
			category.setRequireEval(categoryRegistrationDto.isRequireEval());
			
			categoryRepository.save(category);
			
		
			
			return new ResponseEntity<>("Category is registered successfully",HttpStatus.OK) ;
			
		}
	
		
		@PostMapping(value="/removeCategory/{categoryID}/{userID}")
		public ResponseEntity<?> removeCategory ( @PathVariable Long categoryID,@PathVariable Long userID) 
		{
			
			
			Category category = categoryRepository.getOne(categoryID);
			
			User user = userRepository.getOne(userID);
		
			user.getCategories().remove(category);
			
			userRepository.save(user);
			
			
			return new ResponseEntity<>("Category is deleted successfully",HttpStatus.OK) ;
			
		}
		
		@PostMapping(value="/reportTemplate")
		public ResponseEntity<?> reportTemplate ( @Valid @RequestBody ReportTemplateDto rt) 
		{
			
			ReportTemplate reportTemplate = new ReportTemplate();
			
			if(rt.getCodeIsoLang().equals("en"))
			{
				
				reportTemplate.setDescription_En(rt.getDescription());
				
			}
			
			if(rt.getCodeIsoLang().equals("fr"))
			{
				reportTemplate.setDescription_Fr(rt.getDescription());
				
			}
			
			reportTemplate.setPath(rt.getPath());
			reportTemplate.setCode(rt.getCode());
			
		
			reportTemplateRepository.save(reportTemplate);
			
		
			
			return new ResponseEntity<>("ReportTemplate is registered successfully",HttpStatus.OK) ;
			
		}
		
		@PostMapping(value="/quizz")
		public ResponseEntity<?> registerQuizz ( @Valid @RequestBody QuizzDto quizzDto) 
		{
			
			
			Quizz quizz = new Quizz();
			
			if(quizzDto.getCodeIsoLang().equals("en"))
			{
				
				
				
				quizz.setDescription_En(quizzDto.getDescription());
				
				quizz.setName_En(quizzDto.getName());
				
			}
			
			if(quizzDto.getCodeIsoLang().equals("fr"))
			{
				quizz.setDescription_Fr(quizzDto.getDescription());
				
				quizz.setName_Fr(quizzDto.getName());
				
				
			}
			
			//quizz.setCategoryID(quizzDto.getCategoryID());
			
			quizzRepository.save(quizz);
			
		
			
			return new ResponseEntity<>("Quizz is registered successfully",HttpStatus.OK) ;
			
		}
		
		@GetMapping(value="/quizz/{quizzID}")
		public ResponseEntity<?> getQuizz ( @PathVariable Long quizzID) 
		{
		
			Quizz quizz = quizzRepository.getOne(quizzID);
	
			return new ResponseEntity<>(quizz,HttpStatus.OK) ;
			
		}
		
		@DeleteMapping(value="/quizz/{quizzID}")
		public ResponseEntity<?> deleteQuizz ( @PathVariable Long quizzID) 
		{
		
			quizzRepository.deleteById(quizzID);
	
			return new ResponseEntity<>("Quizz is deleted successfully",HttpStatus.OK) ;
			
		}
		
		/*
		
		@DeleteMapping(value="/quizz/{quizzID}")
		public ResponseEntity<?> deleteQuizzQuestionResponse ( @PathVariable Long quizzID) 
		{
		
			quizzRepository.deleteById(quizzID);
	
			return new ResponseEntity<>("Quizz, Questions & Responses are deleted successfully",HttpStatus.OK) ;
			
		}
		
		
		*/

		
		@PutMapping(value="/quizz/{quizzID}")
		public ResponseEntity<?> updateQuizz ( @PathVariable Long quizzID, @RequestBody QuizzDto quizzDto) 
		{
			
			
			Quizz quizz = quizzRepository.getOne(quizzID);
			
			if(quizzDto.getCodeIsoLang().equals("en"))
			{
				
				quizz.setDescription_En(quizzDto.getDescription());
				
				quizz.setName_En(quizzDto.getName());
				
			}
			
			if(quizzDto.getCodeIsoLang().equals("fr"))
			{
				quizz.setDescription_Fr(quizzDto.getDescription());
				
				quizz.setName_Fr(quizzDto.getName());
				
				
			}
			
			//quizz.setCategoryID(quizzDto.getCategoryID());
			
			quizzRepository.save(quizz);
			
			
			return new ResponseEntity<>("Quizz is updated successfully",HttpStatus.OK) ;
			
		}

	
	
	
	
	private String getAppUrl(HttpServletRequest request) {
		
		
		if(request.isSecure())
		{
			return "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			
		}else {
			
			return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			
		}
		
		
	
	}
	

}

