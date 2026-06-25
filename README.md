# 🎮 Video Game Store - Capstone API Layer

Welcome to the backend API layer of the **Video Game Store**, a RESTful management platform tailored for inventory tracking, gaming category filtering, and product updates. 
<img width="1887" height="1003" alt="Screenshot 2026-06-25 071807" src="https://github.com/user-attachments/assets/aa9573a0-fd59-4160-9eee-4218eaf517fd" />


---

## 🛠️ System Architecture & Data Flow



The application establishes a clear separation of concerns using a standard layer-cake architecture:
1. **Presentation Layer (Controllers):** Exposes endpoints like `/products` and handles browser network protocol rules (CORS).
2. **Business Logic Layer (Services):** Manages data validation, inventory checks, and game property mapping.
3. **Data Access Layer (Repositories):** Uses Spring Data JPA and Hibernate to translate Java objects into relational MySQL queries inside the gaming database.

---

## 🐛 Defect Resolution Log

During the development and testing phases, two critical structural bugs were caught and fixed:

### 1. Bug 1: Missing Results in Product Search Functionality
* **The Issue:** Users reported that the product search functionality was returning incorrect results, causing certain video games to completely vanish from the list. 

<img width="1919" height="1065" alt="Screenshot 2026-06-22 222450" src="https://github.com/user-attachments/assets/387c96de-bc63-4d5b-936a-49bb01584309" />
<img width="1413" height="761" alt="Screenshot 2026-06-22 222534" src="https://github.com/user-attachments/assets/391e7f86-462f-454d-84a8-0b508593eba0" />


* **The Fix:** Removed the restrictive `isFeatured` parameter constraint from the main search query logic in the database layer. This allows the search function to look across the entire video game catalog, ensuring all matching titles appear regardless of their featured status.

<img width="1700" height="537" alt="Screenshot 2026-06-25 062739" src="https://github.com/user-attachments/assets/e36de1df-05e8-4124-8647-d438dd6bada3" />
<img width="1890" height="982" alt="Screenshot 2026-06-22 222752" src="https://github.com/user-attachments/assets/fc293fe1-4330-48a9-b04c-1ee28c032c43" />
<img width="1408" height="755" alt="Screenshot 2026-06-22 222812" src="https://github.com/user-attachments/assets/08876e37-96af-4c53-acf2-fa210d0e244a" />



### 2. Bug 2: Video Game Stock Level Persistence Mismatch
* **The Issue:** Submitting an update payload (`PUT /products/1`) via Insomnia changed a game's name, price, and description successfully, but the `stock` count dropped or remained unaffected in the database. The service mapping code was skipping the stock assignment field entirely.
<img width="1417" height="496" alt="Screenshot 2026-06-25 063916" src="https://github.com/user-attachments/assets/907fc6b6-4c8a-4b05-8ae8-06a71971db41" />
<img width="1088" height="611" alt="Screenshot 2026-06-25 063957" src="https://github.com/user-attachments/assets/c558a886-85ad-4c25-8bf3-b855a80f8b41" />






* **The Fix:** Modified `ProductService.java` to explicitly map incoming inventory updates using `.setStock()`. Verified the correction using automated database-slice regression testing.
* <img width="1714" height="511" alt="Screenshot 2026-06-25 062817" src="https://github.com/user-attachments/assets/25dba397-6e49-463f-8e13-deb21e37d70b" />
<img width="1407" height="437" alt="Screenshot 2026-06-25 064258" src="https://github.com/user-attachments/assets/fbd7b67a-d2fc-4b49-819c-8a25074ccda8" />
<img width="1097" height="611" alt="Screenshot 2026-06-25 064315" src="https://github.com/user-attachments/assets/7ae0ca85-731c-43bb-ae07-a6714d9a3cb6" />



---

## 🚀 Technical Stack

* **Framework:** Spring Boot 3.x (Java 17)
* **Data Layer:** Spring Data JPA, Hibernate ORM
* **Database:** MySQL Server (`create_database_videogamestore.sql`), H2 In-Memory (Test Isolation Engine)
* **Testing Suite:** JUnit 5, Spring `@DataJpaTest`
* **API Client Tooling:** Insomnia REST Client

---

## 🧪 Testing and Quality Assurance

The data tier is fortified with automated regression suites. To execute the automated integration checks:

1. Open the project repository inside IntelliJ IDEA.
2. Locate `src/test/java/org/yearup/repository/ProductRepositoryTest.java`.
3. Click the **Run Class** action button to verify data state mutations.

### Verified Test Vectors:
* **Manual UI Integration Test (Bug 1 - Product Search Fix):** Manually verified by launching the local frontend dashboard through an active HTTP server framework (`http://localhost:3000`). Testing was conducted by comparing the expected quantity of matching database products against what actually rendered on-screen. Because this bug was purely logic-based, we verified the fix by tracing how query parameters mapped across our product models, service layers, and search parameters to ensure zero matching records were dropped
<img width="1910" height="1009" alt="Screenshot 2026-06-25 071408" src="https://github.com/user-attachments/assets/24709ec9-b638-4741-81ab-89c4ef5580ce" />

* `update_shouldPersistNewStockValue_inDatabase`: (Bug 2 - Stock Fix):**Validates that inventory modifications (like modifying a Halo 1 Remake's stock value to `72`) explicitly persist across database transactions.
* <img width="1800" height="1007" alt="Screenshot 2026-06-25 065125" src="https://github.com/user-attachments/assets/36fb4bf7-b898-4012-aa48-da60c4469fd8" />
