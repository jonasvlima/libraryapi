package io.github.jonasvlima.libraryapi;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplicationBuilder builder =
				new SpringApplicationBuilder(Application.class);
		builder.bannerMode(Banner.Mode.OFF);
		builder.run(args);
	}
}
