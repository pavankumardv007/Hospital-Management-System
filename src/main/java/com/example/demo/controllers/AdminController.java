package com.example.demo.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Bill;
import com.example.demo.models.Department;
import com.example.demo.models.InPatient;
import com.example.demo.models.LabReport;
import com.example.demo.models.OtherPayments;
import com.example.demo.models.OutPatient;
import com.example.demo.models.Patient;
import com.example.demo.models.SalaryPayments;
import com.example.demo.models.User;
import com.example.demo.models.appointment;
import com.example.demo.models.doctor;
import com.example.demo.models.doctorEmail;
import com.example.demo.models.room;
import com.example.demo.services.securityService;
import com.example.demo.dao.BillRepository;
import com.example.demo.dao.DepartmentRepository;
import com.example.demo.dao.InPatientRepository;
import com.example.demo.dao.LabReportRepository;
import com.example.demo.dao.OtherPaymentsRepository;
import com.example.demo.dao.OutPatientRepository;
import com.example.demo.dao.PatientRepository;
import com.example.demo.dao.SalaryPaymentsRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dao.appointmentRepository;
import com.example.demo.dao.doctorEmailRepository;
import com.example.demo.dao.doctorRepository;
import com.example.demo.dao.roomRepository;
import com.example.demo.helper.Message;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private securityService securityService;
	
	@Autowired
	private doctorRepository doctorRepository;
	
	@Autowired
	private roomRepository roomRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private doctorEmailRepository doctorEmailRepository;
	
	@Autowired
	private LabReportRepository labReportRepository;
	
	@Autowired
	private appointmentRepository appointmentRepository;
	
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private SalaryPaymentsRepository salaryPaymentsRepository;
	
	@Autowired
	private OtherPaymentsRepository otherPaymentsRepository;
	
	@Autowired
	private InPatientRepository inPatientRepository;
	
	@Autowired
	private OutPatientRepository outPatientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping("/index")
	public String dashboard(Model model) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		return "admin/admin_dashboard";
	}
	
	@RequestMapping("/doctors")
	public String doctors(Model model) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		List<doctor> doctors = this.doctorRepository.getAlldoctors();
		model.addAttribute("doctors",doctors);
		return "admin/doctors";
	}
	
	
	@RequestMapping("/patients")
	public String patients(Model model) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		List<Patient> patients = this.patientRepository.getAllpatients();
		model.addAttribute("patients",patients);
		return "admin/patients";
	}
	
	@RequestMapping("/departments")
	public String departments(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		List<doctor> doctors = this.departmentRepository.getAllDepartments();
		model.addAttribute("doctors",doctors);
		return "admin/departments";
	}
	
	
	@RequestMapping("/appointments")
	public String appointments(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		List<appointment> appointments = this.appointmentRepository.getAllAppointments();
		model.addAttribute("appointments",appointments);
		return "admin/appointments";
	}
	
	@RequestMapping("/labreports")
	public String labreports(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		List<LabReport> labReports = this.labReportRepository.getAllLabReports();
		model.addAttribute("labReports",labReports);
		return "admin/labreports";
	}
	
	
	
	@RequestMapping("/inpatients")
	public String inpatients(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		List<InPatient> inpatients = this.inPatientRepository.getAllInPatient();
		model.addAttribute("inpatients",inpatients);
		return "admin/inpatients";
	}
	
	
	@GetMapping("/addinpatient")
	public String addinpatient(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		InPatient inpatient = new InPatient();
		room room = new room();
		Bill bill = new Bill();
		inpatient.setRoom(room);
		inpatient.setBill(bill);
		model.addAttribute("inpatient",inpatient);
		return "admin/addinpatient";
	}
	
	
	
	@PostMapping("/addinpatient")
	public String postaddinpatient(@ModelAttribute("inpatient") InPatient inpatient, @RequestParam(value = "RoomNo") int RoomNo,@RequestParam(value = "BillId") String BillId,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		inpatient.setInPatientId(inpatient.getAppointmentId());
		room room = new room();
		Bill bill = new Bill();
		room.setRoomNo(RoomNo);
		bill.setBillId(BillId);
		inpatient.setBill(bill);
		inpatient.setRoom(room);
		
		try {
			this.inPatientRepository.createInPatient(inpatient);
			return "redirect:/admin/inpatients";
		} catch (Exception e) {
			model.addAttribute("inpatient",inpatient);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/addinpatient";
		}
	}
	
	
	@GetMapping("/inpatient/delete/{InPatientId}")
	public String deleteinpatient(@PathVariable(name = "InPatientId") int InPatientId,Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		this.inPatientRepository.deleteInPatient(InPatientId);
		return "redirect:/admin/inpatients";
	}
	
	@GetMapping("/inpatient/edit/{InPatientId}")
	public String editinpatient(@PathVariable(name = "InPatientId") int InPatientId,Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		InPatient inpatient = this.inPatientRepository.getInPatientByInPatientId(InPatientId);
		model.addAttribute("inpatient",inpatient);
		return "admin/editinpatient";
	}
	
	
	@PostMapping("/inpatient/edit/{InPatientId}")
	public String posteditinpatient(@ModelAttribute("inpatient") InPatient inpatient, @PathVariable(name = "InPatientId") int InPatientId, @RequestParam(value = "RoomNo") int RoomNo,@RequestParam(value = "BillId") String BillId, Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		room room = new room();
		Bill bill = new Bill();
		room.setRoomNo(RoomNo);
		bill.setBillId(BillId);
		inpatient.setBill(bill);
		inpatient.setRoom(room);
		inpatient.setInPatientId(InPatientId);
		
		try {
			this.inPatientRepository.updateInPatient(inpatient);
			return "redirect:/admin/inpatients";
			
		} catch (Exception e) {
			model.addAttribute("inpatient",inpatient);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/editinpatient";
		}
	}
	
	
	
	@RequestMapping("/outpatients")
	public String outpatients(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		List<OutPatient> outpatients = this.outPatientRepository.getAllOutPatients();
		model.addAttribute("outpatients",outpatients);
		return "admin/outpatients";
	}
	
	@GetMapping("/addoutpatient")
	public String addoutpatient(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		OutPatient outpatient = new OutPatient();
		Bill bill = new Bill();
		outpatient.setBill(bill);
		model.addAttribute("outpatient",outpatient);
		return "admin/addoutpatient";
	}
	
	
	@PostMapping("/addoutpatient")
	public String postaddoutpatient(@ModelAttribute("outpatient") OutPatient outpatient, @RequestParam(value = "BillId") String BillId, Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		outpatient.setOutPatientId(outpatient.getAppointmentId());
		Bill bill = new Bill();
		bill.setBillId(BillId);
		outpatient.setBill(bill);
		
		try {
			this.outPatientRepository.createOutPatient(outpatient);
			return "redirect:/admin/outpatients";
		} catch (Exception e) {
			model.addAttribute("outpatient",outpatient);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/addoutpatient";
		}
	}
	
	
	@GetMapping("/outpatient/delete/{OutPatientId}")
	public String deleteoutpatient(@PathVariable(name = "OutPatientId") int OutPatientId,Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		this.outPatientRepository.deleteOutPatient(OutPatientId);
		return "redirect:/admin/outpatients";
	}
	
	
	@GetMapping("/outpatient/edit/{OutPatientId}")
	public String editoutpatient(@PathVariable(name = "OutPatientId") int OutPatientId,Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		OutPatient outpatient = this.outPatientRepository.getOutPatientByOutPatientId(OutPatientId);
		model.addAttribute("outpatient",outpatient);
		return "admin/editoutpatient";
	}
	
	
	@PostMapping("/outpatient/edit/{OutPatientId}")
	public String posteditoutpatient(@ModelAttribute("outpatient") OutPatient outpatient, @RequestParam(value = "BillId") String BillId, @PathVariable(name = "OutPatientId") int OutPatientId,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		Bill bill = new Bill();
		bill.setBillId(BillId);
		outpatient.setBill(bill);
		outpatient.setOutPatientId(OutPatientId);
		
		try {
			this.outPatientRepository.updateOutPatient(outpatient);
			return "redirect:/admin/outpatients";
			
		} catch (Exception e) {
			model.addAttribute("outpatient",outpatient);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/editoutpatient";
		}
	}
	
	
	@RequestMapping("/rooms")
	public String rooms(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		List<room> rooms = this.roomRepository.getAllrooms();
		model.addAttribute("rooms",rooms);
		return "admin/rooms";
	}
	
	
	@RequestMapping("/otherpayments")
	public String otherpayments(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		List<OtherPayments> otherpayments = this.otherPaymentsRepository.getAllOtherPayments();
		model.addAttribute("otherpayments",otherpayments);
		return "admin/otherpayments";
	}
	
	
	@GetMapping("/addotherpayment")
	public String addotherpayment(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		OtherPayments otherpayment = new OtherPayments();
		Department department = new Department();
		otherpayment.setDepartment(department);
		model.addAttribute("otherpayment",otherpayment);
		return "admin/addotherpayment";
	}
	
	
	@PostMapping("/addotherpayment")
	public String postaddotherpayment(@ModelAttribute("otherpayment") OtherPayments otherpayment,@RequestParam(value = "departmentId") int departmentId,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		Department department = new Department();
		department.setDepartmentId(departmentId);
		otherpayment.setDepartment(department);
		try {
			this.otherPaymentsRepository.createOtherPayments(otherpayment);
			return "redirect:/admin/otherpayments";
		} catch (Exception e) {
			model.addAttribute("otherpayment",otherpayment);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/addotherpayment";
		}
		
	}
	
	
	@GetMapping("/otherpayment/delete/{PaymentId}")
	public String deleteotherpayment(@PathVariable(name = "PaymentId") String PaymentId,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		
		try {
			this.otherPaymentsRepository.deleteOtherPayments(PaymentId);
			return "redirect:/admin/otherpayments";
		} catch (Exception e) {
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			List<OtherPayments> otherpayments = this.otherPaymentsRepository.getAllOtherPayments();
			model.addAttribute("otherpayments",otherpayments);
			return "admin/otherpayments";
		}
	}
	
	
	
	@GetMapping("/otherpayment/edit/{PaymentId}")
	public String editotherpayment(@PathVariable(name = "PaymentId") String PaymentId,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		OtherPayments otherpayment = this.otherPaymentsRepository.getAllPaymentsbypaymentId(PaymentId);
		model.addAttribute("otherpayment",otherpayment);
		return "admin/editotherpayment";
	}
	
	
	@PostMapping("/otherpayment/edit/{PaymentId}")
	public String posteditotherpayment(@ModelAttribute("otherpayment") OtherPayments otherpayment,@RequestParam(value = "departmentId") int departmentId, @PathVariable(name = "PaymentId") String PaymentId,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		otherpayment.setPaymentId(PaymentId);
		Department department = new Department();
		department.setDepartmentId(departmentId);
		otherpayment.setDepartment(department);
		
		try {
			this.otherPaymentsRepository.updateOtherPayments(otherpayment);
			return "redirect:/admin/otherpayments";
			
		} catch (Exception e) {
			model.addAttribute("otherpayment",otherpayment);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/editotherpayment";
		}
		
	}
	
	
	
	
	
	@RequestMapping("/salarypayments")
	public String salarypayments(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		List<SalaryPayments> salarypayments = this.salaryPaymentsRepository.getAllSalaryPayments();
		model.addAttribute("salarypayments", salarypayments);
		return "admin/salarypayments";
	}
	
	
	@GetMapping("/addsalarypayment")
	public String addsalarypayment(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		SalaryPayments salarypayment = new SalaryPayments();
		doctor doctor = new doctor();
		salarypayment.setDoctor(doctor);
		model.addAttribute("salarypayment",salarypayment);
		return "admin/addsalarypayment";
	}
	
	
	@PostMapping("/addsalarypayment")
	public String postaddsalarypayment(@ModelAttribute("salarypayment") SalaryPayments salarypayment,@RequestParam(value = "doctorId") int doctorId, Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		doctor doctor = new doctor();
		doctor.setDoctorId(doctorId);
		salarypayment.setDoctor(doctor);
		
		try {
			this.salaryPaymentsRepository.createSalaryPayments(salarypayment);
			return "redirect:/admin/salarypayments";
		} catch (Exception e) {
			model.addAttribute("salarypayment",salarypayment);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/addsalarypayment";
		}

	}
	
	
	@GetMapping("/salarypayment/delete/{PaymentId}")
	public String deletesalarypayment(@PathVariable(name = "PaymentId") String PaymentId,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		
		try {
			this.salaryPaymentsRepository.deleteSalaryPayments(PaymentId);
			return "redirect:/admin/salarypayments";
		} catch (Exception e) {
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			List<SalaryPayments> salarypayments = this.salaryPaymentsRepository.getAllSalaryPayments();
			model.addAttribute("salarypayments", salarypayments);
			return "admin/salarypayments";
		}
	}
	
	
	@GetMapping("/salarypayment/edit/{PaymentId}")
	public String editsalarypayment(@PathVariable(name = "PaymentId") String PaymentId,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		SalaryPayments salarypayment = this.salaryPaymentsRepository.getSalaryPaymentsByPaymentId(PaymentId);
		model.addAttribute("salarypayment",salarypayment);
		
		return "admin/editsalarypayment";
	}
	
	
	@PostMapping("/salarypayment/edit/{PaymentId}")
	public String posteditsalarypayment(@ModelAttribute("salaryPayment") SalaryPayments salarypayment,@RequestParam(value = "doctorId") int doctorId,@PathVariable(name = "PaymentId") String PaymentId,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		salarypayment.setPaymentId(PaymentId);
		doctor doctor = new doctor();
		doctor.setDoctorId(doctorId);
		salarypayment.setDoctor(doctor);	
		this.salaryPaymentsRepository.updateSalaryPayments(salarypayment);
		return "redirect:/admin/salarypayments";
	}
	
	
	
	@RequestMapping("/bills")
	public String bills(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		List<Bill> bills = this.billRepository.getAllBills();
		model.addAttribute("bills",bills);
		return "admin/bills";
	}
	
	@GetMapping("/addbill")
	public String addbill(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		Bill bill = new Bill();
		model.addAttribute("bill",bill);
		return "admin/addbill";
	}
	
	
	@PostMapping("/addbill")
	public String postaddbill(@ModelAttribute("bill") Bill bill,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		try {
			this.billRepository.createBill(bill);
			return "redirect:/admin/bills";
		} catch (Exception e) {
			model.addAttribute("bill",bill);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/addbill";
		}
		
	}
	
	
	@GetMapping("/bill/view/{BillId}")
	public String viewbill(@PathVariable(name = "BillId") String BillId,Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		Bill bill = this.billRepository.getBillByBillId(BillId);
		model.addAttribute("bill",bill);
		return "admin/viewbill";
	}
	
	
	@GetMapping("/bill/delete/{BillId}")
	public String deletebill(@PathVariable(name = "BillId") String BillId,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		try {
			this.billRepository.deleteBill(BillId);
			return "redirect:/admin/bills";
			
		} catch (Exception e) {
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			List<Bill> bills = this.billRepository.getAllBills();
			model.addAttribute("bills",bills);
			return "admin/bills";
		}
		
	}
	
	
	@GetMapping("/bill/edit/{BillId}")
	public String editbill(@PathVariable(name = "BillId") String BillId,Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		Bill bill = this.billRepository.getBillByBillId(BillId);
		model.addAttribute("bill",bill);
		return "admin/editbill";
	}
	
	
	@PostMapping("/bill/edit/{BillId}")
	public String posteditbill(@ModelAttribute("bill") Bill bill,@PathVariable(name = "BillId") String BillId,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		bill.setBillId(BillId);
		
		try {
			this.billRepository.updateBill(bill);
			return "redirect:/admin/bills";
		} catch (Exception e) {
			model.addAttribute("bill",bill);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/editbill";
		}
		
	}
	
	
	
	@RequestMapping("/doctorsupervisor")
	public String doctorsupervisor(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		return "admin/doctorsupervisor";
	}
	
	@GetMapping("/addadmin")
	public String addadmin(Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		User user1 = new User();
		model.addAttribute("user1",user1);
		return "admin/addadmin";
	}
	
	@GetMapping("/adddoctor")
	public String adddoctor(Model model) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("user1",new User());
		doctor doctor = new doctor();
		Department department = new Department();
		doctor.setDepartment(department);
		model.addAttribute("doctor",doctor);
		model.addAttribute("title","Admin Dashboard");
		return "admin/adddoctor";
	}
	
	@PostMapping("/adddoctor")
	public String savedoctor(@ModelAttribute("doctor") doctor doctor, @RequestParam(value="departmentId") int departmentId,
			@RequestParam(value="username") String username, @RequestParam(value="password") String password,
			@RequestParam(value="email") String email, Model model, HttpSession session) {
		
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		
		User user1 = new User();
		user1.setUsername(username);
		user1.setPassword(password);
		user1.setRole("Doctor");
		Department department = new Department();
		department.setDepartmentId(departmentId);
		doctor.setDepartment(department);
		doctor.setUser(user1);
		doctorEmail doctorEmail = new doctorEmail();
		doctorEmail.setDoctorId(doctor.getDoctorId());
		doctorEmail.setEmail(email);
		
		try {		
			User user2 = this.userRepository.getvalidateuser(user1.getUsername());
			if(user2 != null) {
				throw new Exception(" This username is already taken. Choose another username !!");
			}
			if (!doctor.getPhoneNumber().matches("^[1-9][0-9]{9,9}$")) {
				throw new Exception(" The phone number must be of 10 digits. Enter correct phone number !!");		         
	        }
			this.userRepository.createUser(user1);
			this.doctorRepository.createdoctor(doctor);
			this.doctorEmailRepository.createdoctorEmail(doctorEmail);
			model.addAttribute("user1",new User());
			doctor doctor1 = new doctor();
			Department department1 = new Department();
			doctor1.setDepartment(department1);
			model.addAttribute("doctor",doctor1);
			session.setAttribute("message", new Message("Successfully added doctor !!", "alert-success" ));
			return "admin/adddoctor";
			
		} catch (Exception e) {
			model.addAttribute("user1",user1);
			model.addAttribute("doctor",doctor);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/adddoctor";
		}
	}
	
	@GetMapping("/doctor/view/{username}")
	public String viewdoctor(@PathVariable(name = "username") String username,Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		doctor doctor = this.doctorRepository.getdoctorByUsername(username);
		model.addAttribute("doctor",doctor);
		return "admin/viewdoctor";
	}
	
	@GetMapping("/doctor/edit/{username}")
	public String geteditdoctor(@PathVariable(name = "username") String username,Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("username",username);
		model.addAttribute("title","Admin Dashboard");
		doctor doctor = this.doctorRepository.getdoctorByUsername(username);
		model.addAttribute("doctor",doctor);
		return "admin/editdoctor";
	}
	
	
	@PostMapping("/doctor/edit/{username}")
	public String posteditdoctor(@PathVariable(name = "username") String username,@ModelAttribute("doctor") doctor doctor,
			@RequestParam(value="departmentId") int departmentId,Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		
		User user1 = new User();
		user1.setUsername(username);
		doctor doctor1 = this.doctorRepository.getdoctorByUsername(username);
		doctor.setDoctorId(doctor1.getDoctorId());
		Department department = new Department();
		department.setDepartmentId(departmentId);
		doctor.setDepartment(department);
		doctor.setUser(user1);
		try {
			
			this.doctorRepository.updatedoctor(doctor);
			return "redirect:/admin/doctors";
			
		} catch (Exception e) {
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			model.addAttribute("doctor",doctor);
			return "admin/editdoctor";
		}	
	}
	
	
	@GetMapping("/patient/view/{username}")
	public String viewpatient(@PathVariable(name = "username") String username,Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("username",username);
		model.addAttribute("title","Admin Dashboard");
		Patient patient = this.patientRepository.getPatientByUsername(username);
		model.addAttribute("patient",patient);
		return "admin/viewpatient";
	}
	
	
	@GetMapping("/adddepartment")
	public String addgetdepartment(Model model) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		Department department = new Department();
		model.addAttribute("department",department);
		model.addAttribute("title","Admin Dashboard");
		return "admin/adddepartment";
	}
	
	@PostMapping("/adddepartment")
	public String addpostdepartment(@ModelAttribute("department") Department department,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
	    try {
			this.departmentRepository.createDepartment(department);
			return "redirect:/admin/departments";
		} catch (Exception e) {
			model.addAttribute("department",department);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/adddepartment";
		}
		
	}
	
	@GetMapping("/department/delete/{departmentId}")
	public String department(@PathVariable(name = "departmentId") int departmentId,Model model) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		this.departmentRepository.deleteDepartment(departmentId);
		return "redirect:/admin/departments";
	}
	
	@GetMapping("/department/edit/{departmentId}")
	public String geteditdepartment(@PathVariable(name = "departmentId") int departmentId,Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		Department department = this.departmentRepository.getdepartmentbyDepartmentId(departmentId);
		model.addAttribute("department",department);
		
		return "admin/editdepartment";
	}
	
	
	@PostMapping("/department/edit/{departmentId}")
	public String posteditdepartment(@PathVariable(name = "departmentId") int departmentId, @ModelAttribute("department") Department department,
			Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		department.setDepartmentId(departmentId);
		
		try {
			
			this.departmentRepository.updateDepartment(department);
			return "redirect:/admin/departments";
			
		} catch (Exception e) {
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			model.addAttribute("department",department);
			return "admin/editdepartment";
		}	
	}
	
	@PostMapping("/addadmin")
	public String addpostadmin(@ModelAttribute("user1") User user1,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		user1.setRole("Admin");
	    try {
			this.userRepository.createUser(user1);
			session.setAttribute("message", new Message("Successfully added a new admin !!", "alert-success" ));
			return "admin/addadmin";
			
		} catch (Exception e) {
			model.addAttribute("user1",user1);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/addadmin";
		}
		
	}
	
	
	@GetMapping("/addroom")
	public String addroom(Model model) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		room room = new room();
		model.addAttribute("room",room);
		model.addAttribute("title","Admin Dashboard");
		return "admin/addroom";
	}
	
	
	@PostMapping("/addroom")
	public String addroom(@ModelAttribute("room") room room,Model model,HttpSession session) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		
	    try {
			this.roomRepository.createroom(room);		
			return "redirect:/admin/rooms";
			
		} catch (Exception e) {
			model.addAttribute("room",room);
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			return "admin/addroom";
		}
		
	}
	
	@GetMapping("/room/delete/{RoomNo}")
	public String deleteRoom(@PathVariable(name = "RoomNo") int RoomNo,Model model) {
		User user = this.securityService.findLoggedInUser();
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		this.roomRepository.deleteroom(RoomNo);
		return "redirect:/admin/rooms";
	}
	
	@GetMapping("/room/edit/{RoomNo}")
	public String geteditRoomNo(@PathVariable(name = "RoomNo") int RoomNo, Model model) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		room room = this.roomRepository.getroombyRoomNo(RoomNo);
		model.addAttribute("room",room);
		
		return "admin/editroom";
	}
	
	
	@PostMapping("/room/edit/{RoomNo}")
	public String posteditroom(@PathVariable(name = "RoomNo") int RoomNo, @ModelAttribute("room") room room,
			Model model, HttpSession session) {
		User user = this.securityService.findLoggedInUser();	
		model.addAttribute("user",user);
		model.addAttribute("title","Admin Dashboard");
		room.setRoomNo(RoomNo);
		
		try {
			
			this.roomRepository.updateroom(room);
			return "redirect:/admin/rooms";
			
		} catch (Exception e) {
			session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger" ));
			model.addAttribute("room",room);
			return "admin/editroom";
		}	
	}

}
