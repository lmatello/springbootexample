package ejemplo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @Controller vs @RestController: un método con @ResponseBody en una clase @Controller produce el mismo resultado que
 * un @RestController.
 * Un método de @Controller sin @ResponseBody mapea directamente el string devuelto con un resource (ejemplo un .html)
 *
 * Los @resources de una app de spring-boot deben estar dentro de carpeta static por convención
 */

@Controller
public class GreetingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    Map<String,Persona> mapa = new HashMap();
    AtomicInteger runCount = new AtomicInteger();

    Configuration cfg = new Configuration();
    Map<String, Object> data = new HashMap<String, Object>();

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/greeting")
    @ResponseBody
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/ejemplo")
    @ResponseBody
    public List<Persona> prueba(@RequestParam Map<String,String>map) {

        return map.keySet().stream().map(k->new Persona(k,map.get(k),runCount.incrementAndGet())).collect(Collectors.toList());

    }

    @RequestMapping("/mock")
    @ResponseBody
    public List<Persona> mock() {

        List<Persona>plist = new ArrayList<Persona>();
        plist.add(new Persona("juan","ape",1234));
        plist.add(new Persona("pedro","ape1",12345));
        plist.add(new Persona("saas","ape1",12345));
        plist.add(new Persona("psaasaassaedro","ku",123323345));
        plist.add(new Persona("pedasasssaro","apiuiuiiuiue1",123232345));
        return plist;
    }


    @RequestMapping("/htmlfreemarker")
    @ResponseBody
    public String pruebaHTMLFreemarker() throws IOException, TemplateException {

        Template templatef = cfg.getTemplate("src/main/resources/static/templates/ExampleFreemarker.html");

        List<Persona> plist = Arrays.asList(restTemplate.getForObject("http://localhost:8080/mock", Persona[].class));

        data.put("personas", plist);
        //Writer out = new OutputStreamWriter(System.out);
        Writer out = new StringWriter();
        templatef.process(data, out);
        //out.flush();
        return out.toString();
    }

    @RequestMapping("/html")
    public String htmljs() {
        return "/templates/Example.html";
    }

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("ini");
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        filterChain.doFilter(servletRequest, servletResponse);
//        System.out.println("sa");
//    }
//
//    @Override
//    public void destroy() {
//        System.out.println("des");
//    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/ejemplo2")
    @ResponseBody
    public ResponseEntity<Map> prueba(@RequestBody Persona p) {

        for (int i = 0; i < 300; i++) {
            Persona pe = new Persona(p.getNombre(),p.getApellido(),p.getDni()+i);
            mapa.put(pe.toString(),pe);
        }
        return new ResponseEntity(mapa, HttpStatus.ACCEPTED);

    }

}
