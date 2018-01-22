package com.wherever.precareweb.dto;

public class UserDto {
	private String user_id;
	private String user_pwd;
	private String user_name;
	private String user_sex;
	private String user_age;
	private String user_birthday;
	private String user_occupation;
	private String user_manager;
	private boolean user_enabled;
	private String user_role;
	private String user_questionMode;
	
	public UserDto() {}
	

	public UserDto(String user_id, String user_pwd, String user_name, String user_sex, String user_age,
			String user_birthday, String user_occupation, String user_manager) {
		super();
		this.user_id = user_id;
		this.user_pwd = user_pwd;
		this.user_name = user_name;
		this.user_sex = user_sex;
		this.user_age = user_age;
		this.user_birthday = user_birthday;
		this.user_occupation = user_occupation;
		this.user_manager = user_manager;
	}


	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_birthday() {
		return user_birthday;
	}

	public void setUser_birthday(String user_birthday) {
		this.user_birthday = user_birthday;
	}

	public String getUser_manager() {
		return user_manager;
	}

	public void setUser_manager(String user_manager) {
		this.user_manager = user_manager;
	}

	public boolean isUser_enabled() {
		return user_enabled;
	}

	public void setUser_enabled(boolean user_enabled) {
		this.user_enabled = user_enabled;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}



	public String getUser_questionMode() {
		return user_questionMode;
	}



	public void setUser_questionMode(String user_questionMode) {
		this.user_questionMode = user_questionMode;
	}



	public String getUser_age() {
		return user_age;
	}



	public void setUser_age(String user_age) {
		this.user_age = user_age;
	}



	public String getUser_occupation() {
		return user_occupation;
	}



	public void setUser_occupation(String user_occupation) {
		this.user_occupation = user_occupation;
	}
	
	
	
	
}
