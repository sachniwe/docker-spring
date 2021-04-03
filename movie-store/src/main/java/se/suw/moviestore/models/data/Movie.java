package se.suw.moviestore.models.data;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "movies")
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String title;
    private String original_title;
    private String description;
    private String released;
    private String studio;
    private String image;
    private String tag;
    private String genre;

}
