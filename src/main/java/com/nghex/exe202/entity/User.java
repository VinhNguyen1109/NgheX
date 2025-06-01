package com.nghex.exe202.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nghex.exe202.util.enums.USER_ROLE;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	@Column(nullable = false)
	private String email;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String fullName;
    private String mobile;
    private USER_ROLE role;
    @OneToMany
    private Set<Address> addresses = new HashSet<>();
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_coupons",
//    		 joinColumns = @JoinColumn(name = "user_id"),
    			inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private Set<Coupon> usedCoupons = new HashSet<>();
}
