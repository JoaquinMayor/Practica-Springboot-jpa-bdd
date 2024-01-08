package com.joaquin.curso.springboot.jpa.springbootjpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.joaquin.curso.springboot.jpa.springbootjpa.entities.Person;
import java.util.List;
import java.util.Optional;


public interface IPersonRepository extends CrudRepository<Person, Long> { //se pone el tipo de la tabla que vamos a manejar, y el tipo de dato de la clave primaria

    List<Person> findByProgrammingLanguage(String programmingLanguage); //es para hacer otro filtro no es necesario agregar logica con poner el nombre de esta manera es suficiente el findBy es una palabra clave

    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2") //se pone =?1 para que sea igual al primer parametro, y si se pone un segundo se pone and parametro=?2
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name); //de evsta manera si no se usa la palabra clave, cuando lo ejecutamos tenemos que ponerle la anotacion @Query y la consulta no tiene que se de bdd sino de jpa
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
} 
