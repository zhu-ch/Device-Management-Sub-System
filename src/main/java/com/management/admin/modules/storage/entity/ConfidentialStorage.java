package com.management.admin.modules.storage.entity;

import com.management.admin.common.persistence.DataEntity;
import lombok.Data;

@Data
public class ConfidentialStorage extends DataEntity<ConfidentialStorage> {
    private String department;
    private String subject;
    private String secret_number;
    private String type;
    private String model;
    private String person;
    private String secret_level;
    private String serial_number;
    private String place_location;
    private String usage;
    private String scope;
    private String enablation_time;
    private String use_situation;
    private String remarks;
    private int scrapped_flag;
    private String startTime;
    private String endTime;

    //前端显示给用户
    private String department_code;
    private String department_name;
    private String subject_code;
    private String subject_name;
    private String _type;
    private String _secret_level;
    private String _usage;
    private String _scope;
    private String _use_situation;
}
