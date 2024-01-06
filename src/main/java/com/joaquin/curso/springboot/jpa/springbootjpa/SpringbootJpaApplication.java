package com.joaquin.curso.springboot.jpa.springbootjpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.joaquin.curso.springboot.jpa.springbootjpa.entities.Person;
import com.joaquin.curso.springboot.jpa.springbootjpa.repositories.IPersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{ //se implementa porque no trfabajamos con web sino con las base de datos

	@Autowired
	private IPersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception { //se trabaja con las instancias y probar lo que hacemos
		
		List<Person> persons = (List<Person>)repository.findAll();

		persons.stream().forEach(person -> System.out.println(person.toString()));
	}

}
