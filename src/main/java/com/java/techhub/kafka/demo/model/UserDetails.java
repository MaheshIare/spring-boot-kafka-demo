/**
 * 
 */
package com.java.techhub.kafka.demo.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mahes
 *
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {

	private String userId;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String email;
	
}
