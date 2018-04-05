
public enum Placeholder {
	USERNAME, PASSWORD, OLDPASSWORD, NEWPASSWORD, CONFIRMPASSWORD, FIRSTNAME, LASTNAME, ADDRESS, ADMINCODE, VERIFYADMINCODE;
	
	public String toString()
	{
		switch(this)
		{
		case USERNAME : return "Username";
		case PASSWORD : return "Password";
		case OLDPASSWORD : return "Current Password";
		case NEWPASSWORD : return "New Password";
		case CONFIRMPASSWORD : return "Confirm Password";
		case FIRSTNAME : return "First Name";
		case LASTNAME : return "Last Name";
		case ADDRESS : return "Address";
		case ADMINCODE : return "Admin Code";
		case VERIFYADMINCODE : return "DLSU2017";
		default : return "Invalid";
		}
	}
}
