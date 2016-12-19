package ejemplo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/ejemplo")
    public List<Persona> prueba() {
        Persona p1 = new Persona("juan", "pablo", 12345678);
        Persona p2 = new Persona("daniel", "lopez", 87654321);
        Persona p3 = new Persona("sergio", "gui", 7777777);

        List<Persona> personaList = new ArrayList() {{
            add(p1);
            add(p2);
            add(p3);
        }};
//        Map<String,Persona> map = Collections.unmodifiableMap(new HashMap<String, Persona>() {
//            {
//
//                put("p1", p1);
//                put("p2", p2);
//                put("p3", p3);
//            }
//        });

        personaList.stream().forEach(persona -> LOGGER.info(String.valueOf(persona.getDni())));

        return personaList;


    }

    class Persona {
        private String nombre;
        private String apellido;
        private int dni;

        public Persona(String nombre, String apellido, int dni) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.dni = dni;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public int getDni() {
            return dni;
        }
    }
}
