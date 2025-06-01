package com.nghex.exe202.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String name;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String locality;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String address;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String city;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String state;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String pinCode;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String mobile;
}
