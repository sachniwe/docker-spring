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
    public String add(@Valid Movie movie, BindingResult bindingResult, MultipartFile file,
                      RedirectAttributes redirectAttributes,
                      Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            return "movies/add";
        }

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename);

        if (filename.endsWith("jpg") || filename.endsWith("png") ) {
            fileOK = true;
        }
        if (! fileOK ) {
            redirectAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("movie", movie);
        }

        redirectAttributes.addFlashAttribute("message", "Page added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        movie.setImage(filename);
        movieRepository.save(movie);
        Files.write(path, bytes);

        return "redirect:/movies/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Movie movie = movieRepository.getOne(id);
        model.addAttribute("movie", movie);
        return "/movies/edit";

    }

    @PostMapping("/edit")
    public String edit(@Valid Movie movie, BindingResult bindingResult, MultipartFile file,
                       RedirectAttributes redirectAttributes,
                       Model model) throws IOException {

        Movie movieCurrent = movieRepository.getOne(movie.getId());
        if (bindingResult.hasErrors()) {
            model.addAttribute("title",movieCurrent.getTitle());
            return "movies/edit";
        }
        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename);

        if (!file.isEmpty()) {
            if (filename.endsWith("jpg") || filename.endsWith("png") ) {
                fileOK = true;
            }
        } else {
            fileOK = true;
        }
      if (! fileOK ) {
            redirectAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("movie", movie);
        }
        if (!file.isEmpty()) {
            Path path2 = Paths.get("src/main/resources/static/media/" + movieCurrent.getImage());
            Files.delete(path2);
            movie.setImage(filename);
            Files.write(path, bytes);
        } else {
            movie.setImage(movieCurrent.getImage());
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
