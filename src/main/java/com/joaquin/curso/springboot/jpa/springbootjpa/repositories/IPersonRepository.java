package com.joaquin.curso.springboot.jpa.springbootjpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.joaquin.curso.springboot.jpa.springbootjpa.dto.PersonDto;
import com.joaquin.curso.springboot.jpa.springbootjpa.entities.Person;
import java.util.List;
import java.util.Optional;


public interface IPersonRepository extends CrudRepository<Person, Long> { //Se pone el tipo de la tabla que vamos a manejar, y el tipo de dato de la clave primaria

    List<Person> findByProgrammingLanguage(String programmingLanguage); //Es para hacer otro filtro no es necesario agregar lógica con poner el nombre de esta manera es suficiente el findBy es una palabra clave

    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2") //Se pone =?1 para que sea igual al primer parámetro, y si se pone un segundo se pone and parámetro=?2
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name); //De esta manera si no se usa la palabra clave, cuando lo ejecutamos tenemos que ponerle la anotación @Query y la consulta no tiene que se de bdd sino de jpa
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    Optional<Person> findByName(String name); //Palabra clave para buscar uno es el findBy y el nombre del atributo

    Optional<Person> findByNameContaining(String name);//Palabra clave para buscar uno es el findBy, el nombre del atributo y Containing para que funcione como el like


    //Consultas personalizadas 
    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id);

    @Query("select p.id from Person p where p.id=?1")
    Long getIdById(Long id);

    @Query("select concat(p.name,' ', p.lastname) as fullname from Person p where p.id=?1") //Se pueden cocatenar Strings
    String getFullNameById(Long id);

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id=?1")
    Object getPersonDataFullById(Long id);


    @Query("select p, p.programmingLanguage from Person p")
    List<Object[]> findAllMixPerson();

    @Query("select new Person(p.name, p.lastname) from Person p")
    List<Person> findAllObjectPerson();


     @Query("select new com.joaquin.curso.springboot.jpa.springbootjpa.dto.PersonDto(p.name, p.lastname) from Person p") //Como no es una clase entity el personDto se pone todo el package delante de la instancia
    List<PersonDto> findAllObjectPersonDto();

    @Query("select distinct(p.programmingLanguage) from Person p ") //No se repite
    List<String> findAllProgrammingLanguague();

    @Query("select count(distinct(p.programmingLanguage)) from Person p ") //Cuenta los nombres distintos
    Long findAllProgrammingLanguagueCount();


    @Query("select p.name || ' ' || p.lastname from Person p ") //Se pueden cocatenar Stringscon el ||
    List<String> findAllFullNameConcat();

    @Query("select upper(p.name || ' ' || p.lastname) from Person p ") //Letras mayúsculas
    List<String> findAllFullNameConcatUpper();

    @Query("select lower(p.name || ' ' || p.lastname) from Person p ") //Letras minúsculas
    List<String> findAllFullNameConcatLower();

    @Query("select p from Person p where p.id between 2 and 5 order by p.name asc, p.lastname desc") //BETWEEN mostrar entre 2 valores, además el order by para tener un orden a la hora de tener los datos según los distintos parámetros, primero los principales y despues en caso de repetición
    List<Person> findAllBeweenId();

    @Query("select p from Person p where p.name between 'J' and 'P' order by p.name") //Entre los que empiezan con esas letras
    List<Person> findAllBeweenName(); //Se puede hacer también sin el query poniendo findByparametroBetween(parámetro 1, parametro 2)
    //Ejemplo de función con palabra claves findByIdBetweenOrderByIdDescLastnameAsc

    //Funciones de agregación

    @Query("select count(p.id) from Person p") //Contar cantidad de algo
    Long getTotalPersons();

    @Query("select min(p.id) from Person p") //Saber el mínimo de un campo numerico
    Long getMinPersonId();

    @Query("select max(p.id) from Person p") //Saber el máximo de un campo numerico
    Long getMaxPersonId();

    @Query("select p.name, length(p.name) from Person p") //Para saber la longitud de cada nombre
    public List<Object[]> getPersonNameLength(); 

    @Query("select min(length(p.name)) from Person p")
    public Integer getMinLengthName();

    @Query("select max(length(p.name)) from Person p")
    public Integer getMaxLengthName();

    @Query("select min(p.id), max(p.id), sum(p.id), avg(length(p.name)), count(p.id) from Person p")
    public Object getResumeAggregationFunction();

    //Subconsultas
    @Query("select p.name, length(p.name) from Person p where length(p.name) = (select min(length(p2.name)) from Person p2)")
    public List<Object[]> getShorterNames();

    @Query("select p from Person p where p.id = (select max(p2.id) from Person p2)")
    public Optional<Person> getLastRegistration();

    //Where in, se usa para buscar un conjunto de valores 

    @Query("select p from Person p where p.id in ?1") //Para que sean distinto a los valores ingresados en vez de poner in se pone not in
    public List<Person> getPersonByIds(List<Long> ids);
} 
