package com.example.demo.controllers;




import javax.servlet.http.HttpSession;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.PatientRepository;
import com.example.demo.services.securityService;
import com.example.demo.dao.UserRepository;
import com.example.demo.dao.patientEmailRepository;
import com.example.demo.helper.Message;
import com.example.demo.models.Patient;
import com.example.demo.models.User;
import com.example.demo.models.appointment;
import com.example.demo.models.patientEmail;



@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private patientEmailRepository patientEmailRepository;
	
	@Autowired
	private securityService securityService;
	
	
	@RequestMapping("/")
	public String home(Model model) 
	{
		
		model.addAttribute("title","Home Page HMS");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) 
	{
		model.addAttribute("title","About Page HMS");
		return "about";
	}
	
	@RequestMapping("/departments")
	public String departments(Model model) 
	{
		model.addAttribute("title","Departments Page HMS");
		return "departments";
	}
	
	@RequestMapping("/doctors")
	public String doctors(Model model) 
	{
		model.addAttribute("title","Doctors Page HMS");
		return "doctors";
	}
	
	@RequestMapping("/contact")
	public String contact(Model model) 
	{
		model.addAttribute("title","Contact Page HMS");
		return "contact";
	}
	
	
	@GetMapping("/postcontact")
	public String postcontact(Model model, HttpSession session) 
	{
		session.setAttribute("message", new Message("Your messsage is recieved. We will reply back shortly.", "alert-success" ));
		model.addAttribute("title","Contact Page HMS");
		return "contact";
	}
	
	
	@RequestMapping("/login")
	public String login(Model model) 
	{
		model.addAttribute("title","Login Page HMS");
		
		return "login";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) 
	{
		model.addAttribute("title","SignUp Page HMS");
		model.addAttribute("user",new User());
		model.addAttribute("patient",new Patient());
		patientEmail patientEmail = new patientEmail(); 
		model.addAttribute("email",patientEmail.getEmail());
		return "signup";
	}
	
	
	@PostMapping("/register")
	public String do_register(@RequestParam(value="username") String username,@RequestParam(value="password") String password,
			@RequestParam(value="first_name") String first_name, @RequestParam(value="last_name") String last_name, @RequestParam(value="gender") String gender,
			@RequestParam(value="dateOfBirth") String dateOfBirth,@RequestParam(value="address") String address,
			@RequestParam(value="email") String email, @RequestParam(value="phoneNumber") String phoneNumber, Model model, HttpSession session) 
	{
		
		model.addAttribute("title","register Page HMS");
		User user1 = new User();
		Patient patient1 = new Patient();
		patientEmail patientEmail1 = new patientEmail(); 
		user1.setUsername(username);
		user1.setPassword(password);
		user1.setRole("Patient");
		patient1.setFirst_name(first_name);
		patient1.setLast_name(last_name);
		patient1.setGender(gender);
		patient1.setDateOfBirth(dateOfBirth);
		patient1.setAddress(address);
		patient1.setPhoneNumber(phoneNumber);
		patient1.setUser(user1);
		patientEmail1.setEmail(email);
		
		try {
	        User user2 = this.userRepository.getvalidateuser(user1.getUsername());
			if(user2 != null) {
				throw new Exception(" This username is already taken. Choose another username !!");
			}
			if (!patient1.getPhoneNumber().matches("^[1-9][0-9]{9,9}$")) {
				throw new Exception(" The phone number must be of 10 digits. Enter correct phone number !!");		         
	        }
			this.userRepository.createUser(user1);
			this.patientRepository.createPatient(patient1);
			Patient patientUser =  this.patientRepository.getPatientByUsername(user1.getUsername());
			patientEmail1.setPatientId(patientUser.getPatientId());
			this.patientEmailRepository.createpatientEmail(patientEmail1);
			model.addAttribute("user",new User());
			model.addAttribute("patient",new Patient());
			patientEmail patientEmail = new patientEmail(); 
			model.addAttribute("email",patientEmail.getEmail());
			session.setAttribute("message", new Message("Successfully Registered !!", "alert-success" ));
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user1);
			model.addAttribute("patient",patient1);
			model.addAttribute("email",email);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "signup";
		}
		
		
	}
	
	
	@GetMapping("/welcome")
	public String user() {

		User user = securityService.findLoggedInUser();

		if (user == null)
			return "redirect:/login?error";

		if (user.getRole().equals("Admin"))
			return "redirect:/admin/index";

		else if (user.getRole().equals("Patient"))
			return "redirect:/patient/index";

		else if (user.getRole().equals("Doctor"))
			return "redirect:/doctor/index";

		else
			return "redirect:/login?error";

	}
	
	
	

}
