package ec.com.zurich.suscription;

import ec.com.zurich.suscription.repository.DemoDbRepository;
import ec.com.zurich.suscription.service.impl.DemoDbServiceImpl;
import ec.com.zurich.suscription.service.impl.DemoRestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UnitApplicationTests {

	@Mock
	private DemoDbRepository repository;

	@InjectMocks
	private DemoDbServiceImpl serviceDb;

	@InjectMocks
	private DemoRestServiceImpl serviceRest;

	@Test
	void getVariable() {
	}

	@Test
	void getHello() {
		String say = serviceRest.sayHello("Marco");
		assertThat(say).isEqualTo("Hola, como estas Marco !!!");
	}



}
