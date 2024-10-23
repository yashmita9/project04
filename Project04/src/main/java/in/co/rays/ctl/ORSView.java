package in.co.rays.ctl;

public interface ORSView {

	public String APP_CONTEXT = "/Project04";
	
	public String PAGE_FOLDER = "/jsp";
	
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String ADD_COLLEGE_VIEW = PAGE_FOLDER + "/AddCollegeView.jsp";
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";
	public String ADD_COLLEGE_CTL = APP_CONTEXT + "/AddCollegeCtl";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";
}
