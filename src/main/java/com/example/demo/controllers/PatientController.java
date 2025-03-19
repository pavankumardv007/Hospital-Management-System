package com.example.demo.controllers;


import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.BillRepository;
import com.example.demo.dao.InPatientRepository;
import com.example.demo.dao.LabReportRepository;
import com.example.demo.dao.OutPatientRepository;
import com.example.demo.dao.PatientRepository;
import com.example.demo.dao.appointmentRepository;
import com.example.demo.dao.doctorRepository;
import com.example.demo.dao.patientEmailRepository;
import com.example.demo.helper.Message;
import com.example.demo.models.Bill;
import com.example.demo.models.InPatient;
import com.example.demo.models.LabReport;
import com.example.demo.models.OutPatient;
import com.example.demo.models.Patient;
import com.example.demo.models.User;
import com.example.demo.models.appointment;
import com.example.demo.models.doctor;
import com.example.demo.models.doctorEmail;
import com.example.demo.models.patientEmail;
import com.example.demo.services.securityService;

@Controller
@RequestMapping("/patient")
public class PatientController {
	
	
	@Autowired
	private securityService securityService;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private patientEmailRepository patientEmailRepository;
	
	@Autowired
	private doctorRepository doctorRepository;
	
	@Autowired
	private appointmentRepository appointmentRepository;
	
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private LabReportRepository labReportRepository;
	
	@Autowired
	private InPatientRepository inPatientRepository;
	
	@Autowired
	private OutPatientRepository outPatientRepository;
	
	@RequestMapping("/index")
	public String dashboard(Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		model.addAttribute("title","User Dashboard");
		return "patient/patient_dashboard";
	}
	
	
	@GetMapping("/bookappointment")
	public String getbookappointment(Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		appointment appointment = new appointment();
		model.addAttribute("appointment",appointment);
		List<doctor> doctors = this.doctorRepository.getAlldoctors();
		model.addAttribute("doctors",doctors);
		model.addAttribute("title","User Dashboard");
		return "patient/book_appointment";
	}
	
	
	@PostMapping("/bookappointment")
	public String postbookappointment(@ModelAttribute(name = "appointment") appointment appointment, @RequestParam(value="doctorId") int doctorId,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		appointment.setPatient(patient);
		doctor doctor = new doctor();
		doctor.setDoctorId(doctorId);
		appointment.setDoctor(doctor);
		List<doctor> doctors = this.doctorRepository.getAlldoctors();
		model.addAttribute("doctors",doctors);
		
		try {
            appointment appointment2 = this.appointmentRepository.getvalidateappointment(doctorId, appointment.getAppointmentDate(), appointment.getAppointmentTime());
			
			if(appointment2 != null) {
				throw new Exception("This slot is already booked. Choose another slot !!");
			}
			this.appointmentRepository.createappointment(appointment);
			appointment appointment1 = new appointment();
			model.addAttribute("appointment",appointment1);
			session.setAttribute("message", new Message("Successfully booked appointment !!", "alert-success" ));
			return "patient/book_appointment";
			
		} catch (Exception e) {
			model.addAttribute("appointment",appointment);
			session.setAttribute("message", new Message("Something went wrong !! "+e.getMessage(), "alert-danger" ));
			return "patient/book_appointment";
		}
	
	}
	
	@RequestMapping("/upcomingappointments")
	public String upcomingappointments(Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		
		LocalDate LocalDate = java.time.LocalDate.now();
		String date = LocalDate.toString();
		List<appointment> appointments = this.appointmentRepository.getAllpatientupcomingAppointments(date, patient.getPatientId());
		model.addAttribute("appointments",appointments);
		
		model.addAttribute("title","User Dashboard");
		return "patient/upcoming_appointments";
	}
	
	
	@GetMapping("/appointment/edit/{appointmentId}")
	public String geteditappointment(@PathVariable(name = "appointmentId") int appointmentId,Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		List<doctor> doctors = this.doctorRepository.getAlldoctors();
		model.addAttribute("doctors",doctors);
		appointment appointment = this.appointmentRepository.getappointment(appointmentId);
		model.addAttribute("appointment",appointment);
		
		model.addAttribute("title","User Dashboard");
		return "patient/editappointment";
	}
	
	
	@PostMapping("/appointment/edit/{appointmentId}")
	public String posteditappointment(@ModelAttribute("appointment") appointment appointment,@RequestParam(value="doctorId") int doctorId, @PathVariable(name = "appointmentId") int appointmentId,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		appointment.setAppointmentId(appointmentId);
		doctor doctor = new doctor();
		doctor.setDoctorId(doctorId);
		appointment.setDoctor(doctor);
		
		try {
            appointment appointment2 = this.appointmentRepository.getvalidateappointment(doctorId, appointment.getAppointmentDate(), appointment.getAppointmentTime());
			
			if(appointment2 != null) {
				throw new Exception("This slot is already booked. Choose another slot !!");
			}
			this.appointmentRepository.updateappointment(appointment);
			return "redirect:/patient/upcomingappointments";
			
		} catch (Exception e) {
			List<doctor> doctors = this.doctorRepository.getAlldoctors();
			model.addAttribute("doctors",doctors);
			appointment appointment1 = this.appointmentRepository.getappointment(appointmentId);
			model.addAttribute("appointment",appointment1);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "patient/editappointment";
		}
		
	
	}
	
	
	@GetMapping("/appointment/delete/{appointmentId}")
	public String deleteappointment(@PathVariable(name = "appointmentId") int appointmentId,Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		this.appointmentRepository.deleteappointment(appointmentId);
		model.addAttribute("title","User Dashboard");
		return "redirect:/patient/upcomingappointments";
	}
	
	
	@RequestMapping("/previousappointments")
	public String previousappointments(Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		
		LocalDate LocalDate = java.time.LocalDate.now();
		String date = LocalDate.toString();
		List<appointment> appointments = this.appointmentRepository.getAllPatientspreviousAppointments(date, patient.getPatientId());
		model.addAttribute("appointments",appointments);
		
		model.addAttribute("title","User Dashboard");
		return "patient/previous_appointments";
	}
	
	
	@RequestMapping("/previousrecords")
	public String previousrecords(Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		model.addAttribute("title","User Dashboard");
		List<LabReport> labReports = this.labReportRepository.getAllpatientLabReports(patient.getPatientId());
		model.addAttribute("labReports",labReports);
		return "patient/previous_records";
	}
	
	
	
