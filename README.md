# Medizine - aims to connect users to healthcare services

**Tech Stack** 

JAVA, Spring Boot, Gradle, Swagger Documentation, MongoDB,  AWS Elastic Beanstalk

## Local installation
- Clone the repo.
- Change the directory
- Update the file `src/main/resources/application.properties`.
	- There you need to declare your database configurations and since the backend is using MongoDB as its database.
	- Try to use mongo's configuration (either local or MongoDB Atlas).
	- Also update property `file.upload-dir` in the same file pointing to some local directory, **it should not be null**.

- Run the command `gralde bootrun`
- Server would be up at http://localhost:8081/
- API documentation can be viewed at http://localhost:8081/swagger-ui/index.html

## **Features**

**Types of User:**

* Patients - Patients are users with necessary permissions who can request online consultation with a doctor
* Doctors - Users who provide online consultation to patients, view patients’ profiles, lab reports, and schedule appointments


**Patient’s Dashboard:**

 If you log in as a patient (normal user), you can find the following components in your app:

### Profile 

	Patients can create profiles by entering their essential information:
 
	 Name
	 Email address
	 Phone number
	 Age 
	 Gender
	 Medical history
	 Problems they are suffering from
 
	 They can update their profile information anytime

### Find a Doctor

	Find doctors using filters such as:
	Specialty
	Language 
	
	It would help patients to find a physician that meets their situation and needs

### Appointment Scheduling

 	Patients can browse doctors’ profiles on the app and 
 	book an appointment with a doctor by looking at their availability via the calendar

### Real-time Visits

 	Patients can interact with doctors via video and audio calling
	Video conferencing should be smooth and high-quality

### Payments and billing (expected in future)

	Patients can pay online for their visits
	The app should be able to provide multiple payment options so that users can choose the convenient method

### Messages/Instant Chat (expected in future)

	Patients can also send messages to the doctors related to their problems or chat with them instantly

### Previous Medical Records (expected in future)

	Patients and doctors can access their past medical records from the application


### Notifications (expected in future)
	Remind patients about upcoming visits a few minutes before the scheduled time
 

**Doctor’s Dashboard:**

### Doctor Profile

	Doctors should be able to create profiles so patients can check their backgrounds, certifications and hospital affiliation

### Appointment Schedule Management

	Doctors should be able to make changes to their schedules and manage their day-to-day availability
	They should be able to accept and reject appointments
	Provide Digital Prescriptions

### Messages (expected in future)

	Doctors can chat with patients in real-time using instant messaging
	Doctors can respond to the patients’ queries and prescribe them medicines and treatment through messages

### Calls

	Face-to-face video consultations

### Visit Patients’ Medical History (expected in future)

	The option of viewing the patients’ medical history

### Notifications (expected in future)

	Alert doctors about their appointment a few minutes before it gets started
	Also, notify doctors when a patient requests for an appointment

