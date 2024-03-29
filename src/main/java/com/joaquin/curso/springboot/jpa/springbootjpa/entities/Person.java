package com.joaquin.curso.springboot.jpa.springbootjpa.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //Determina que es una clase de persistencia para que lo maneje hibernate y jpa
@Table(name = "persons") //Se pone para determinar de que tabla lo sacamos los datos, si no se pone por defecto va a ser con el nombre del objeto
public class Person { //Jpa usa el contructor vacío para crear la instancia, así que siempre hay que tenerlo, pero igual podemos tener contructores con parámetros
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Identity para que lo genere de manera autoincremental
    private Long id;
    @Column(name = "first_name")
    private String name;
    private String lastname;
    @Column(name = "programming_language") //Indica el nombre de la tabla a la que debe buscar en la base de datos, si no se pone va a buscar una columna que se llame igual al atributo
    private String programmingLanguage;
    @Embedded//Con esto se puede obtener los datos de otra clase que se comparten entre varios entitys
    private Audit audit = new Audit();
   
    
    
    public Person() {
    }


    public Person(String name, String lastname) { //De esta manera se puede hacer una devolución personalizada con algunos campos de persona 
        this.name = name;
        this.lastname = lastname;
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
                + programmingLanguage + ", createAt= " + audit.getCreatAt() + ", updateAt= "+ audit.getUpdateAt();
    }

    
}
