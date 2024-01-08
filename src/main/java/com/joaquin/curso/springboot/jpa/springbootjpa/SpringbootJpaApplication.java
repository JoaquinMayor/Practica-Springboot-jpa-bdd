package com.joaquin.curso.springboot.jpa.springbootjpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

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
		
		create();
	}

	@Transactional //Se usa siempre que se maneja las bdd que altere la tabla o consultas
	public void create(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el nombre");
		String name = scanner.next();
		System.out.println("Ingrese el apellido");
		String lastname = scanner.next();
		System.out.println("Ingrese el lenguaje de programacion");
		String programmingLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null, name, lastname, programmingLanguage);

		Person personNew = repository.save(person); //guarda una nueva entidad en la base de datos

		repository.findById(personNew.getId()).ifPresent(p -> System.out.println(p));
		
	}

	@Transactional
	public void update(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a editar");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		
			if(optionalPerson.isPresent()){
				Person person = optionalPerson.get();
				System.out.println("Ingrese el lenguaje de programacion");
				String programmingLenguage = scanner.next();
				person.setProgrammingLanguage(programmingLenguage);
				repository.save(person);
			}else{
				System.out.println("El usuario con esa id no existe");
			}
			
		
		
		scanner.close();
	}

	@Transactional
	public void delete(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar");
		Long id = scanner.nextLong();
		repository.deleteById(id);

		
		scanner.close();
	}

	@Transactional
	public void delete2(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar");
		Long id = scanner.nextLong();
		
		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresentOrElse(person -> repository.delete(person), 
									  ()->System.out.println("El usuario no existia"));

		
		scanner.close();
	}

	@Transactional(readOnly = true)
	public void findOne(){
		Person person = null;
		//Optional<Person> optionalPerson = repository.findById(8L);
		//Optional<Person> optionalPerson = repository.findOne(2L);
		Optional<Person> optionalPerson = repository.findByNameContaining("jo");
		 if(optionalPerson.isPresent()){
			person = optionalPerson.get();
		 } //el  findById devuelve un pocional entonces tenemos que llamar al metodo get() o el orElseThow
		
		 System.out.println(person);
	}

	@Transactional(readOnly = true)
	public void list(){
		//List<Person> persons = (List<Person>)repository.findAll();
		//List<Person> persons = (List<Person>)repository.findByProgrammingLanguage("java");
		//List<Person> persons = (List<Person>)repository.buscarByProgrammingLanguage("Pyton", "Dario");
		List<Person> persons = (List<Person>)repository.findByProgrammingLanguageAndName("java","joaquin");

		persons.stream().forEach(person -> System.out.println(person.toString()));

		List<Object[]> personsValues = repository.obtenerPersonData();
		personsValues.stream().forEach(person -> System.out.println(person[0] + " es experto en " + person[1]));
	}

}
