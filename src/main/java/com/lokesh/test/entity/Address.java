package com.lokesh.test.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Entity
@Audited
@EntityListeners(AuditingEntityListener.class)
@Table(name = "address")
@ApiModel(description = "All details about the User. ")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;


    public enum AddressTypeEnum {

        WORK("WORK"), PERSONAL("PERSONAL");

        private String value;

        AddressTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static AddressTypeEnum fromValue(String text) {
            for (AddressTypeEnum b : AddressTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    @JsonProperty(access = READ_ONLY)
    private long addressId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AddressTypeEnum type = AddressTypeEnum.PERSONAL;

    @Column(name = "street_address")
    private String streetAddress = null;

    @Column(name = "suburb")
    private String suburb = null;

    @Column(name = "state")
    private String state = null;

    @Column(name = "country")
    private String country = null;

    @Column(name = "email_address")
    private String emailAddress = null;
}
