import rmit.sef.assignment1.collection.*;
import static spark.Spark.*;
import spark.ModelAndView;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;
import rmit.sef.assignment1.web.util.JadeTemplateEngine;

public class Unicorn {
	private CommentCollection mCommentContrl;
	private EmployerCollection mEmployerContrl;
	private JobApplicationCollection mJobApplicationContrl;
	private JobCategoryCollection mJobCategoryContrl;
	private JobCollection mJobContrl;
	private StudentCollection mStudentContrl;
	private StuffCollection mStuffContrl;

	private static Unicorn mUnicorn = null;

	public static Unicorn getInstance() {
		if (mUnicorn == null) {
			mUnicorn = new Unicorn();
		}
		return mUnicorn;
	}

	public Unicorn() {
		mCommentContrl = new CommentCollection();
		mEmployerContrl = new EmployerCollection();
		mJobApplicationContrl = new JobApplicationCollection();
		mJobCategoryContrl = new JobCategoryCollection();
		mJobContrl = new JobCollection();
		mStudentContrl = new StudentCollection();
		mStuffContrl = new StuffCollection();
	}

	public boolean creatStuff() {
		return false;
	}

	public boolean registEmployer() {
		return false;
	}

	public boolean registStudent() {
		return false;
	}

	public boolean removeEmployer() {
		return false;
	}

	public boolean removeStudent() {
		return false;
	}

	public boolean updateEmployerProfile() {
		return false;
	}

	public boolean updateStudentProfile() {
		return false;
	}

	public boolean loginAsStudent() {
		return false;
	}

	public boolean loginAsStuff() {
		return false;
	}

	public boolean loginAsEmployer() {
		return false;
	}

	public boolean postJob() {
		return false;
	}

	public boolean postJobApplication() {
		return false;
	}

	public static void initWebService() {
		get("/registStudent", (request, response) -> {
			Map<String, String> model = new HashMap<>();
			// model.put("message", "Hello Jade!");
			return new ModelAndView(model, "home"); // located in resources/templates directory
		}, new JadeTemplateEngine());

		get("/json","application/json", (request, response) -> {
			
			JSONObject reqJson = JSONObject.fromObject(request.body());
			System.out.print(reqJson);
			
			response.type("application/json");
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("josn", "testData");
			
			return jsonObj.toString();
		});
	}
}
