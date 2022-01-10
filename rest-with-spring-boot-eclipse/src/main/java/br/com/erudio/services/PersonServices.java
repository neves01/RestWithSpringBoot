package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonServices {

	// private final AtomicLong counter = new AtomicLong(); // simulate id DB

	@Autowired
	PersonRepository repository;

	public Person create(Person person) {
		return repository.save(person);
	}

	public Person update(Person person) {
		Person entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records!"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		return repository.save(entity);
	}

	public void delete(Long id) {
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records!"));
		repository.delete(entity);
	}

	public Person findById(Long id) {
		/*
		 * Person person = new Person(); // person.setId(counter.incrementAndGet());
		 * person.setFirstName("Henrique"); person.setLastName("Silva");
		 * person.setAddress("Curitiba"); person.setGender("Male"); return person;
		 */
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records!"));
	}

	public List<Person> findAll() {
		/*
		 * List<Person> persons = new ArrayList<Person>(); for (int i = 0; i < 8; i++) {
		 * Person person = mockPerson(i); persons.add(person); } return persons;
		 */
		return repository.findAll();
	}

	private Person mockPerson(int i) {
		Person person = new Person();
		// person.setId(counter.incrementAndGet());
		person.setFirstName("Person Name " + i);
		person.setLastName("Person Last Name " + i);
		person.setAddress("Address " + i);
		person.setGender("Gender " + i);
		return person;
	}

}
