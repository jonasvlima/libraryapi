package io.github.jonasvlima.libraryapi;

import io.github.jonasvlima.libraryapi.model.Autor;
import io.github.jonasvlima.libraryapi.repository.AutorRepository;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.time.LocalDate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		var context = SpringApplication.run(Application.class, args);
		AutorRepository repository = context.getBean(AutorRepository.class);

		exemploSalvarRegistro(repository);

		//SpringApplicationBuilder builder =
		//		new SpringApplicationBuilder(Application.class);
		//builder.bannerMode(Banner.Mode.OFF);
		//builder.run(args);
	}

	public static void exemploSalvarRegistro(AutorRepository autorRepository) {
		Autor autor = new Autor();
		autor.setNome("José");
		autor.setNacionalidade("Brasileira");
		autor.setDataNascimento(LocalDate.of(1950, 1, 31));

		var autorSalvo = autorRepository.save(autor);

		System.out.println("Autor Salvo: " + autorSalvo);
	}

}
