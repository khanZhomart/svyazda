package com.svyazda.dtos;

import com.svyazda.enums.Visibility;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdatePostForm {
    public Long id;
    public String title;
    public String text;
    public boolean disabledComments;
    public Visibility visibility;
}
