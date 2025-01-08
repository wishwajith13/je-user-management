package com.jeewaeducation.user_management.dto.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventGetDto {
    private int id;
    private String title;
    private Date date;
    private String time;
}
