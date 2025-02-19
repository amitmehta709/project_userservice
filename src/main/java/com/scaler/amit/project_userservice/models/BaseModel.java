package com.scaler.amit.project_userservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date created_at;
    private Date updated_at;

   @PrePersist
    public void onCreate() {
        Date curDateTime = new Date();
        this.setCreated_at(curDateTime);
        this.setUpdated_at(curDateTime);
    }

    @PreUpdate
    public void onUpdate() {
        this.setUpdated_at(new Date());
    }
}
