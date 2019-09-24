package com.lokesh.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.validation.annotation.Validated;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static javax.persistence.CascadeType.ALL;

@Entity
@Audited
@Table(name = "profiles")
@ApiModel(description = "All details about the User. ")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    @JsonProperty(access = READ_ONLY)
    private long profileId;

    @Column(name = "first_name")
    private String firstName = null;

    @Column(name = "last_name")
    private String lastName = null;

    @Column(name = "date_of_birth")
    @JsonFormat(pattern  =  "yyyy-MM-dd")
    private LocalDate dateOfBirth = null;

    @OneToMany(cascade  =  ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_address_id")
    private List<Address> addressList = new ArrayList<>();
}
