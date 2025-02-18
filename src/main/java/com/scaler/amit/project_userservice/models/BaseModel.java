package com.scaler.amit.project_userservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseModel {
    private String id;
    private Date created_at;
    private Date updated_at;
    private boolean isDeleted;
}
