package se.suw.moviestore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.suw.moviestore.models.MovieRepository;
import se.suw.moviestore.models.data.Movie;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MoviesController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public String index(Model model){
        List<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "movies/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute Movie movie, Model model) {
        model.addAttribute("movie", new Movie());

        return "movies/add";
    }

    @PostMapping("/add")
    public String add(@Valid Movie movie, BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            return "movies/add";
        }


        movieRepository.save(movie);

        redirectAttributes.addFlashAttribute("message", "Page added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/movies/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Movie movie = movieRepository.getOne(id);
        model.addAttribute("movie", movie);
        return "movies/edit";

    }

    @PostMapping("/edit")
    public String edit(@Valid Movie movie, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) throws IOException {

        Movie movieCurrent = movieRepository.getOne(movie.getId());
        if (bindingResult.hasErrors()) {
            model.addAttribute("title",movieCurrent.getTitle());
            return "movies/edit";
        }

        redirectAttributes.addFlashAttribute("message", "Movie edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        movieRepository.save(movie);

        return "redirect:/movies/edit/"+movie.getId();
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {

        movieRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Page deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/movies/";
    }
}
