package com.QuizApp.Persistence;

public class Users {
	private String userID;
	private String userName;
	private int score;
	
	public Users(String id,String name,int x){
		userID=id;
		userName=name;
		score=x;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

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
		return "Users [userID=" + userID + ", userName=" + userName + ", score=" + score + "]";
	}
	
	

}
