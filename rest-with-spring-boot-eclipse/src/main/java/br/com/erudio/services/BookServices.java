package br.com.erudio.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.converter.custom.PersonConverter;
import br.com.erudio.data.model.Book;
import br.com.erudio.data.vo.BookVO;
import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.repository.BookRepository;

@Service
public class BookServices {

	@Autowired
	BookRepository repository;

	@Autowired
	PersonConverter converter;

	public BookVO create(BookVO person) {
		var entity = DozerConverter.parseObject(person, Book.class);
		var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}

	public BookVO update(BookVO book) {
		var entity = repository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records!"));
		entity.setAuthor(book.getAuthor());
		entity.setTitle(book.getTitle());
		entity.setLaunchdate(book.getLaunchdate());
		entity.setPrice(book.getPrice());

		var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}

	public void delete(Long id) {
		Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records!"));
		repository.delete(entity);
	}

	public BookVO findById(Long id) {

		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records!"));
		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public List<BookVO> findAll() {
		/*
		 * List<Person> persons = new ArrayList<Person>(); for (int i = 0; i < 8; i++) {
		 * Person person = mockPerson(i); persons.add(person); } return persons;
		 */
		return DozerConverter.parseListObjects(repository.findAll(), BookVO.class);
	}

	private BookVO mockBook(int i) {
		BookVO book = new BookVO();
		// person.setId(counter.incrementAndGet());
		book.setAuthor("Author Name " + i);
		book.setTitle("Title " + i);
		book.setLaunchdate(new Date());
		book.setPrice((double) i);

		return book;
	}

}
