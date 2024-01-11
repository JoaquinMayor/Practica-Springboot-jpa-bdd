package com.joaquin.curso.springboot.jpa.springbootjpa.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Embeddable //Con esto se puede mandar o trabajar con estos datos o metodos desde distinos entitys
public class Audit {

    @Column(name = "create_at")
    private LocalDateTime creatAt;
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @PrePersist //Método que funciona antes de guardarse en la base de datos
    public void prePersist(){
        System.out.println("Evento de ciclo de vida del entity pre-persist");
        this.creatAt = LocalDateTime.now();
    }

    @PreUpdate //Método que funciona antes de que se actualice la base de datos
    public void preUpdate(){
        System.out.println("Evento de ciclo de vida del entity pre-update");
        this.updateAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(LocalDateTime creatAt) {
        this.creatAt = creatAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