	@GetMapping("/view/labReport/{appointmentId}/{feedback}")
	public String viewpreviousrecords(@PathVariable(name = "appointmentId") int appointmentId, @PathVariable(name = "feedback") String feedback, Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		model.addAttribute("title","User Dashboard");
		LabReport labreport = this.labReportRepository.getLabReportByAppointmentId(appointmentId);
			
		if (feedback.equals("In Patient")) {
			
			try {
				model.addAttribute("labreport",labreport);
				InPatient inpatient = this.inPatientRepository.getInPatientByappointmentId(appointmentId);
				model.addAttribute("inpatient",inpatient);
				return "patient/viewinpatient";
			} catch (Exception e) {
				return "redirect:/patient/previousrecords";
			}
			
		} else {
			
			try {
				model.addAttribute("labreport",labreport);
				OutPatient outpatient = this.outPatientRepository.getOutPatientByappointmentId(appointmentId);
				model.addAttribute("outpatient",outpatient);
				return "patient/viewoutpatient";
			} catch (Exception e) {
				return "redirect:/patient/previousrecords";
			}
		}
			
		
	}
	

	
	
	@RequestMapping("/mydetails")
	public String mydetails(Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("username",user.getUsername());
		model.addAttribute("patient",patient);
		List<patientEmail> patientEmails = this.patientEmailRepository.getpatientEmailBypatientId(patient.getPatientId());
		model.addAttribute("patientEmails",patientEmails);
		model.addAttribute("title","User Dashboard");
		return "patient/my_details";
	}
	
	
	@GetMapping("/mydetails/updatedetails")
	public String getupdatemydetails(Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		model.addAttribute("title","User Dashboard");
		return "patient/updatemydetails";
	}
	
	
	@PostMapping("/mydetails/updatedetails")
	public String postupdatemydetails(@ModelAttribute(name = "patient") Patient patient,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("title","User Dashboard");
		Patient patient1 = this.patientRepository.getPatientByUsername(user.getUsername());
		patient.setPatientId(patient1.getPatientId());
		patient.setUser(user);
		
		try {
			if (!patient.getPhoneNumber().matches("^[1-9][0-9]{9,9}$")) {
				throw new Exception(" The phone number must be of 10 digits. Enter correct phone number !!");		         
	        }
			this.patientRepository.updatePatient(patient);
			return "redirect:/patient/mydetails";
			
		} catch (Exception e) {
			
			model.addAttribute("patient",patient);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "patient/updatemydetails";
		}
		
	}
	
	
	
	@GetMapping("/mydetails/addemail")
	public String getaddemail(Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		model.addAttribute("title","User Dashboard");
		return "patient/addemail";
	}
	
	
	@PostMapping("/mydetails/addemail")
	public String postaddemail(@RequestParam(value = "email") String email,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		patientEmail patientEmail = new patientEmail();
		patientEmail.setPatientId(patient.getPatientId());
		patientEmail.setEmail(email);
		
		try {
			this.patientEmailRepository.createpatientEmail(patientEmail);
			return "redirect:/patient/mydetails";
			
		} catch (Exception e) {
			model.addAttribute("email",email);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "patient/addemail";
		}
		
	}
	
	
	@GetMapping("/mydetails/deleteemail")
	public String getdeleteemail(Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		List<patientEmail> patientEmails = this.patientEmailRepository.getpatientEmailBypatientId(patient.getPatientId());
		model.addAttribute("patientEmails",patientEmails);
		model.addAttribute("title","User Dashboard");
		return "patient/deleteemail";
	}
	
	
	@GetMapping("/mydetails/post/deleteemail/{email}")
	public String postdeleteemail(@PathVariable(name = "email") String email,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		patientEmail patientEmail = new patientEmail();
		patientEmail.setPatientId(patient.getPatientId());
		patientEmail.setEmail(email);
		this.patientEmailRepository.deletepatientEmail(patientEmail);
		return "redirect:/patient/mydetails";
	}
	
	
	
	@RequestMapping("/mybills")
	public String mybills(Model model) {
		User user = this.securityService.findLoggedInUser();
		Patient patient = this.patientRepository.getPatientByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("patient",patient);
		model.addAttribute("title","User Dashboard");
		List<Bill> bills = this.billRepository.getAllpatientBills(patient.getPatientId());
		model.addAttribute("bills",bills);
		return "patient/my_bills";
	}

}


