package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.*;

@SpringBootTest
class EshopApplicationTests {

	@Test
	void main_ShouldRunSpringApplication() {
		try (var mockedSpringApplication = mockStatic(SpringApplication.class)) {
			String[] args = {};
			EshopApplication.main(args);

			mockedSpringApplication.verify(() -> SpringApplication.run(EshopApplication.class, args));
		}
	}
}