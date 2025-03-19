CREATE TABLE IF NOT EXISTS User(
  username varchar(30) UNIQUE NOT NULL,
  password varchar(255) NOT NULL,
  role enum('Admin','Doctor','Patient') NOT NULL,
  isAccountNonLocked BOOLEAN NOT NULL DEFAULT true,
  PRIMARY KEY(username)
);

CREATE TABLE IF NOT EXISTS Patient(
  patientId INT UNIQUE NOT NULL AUTO_INCREMENT,
  first_name varchar(25) NOT NULL,
  last_name varchar(25) NOT NULL,
  gender enum('Male','Female','Not Specified') NOT NULL DEFAULT 'Not Specified',
  dateOfBirth DATE NOT NULL,
  address VARCHAR(255) NOT NULL,
  phoneNumber VARCHAR(50) NOT NULL,
  username varchar(20) NOT NULL UNIQUE,
  FOREIGN KEY (username) REFERENCES User(username),
  PRIMARY KEY(patientId) 
 );

CREATE TABLE IF NOT EXISTS doctor(
  doctorId INT UNIQUE NOT NULL,
  first_name varchar(25) NOT NULL,
  last_name varchar(25) NOT NULL,
  gender enum('Male','Female','Not Specified') NOT NULL DEFAULT 'Not Specified',
  dateOfBirth DATE NOT NULL,
  address VARCHAR(255) NOT NULL,
  phoneNumber VARCHAR(50) NOT NULL,
  specialization VARCHAR(255) NOT NULL,
  qualification VARCHAR(255) NOT NULL,
  salary INT,
  departmentId INT NOT NULL,
  username varchar(20) NOT NULL UNIQUE,
  FOREIGN KEY (username) REFERENCES User(username),
  PRIMARY KEY(doctorId) 
 );

CREATE TABLE IF NOT EXISTS Department(
  departmentId INT UNIQUE NOT NULL,
  departmentName VARCHAR(30) NOT NULL,
  managerId INT ,
  manager_start_date Date,
  FOREIGN KEY (managerId) REFERENCES doctor(doctorId),
  PRIMARY KEY (departmentId) 
 );

ALTER TABLE doctor ADD FOREIGN KEY(departmentId)  REFERENCES Department(departmentId);

 CREATE TABLE IF NOT EXISTS patientEmail(
   patientId INT NOT NULL,
   email VARCHAR(50) NOT NULL,
   FOREIGN KEY (patientId) REFERENCES patient(patientId) ON DELETE CASCADE,
   PRIMARY KEY (patientId, email)
);

CREATE TABLE IF NOT EXISTS doctorEmail(
   doctorId INT NOT NULL,
   email VARCHAR(50) NOT NULL,
   FOREIGN KEY (doctorId) REFERENCES doctor(doctorId) ON DELETE CASCADE,
   PRIMARY KEY (doctorId, email)
);


CREATE TABLE IF NOT EXISTS appointment(
  appointmentId INT UNIQUE NOT NULL AUTO_INCREMENT,
  patientId INT NOT NULL,
  doctorId INT NOT NULL,
  appointmentDate DATE NOT NULL,
  appointmentTime enum('10:00AM-10:30AM', 
                '10:30AM-11:00AM', 
                '11:00AM-11:30AM', 
                '11:30AM-12:00PM',
                '12:00PM-12:30PM',
                '12:30PM-1:00PM',
                '2:00PM-2:30PM',
                '2:30PM-3:00PM',
                '3:00PM-3:30PM',
                '3:30PM-4:00PM'
                ) NOT NULL,
  registeredTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  message VARCHAR(255) NOT NULL,
  FOREIGN KEY (patientId) REFERENCES patient(patientId),
  FOREIGN KEY (doctorId) REFERENCES doctor(doctorId),
  PRIMARY KEY(appointmentId) 
 );

CREATE TABLE IF NOT EXISTS LabReport(
  appointmentId INT UNIQUE NOT NULL,
  date DATE NOT NULL,
  LabTests VARCHAR(255) NOT NULL,
  LabResult VARCHAR(255) NOT NULL,
  amount INT NOT NULL,
  feedback enum('In Patient','Out Patient') NOT NULL,
  FOREIGN KEY (appointmentId) REFERENCES appointment(appointmentId),
  PRIMARY KEY (appointmentId) 
 );

CREATE TABLE IF NOT EXISTS room(
  RoomNo INT UNIQUE NOT NULL,
  RoomType VARCHAR(50) NOT NULL,
  CostPerDay INT NOT NULL,
  Capacity INT NOT NULL,
  PRIMARY KEY (RoomNo) 
 );

 CREATE TABLE IF NOT EXISTS OtherPayments(
  PaymentId VARCHAR(50) UNIQUE NOT NULL,
  PaymentTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  departmentId INT NOT NULL,
  amount INT NOT NULL,
  PaidTo VARCHAR(50) NOT NULL,
  reason VARCHAR(255) NOT NULL,
  FOREIGN KEY (departmentId) REFERENCES department(departmentId),
  PRIMARY KEY (PaymentId) 
 );

CREATE TABLE IF NOT EXISTS SalaryPayments(
  PaymentId VARCHAR(50) UNIQUE NOT NULL,
  PaymentTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  doctorId INT NOT NULL,
  amount INT NOT NULL,
   month enum('January', 
               'February', 
               'March', 
               'April',
               'May',
               'June',
               'July',
               'August',
               'September',
               'October',
               'November',
               'December'	
               )NOT NULL,
  FOREIGN KEY (doctorId) REFERENCES doctor(doctorId),
  PRIMARY KEY (PaymentId) 
 );

CREATE TABLE IF NOT EXISTS Bill(
  BillId VARCHAR(50) UNIQUE NOT NULL,
  PaymentTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  appointmentId INT UNIQUE NOT NULL,
  patientId INT NOT NULL,
  AppointmentFee INT NOT NULL,
  LabCharge INT DEFAULT 0,
  MedicineCharge INT DEFAULT 0,
  TreatmentFee INT DEFAULT 0,
  RoomCharge INT DEFAULT 0,
  TotalAmount INT NOT NULL,
  FOREIGN KEY (patientId) REFERENCES patient(patientId),
  FOREIGN KEY (appointmentId) REFERENCES appointment(appointmentId),
  PRIMARY KEY (BillId) 
 );

CREATE TABLE IF NOT EXISTS InPatient(
  InPatientId INT UNIQUE NOT NULL,
  appointmentId INT UNIQUE NOT NULL,
  AdmissionDate DATE NOT NULL,
  DischargeDate DATE NOT NULL,
  advance INT NOT NULL,
  treatment VARCHAR(255) NOT NULL,
  RoomNo INT NOT NULL,
  BillId VARCHAR(50) UNIQUE NOT NULL,
  FOREIGN KEY (RoomNo) REFERENCES room(RoomNo),
  FOREIGN KEY (BillId) REFERENCES Bill(BillId),
  FOREIGN KEY (appointmentId) REFERENCES appointment(appointmentId),
  PRIMARY KEY (InPatientId) 
 );

CREATE TABLE IF NOT EXISTS OutPatient(
  OutPatientId INT UNIQUE NOT NULL,
  appointmentId INT UNIQUE NOT NULL,
  treatment VARCHAR(255) NOT NULL,
  remarks VARCHAR(255) NOT NULL,
  BillId VARCHAR(50) UNIQUE NOT NULL,
  FOREIGN KEY (BillId) REFERENCES Bill(BillId),
  FOREIGN KEY (appointmentId) REFERENCES appointment(appointmentId),
  PRIMARY KEY (OutPatientId) 
 );
