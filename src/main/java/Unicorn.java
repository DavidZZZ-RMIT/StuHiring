import rmit.sef.assignment1.collection.*;
import rmit.sef.assignment1.core.*;

import static spark.Spark.*;
import spark.ModelAndView;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
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

	private boolean creatStuff() {
		return false;
	}

	private boolean registEmployer() {
		return false;
	}

	private boolean registStudent() {
		return false;
	}

	private boolean removeEmployer() {
		return false;
	}

	private boolean removeStudent() {
		return false;
	}

	private boolean updateEmployerProfile() {
		return false;
	}

	private boolean updateStudentProfile() {
		return false;
	}

	private boolean loginAsStudent() {
		return false;
	}

	private boolean loginAsStuff() {
		return false;
	}

	private boolean loginAsEmployer() {
		return false;
	}

	private boolean postJob() {
		return false;
	}

	private boolean postJobApplication() {
		return false;
	}

	private static JSONObject convertJobList(List<Job> list) {
		JSONObject jsonObj = new JSONObject();
		Iterator<Job> i = list.iterator();
		while (i.hasNext()) {
			Job j = i.next();
			jsonObj.put(j.getTitle(), j.toJson());
		}
		return jsonObj;
	}

	private static JSONObject convertStudentList(List<Student> list) {
		JSONObject jsonObj = new JSONObject();
		Iterator<Student> i = list.iterator();
		while (i.hasNext()) {
			Student j = i.next();
			jsonObj.put(j.getFullName(), j.toJson());
		}
		return jsonObj;
	}

	private List<Job> findJobs(String keywords) {
		return mJobContrl.findJobs(keywords);
	}
	
//	private List<Job> findJobsByFilter(JSONObject Json) {
//		return mJobContrl.findJobsByFilter(Json);
//	}

	private List<Student> findApplicants(String keywords) {
		return mStudentContrl.findApplicants(keywords);
	}

	public static void initWebService() {
		get("/registStudent", (request, response) -> {
			Map<String, String> model = new HashMap<>();
			// model.put("message", "Hello Jade!");
			return new ModelAndView(model, "home"); // located in resources/templates directory
		}, new JadeTemplateEngine());

		post("/json", "application/json", (request, response) -> {

			JSONObject reqJson = JSONObject.fromObject(request.body());
			System.out.print(reqJson);

			response.type("application/json");
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("josn", "testData");

			return jsonObj.toString();
		});

		post("/findJobs", "application/json", (request, response) -> {
			response.type("application/json");
			JSONObject json = JSONObject.fromObject(request.body());
			if (json.has("keywords"))
				return convertJobList(Unicorn.getInstance().findJobs((String) json.get("keywords"))).toString();
//			else if (json.has("filter"))
//				return convertJobList(Unicorn.getInstance().findJobs((String) json.get("keywords"))).toString();
			return null;
		});

		post("/findApplicants", "application/json", (request, response) -> {
			response.type("application/json");
			return convertStudentList(Unicorn.getInstance()
					.findApplicants((String) JSONObject.fromObject(request.body()).get("keywords"))).toString();
		});
	}
}
