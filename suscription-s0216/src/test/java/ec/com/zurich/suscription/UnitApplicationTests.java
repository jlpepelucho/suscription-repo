package ec.com.zurich.suscription;

import ec.com.zurich.suscription.repository.DemoDbRepository;
import ec.com.zurich.suscription.resources.entity.VariablesSistema;
import ec.com.zurich.suscription.resources.model.DemoRequest;
import ec.com.zurich.suscription.service.impl.DemoDbServiceImpl;
import ec.com.zurich.suscription.service.impl.DemoRestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
		DemoRequest request = new DemoRequest(Arrays.asList("CIERREMES"));
		when(repository.getByNombreIn(Arrays.asList("CIERREMES")))
				.thenReturn(Arrays.asList(new VariablesSistema("123", "CIERREMES", "0")).stream());
		List<VariablesSistema> lst = serviceDb.getVariableSistema(request);
		assertThat(lst.size()).isGreaterThan(0);
		assertThat(lst.get(0).getNombre()).isEqualTo("CIERREMES");
	}

	@Test
	void getHello() {
		String say = serviceRest.sayHello("Marco");
		assertThat(say).isEqualTo("Hola, como estas Marco !!!");
	}



}
