package net.histos.java2as.as3.transfer.extensions.test;

import org.springframework.flex.core.io.AmfIgnore;

import javax.persistence.Id;

/**
 * @author cliff.meyers
 */
public class Person {

	//
	// Fields
	//

	private Long id;

	private String firstName;

	private String lastName;

	private byte[] photo;

	//
	// Getters and Setters
	//

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@AmfIgnore
	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
}
