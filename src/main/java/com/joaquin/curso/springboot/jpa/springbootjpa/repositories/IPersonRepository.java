package com.joaquin.curso.springboot.jpa.springbootjpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.joaquin.curso.springboot.jpa.springbootjpa.entities.Person;

public interface IPersonRepository extends CrudRepository<Person, Long> { //se pone el tipo de la tabla que vamos a manejar, y el tipo de dato de la clave primaria

    
} 
