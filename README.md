# Distributed Hospital Management System

An N-Tier enterprise architecture developed for clinical management, utilizing the full Jakarta EE (Java EE) stack. This project implements distributed systems design patterns, asynchronous background processing, and robust data persistence.

**Academic Context:** Developed during the Bachelor's in Software Engineering (Erasmus+ Exchange at Univerza v Mariboru). Graded 20/20.

## System Architecture & Modules

The ecosystem is structured as a Maven multi-module project, divided into three main decoupled components:

### 1. Core Enterprise Application (`/Project`)
The central backend and web module built with Jakarta EE.
* **Business Logic (EJB):** Uses Stateless and Stateful Session Beans (`DoctorBean`, `PatientBean`, `DoctorManagerBean`) to encapsulate complex clinical rules and maintain session integrity.
* **Persistence (JPA/Hibernate):** Data persistence layer using the Java Persistence API to manage `Doctor` and `Patient` entities with relational mapping.
* **Asynchronous Processing (JMS):** Implements Message-Driven Beans (`DoctorSelectionMDB`) and message senders to handle heavy background tasks (e.g., doctor assignment workflows) asynchronously via message queues.
* **Web Interface (JSF):** A frontend layer built with JavaServer Faces (`doctors.xhtml`, `patient.xhtml`) for direct system administration.

### 2. JMS CLI Client (`/ConsoleApplicationJMS`)
A standalone Java Console Application acting as a message producer. It demonstrates the system's asynchronous messaging capabilities by dispatching `DoctorSelectionRequest` payloads to the main application's queue and awaiting registry responses.

### 3. REST CLI Client (`/ConsoleApplicationREST`)
A standalone client demonstrating system interoperability. It consumes the main application's exposed RESTful endpoints (`DoctorResource`, `PatientResource`) via JAX-RS, allowing remote terminal-based management of patients and doctors without relying on the web UI.

## Key Technologies & Standards

* **Framework:** Jakarta EE / Java EE
* **Business Tier:** EJB (Enterprise JavaBeans)
* **Messaging:** JMS (Java Message Service) & MDB (Message-Driven Beans)
* **Web Services:** JAX-RS (RESTful APIs)
* **Persistence:** JPA (Java Persistence API) / Hibernate
* **Frontend:** JSF (JavaServer Faces)
* **Build Tool:** Maven

## Repository Structure

```text
.
├── ConsoleApplicationJMS/    # Standalone JMS message producer
├── ConsoleApplicationREST/   # Standalone JAX-RS API consumer
├── Project/                  # Main Jakarta EE application (EJB, JPA, JSF)
└── pom.xml                   # Parent Maven POM aggregating all modules
```

## How to Build and Run

1. **Build the entire project:** Run the parent POM from the root directory to compile all modules simultaneously.
```bash
   mvn clean install
   ```
2. **Deploy the Core Application:** Deploy the generated `.war` file from the `Project/target` directory into your Jakarta EE application server (e.g., WildFly, GlassFish).
3. **Run the Clients:** Execute the standalone `.jar` files generated in the respective `ConsoleApplication` target folders to interact with the deployed backend.
