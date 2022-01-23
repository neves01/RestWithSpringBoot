package br.com.erudio.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.data.vo.PersonVOV2;
import br.com.erudio.services.PersonServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Person Endpoint", description = "Description for person", tags = { "Person Endpoint" })
@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

	// @RequestParam = param opcional

	@Autowired
	private PersonServices service;

	@Autowired
	PagedResourcesAssembler<PersonVO> assembler;

	@ApiOperation(value = "Disable a specific person by your ID")
	@PatchMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO disablePerson(@PathVariable(value = "id") Long id) {
		PersonVO personVO = service.disablePerson(id);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		// personVO.add(linkTo(methodOn(PersonController.class).findAll(0, 5,
		// "asc")).withRel("All Persons"));
		return personVO;
	}

//	public List<PersonVO> findAll() {
//		List<PersonVO> persons = service.findAll();
//		persons.stream()
//				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
//		return persons;
//	}

	// @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:8080")
	@ApiOperation(value = "Find people by id")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		PersonVO personVO = service.findById(id);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		// personVO.add(linkTo(methodOn(PersonController.class).findAll(0, 5,
		// "asc")).withRel("All Persons"));
		return personVO;
	}

	// @RequestMapping(method = RequestMethod.GET, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = { "http://localhost:8080", "http://www.erudio.com.br" })
	@ApiOperation(value = "Find all people")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

		Page<PersonVO> persons = service.findAll(pageable);

		persons.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		PagedResources<?> resources = assembler.toResource(persons);

		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

	@ApiOperation(value = "Find person by first name")
	@GetMapping(value = "/findPersonByName/{firstName}", produces = { "application/json", "application/xml",
			"application/x-yaml" })
	public ResponseEntity<?> findPersonByName(@RequestParam(value = "page", defaultValue = "0") int page,
			@PathVariable(value = "firstName") String firstName,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

		Page<PersonVO> persons = service.findPersonByName(firstName, pageable);

		persons.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		PagedResources<?> resources = assembler.toResource(persons);

		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

	// @RequestMapping(method = RequestMethod.POST, produces =
	// MediaType.APPLICATION_JSON_VALUE, consumes =
	// MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create a person")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public PersonVO create(@RequestBody PersonVO person) {
		PersonVO personVO = service.create(person);
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		return personVO;
	}

	@PostMapping("/v2")
	public PersonVOV2 createV2(@RequestBody PersonVOV2 person) {
		return service.createV2(person);
	}

	// @RequestMapping(method = RequestMethod.PUT, produces =
	// MediaType.APPLICATION_JSON_VALUE, consumes =
	// MediaType.APPLICATION_JSON_VALUE)
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public PersonVO update(@RequestBody PersonVO person) {
		PersonVO personVO = service.update(person);
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		return personVO;
	}

	// @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}