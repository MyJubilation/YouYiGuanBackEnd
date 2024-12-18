package com.example.youyiguanbackend.models.doctor.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author beetles
 * @date 2024/12/18
 * @Description
 */
@Data
public class MedicalRecords {
    private int record_id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime visit_date;
    private String diagnosis;
    private String recipe_name;
    private String recipe_details;
    private String review_status;
    private String remarks;
}
