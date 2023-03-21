package com.QuizApp.Services;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.QuizApp.Persistence.Users;
import com.QuizApp.Persistence.question;
import com.QuizApp.Persistence.teacher;

@Repository
public interface IQuizServices {
	ArrayList<String> getExamensList(String module,String trim);
	//public question getQuestion(String module,String trim,String exam,String q);
	boolean addQuestion(String module,String trim,ArrayList<question> questionList);
	public ArrayList<question> getListQuestion( String module, String trim, String examNumber) ;
	boolean addUser(String userID, String userName);
	Users userIsExist(String userID);
	boolean upDateScore(String userID,int score);
	boolean saveExam(String userID,String examSchema,int score);
	String getSeason();
	String examIsExist(String userID,String examSchema);
	int getNumberOfExamsInTrim(String trim);
	int getScoreOfTrim(String userID,String trim);
	int getExamNumberOfTrim(String userID,String trim);
	boolean addNewTeacher(teacher _teacher);
	String isNumberTeacherExist(String teacherNumber);
	teacher teacherLogin(String phoneNumber,String password);
	int getUserCoins(String userID);
	boolean setUserCoins(String userID, int coins);
	
}
