import static spark.Spark.*;
import spark.ModelAndView;
import java.util.HashMap;
import java.util.Map;
import rmit.sef.assignment1.web.util.JadeTemplateEngine;

public class MainClass {
    public static void main(String[] args) {
    	staticFileLocation("/public");    	
    	Unicorn.getInstance().initWebService();
    }
}