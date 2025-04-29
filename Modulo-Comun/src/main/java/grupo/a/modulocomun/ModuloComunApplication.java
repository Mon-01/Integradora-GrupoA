package grupo.a.modulocomun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.example.aplicacioncorporativa"
})
public class ModuloComunApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuloComunApplication.class, args);
    }

}
