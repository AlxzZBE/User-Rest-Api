package com.alex.spring.testsjunitmockito.domain;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;


}
