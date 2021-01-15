package com.example.demo.services;

import java.util.*;

import org.springframework.stereotype.Service;

import com.example.demo.models.*;
import com.example.demo.repositories.*;

@Service
public class RelationService {
	private final PersonRepo personRepo;
	private final LicenseRepo licRepo;
	public RelationService(PersonRepo pRepo, LicenseRepo licRepo) {
		this.personRepo = pRepo;
		this.licRepo = licRepo;
	}
	public Person getPerson(Long id) {
		Optional<Person> optionalPerson = personRepo.findById(id);
        if(optionalPerson.isPresent()) {
            return optionalPerson.get();
        } else {
            return null;
        }
    }
	public List<Person> getUnlicensedPeople() {
		return personRepo.findByLicenseIdIsNull();
	}
	public Person createPerson(Person p) {
		return personRepo.save(p);
	}
	public License createLicense(License l) {
		l.setNumber( String.valueOf(this.generateLicenseNumber()));
		return licRepo.save(l);
	}
	public int generateLicenseNumber() {
		License l = licRepo.findTopByOrderByNumberDesc();
		if(l == null)
			return 1;
		int largestNumber = Integer.valueOf(l.getNumber());
		largestNumber++;
		return (largestNumber);
	}
	
}
