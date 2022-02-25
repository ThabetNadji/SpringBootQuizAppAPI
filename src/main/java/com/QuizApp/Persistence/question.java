package com.QuizApp.Persistence;

public class question {
	private int correctRepNum;
	private String rep1;
	private String rep2;
	private String rep3;
	private String rep4;
	private String questionText;
	
	question(){}
	
	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public int getCorrectRepNum() {
		return correctRepNum;
	}

	public void setCorrectRepNum(int correctRepNum) {
		this.correctRepNum = correctRepNum;
	}

	public String getRep1() {
		return rep1;
	}

	public void setRep1(String rep1) {
		this.rep1 = rep1;
	}

	public String getRep2() {
		return rep2;
	}

	public void setRep2(String rep2) {
		this.rep2 = rep2;
	}

	public String getRep3() {
		return rep3;
	}

	public void setRep3(String rep3) {
		this.rep3 = rep3;
	}

	public String getRep4() {
		return rep4;
	}

	public void setRep4(String rep4) {
		this.rep4 = rep4;
	}

	@Override
	public String toString() {
		return "question [correctRepNum=" + correctRepNum + ", rep1=" + rep1 + ", rep2=" + rep2 + ", rep3=" + rep3
				+ ", rep4=" + rep4 + ", getCorrectRepNum()=" + getCorrectRepNum() + ", getRep1()=" + getRep1()
				+ ", getRep2()=" + getRep2() + ", getRep3()=" + getRep3() + ", getRep4()=" + getRep4() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
