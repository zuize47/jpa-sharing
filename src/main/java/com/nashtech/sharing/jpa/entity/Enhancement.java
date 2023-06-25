package com.nashtech.sharing.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TICKET_ENHANCEMENTS")
public class Enhancement extends Ticket {

    private Boolean duplicate;
    private String  priority;



    @PostPersist
    @PostUpdate
    public void onPostPersist(){
        System.out.println("===============onPostPersist:" + this.getTitle());
    }

    @PostLoad
    public void onPostLoad() {
        System.out.println("==============onPostLoad:" + this.getTitle());
    }

}
