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

import biz.advanceitgroup.rdvserver.authentication.OnRegistrationCompleteEvent;
import biz.advanceitgroup.rdvserver.authentication.dto.AdminRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.dto.CategoryDto;
import biz.advanceitgroup.rdvserver.authentication.dto.PaymentInfosDto;
import biz.advanceitgroup.rdvserver.authentication.dto.PostFilesDto;
import biz.advanceitgroup.rdvserver.authentication.dto.QuizzDto;
import biz.advanceitgroup.rdvserver.authentication.dto.ReportTemplateDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UpdateProfileInformationDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UserClientDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UserLoginDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UserRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.entities.Category;
import biz.advanceitgroup.rdvserver.authentication.entities.Quizz;
import biz.advanceitgroup.rdvserver.authentication.entities.ReportTemplate;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.repository.CategoryRepository;
import biz.advanceitgroup.rdvserver.authentication.repository.FileRepository;
import biz.advanceitgroup.rdvserver.authentication.repository.QuizzQuestionRepository;
import biz.advanceitgroup.rdvserver.authentication.repository.QuizzQuestionResponseRepository;
import biz.advanceitgroup.rdvserver.authentication.repository.QuizzRepository;
import biz.advanceitgroup.rdvserver.authentication.repository.ReportTemplateRepository;
//import biz.advanceitgroup.rdvserver.authentication.entities.File;
import biz.advanceitgroup.rdvserver.authentication.repository.UserRepository;
import biz.advanceitgroup.rdvserver.authentication.services.impl.UserDetailsServiceImpl;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.FileService;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;
import io.swagger.annotations.Api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationEventPublisher;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;



@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin(origins = "*")
@Api(value = "Authentication Rest API", description = "Définition des API d'authentification.")
public class AuthRestController {
	
	
	
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
	
	
	 
	
	//@Value("${spring.profiles.active:}")
	private String activeProfile;
	
	
	//Manual test : OK
	
	@PostMapping("/user/registerUserAccount")
	public ResponseEntity<?> registerUserAccount (@Valid @RequestBody UserRegistrationDto userRegistrationDto,HttpServletRequest request)
	{
		
		User user = userService.registerNewUserAccount(userRegistrationDto);
		
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
		
		UserClientDto userClientDto = new UserClientDto();
		
		userClientDto.setEmail(user.getEmail());
		
		
		
		userClientDto.setEnabled(user.isEnabled());
		
		userClientDto.setValidated(user.isValidated());
		
		userClientDto.setAccountStatus(user.getAccountStatus());
		
		userClientDto.setRoles(user.getRoles()); 
		
		
		
		return new ResponseEntity<>(userClientDto,HttpStatus.OK);
		
	}
	
	
	//Manual test : OK
	
	@PostMapping("/user/activateEmailAccount")
	public ResponseEntity<?> activateEmailAccount (@RequestParam("token") String token)
	{
		
		String result = userService.validateVerificationToken(token);
		
		
		
        if (result.equals("valid")) {
        	
            User user = userService.getUser(token);
      
    		
            
            return new ResponseEntity<>(user,HttpStatus.OK) ;
            
            	
        }

 	
        return null ;
		
	}
	

	//Manual test : OK
	
	@PostMapping("/user/login")
	public ResponseEntity<?> login (@Valid @RequestBody UserLoginDto userLoginDto,HttpServletRequest request)
	{
		
		try {
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDto.getEmail());
			
		    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
		    
		    SecurityContext sc = SecurityContextHolder.getContext();
		    
		    sc.setAuthentication(authentication);
		    
		    return new ResponseEntity<>(userLoginDto,HttpStatus.OK) ;
		     
			
		}catch(UsernameNotFoundException e) {
			
			return null ;
			
		}
		
		
		
	}
	
	/*  Google
	
	"/oauth2/authorization/google" -> href
	
		user/loginGG
	
		user/registerUserAccountByGG
	
	
	*/
	
	/* Facebook
	
	"/oauth2/authorization/facebook" -> href
	
		user/loginFB
	
		user/registerUserAccountByFB
	
	
	
	*/
	
	/*
	 
	 Les API Facebook & Google ne peuvent qu'être testées de manière manuelle.
	 Il suffit de mettre les url mentionnées plus haut dans le href correspendant.
	  
	 */
	
	/*
	 
	 
	  
	 
	 */
	
	
	
	
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
		
		
			return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	
	}
	

}

