package com.myblog.pojo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Tag {
    private Long id;
    private String name;
    private String blogs;
}
