package pmb.pmb;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import pmb.pmb.config.ExcludeFromJacocoGeneratedReport;





@SpringBootApplication(scanBasePackages = "pmb.pmb")
@EnableJpaRepositories
@EnableTransactionManagement
public class PmbApplication extends SpringBootServletInitializer {

	@ExcludeFromJacocoGeneratedReport
	public static void main(String[] args) {
		SpringApplicationBuilder app = new SpringApplicationBuilder(PmbApplication.class);
		app.run();
	}

	@Override
	@ExcludeFromJacocoGeneratedReport
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PmbApplication.class);
	}
}
