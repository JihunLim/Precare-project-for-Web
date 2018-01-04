package com.wherever.precareweb.dto;

public class UserDto {
	private String user_id;
	private String user_pwd;
	private String user_name;
	private String user_birthday;
	private String user_manager;
	private boolean user_enabled;
	private String user_role;
	private String user_sex;
	
	public UserDto() {}
	
	public UserDto(String user_id, String user_pwd, String user_name, String user_birthday, String user_manager,
			boolean user_enabled, String user_role, String user_sex) {
		super();
		this.user_id = user_id;
		this.user_pwd = user_pwd;
		this.user_name = user_name;
		this.user_birthday = user_birthday;
		this.user_manager = user_manager;
		this.user_enabled = user_enabled;
		this.user_role = user_role;
		this.user_sex = user_sex;
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
	
	
	
	
}
