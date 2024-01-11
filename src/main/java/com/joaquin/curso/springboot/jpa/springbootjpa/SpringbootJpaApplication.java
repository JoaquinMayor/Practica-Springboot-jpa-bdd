package com.joaquin.curso.springboot.jpa.springbootjpa;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.joaquin.curso.springboot.jpa.springbootjpa.dto.PersonDto;
import com.joaquin.curso.springboot.jpa.springbootjpa.entities.Person;
import com.joaquin.curso.springboot.jpa.springbootjpa.repositories.IPersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{ //Se implementa porque no trabajamos con web sino con las base de datos

	@Autowired
	private IPersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception { //Función en la cual se trabaja con las instancias y probar lo que hacemos
		
		update();
	}

	

	@Transactional //Se usa siempre que se maneja las bdd que altere la tabla o consultas
	public void create(){
		System.out.println("---------------Creando---------------");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el nombre");
		String name = scanner.next();
		System.out.println("Ingrese el apellido");
		String lastname = scanner.next();
		System.out.println("Ingrese el lenguaje de programación");
		String programmingLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null, name, lastname, programmingLanguage);

		Person personNew = repository.save(person); //Guarda una nueva entidad en la base de datos

		repository.findById(personNew.getId()).ifPresent(p -> System.out.println(p));
		
	}

	@Transactional
	public void update(){
		System.out.println("---------------Edición---------------");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a editar");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		
			if(optionalPerson.isPresent()){
				Person person = optionalPerson.get();
				System.out.println("Ingrese el lenguaje de programación");
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
		System.out.println("---------------Eliminación---------------");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar");
		Long id = scanner.nextLong();
		repository.deleteById(id);

		
		scanner.close();
	}

	@Transactional
	public void delete2(){
		System.out.println("---------------Eliminación 2---------------");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar");
		Long id = scanner.nextLong();
		
		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresentOrElse(person -> repository.delete(person), 
									  ()->System.out.println("El usuario no existía"));

		
		scanner.close();
	}

	@Transactional(readOnly = true)
	public void findOne(){

		System.out.println("---------------Encontrando un usuario por diferente parámetros---------------");
		Person person = null;
		//Optional<Person> optionalPerson = repository.findById(8L);
		//Optional<Person> optionalPerson = repository.findOne(2L);
		Optional<Person> optionalPerson = repository.findByNameContaining("jo");
		 if(optionalPerson.isPresent()){
			person = optionalPerson.get();
		 } //El  findById devuelve un optional entonces tenemos que llamar al metodo get() o el orElseThow()
		
		 System.out.println(person);
	}

	@Transactional(readOnly = true)
	public void list(){

		System.out.println("---------------Armando listas por diferentes parámetros---------------");
		//List<Person> persons = (List<Person>)repository.findAll();
		//List<Person> persons = (List<Person>)repository.findByProgrammingLanguage("java");
		//List<Person> persons = (List<Person>)repository.buscarByProgrammingLanguage("Pyton", "Dario");
		List<Person> persons = (List<Person>)repository.findByProgrammingLanguageAndName("java","joaquin");

		persons.stream().forEach(person -> System.out.println(person.toString()));

		List<Object[]> personsValues = repository.obtenerPersonData();
		personsValues.stream().forEach(person -> System.out.println(person[0] + " es experto en " + person[1]));
	}


	//Consultas personalizadas
	@Transactional(readOnly = true)
	public void personalizedQueriesId(){
		System.out.println("---------------Buscando el id por el id XD personalizado---------------");
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Ingrese el id para el id:");
		Long id = scanner.nextLong();
		Long idSearch  = repository.getIdById(id);
		System.out.println("Su id es " + idSearch);
		scanner.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesName(){

		System.out.println("---------------Buscando el nombre por el id personalizado ---------------");
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Ingrese el id para el nombre:");
		Long id = scanner.nextLong();
		String name  = repository.getNameById(id);
		System.out.println("Su nombre es " + name);
		scanner.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesFullName(){
		System.out.println("---------------Buscando el nombre por el id el nombre completo personalizado---------------");
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Ingrese el id para el nombre completo:");
		Long id = scanner.nextLong();
		String fullName  = repository.getFullNameById(id);
		System.out.println("Su nombre es " + fullName);
		scanner.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesFullObject(){
		System.out.println("---------------Buscando persona completa por el id personalizado---------------");
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Ingrese el id para ver a la persona completa:");
		Long id = scanner.nextLong();
		Object[] personReg = (Object[]) repository.getPersonDataFullById(id);
		System.out.println("Id: " + personReg[0] + ", Nombre: " + personReg[1] + " " + personReg[2]+ ", lenguaje de programación: " + personReg[3]);
		scanner.close();
	}


	@Transactional(readOnly = true)
	public void personalizedQueriesMixPerson(){
		System.out.println("---------------Obteniendo lista de varias personas pero en forma mix---------------");
		List<Object[]> personRegs = repository.findAllMixPerson();

		personRegs.forEach(reg ->{
			System.out.println("Lenguaje de programación = " + reg[1] + ", Persona: " + reg[0].toString());
		});
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesMixPersonContructor(){
		System.out.println("---------------Obteniendo lista de varios datos de las personas pero a partir del contructor de la clase Person---------------");
		List<Person> personRegs = repository.findAllObjectPerson();

		personRegs.forEach(reg ->{
			System.out.println(reg.toString());
		});
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesMixPersonDto(){
		System.out.println("---------------Obteniendo lista del PersonDto para tener datos limitados de las personas(Mejor Forma)---------------");
		List<PersonDto> personRegs = repository.findAllObjectPersonDto();

		personRegs.forEach(reg ->{
			System.out.println("Nombre: "+ reg.getName() + " Apellido:" + reg.getLastname());
		});
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct(){
		System.out.println("---------------Obteniendo la cantidad de lenguajes de programación usando Distinct---------------");
		System.out.println("Consultas con nombres de personas");
		Long cant = repository.findAllProgrammingLanguagueCount();
		System.out.println("La cantidad de lenguajes de programación son " + cant);
		List<String> names = repository.findAllProgrammingLanguague();
		names.forEach(name -> System.out.println(name));
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesConcat(){
		System.out.println("---------------Obteniendo lista de nombres de personas usando concat---------------");
		System.out.println("Consultas con nombres de personas");
		List<String>  names = repository.findAllFullNameConcat();
		
		names.forEach(name -> System.out.println(name));
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesUpper(){
		System.out.println("---------------Obteniendo lista de nombres de personas usando concat y upper---------------");
		System.out.println("Consultas con nombres de personas");
		List<String>  names = repository.findAllFullNameConcatUpper();
		
		names.forEach(name -> System.out.println(name));
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesLower(){
		System.out.println("---------------Obteniendo lista de nombres de personas usando lower---------------");
		System.out.println("Consultas con nombres de personas");
		List<String>  names = repository.findAllFullNameConcatLower();
		
		names.forEach(name -> System.out.println(name));
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesBetween(){
		System.out.println("---------------Obteniendo lista de personas entre ciertos valores---------------");
		System.out.println("Consultas con nombres de personas");
		List<Person>  persons = repository.findAllBeweenName();
		
		persons.forEach(person -> System.out.println("Persona entre 2 y 5 " + person.toString()));
	}

	@Transactional(readOnly = true)
	public void personalizedCountMaxMin(){
		System.out.println("---------------Obteniendo el id min, el id, max y el total de usuarios---------------");
		Long min = repository.getMinPersonId();
		Long max = repository.getMaxPersonId();
		Long total = repository.getTotalPersons();

		System.out.println("El id minimo es: " + min + " El id maximo es: " + max + " y el total de usuarios es: " + total);

		System.out.println("Consulta con su nombre y su largo");
		List<Object[]> regs = repository.getPersonNameLength();

		regs.forEach(reg -> {
			String name = (String)reg[0];
			Integer length = (Integer)reg[1];
			System.out.println("name= " + name + ", length= " + length);
		});

		System.out.println("Consulta con el nombre mas corto");
		Integer minNameLength = repository.getMinLengthName();
		System.out.println(minNameLength);

		System.out.println("Consulta con el nombre mas largo");
		Integer maxNameLength = repository.getMaxLengthName();
		System.out.println(maxNameLength);

	}

	@Transactional(readOnly = true)
	public void personalizedAgregation(){
		System.out.println("---------------Obteniendo el id min, el max, la sum, la suma de todos los id y el promedio de cantidad de letras de los nombres de lo usuarios---------------");
		
		Object[] resumeReg = (Object[])repository.getResumeAggregationFunction();

		System.out.println("Min= " + resumeReg[0]+ ", Max= " + resumeReg[1] + ", Sum= " + resumeReg[2] + ", Avg= " + resumeReg[3]);
	} 

	@Transactional(readOnly = true)
	public void subQueries(){
		System.out.println("---------------Obteniendo lista de nombres mas cortos con subqueries ---------------");
		
		List<Object[]> registers = repository.getShorterNames();
		registers.forEach(reg -> {
			String name = (String)reg[0];
			Integer length = (Integer)reg[1];
			System.out.println("name= " + name + ", length= " + length);
		});

		Optional<Person> optionalPerson= repository.getLastRegistration();
		System.out.println("---------------Obteniendo última persona registrada---------------");

		if(optionalPerson.isPresent()){
			System.out.println("Ultima persona registrada: " + optionalPerson.get().toString());
		}
	}

	@Transactional(readOnly = true)
	public void whereIn(){
		System.out.println("---------------Obteniendo lista de personas segun ciertas ids---------------");

		List<Person> persons = repository.getPersonByIds(Arrays.asList(1L,3L,5L));
		persons.forEach(System.out::println);
	}
}
