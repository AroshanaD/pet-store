package com.example.petstore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;


@Entity
@Schema(name = "Pet")
public class Pet extends PanacheEntityBase {

	@Id
	@Schema(required = true, description = "Pet id")
	@JsonProperty("pet_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Schema(required = true, description = "Pet type")
	@JsonProperty("pet_type")
	private String petType;

	@Schema(required = true, description = "Pet name")
	@JsonProperty("pet_name")
	private String petName;

	@JsonProperty("pet_age")
	private Integer petAge;


	public Pet() {
	}

	public Pet(int id,String petType, String petName, Integer petAge) {
		this.id=id;
		this.petType = petType;
		this.petName = petName;
		this.petAge = petAge;
	}

	@Override
	public String toString() {
		return "Pet{" +
				", petType='" + petType + '\'' +
				", petName='" + petName + '\'' +
				", petAge=" + petAge +
				'}';
	}


	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public Integer getPetAge() {
		return petAge;
	}

	public void setPetAge(Integer petAge) {
		this.petAge = petAge;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}


	//search functions
	public static Pet findByName(String petName){
		return find("petName", petName).firstResult();
	}

	//search by age
	public static List<Pet> findByAge(int petAge){
		return find("petAge", petAge).list();
	}

	//search by petType
	public static List<Pet> findByType(int petType){
		return find("petType", petType).list();
	}


}
