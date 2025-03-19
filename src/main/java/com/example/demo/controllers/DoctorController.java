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

import com.example.demo.models.InPatient;
import com.example.demo.models.LabReport;
import com.example.demo.models.OutPatient;
import com.example.demo.models.Patient;
import com.example.demo.models.SalaryPayments;
import com.example.demo.models.User;
import com.example.demo.models.appointment;
import com.example.demo.models.doctor;
import com.example.demo.models.doctorEmail;
import com.example.demo.models.patientEmail;
import com.example.demo.services.securityService;
import com.example.demo.dao.InPatientRepository;
import com.example.demo.dao.LabReportRepository;
import com.example.demo.dao.OutPatientRepository;
import com.example.demo.dao.SalaryPaymentsRepository;
import com.example.demo.dao.appointmentRepository;
import com.example.demo.dao.doctorEmailRepository;
import com.example.demo.dao.doctorRepository;
import com.example.demo.helper.Message;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private securityService securityService;
	
	@Autowired
	private doctorRepository doctorRepository;
	
	@Autowired
	private doctorEmailRepository doctorEmailRepository;
	
	@Autowired
	private appointmentRepository appointmentRepository;
	
	@Autowired
	private LabReportRepository labReportRepository;
	
	@Autowired
	private SalaryPaymentsRepository salaryPaymentsRepository;
	
	@Autowired
	private InPatientRepository inPatientRepository;
	
	@Autowired
	private OutPatientRepository outPatientRepository;
	
	
	@RequestMapping("/index")
	public String dashboard(Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		model.addAttribute("title","Doctor Dashboard");
		return "doctor/doctor_dashboard";
	}
	
	@RequestMapping("/upcomingappointments")
	public String upcomingappointments(Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		
		LocalDate LocalDate = java.time.LocalDate.now();
		String date = LocalDate.toString();
		List<appointment> appointments = this.appointmentRepository.getAlldoctorupcomingAppointments(date, doctor.getDoctorId());
		model.addAttribute("appointments",appointments);
		
		model.addAttribute("title","Doctor Dashboard");
		return "doctor/upcomingappointments";
	}
	
	
	@GetMapping("/appointment/delete/{appointmentId}")
	public String deleteappointment(@PathVariable(name = "appointmentId") int appointmentId,Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		this.appointmentRepository.deleteappointment(appointmentId);
		model.addAttribute("title","Doctor Dashboard");
		return "redirect:/doctor/upcomingappointments";
	}
	
	
	
	@RequestMapping("/previousappointments")
	public String previousappointments(Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		
		LocalDate LocalDate = java.time.LocalDate.now();
		String date = LocalDate.toString();
		List<appointment> appointments = this.appointmentRepository.getAlldoctorpreviousAppointments(date, doctor.getDoctorId());
		model.addAttribute("appointments",appointments);
		
		model.addAttribute("title","Doctor Dashboard");
		return "doctor/previousappointments";
	}
	
	
	
	@GetMapping("/appointment/adddetails/{appointmentId}")
	public String appointmentadddetails(@PathVariable(value = "appointmentId")int appointmentId,Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		
		LabReport labReport1 = this.labReportRepository.getLabReportByAppointmentId(appointmentId);
		if(labReport1 != null) {
			return "redirect:/doctor/previousrecords";
		}
		
		model.addAttribute("appointmentId",appointmentId);
		LabReport labReport = new LabReport();
		model.addAttribute("labReport",labReport);
		model.addAttribute("title","Doctor Dashboard");
		return "doctor/addappointmentdetails";
	}
	
	
	@PostMapping("/appointment/adddetails/{appointmentId}")
	public String postappointmentadddetails(@ModelAttribute(name = "labReport") LabReport labReport,@PathVariable(value = "appointmentId")int appointmentId,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		model.addAttribute("appointmentId",appointmentId);
		labReport.setAppointmentId(appointmentId);
		
		try {
			this.labReportRepository.createLabReport(labReport);
			return "redirect:/doctor/previousrecords";
		} catch (Exception e) {
			model.addAttribute("labReport",labReport);
			session.setAttribute("message", new Message("Something went wrong !! "+e.getMessage(), "alert-danger" ));
			return "doctor/addappointmentdetails";
		}
		
	}
	
	
	@RequestMapping("/previousrecords")
	public String previousrecords(Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		
		List<LabReport> labReports = this.labReportRepository.getAlldoctorLabReports(doctor.getDoctorId());
		model.addAttribute("labReports",labReports);
		
		model.addAttribute("title","Doctor Dashboard");
		return "doctor/previousrecords";
	}
	
	
	@GetMapping("/view/labReport/{appointmentId}/{feedback}")
	public String viewpreviousrecords(@PathVariable(name = "appointmentId") int appointmentId, @PathVariable(name = "feedback") String feedback, Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		model.addAttribute("title","Doctor Dashboard");
		LabReport labreport = this.labReportRepository.getLabReportByAppointmentId(appointmentId);
		try {
			
			if (feedback.equals("In Patient")) {
				
				model.addAttribute("labreport",labreport);
				InPatient inpatient = this.inPatientRepository.getInPatientByappointmentId(appointmentId);
				model.addAttribute("inpatient",inpatient);
				return "doctor/viewinpatient";
				
			} else {
				
				model.addAttribute("labreport",labreport);
				OutPatient outpatient = this.outPatientRepository.getOutPatientByappointmentId(appointmentId);
				model.addAttribute("outpatient",outpatient);
				return "doctor/viewoutpatient";
			}
			
		} catch (Exception e) {
			
			return "redirect:/doctor/previousrecords";
			
		}
		
		
	}
	
	
	
	@GetMapping("/edit/labReport/{appointmentId}")
	public String editlabreport(@PathVariable(name = "appointmentId") int appointmentId, Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		model.addAttribute("title","Doctor Dashboard");
		LabReport labReport = this.labReportRepository.getLabReportByAppointmentId(appointmentId);
		model.addAttribute("labreport",labReport);
		return "doctor/editlabreport";
	}
	
	
	@PostMapping("/edit/labReport/{appointmentId}")
	public String posteditlabreport(@ModelAttribute("labreport") LabReport labreport,@PathVariable(name = "appointmentId") int appointmentId, Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		model.addAttribute("title","Doctor Dashboard");
		labreport.setAppointmentId(appointmentId);
		try {
			this.labReportRepository.updateLabReport(labreport);
			return "redirect:/doctor/previousrecords";
		} catch (Exception e) {
			model.addAttribute("labreport",labreport);
			session.setAttribute("message", new Message("Something went wrong !! "+e.getMessage(), "alert-danger" ));
			return "doctor/editlabreport";
		}
	}
	
	
	@RequestMapping("/mydetails")
	public String mydetails(Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		List<doctorEmail> doctorEmails = this.doctorEmailRepository.getdoctorEmailByDoctorId(doctor.getDoctorId());
		model.addAttribute("doctorEmails",doctorEmails);
		model.addAttribute("title","Doctor Dashboard");
		return "doctor/mydetails";
	}
	
	
	@GetMapping("/mydetails/updatedetails")
	public String getupdatemydetails(Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		model.addAttribute("title","Doctor Dashboard");
		return "doctor/updatemydetails";
	}
	
	
	@PostMapping("/mydetails/updatedetails")
	public String postupdatemydetails(@ModelAttribute(name = "doctor") doctor doctor,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("title","Doctor Dashboard");
		doctor doctor1 = this.doctorRepository.getdoctorByUsername(user.getUsername());
		doctor.setDoctorId(doctor1.getDoctorId());
		doctor.setDepartment(doctor1.getDepartment());
		doctor.setUser(user);
		doctor.setSalary(doctor1.getSalary());
		
		try {
			if (!doctor.getPhoneNumber().matches("^[1-9][0-9]{9,9}$")) {
				throw new Exception(" The phone number must be of 10 digits. Enter correct phone number !!");		         
	        }
			this.doctorRepository.updatedoctor(doctor);
			return "redirect:/doctor/mydetails";
			
		} catch (Exception e) {
			
			model.addAttribute("doctor",doctor);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "doctor/updatemydetails";
		}
		
	}
	
	
	@GetMapping("/mydetails/addemail")
	public String getaddemail(Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		model.addAttribute("title","Doctor Dashboard");
		return "doctor/addemail";
	}
	
	
	@PostMapping("/mydetails/addemail")
	public String postaddemail(@RequestParam(value = "email") String email,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		model.addAttribute("title","Doctor Dashboard");
		doctorEmail doctorEmail = new doctorEmail();
		doctorEmail.setDoctorId(doctor.getDoctorId());
		doctorEmail.setEmail(email);
		
		try {
			this.doctorEmailRepository.createdoctorEmail(doctorEmail);
			return "redirect:/doctor/mydetails";
			
		} catch (Exception e) {
			model.addAttribute("email",email);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "doctor/addemail";
		}
		
	}
	
	
	@GetMapping("/mydetails/deleteemail")
	public String getdeleteemail(Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		List<doctorEmail> doctorEmails = this.doctorEmailRepository.getdoctorEmailByDoctorId(doctor.getDoctorId());
		model.addAttribute("doctorEmails",doctorEmails);
		model.addAttribute("title","Doctor Dashboard");
		return "doctor/deleteemail";
	}
	
	
	@GetMapping("/mydetails/post/deleteemail/{email}")
	public String postdeleteemail(@PathVariable(name = "email") String email,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		model.addAttribute("title","Doctor Dashboard");
		doctorEmail doctorEmail = new doctorEmail();
		doctorEmail.setDoctorId(doctor.getDoctorId());
		doctorEmail.setEmail(email);
		this.doctorEmailRepository.deletedoctorEmail(doctorEmail);
		return "redirect:/doctor/mydetails";
	}
	
	
	@RequestMapping("/supervisordetails")
	public String supervisordetails(Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		model.addAttribute("title","Doctor Dashboard");
		return "doctor/supervisordetails";
	}
	
	@RequestMapping("/salarypayments")
	public String salarypayments(Model model) {
		User user = this.securityService.findLoggedInUser();
		doctor doctor = this.doctorRepository.getdoctorByUsername(user.getUsername());
		model.addAttribute("user",user);
		model.addAttribute("doctor",doctor);
		model.addAttribute("title","Doctor Dashboard");
		List<SalaryPayments> salarypayments = this.salaryPaymentsRepository.getAlldoctorSalaryPayments(doctor.getDoctorId());
		model.addAttribute("salarypayments",salarypayments);
		return "doctor/salarypayments";
	}

}
