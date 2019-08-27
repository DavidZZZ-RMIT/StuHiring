import static spark.Spark.*;
import spark.ModelAndView;
import java.util.HashMap;
import java.util.Map;
import rmit.sef.assignment1.web.util.JadeTemplateEngine;

public class MainClass {
    public static void main(String[] args) {
    	staticFileLocation("/public");

    	get("/", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            model.put("message", "Hello Jade!");
            return new ModelAndView(model, "home"); // located in resources/templates directory
        }, new JadeTemplateEngine());
    }
}