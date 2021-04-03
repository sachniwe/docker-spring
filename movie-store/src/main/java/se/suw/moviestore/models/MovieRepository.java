package se.suw.moviestore.models;

import org.springframework.data.jpa.repository.JpaRepository;
import se.suw.moviestore.models.data.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
