package br.com.erudio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.converter.custom.PersonConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.data.vo.PersonVOV2;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonServices {

	// private final AtomicLong counter = new AtomicLong(); // simulate id DB

	@Autowired
	PersonRepository repository;

	@Autowired
	PersonConverter converter;

	public PersonVO create(PersonVO person) {
		var entity = DozerConverter.parseObject(person, Person.class);
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	public PersonVOV2 createV2(PersonVOV2 person) {
		var entity = converter.convertVOToEntity(person);
		var vo = converter.convertEntityToVO(repository.save(entity));
		return vo;
	}

	public PersonVO update(PersonVO person) {
		var entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records!"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	@Transactional
	public PersonVO disablePerson(Long id) {
		repository.disablePerson(id);
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records!"));
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public void delete(Long id) {
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records!"));
		repository.delete(entity);
	}

	public PersonVO findById(Long id) {
		/*
		 * Person person = new Person(); // person.setId(counter.incrementAndGet());
		 * person.setFirstName("Henrique"); person.setLastName("Silva");
		 * person.setAddress("Curitiba"); person.setGender("Male"); return person;
		 */
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records!"));
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

//	public List<PersonVO> findAll() {
//		/*
//		 * List<Person> persons = new ArrayList<Person>(); for (int i = 0; i < 8; i++) {
//		 * Person person = mockPerson(i); persons.add(person); } return persons;
//		 */
//		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
//	}

//	public List<PersonVO> findAll(Pageable pageable) {
//		var entities = repository.findAll(pageable).getContent();
//		return DozerConverter.parseListObjects(entities, PersonVO.class);
//	}

	public Page<PersonVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		return page.map(this::convertToPersonVO);
	}

	public Page<PersonVO> findPersonByName(String firstName, Pageable pageable) {
		var page = repository.findPersonByName(firstName, pageable);
		return page.map(this::convertToPersonVO);
	}

	private PersonVO convertToPersonVO(Person entity) {
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	private PersonVO mockPerson(int i) {
		PersonVO person = new PersonVO();
		// person.setId(counter.incrementAndGet());
		person.setFirstName("Person Name " + i);
		person.setLastName("Person Last Name " + i);
		person.setAddress("Address " + i);
		person.setGender("Gender " + i);
		return person;
	}

}
