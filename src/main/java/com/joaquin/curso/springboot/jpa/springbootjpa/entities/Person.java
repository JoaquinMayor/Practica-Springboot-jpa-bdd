package com.joaquin.curso.springboot.jpa.springbootjpa.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //determina que es una clase de persistencia
@Table(name = "persons") //se pone para determinar de que tabla lo sacamos los datos, si no se pone por defecto va a ser con el nombre del objeto
public class Person { //jpa usa el contructor vacio para crear la instancia, asi que siempre hay que tenerlo, pero igual podemos tener contructores con parametros
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //identity para que lo genere de manera autoincremental
    private Long id;
    @Column(name = "first_name")
    private String name;
    private String lastname;
    @Column(name = "programming_language") //indica el nombre de la tabla a la que debe buscar en la base de datos, si no se pone va a buscar una columna que se llame igual al atributo
    private String programmingLanguage;
    
    
    
    public Person() {
    }

    public Person(Long id, String name, String lastname, String programmingLanguage) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.programmingLanguage = programmingLanguage;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getProgrammingLanguage() {
        return programmingLanguage;
    }
    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", lastname=" + lastname + ", programmingLanguage="
                + programmingLanguage;
    }

    
}
