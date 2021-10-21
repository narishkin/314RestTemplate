package home.catechumen.rest;


import home.catechumen.rest.configuration.Config;
import home.catechumen.rest.controller.ConsumeWebService;
import home.catechumen.rest.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class BootRestTemplateApplication {

    private static String key;

    public static void main(String[] args) {
        SpringApplication.run(BootRestTemplateApplication.class, args);

    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);

    ConsumeWebService consumeWebService = annotationConfigApplicationContext.getBean("consumeWebService", ConsumeWebService.class);
        String userList= consumeWebService.getUserList();
        System.out.println(userList);
        User user = new User(3L, "James", "Brown", (byte)44);
        String postUser = consumeWebService.addUser(user);
        key = postUser;
        System.out.println(key);
        user.setName("Thomas");
        user.setLastName("Shelby");
        String updateUser = consumeWebService.updateUser(user);
        key+=updateUser;
        System.out.println(key);
        String deleteUser = consumeWebService.deleteUser(String.valueOf(3));
        key+=deleteUser;
        System.out.println(key);

    }

}
