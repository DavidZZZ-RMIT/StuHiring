import rmit.sef.assignment1.collection.*;
import rmit.sef.assignment1.core.*;

import static spark.Spark.*;
import spark.ModelAndView;

import java.time.LocalDateTime;
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
	private CVCollection mCVContrl;
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
		mCVContrl = new CVCollection();

		Stuff admin = new Stuff("admin", "c33367701511b4f6020ec61ded352059", "admin@unicorn.com", "admin", "unicorn");
		if (!mStuffContrl.isStuffExist(admin)) {
			System.out.print("create admin account!");
			mStuffContrl.addstuff(admin);
		}
	}

	private boolean isStudentExist(String email) {
		return mStudentContrl.isStudentExist(email);
	}

	private Student getStudent(String email) {
		return mStudentContrl.getStudent(email);
	}

	private void updateStudent(Student user) {
		mStudentContrl.updateStudent(user);
	}

	private boolean loginAsStudent(String email, String pwd) {
		return mStudentContrl.getStudent(email).checkPwd(pwd) && mStudentContrl.getStudent(email).isBlackListed();
	}

	private boolean isEmployerExist(String email) {
		return mEmployerContrl.isEmployerExist(email);
	}

	private boolean loginAsEmployer(String email, String pwd) {
		return mEmployerContrl.getEmployer(email).checkPwd(pwd) && mEmployerContrl.getEmployer(email).isBlackListed();
	}

	private boolean applyJob(String studentEmail, String CVID, String JobId, String emp, String msg) {
		JobApplication ja = new JobApplication(studentEmail, CVID, JobId, emp, msg);
		return mJobApplicationContrl.add(ja);
	}

	private boolean isAppliedJob(String studentEmail, String JobId) {
		return mJobApplicationContrl.isApplied(studentEmail, JobId);
	}

	private boolean isStuffExist(String email) {
		return mStuffContrl.isStuffExist(email);
	}

	private boolean loginAsStuff(String email, String pwd) {
		return mStuffContrl.getstuff(email).checkPwd(pwd);
	}

	private boolean registEmployer(Employer e) {
		return mEmployerContrl.addEmployer(e);
	}

	private boolean registStudent(Student s) {
		return mStudentContrl.addStudent(s);
	}

	public boolean postJob(Job j) {
		return mJobContrl.add(j);
	}

	public void updateJob(String id, String title, String description) {
		Job j = mJobContrl.getJob(id);
		j.setTitle(title);
		j.setDescription(description);
		mJobContrl.update(j);
	}

	public Job getJobbyId(String id) {
		return mJobContrl.getJob(id);
	}

	public void removeJob(Job jb) {
		mJobContrl.remove(jb);
	}

	public CV getCVbyId(String id) {
		return mCVContrl.getCV(id);
	}

	public List<CV> getStudentCV(String studetnId) {
		return mCVContrl.getStudentCV(studetnId);
	}

	public boolean addCV(String studentId, String title, String content) {
		return mCVContrl.add(new CV(studentId, title, content));
	}

	public void updateCV(String id, String title, String content) {
		CV cv = mCVContrl.getCV(id);
		cv.setTitle(title);
		cv.setContent(content);
		mCVContrl.update(cv);
	}

	public void removeCV(String id) {
		mCVContrl.remove(id);
	}

	public String addComment(String author, String applicationId, String content, String dateStr) {
		Comment cmt = new Comment(applicationId, null, author, content, dateStr);
		mCommentContrl.add(cmt);
		return cmt.getCmtId();
	}

	public List<Comment> getCommentsOfApplication(String applicationId) {
		return mCommentContrl.getCommentsByJobApplicationId(applicationId);
	}

	public void blockStudent(String email, boolean isblocked) {
		Student s = mStudentContrl.getStudent(email);
		s.setBlackListed(isblocked);
		mStudentContrl.updateStudent(s);
	}

	public void blockEmployer(String email, boolean isblocked) {
		Employer s = mEmployerContrl.getEmployer(email);
		s.setBlackListed(isblocked);
		mEmployerContrl.updateEmployer(s);
	}

	private static JSONObject convertCommentList(List<Comment> list) {
		JSONObject jsonObj = new JSONObject();
		if (list != null) {
			Iterator<Comment> i = list.iterator();
			while (i.hasNext()) {
				Comment cmt = i.next();
				jsonObj.put(cmt.getCmtId(), cmt.toJson());
			}
		}
		return jsonObj;
	}

	private static JSONObject convertJobList(List<Job> list) {
		JSONObject jsonObj = new JSONObject();
		if (list != null) {
			Iterator<Job> i = list.iterator();
			while (i.hasNext()) {
				Job j = i.next();
				jsonObj.put(j.getId(), j.toJson());
			}
		}
		return jsonObj;
	}

	private static JSONObject convertCVList(List<CV> list) {
		JSONObject jsonObj = new JSONObject();
//		System.out.print("convertCVList:" + list.size());
		if (list != null) {
			Iterator<CV> i = list.iterator();
			while (i.hasNext()) {
				CV j = i.next();
				jsonObj.put(j.getId(), j.toJson());
			}
		}
		return jsonObj;

	}

	private static JSONObject convertStudentList(List<Student> list) {
		JSONObject jsonObj = new JSONObject();
		if (list != null) {
			Iterator<Student> i = list.iterator();
			while (i.hasNext()) {
				Student j = i.next();
				jsonObj.put(j.getFullName(), j.toJson());
			}
		}
		return jsonObj;
	}

	private static JSONObject convertEmployerList(List<Employer> list) {
		JSONObject jsonObj = new JSONObject();
		if (list != null) {
			Iterator<Employer> i = list.iterator();
			while (i.hasNext()) {
				Employer j = i.next();
				jsonObj.put(j.getEmail(), j.toJson());
			}
		}
		return jsonObj;
	}

	private static JSONObject convertJobApplicationList(List<JobApplication> list) {
		JSONObject jsonObj = new JSONObject();
		if (list != null) {
			Iterator<JobApplication> i = list.iterator();
			while (i.hasNext()) {
				JobApplication j = i.next();
				JSONObject _json = j.toJson();
				System.out.println("convertJobApplicationList j.getJobID() " + j.getJobID() + " null?"
						+ (Unicorn.getInstance().mJobContrl.getJob(j.getJobID()) == null));
				// handle error of db records
				if ((Unicorn.getInstance().mJobContrl.getJob(j.getJobID()) == null)) {
					System.out.println("remove JobApplication:" + j.toJson().toString());
					Unicorn.getInstance().mJobApplicationContrl.remove(j);
				} else {
					_json.put("job", Unicorn.getInstance().mJobContrl.getJob(j.getJobID()).toJson());
					_json.put("applicant", Unicorn.getInstance().mStudentContrl.getStudent(j.getApplicant()).toJson());
					jsonObj.put(j.getId(), _json);
				}
			}
		}
		return jsonObj;
	}

	private List<JobApplication> getJobApplicationsByApplicant(String email) {
		return mJobApplicationContrl.getJobApplicationsByApplicant(email);
	}

	private List<JobApplication> getJobApplicationsByEmployer(String email) {
		return mJobApplicationContrl.getJobApplicationsByEmployer(email);
	}

	private JobApplication getJobApplication(String id) {
		return mJobApplicationContrl.getJobApplication(id);
	}

	private void getJobApplicationMessage(String id, String msg) {
		JobApplication ja = mJobApplicationContrl.getJobApplication(id);
		ja.setMessage(msg);
		mJobApplicationContrl.update(ja);
	}

	private void updateJobApplicationStatus(String id, JobApplicationStatus status) {
		JobApplication ja = mJobApplicationContrl.getJobApplication(id);
		// System.out.print("updateJobApplicationStatus ja null "+id+" ? "+(ja ==
		// null));
		ja.setStatus(status);
		mJobApplicationContrl.update(ja);
	}

	private List<Job> findJobs(String keywords) {
		return mJobContrl.findJobs(keywords);
	}

	private List<Job> getMyJobs(String email) {
		return mJobContrl.getMyJobs(email);
	}

	private JSONObject getAllUsers() {
		List<Student> s1 = mStudentContrl.getAllStudents();
		List<Employer> s2 = mEmployerContrl.getAllEmployers();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("students", convertStudentList(s1));
		jsonObj.put("employers", convertEmployerList(s2));
		return jsonObj;
	}

