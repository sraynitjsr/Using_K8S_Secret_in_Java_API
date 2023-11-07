import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.api.model.Secret;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class MySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }
}

@RestController
class MyController {
    @GetMapping("/get-secret-data")
    public String getSecretData() {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Secret secret = client.secrets().inNamespace("your-namespace").withName("my-secret").get();

            String username = new String(secret.getData().get("username"), StandardCharsets.UTF_8);
            String password = new String(secret.getData().get("password"), StandardCharsets.UTF_8);

            return "Username: " + username + ", Password: " + password;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to retrieve secret data.";
        }
    }
}
