package com.QuizApp.Persistence;

public class User {
	private String userName;
	private int score;
	private int coins;
	
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

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}
	
	@Override
	public String toString() {
		return "User [userName=" + userName + ", score=" + score + ", coins=" + coins + "]";
	}
	
}
