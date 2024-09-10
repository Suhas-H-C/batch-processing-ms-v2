package com.per.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "portfolio_details")
public class PortFolioDetails {

    @Id
    private Integer id;
    private String portfolio;
}
