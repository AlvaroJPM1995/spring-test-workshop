package py.com.sodep.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import py.com.sodep.entities.Movie;
import py.com.sodep.enums.ContentRating;
import py.com.sodep.exceptions.MovieNotFoundException;
import py.com.sodep.repository.MovieRepository;
import py.com.sodep.services.MovieService;
import py.com.sodep.services.MovieServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class MovieServiceIntegrationTest {

	private MovieService movieService;
	
	@Autowired
	private MovieRepository repo;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setup() {
		this.movieService = new MovieServiceImpl(repo);
	}
	
	@Test
	public void getMovieByTitleShouldReturnMovieAndRating() {
		Movie movie = movieService.getByTitle("Man of Steel");
		assertThat(movie.getTitle()).isEqualTo("Man of Steel");
		assertThat(movie.getRating()).isEqualTo(ContentRating.G);
	}
	
	@Test
	public void getNotExistingMovieShouldThrowException() {
		this.thrown.expect(MovieNotFoundException.class);
		this.thrown.expectMessage("Movie with title Pocahontas not found");
	
		movieService.getByTitle("Pocahontas");
	}
	
}
