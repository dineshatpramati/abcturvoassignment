package com.imaginea.assignment.turvoapi.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long id;

    @NotNull
    @Column(unique = true)
    private String branchCode;

    @NotNull
    @Column
    private String branchName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranch_code() {
        return branchCode;
    }

    public void setBranch_code(String branch_code) {
        this.branchCode = branch_code;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