//	private List<Job> findJobsByFilter(JSONObject Json) {
//		return mJobContrl.findJobsByFilter(Json);
//	}

	private List<Student> findApplicants(String keywords) {
		return mStudentContrl.findApplicants(keywords);
	}

	private void addStudentToBlackList(String email) {
		// mStudentContrl.
	}

	private void addEmployerToBlackList(String email) {
		// mStudentContrl.
	}

	public static void initWebService() {
		post("/registStudent", (request, response) -> {
			JSONObject reqJson = JSONObject.fromObject(request.body());
			System.out.print(reqJson);

			response.type("application/json");
			JSONObject jsonObj = new JSONObject();

			if (Unicorn.getInstance().isStudentExist((String) reqJson.get("email"))) {
				jsonObj.put("isMemberExist", true);
			} else {
				if (Unicorn.getInstance()
						.registStudent(new Student((String) reqJson.get("username"), (String) reqJson.get("pwd"),
								(String) reqJson.get("email"), (String) reqJson.get("firstname"),
								(String) reqJson.get("lastname"), (String) reqJson.get("school"),
								(String) reqJson.get("major"), Gender.fromString((String) reqJson.get("gender")),
								Integer.valueOf((String) reqJson.get("graduateyear")),
								new Availabilities(true, true, true), ApplicantStatus.Available))) {
					jsonObj.put("result", "success");
					request.session(true);
					request.session().attribute("User", (String) reqJson.get("email"));
					request.session().attribute("UserType", UserType.Student);
				} else {
					jsonObj.put("result", "failed");
				}
			}

			return jsonObj.toString();
		});

		post("/registEmployer", (request, response) -> {
			JSONObject reqJson = JSONObject.fromObject(request.body());
			System.out.print(reqJson);

			response.type("application/json");
			JSONObject jsonObj = new JSONObject();

			if (Unicorn.getInstance().isStudentExist((String) reqJson.get("email"))) {
				jsonObj.put("isMemberExist", true);
			} else {
				if (Unicorn.getInstance()
						.registEmployer(new Employer((String) reqJson.get("username"), (String) reqJson.get("pwd"),
								(String) reqJson.get("email"), (String) reqJson.get("firstname"),
								(String) reqJson.get("lastname"), (String) reqJson.get("companyname"),
								(String) reqJson.get("companydiscrption")))) {
					jsonObj.put("result", "success");
					request.session(true);
					request.session().attribute("User", (String) reqJson.get("email"));
					request.session().attribute("UserType", UserType.Employer);
				} else {
					jsonObj.put("result", "failed");
				}
			}

			return jsonObj.toString();
		});

		post("/login", "application/json", (request, response) -> {
			response.type("application/json");

			JSONObject reqJson = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();

			if (reqJson.has("email") && reqJson.has("pwd")) {
				if (Unicorn.getInstance().isStudentExist((String) reqJson.get("email"))) {
					if (Unicorn.getInstance().loginAsStudent((String) reqJson.get("email"),
							(String) reqJson.get("pwd"))) {
						request.session(true);
						responseJson.put("result", "success");
						responseJson.put("usertype", "student");
						responseJson.put("msg", "weclome");
						responseJson.put("session", request.session().id());
						request.session().attribute("User", (String) reqJson.get("email"));
						request.session().attribute("UserType", UserType.Student);
					} else {
						responseJson.put("result", "failed");
						responseJson.put("msg", "password incorrect");
					}
				} else if (Unicorn.getInstance().isEmployerExist((String) reqJson.get("email"))) {
					if (Unicorn.getInstance().loginAsEmployer((String) reqJson.get("email"),
							(String) reqJson.get("pwd"))) {
						request.session(true);
						responseJson.put("result", "success");
						responseJson.put("usertype", "employer");
						responseJson.put("msg", "weclome");
						responseJson.put("session", request.session().id());
						request.session().attribute("User", (String) reqJson.get("email"));
						request.session().attribute("UserType", UserType.Employer);
					} else {
						responseJson.put("result", "failed");
						responseJson.put("msg", "password incorrect");
					}
				} else if (reqJson.has("email") && reqJson.has("pwd")) {
					if (Unicorn.getInstance().isStuffExist((String) reqJson.get("email"))) {
						if (Unicorn.getInstance().loginAsStuff((String) reqJson.get("email"),
								(String) reqJson.get("pwd"))) {
							request.session(true);
							responseJson.put("result", "success");
							responseJson.put("usertype", "stuff");
							responseJson.put("msg", "weclome");
							responseJson.put("session", request.session().id());
							request.session().attribute("User", (String) reqJson.get("email"));
							request.session().attribute("UserType", UserType.Stuff);
						} else {
							responseJson.put("result", "failed");
							responseJson.put("msg", "password incorrect");
						}
					} else {
						responseJson.put("result", "failed");
						responseJson.put("msg", "stuff account is not exist");
					}
				}
			} else {
				responseJson.put("result", "failed");
				responseJson.put("msg", "The email is not sign up!");
			}

			return responseJson.toString();
		});

		post("/logout", "application/json", (request, response) -> {
			if (request.session() != null) {
				request.session().removeAttribute("User");
				request.session().removeAttribute("UserType");
			}
			JSONObject responseJson = new JSONObject();
			responseJson.put("status", "loginout");
			responseJson.put("result", "success");

			response.type("application/json");
			return responseJson.toString();
		});

		post("/postJob", "application/json", (request, response) -> {
			JSONObject reqJson = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();
			String owner = request.session().attribute("User");
			if (UserType.getUserType(request.session().attribute("UserType").toString()).equals(UserType.Employer)) {
				if (Unicorn.getInstance()
						.postJob(new Job(owner, (String) reqJson.get("title"), (String) reqJson.get("description"),
								LocalDateTime.now(), LocalDateTime.now(), new Availabilities(true, true, true)))) {
					responseJson.put("result", "success");
				} else {
					responseJson.put("result", "failed");
					responseJson.put("msg", "Post failed since database error.");
				}
			} else {
				responseJson.put("result", "failed");
				responseJson.put("msg", "Yon are not a employer, CAN NOT post a job!");

			}
			response.type("application/json");
			return responseJson.toString();
		});

		post("/updateJob", "application/json", (request, response) -> {
			JSONObject reqJson = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();
			String owner = request.session().attribute("User");
			if (UserType.getUserType(request.session().attribute("UserType").toString()).equals(UserType.Employer)) {
				Unicorn.getInstance().updateJob((String) reqJson.get("jobId"), (String) reqJson.get("title"),
						(String) reqJson.get("description"));
				responseJson.put("result", "success");
			} else {
				responseJson.put("result", "failed");
				responseJson.put("msg", "Yon are not a employer, CAN NOT update a job!");

			}
			response.type("application/json");
			return responseJson.toString();
		});

		post("/removeJob", "application/json", (request, response) -> {
			JSONObject reqJson = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();
			String owner = request.session().attribute("User");
			if (UserType.getUserType(request.session().attribute("UserType").toString()).equals(UserType.Employer)) {
				Job j = Unicorn.getInstance().getJobbyId((String) reqJson.get("jobId"));
				if (j.getEmployer().equals(owner)) {
					Unicorn.getInstance().removeJob(j);
					responseJson.put("result", "success");
				} else {
					responseJson.put("result", "failed");
					responseJson.put("msg", "Yon are not a the job owner, CAN NOT remove a job!");
				}
			} else {
				responseJson.put("result", "failed");
				responseJson.put("msg", "Yon are not a employer, CAN NOT Remove a job!");
			}
			response.type("application/json");
			return responseJson.toString();
		});

		post("/isLive", "application/json", (request, response) -> {
			JSONObject responseJson = new JSONObject();
			if (request.session() != null) {
				responseJson.put("session", request.session().id());
				if (!request.session().isNew()) {
					responseJson.put("email", request.session().attribute("User"));
					responseJson.put("status", "logined");
					responseJson.put("usertype", request.session().attribute("UserType"));
					responseJson.put("result", "success");
				} else {
					responseJson.put("email", "unknown");
				}
			} else {
				responseJson.put("status", "not login");
			}

			response.type("application/json");
			return responseJson.toString();
		});

		post("/getProfile", "application/json", (request, response) -> {
			JSONObject responseJson = new JSONObject();
			request.session(true);
			response.type("application/json");
			String owner = request.session().attribute("User");
			if (owner != null) {
				responseJson.put("result", "success");
				Student s = Unicorn.getInstance().getStudent(owner);
				responseJson.put("description", s.getDescription());
				responseJson.put("employmentrecords", s.getEmploymentRecords());
				responseJson.put("references", s.getReferences());
				responseJson.put("qualifications", s.getQualifications());
				responseJson.put("licenses", s.getLicenses());
			} else {
				responseJson.put("result", "failed");
			}

			return responseJson.toString();
		});

		post("/updateProfile", "application/json", (request, response) -> {
			JSONObject responseJson = new JSONObject();
			JSONObject reqJson = JSONObject.fromObject(request.body());

			request.session(true);
			response.type("application/json");
			String owner = request.session().attribute("User");
			if (owner != null) {
				responseJson.put("result", "success");
				Student s = Unicorn.getInstance().getStudent(owner);
				s.setDescription((String) reqJson.get("description"));
				s.setEmploymentRecords((String) reqJson.get("employmentrecords"));
				s.setReferences((String) reqJson.get("references"));
				s.setQualifications((String) reqJson.get("qualifications"));
				s.setLicenses((String) reqJson.get("licenses"));
				Unicorn.getInstance().updateStudent(s);
			} else {
				responseJson.put("result", "failed");
			}

			return responseJson.toString();
		});

		post("/findJobs", "application/json", (request, response) -> {
			request.session(true);
			JSONObject responseJson = new JSONObject();
			response.type("application/json");
			JSONObject json = JSONObject.fromObject(request.body());
			if (json.has("keywords")) {
				responseJson.put("result", "success");
				responseJson.put("jobs", convertJobList(Unicorn.getInstance().findJobs((String) json.get("keywords"))));
				return responseJson.toString();
			}
			return "{}";
		});

		post("/getMyJobs", "application/json", (request, response) -> {
			request.session(true);
			response.type("application/json");
			String owner = request.session().attribute("User");
//			System.out.println("getMyJobs for "+owner);
			return convertJobList(Unicorn.getInstance().getMyJobs(owner)).toString();
		});

		post("/getJob", "application/json", (request, response) -> {
			request.session(true);
			JSONObject reqJson = JSONObject.fromObject(request.body());
			response.type("application/json");
			String owner = request.session().attribute("User");
			JSONObject responseJson = new JSONObject();
			Job j = Unicorn.getInstance().getJobbyId((String) reqJson.get("id"));
			if (j != null) {
				responseJson.put("result", "success");
				responseJson.put("editable", owner.equals(j.getEmployer()));
				responseJson.put("job", j.toJson());
//				System.out.print("getJob "+owner +" "+j.getEmployer());

			}
			return responseJson.toString();
		});

		post("/getCV", "application/json", (request, response) -> {
			request.session(true);
			JSONObject reqJson = JSONObject.fromObject(request.body());
			response.type("application/json");
			String owner = request.session().attribute("User");
			JSONObject responseJson = new JSONObject();

			CV cv = Unicorn.getInstance().getCVbyId((String) reqJson.get("cvId"));
			if (cv != null) {
				responseJson.put("result", "success");
				responseJson.put("editable", owner.equals(cv.getStudentId()));
				responseJson.put("cv", cv.toJson());

			}
			return responseJson.toString();
		});

		post("/getMyCVs", "application/json", (request, response) -> {
			request.session(true);
			response.type("application/json");
			String owner = request.session().attribute("User");
//			System.out.println("getMyCVs for " + owner);
			return convertCVList(Unicorn.getInstance().getStudentCV(owner)).toString();
		});

		post("/updateCV", "application/json", (request, response) -> {
			JSONObject reqJson = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();
			request.session(true);
			String owner = request.session().attribute("User");
			if (UserType.getUserType(request.session().attribute("UserType").toString()).equals(UserType.Student)) {
				Unicorn.getInstance().updateCV((String) reqJson.get("cvId"), (String) reqJson.get("title"),
						(String) reqJson.get("content"));
				responseJson.put("result", "success");
			} else {
				responseJson.put("result", "failed");
				responseJson.put("msg", "Yon are not a Student, CAN NOT update a CV!");

			}
			response.type("application/json");
			return responseJson.toString();
		});

		post("/removeCV", "application/json", (request, response) -> {
			JSONObject reqJson = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();
			request.session(true);
			String owner = request.session().attribute("User");
			if (UserType.getUserType(request.session().attribute("UserType").toString()).equals(UserType.Student)) {
				Unicorn.getInstance().removeCV((String) reqJson.get("cvId"));
				responseJson.put("result", "success");
			} else {
				responseJson.put("result", "failed");
				responseJson.put("msg", "Yon are not a Student, CAN NOT remove a CV!");

			}
			response.type("application/json");
			return responseJson.toString();
		});

		post("/addCV", "application/json", (request, response) -> {
			JSONObject reqJson = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();
			request.session(true);
			String owner = request.session().attribute("User");
			if (UserType.getUserType(request.session().attribute("UserType").toString()).equals(UserType.Student)) {
				if (Unicorn.getInstance().addCV(owner, (String) reqJson.get("title"),
						(String) reqJson.get("content"))) {
					responseJson.put("result", "success");
				} else {
					responseJson.put("result", "failed");
				}
			} else {
				responseJson.put("result", "failed");
				responseJson.put("msg", "Yon are not a Student, CAN NOT Add a CV!");

			}
			response.type("application/json");
			return responseJson.toString();
		});

		post("/findApplicants", "application/json", (request, response) -> {
			request.session(true);
			response.type("application/json");
			return convertStudentList(Unicorn.getInstance()
					.findApplicants((String) JSONObject.fromObject(request.body()).get("keywords"))).toString();
		});

		post("/applyJob", "application/json", (request, response) -> {
			JSONObject json = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();

			request.session(true);
			String owner = request.session().attribute("User");
			response.type("application/json");

			if (json.has("CVID") && json.has("jobID") && json.has("employer") && json.has("message")) {
				if (!Unicorn.getInstance().isAppliedJob(owner, (String) json.get("jobID"))) {
					if (Unicorn.getInstance().applyJob(owner, (String) json.get("CVID"), (String) json.get("jobID"),
							(String) json.get("employer"), (String) json.get("message"))) {
						responseJson.put("result", "success");
					} else {
						responseJson.put("result", "failed");
						responseJson.put("result", "Database mistake!");
					}
				} else {
					responseJson.put("result", "failed");
					responseJson.put("msg", "You have applied this job before, can not apply again!");

				}
			} else {
				responseJson.put("result", "failed");
				responseJson.put("msg", "parameters missing");
			}
			return responseJson.toString();
		});

		post("/getMyApplications", "application/json", (request, response) -> {
			request.session(true);
			String owner = request.session().attribute("User");
			response.type("application/json");
			return convertJobApplicationList(Unicorn.getInstance().getJobApplicationsByApplicant(owner)).toString();
		});

		post("/getReceivedApplications", "application/json", (request, response) -> {
			request.session(true);
			String owner = request.session().attribute("User");
			response.type("application/json");
			return convertJobApplicationList(Unicorn.getInstance().getJobApplicationsByEmployer(owner)).toString();
		});

		post("/getApplication", "application/json", (request, response) -> {
			JSONObject json = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();

			request.session(true);
			String owner = request.session().attribute("User");
			response.type("application/json");
			JobApplication ja = Unicorn.getInstance().getJobApplication((String) json.get("id"));
			if (ja != null) {
				responseJson.put("result", "success");
				responseJson.put("application", ja.toJson());
				responseJson.put("applicant", Unicorn.getInstance().getStudent(ja.getApplicant()).toJson());
				responseJson.put("job", Unicorn.getInstance().getJobbyId(ja.getJobID()).toJson());
				responseJson.put("cv", Unicorn.getInstance().getCVbyId(ja.getCVID()).toJson());
			} else {
				responseJson.put("result", "failed");
			}
			return responseJson.toString();
		});

		post("/updateApplicationMessage", "application/json", (request, response) -> {
			JSONObject json = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();

			request.session(true);
			String owner = request.session().attribute("User");
			response.type("application/json");
			JobApplication ja = Unicorn.getInstance().getJobApplication((String) json.get("id"));
			if (ja != null) {
				responseJson.put("result", "success");
				ja.setMessage((String) json.get("message"));
				Unicorn.getInstance().getJobApplicationMessage((String) json.get("id"), (String) json.get("message"));
			} else {
				responseJson.put("result", "failed");
			}
			return responseJson.toString();
		});

		post("/handleApplication", "application/json", (request, response) -> {
			JSONObject json = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();

			request.session(true);
			String owner = request.session().attribute("User");
			String action = (String) json.get("action");
			if (UserType.getUserType(request.session().attribute("UserType").toString()).equals(UserType.Student)) {
				if (action.equals("cancel")) {
					responseJson.put("result", "success");
					Unicorn.getInstance().updateJobApplicationStatus((String) json.get("id"),
							JobApplicationStatus.Cancelled);
				} else if (action.equals("request")) {
					responseJson.put("result", "success");
					Unicorn.getInstance().updateJobApplicationStatus((String) json.get("id"),
							JobApplicationStatus.Requesting);
				} else {
					responseJson.put("result", "failed");
				}
			} else if (UserType.getUserType(request.session().attribute("UserType").toString())
					.equals(UserType.Employer)) {
				if (action.equals("accept")) {
					responseJson.put("result", "success");
					Unicorn.getInstance().updateJobApplicationStatus((String) json.get("id"),
							JobApplicationStatus.Accepted);
				} else if (action.equals("reject")) {
					responseJson.put("result", "success");
					Unicorn.getInstance().updateJobApplicationStatus((String) json.get("id"),
							JobApplicationStatus.Rejected);
				} else if (action.equals("reset")) {
					responseJson.put("result", "success");
					Unicorn.getInstance().updateJobApplicationStatus((String) json.get("id"),
							JobApplicationStatus.Requesting);
				} else {
					responseJson.put("result", "failed");
				}
			}
			response.type("application/json");
			return responseJson.toString();
		});

		post("/postCommentOnApplication", "application/json", (request, response) -> {
			JSONObject json = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();

			request.session(true);
			String owner = request.session().attribute("User");
			response.type("application/json");

			responseJson.put("result", "success");
			responseJson.put("commentId", Unicorn.getInstance().addComment(owner, (String) json.get("applicationId"),
					(String) json.get("content"), (String) json.get("dateStr")));
			return responseJson.toString();
		});

		post("/getCommentsOfApplication", "application/json", (request, response) -> {
			JSONObject json = JSONObject.fromObject(request.body());

			request.session(true);
			response.type("application/json");

			return Unicorn.getInstance().convertCommentList(
					Unicorn.getInstance().getCommentsOfApplication((String) json.get("applicationId")));
		});

		post("/getAllUsers", "application/json", (request, response) -> {
			response.type("application/json");

			request.session(true);
			if (UserType.getUserType(request.session().attribute("UserType").toString()).equals(UserType.Stuff)) {
				return Unicorn.getInstance().getAllUsers().toString();
			}
			return "{}";
		});

		post("/addUserToBlackList", "application/json", (request, response) -> {
			JSONObject json = JSONObject.fromObject(request.body());
			JSONObject responseJson = new JSONObject();

			request.session(true);
			response.type("application/json");

			String action = (String) json.get("action");
			String usertype = (String) json.get("usertpye");
			if (usertype.equals("Student")) {
				if (action.equals("block")) {
					Unicorn.getInstance().blockStudent((String) json.get("email"), true);
					responseJson.put("result", "success");

				} else if (action.equals("unblock")) {
					Unicorn.getInstance().blockStudent((String) json.get("email"), false);
					responseJson.put("result", "success");
				}
			}
			if (usertype.equals("Employer")) {
				if (action.equals("block")) {
					Unicorn.getInstance().blockEmployer((String) json.get("email"), true);
					responseJson.put("result", "success");

				} else if (action.equals("unblock")) {
					Unicorn.getInstance().blockEmployer((String) json.get("email"), false);
					responseJson.put("result", "success");
				}
			}

			return responseJson.toString();
		});
	}
}
