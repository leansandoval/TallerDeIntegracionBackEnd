<div align="center">
  <h1>⚙️ Taller de Integración - BackEnd</h1>
  <p><em>Back-End del Sistema de Control de Stock implementado con Spring Boot y Java (3680) - 1C 2024 - UNLaM.</em></p>

  <!-- Badges -->
  <img src="https://img.shields.io/badge/Universidad-UNLaM-1F4F90?style=for-the-badge&logo=google-scholar&logoColor=white" alt="UNLaM" />
  <img src="https://img.shields.io/badge/Año-2024-2EA043?style=for-the-badge" alt="2024" />
  <img src="https://img.shields.io/badge/Lenguaje-Java-007396?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" /><img src="https://img.shields.io/badge/-Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot" /><img src="https://img.shields.io/badge/-MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/Grupo-1-7057D2?style=for-the-badge" alt="Grupo 1" />
  <img src="https://img.shields.io/badge/Estado-Completado-blue?style=for-the-badge" alt="Completado" />
</div>

<br>

## 📑 Índice
- [Información de la Asignatura](#informacion-de-la-asignatura)
- [Trayecto - Gestión y Complementarias](#trayecto)
- [Docentes](#docentes)
- [Integrantes](#integrantes)
- [Descripción General](#descripcion-general)
- [Estructura y Arquitectura MVC](#estructura-y-arquitectura-mvc)
- [Circuito Controller, Service y Repository](#circuito-controller-service-y-repository)
- [Dependencias](#dependencias)
- [Ejecución y Uso](#ejecucion-y-uso)
- [Comunicación Front-End y Back-End](#comunicacion-front-end-y-back-end)
- [Contribución](#contribucion)
- [Herramientas Utilizadas](#herramientas-utilizadas)
- [Estructura del Proyecto](#estructura-del-proyecto)

---

<a id="informacion-de-la-asignatura"></a>
## ℹ️ Información de la Asignatura
* **Carrera**: Ingeniería en Informática
* **Asignatura**: Taller de Integración (3680)
* **Cuatrimestre**: Primer Cuatrimestre
* **Año**: 2024
* **Grupo**: 1

<a id="trayecto"></a>
## ⚙️ Trayecto - Gestión y Complementarias
* **Año académico**: Segundo Año - Segundo Cuatrimestre
* **Responsable / Jefe de cátedra**: Eribe, Roberto
* **Carga horaria semanal**: 4 hs
* **Carga horaria total**: 64 hs
* **Modalidad**: Virtual
* **Correlativas anteriores**:
  * Principios de Calidad de Software (3626)
  * Introducción a la Gestión de Requisitos (3630)
  * Introducción a Proyectos Informáticos (3632)
  * Tópicos de Programación (3635)
  * Bases de Datos (3636)
  * Arquitectura de Computadoras (3638)

<a id="docentes"></a>
## 👨‍🏫 Docentes
* Bucher, Mariano
* Eribe, Roberto
* Paker, Fernando

<a id="integrantes"></a>
## 👥 Integrantes
<div align="center">

| DNI | Apellido/s | Nombre/s |
| :--: | :--: | :--: |
| **40.137.650** | Corrales | Mauro Exequiel |
| **44.834.085** | Justiniano | Maximo |
| **37.276.705** | Pompeo | Nicolas Ruben |
| **41.548.235** | Sandoval Vasquez | Juan Leandro |
| **37.841.788** | Sullca | Willian Fernando |

</div>

---

<a id="descripcion-general"></a>
## 🚀 Descripción General
Este proyecto corresponde a la aplicación Back-End del Sistema de Control de Stock, desarrollada con Java y Spring Boot para implementar una API REST robusta encargada de la gestión de usuarios, autenticación y persistencia de datos.

<div align="center">
  <img src="https://github.com/user-attachments/assets/79adca90-1804-4f77-9234-e68c66e0af44" alt="¿Como funciona Spring Boot?" width="450" />
  <br>
  <sup><em>Arquitectura y flujo de ejecución en Spring Boot</em></sup>
</div>

---

<a id="estructura-y-arquitectura-mvc"></a>
## 🏗️ Estructura y Arquitectura MVC
El proyecto sigue la arquitectura Modelo-Vista-Controlador (MVC) para mantener una separación clara de responsabilidades:

* **Modelo**: Define la entidad `Usuario` que representa la estructura de datos almacenada en la base de datos.
* **Repositorio**: Define la interfaz `UsuarioRepository` para acceder y manipular los datos en el motor de persistencia.
* **Servicio**: Implementa la clase `UsuarioService` con la lógica de negocio y reglas del sistema.
* **Controlador**: Define `UsuarioController` para atender y procesar las peticiones HTTP entrantes.

<div align="center">
  <img src="https://github.com/user-attachments/assets/ca14a98f-0c92-4d02-ab8e-530eeba91a6d" alt="Spring Boot con Arquitectura MVC" width="700" />
  <br>
  <sup><em>Estructura de capas en Spring Boot bajo el patrón MVC</em></sup>
</div>

---

<a id="circuito-controller-service-y-repository"></a>
## 🔄 Circuito de Controller, Service y Repository en Spring Boot

El flujo de información sigue una arquitectura en capas ordenada:

1. **Controller**: Maneja las solicitudes HTTP y las respuestas hacia el cliente.
2. **Service**: Procesa y valida la lógica de negocio.
3. **Repository**: Se comunica directamente con la base de datos mediante JPA.

### 1. Controlador (Controller)
El controlador recibe las peticiones del front-end y las delega al servicio correspondiente:

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
```

### 2. Servicio (Service)
Actúa como intermediario entre el controlador y el repositorio, ejecutando la lógica operativa:

```java
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
```

### 3. Repositorio (Repository)
Interactúa directamente con la base de datos realizando operaciones CRUD mediante JPA:

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Métodos de consulta personalizados si es necesario
}
```

### 4. Modelo (Model)
Representa la estructura de entidades en la base de datos:

```java
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    // Getters y setters
}
```

---

<a id="dependencias"></a>
## 📦 Dependencias
El proyecto utiliza las siguientes tecnologías y librerías principales:
* **Spring Boot**: Framework base para el desarrollo de la aplicación API REST.
* **Spring Data JPA**: Abstracción para la gestión de persistencia de datos y repositorios.
* **Hibernate**: Motor ORM para el mapeo objeto-relacional.
* **MySQL Database**: Sistema de gestión de base de datos relacional para el almacenamiento persistente.

<div align="center">
  <img src="https://github.com/user-attachments/assets/ce9e9e37-c7e1-4d49-9f5e-e9646f52ff9c" alt="¿Qué es Hibernate en Java?" width="700" />
  <br>
  <sup><em>Rol del motor ORM Hibernate en la capa de persistencia</em></sup>
  <br><br>
  <img src="https://github.com/user-attachments/assets/f8f7f0e3-7568-47eb-9859-213c97fad0a7" alt="Flujo de Spring Data JPA hasta Hibernate" width="700" />
  <br>
  <sup><em>Flujo de abstracción desde Spring Data JPA hasta Hibernate y la Base de Datos</em></sup>
</div>

---

<a id="ejecucion-y-uso"></a>
## 🛠️ Ejecución y Uso

### Ejecutar la Aplicación
Para iniciar el servidor embebido Tomcat en el puerto por defecto `8080`, ejecutar:

```bash
mvn spring-boot:run
```

### Ejemplo de Endpoint: Iniciar Sesión

```http
POST /api/login
```

**Parámetros de solicitud:**
* `username`: Nombre de usuario
* `password`: Contraseña

**Respuestas HTTP:**
* `200 OK`: Usuario encontrado y autenticado correctamente.
* `401 Unauthorized`: Nombre de usuario o contraseña incorrectos.

<div align="center">
  <img src="https://github.com/user-attachments/assets/cf52172b-aeb7-4569-84c8-4c5e3bc6f4c1" alt="Swagger de la API funcionando" width="700" />
  <br>
  <sup><em>Prueba y documentación interactiva de la API REST mediante Swagger UI</em></sup>
</div>

---

<a id="comunicacion-front-end-y-back-end"></a>
## 🌐 Comunicación entre Front-End y Back-End

Para integrar el cliente web (Angular) con la API REST de Spring Boot se sigue este flujo:

1. **Solicitud HTTP desde el Front-End**:
```typescript
// Service en Angular para consumir la API REST
@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }
}
```
2. **Manejo en el Controlador**: El controlador recibe la petición, invoca al servicio y retorna el estado HTTP correspondiente.
3. **Procesamiento en el Servicio**: Realiza las validaciones de negocio y consulta al repositorio.
4. **Operación en la Base de Datos**: El repositorio ejecuta la consulta SQL/JPA requerida en la base de datos.
5. **Respuesta al Cliente**: Los datos procesados retornan al front-end serializados en formato JSON.

---

<a id="contribucion"></a>
## 🤝 Contribución

Si deseas contribuir al desarrollo del proyecto:

1. Haz un fork del repositorio.
2. Crea una rama para tu funcionalidad (`git checkout -b feature/NuevaFuncionalidad`).
3. Realiza tus cambios y confirma los commits (`git commit -am 'Add nueva funcionalidad'`).
4. Realiza el push a la rama (`git push origin feature/NuevaFuncionalidad`).
5. Abre un Pull Request para revisión.

---

<a id="herramientas-utilizadas"></a>
## 🛠️ Herramientas Utilizadas
* **Java (JDK 17)**: Lenguaje principal aplicado para construir la lógica y arquitectura Back-End.
* **Spring Boot**: Framework utilizado para la estructuración de la API RESTful.
* **Spring Data JPA / Hibernate**: Herramientas ORM para el acceso y gestión de persistencia de datos.
* **MySQL Database**: Sistema de gestión de base de datos relacional utilizado para el almacenamiento persistente.
* **Apache Maven**: Gestor de dependencias y automatización de la compilación del proyecto.
* **Swagger UI / OpenAPI**: Entorno para prueba interactiva y especificación de endpoints REST.
* **Visual Studio Code / Eclipse / IntelliJ IDEA**: IDEs empleados para el desarrollo del proyecto.

---

<a id="estructura-del-proyecto"></a>
## 📁 Estructura del Proyecto
```text
📦 TallerDeIntegracionBackEnd
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java
 ┃ ┃ ┃ ┗ 📂 com
 ┃ ┃ ┃ ┗ 📂 unlam
 ┃ ┃ ┃ ┗ 📂 stock
 ┃ ┃ ┃ ┣ 📂 controller
 ┃ ┃ ┃ ┃ ┗ 📜 UserController.java
 ┃ ┃ ┃ ┣ 📂 model
 ┃ ┃ ┃ ┃ ┗ 📜 User.java
 ┃ ┃ ┃ ┣ 📂 repository
 ┃ ┃ ┃ ┃ ┗ 📜 UserRepository.java
 ┃ ┃ ┃ ┗ 📂 service
 ┃ ┃ ┃ ┗ 📜 UserService.java
 ┃ ┃ ┗ 📂 resources
 ┃ ┃ ┗ 📜 application.properties
 ┃ ┗ 📂 test
 ┣ 📜 pom.xml
 ┗ 📜 README.md
```
