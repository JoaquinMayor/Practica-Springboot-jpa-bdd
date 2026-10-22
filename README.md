# Manejo de Base de Datos y Queries en SpringBoot

### En el siguiente código encontraremos diferentes maneras y características que pueden tener los diferentes llamados a los datos de nuestra Base de Datos.

#### Todas las Queries se podrán observar dentro del Repository **IPersonRepository.java** 

#### Todas sus aplicaciones por ejemplo: Creación, edición, eliminación de datos, etc, se ven y aplican en el main **SpringbootJpaApplication.java**.

#### Pudiendo ejecutar cada función llamándola en el *run*.

<p>
Dicho diseño se pensó para hacer más rápida y más fácil de testear, estos códigos para fines didácticos.
</p>

![Queries](https://www.educative.io/cdn-cgi/image/f=auto,fit=contain,w=600/api/page/6045894932692992/image/download/6526134821847040)

---

## Objetivo

Proyecto de práctica del curso de Spring Boot 3 centrado en **Spring Data JPA con Hibernate y MySQL**. No es una aplicación web: la clase principal implementa `CommandLineRunner` y desde su método `run()` se invoca el ejemplo que se quiera probar (CRUD por consola, consultas derivadas por nombre de método, consultas JPQL con `@Query`, proyecciones a DTO, funciones de agregación, subconsultas, etc.).

## Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot | 3.2.1 |
| Spring Data JPA (`spring-boot-starter-data-jpa`) / Hibernate | gestionada por Spring Boot |
| MySQL Connector/J (`mysql-connector-j`) | gestionada por Spring Boot |
| Spring Boot DevTools | gestionada por Spring Boot |
| Base de datos | MySQL |
| Maven (wrapper `mvnw`) | - |

## Estructura del proyecto

```
src/main/java/com/joaquin/curso/springboot/jpa/springbootjpa
├── SpringbootJpaApplication.java   # CommandLineRunner con todos los métodos de ejemplo
├── dto
│   └── PersonDto.java              # DTO (name, lastname) para proyecciones en JPQL
├── entities
│   ├── Person.java                 # Entidad mapeada a la tabla "persons"
│   └── Audit.java                  # Clase @Embeddable con fechas de creación/actualización
└── repositories
    └── IPersonRepository.java      # CrudRepository<Person, Long> con todas las consultas
```

### Entidad `Person` (tabla `persons`)

| Atributo | Columna | Notas |
|---|---|---|
| `id` | `id` | `@Id`, `@GeneratedValue(strategy = IDENTITY)` (autoincremental) |
| `name` | `first_name` | `@Column(name = "first_name")` |
| `lastname` | `lastname` | |
| `programmingLanguage` | `programming_language` | |
| `audit` | `create_at`, `updated_at` | `@Embedded` de la clase `Audit` |

## Conceptos aplicados

- **Mapeo de entidades**: `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`; JPA necesita el constructor vacío, aunque se pueden agregar constructores con parámetros.
- **`@Embeddable` / `@Embedded`**: la clase `Audit` agrupa campos reutilizables entre entidades.
- **Eventos del ciclo de vida**: `@PrePersist` (asigna `create_at` antes de insertar) y `@PreUpdate` (asigna `updated_at` antes de actualizar).
- **`CrudRepository`**: `save`, `findById`, `findAll`, `deleteById`, `delete`.
- **Consultas derivadas por nombre de método**: `findByProgrammingLanguage`, `findByProgrammingLanguageAndName`, `findByName`, `findByNameContaining` (equivalente a `like`), y el ejemplo comentado `findByIdBetweenOrderByIdDescLastnameAsc`.
- **JPQL con `@Query`** y parámetros posicionales (`?1`, `?2`):
  - Selección de campos sueltos (`List<Object[]>`, `Object`, `String`, `Long`).
  - Instanciación de objetos en la consulta: `new Person(p.name, p.lastname)` y `new ...dto.PersonDto(...)` (para clases que no son entidad se usa el nombre completo del paquete).
  - `like`, `distinct`, `count(distinct ...)`, `concat(...)` y `||`, `upper`, `lower`, `length`.
  - `between` con números y letras, y `order by` con varios criterios.
  - Funciones de agregación: `count`, `min`, `max`, `sum`, `avg`.
  - Subconsultas (nombres más cortos, último registro).
  - `where ... in ?1` con una lista de ids (y la variante `not in`).
- **`Optional`**: manejo de resultados únicos con `isPresent()`, `get()`, `ifPresent()` e `ifPresentOrElse()`.
- **`@Transactional`** en las operaciones de escritura y **`@Transactional(readOnly = true)`** en las de solo lectura.
- **Entrada por consola** con `Scanner` para los ejemplos de crear, editar y eliminar.

## Métodos de ejemplo en `SpringbootJpaApplication`

| Método | Qué hace |
|---|---|
| `create()` | Pide nombre, apellido y lenguaje por consola y guarda una nueva persona. |
| `update()` | Pide un id y actualiza el lenguaje de programación (es el que se ejecuta por defecto en `run()`). |
| `delete()` / `delete2()` | Eliminan por id (`deleteById` o buscando primero con `findById` + `delete`). |
| `findOne()` | Busca una persona con distintas variantes (`findById`, `findOne`, `findByNameContaining`). |
| `list()` | Listados filtrados por lenguaje y/o nombre, y proyección nombre/lenguaje. |
| `personalizedQueriesId/Name/FullName/FullObject()` | Consultas personalizadas que devuelven id, nombre, nombre completo o todos los datos por id. |
| `personalizedQueriesMixPerson()` / `...Contructor()` / `...Dto()` | Resultados mixtos, instanciación con constructor de `Person` y proyección a `PersonDto`. |
| `personalizedQueriesDistinct/Concat/Upper/Lower/Between()` | Ejemplos de `distinct`, concatenación, mayúsculas/minúsculas y `between`. |
| `personalizedCountMaxMin()` / `personalizedAgregation()` | Funciones de agregación y longitudes de nombres. |
| `subQueries()` | Nombres más cortos y último registro usando subconsultas. |
| `whereIn()` | Personas cuyos ids están en la lista `1, 3, 5`. |

Para probar otro ejemplo, reemplaza la llamada `update();` dentro de `run()` por el método deseado.

## Configuración

Se requiere un servidor **MySQL** en `localhost:3306` con una base de datos llamada `db_springboot`:

```sql
CREATE DATABASE db_springboot;
```

`src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_springboot
spring.datasource.username=root
spring.datasource.password=<tu_password>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

Con `ddl-auto=update` Hibernate crea/actualiza la tabla `persons` automáticamente. Ajusta usuario y contraseña a tu entorno. Para los ejemplos de consulta conviene cargar previamente algunos registros en la tabla.

## Cómo ejecutarlo

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

Al iniciar se ejecuta el método llamado en `run()`; los resultados y las sentencias SQL (`show-sql=true`) se muestran en la consola. Los ejemplos que usan `Scanner` esperan datos por teclado.
