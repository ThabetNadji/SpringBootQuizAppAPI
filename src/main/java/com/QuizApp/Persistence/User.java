package com.QuizApp.Persistence;

public class User {
	private String userName;
	private int score;
	
	User(){}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", score=" + score + "]";
	}
	
}
