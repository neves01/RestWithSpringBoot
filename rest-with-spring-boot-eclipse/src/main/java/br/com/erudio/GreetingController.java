package br.com.erudio;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s";

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
    public static List<Integer> calculation(int maxDigit) {
        List<Integer> v = new ArrayList<Integer>();
        int soma = 0;
        
        for (int i = 1000; i < 9999; i++) {
            String s = i + "";
            for (int j = 0; j < 4; j++) {
                if (s.charAt(j) > maxDigit)
                    break;
                soma += s.charAt(j) - '0';
            }
            if (soma == 21) {
                v.add(i);
                System.out.println(soma);
            }
            soma = 0;
            
        }
        
        return v;
        

    }

}
