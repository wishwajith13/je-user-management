package com.jeewaeducation.user_management.dto.navMenu;

import com.jeewaeducation.user_management.entity.MenuVisibleRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NavigationMenuGetDTO {
    private String href;
    private String label;
    private List<String> visible;
}
