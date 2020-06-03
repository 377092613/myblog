package com.myblog.pojo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Type {
    private Long id;
    private String name;
    private String blogs;
}
