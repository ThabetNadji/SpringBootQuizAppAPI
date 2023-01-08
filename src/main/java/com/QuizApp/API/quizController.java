package com.QuizApp.API;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.QuizApp.Persistence.Users;
import com.QuizApp.Persistence.credentialTeacherLogin;
import com.QuizApp.Persistence.question;
import com.QuizApp.Persistence.teacher;
import com.QuizApp.Services.IQuizServices;

//@CrossOrigin(origins = "https://myeduappse.web.app")
@CrossOrigin(origins = "http://localhost:4200")//https://myeduappse.web.app/
@RestController
public class quizController {
	
	@Autowired
	IQuizServices iQuizServices;
	
	@GetMapping("/index")
	String main() {
		String s="";
		s="<br><br><center><h1>ExamensDZ QuizApp API</h1></center><hr>"+
		"<div>\n" + 
		"<br><p><center>API pour crée des examens pour l'application mobile examensDZ, l'API contient tout les fonctionnalité de "
		+ "gestion 'add, delete, update' pour bien gérer le cotenue du app"
		+"</center></p>"+
		"<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br<br><hr><h6><center>tout les droit reserver pour ExamensDZ app</center></h6>"+
		"</div>";
		return s;
	}
	
	@GetMapping("/getSeason")
	public String getSeason() {
		System.out.println("getSeason process...");
		return iQuizServices.getSeason();
	}
	
	// getExamensList => for students
	@GetMapping("/getExamens/{module}/{trim}") 
	public ArrayList<String> getExamensList(@PathVariable String module,@PathVariable String trim) {
		 System.out.println("getExmams process...");
		 return iQuizServices.getExamensList( module, trim);
	}
	
	// getListQuestion => for students
	@GetMapping("/getListQuestion/{module}/{trim}/{examNumber}") 
	public ArrayList<question> getListQuestion(@PathVariable String module,@PathVariable String trim, @PathVariable String examNumber) {
		System.out.println("getListquestion process...");
		return iQuizServices.getListQuestion( module, trim, examNumber);
	}

	// addQuestion => for teacher
	@PostMapping("/addQuestion/{module}/{trim}") 
	public boolean addQuestion(@PathVariable String module,@PathVariable String trim,@RequestBody ArrayList<question> questionList) {
		System.out.println("questionList Value: ");
		for(int i=0;i<questionList.size();i++) {
			System.out.println(questionList.get(i));
		}
		return iQuizServices.addQuestion(module, trim, questionList);
	}
	
	// addUser => for student
	@PostMapping("/addUser/{userID}/{userName}") 
	public boolean addUser(@PathVariable String userID, @PathVariable String userName) {
		System.out.println("addUser process...");
		return iQuizServices.addUser(userID,userName);
	}
	
	// userIsExist => for student
	@GetMapping("/userIsExist/{userID}")
	public Users userIsExist(@PathVariable String userID) {
		System.out.println("userIsExiste process...");
		return iQuizServices.userIsExist(userID);
	}
	
	// upDateScore  -> for student
	@PostMapping("/upDateScore/{userID}/{score}") 
	public boolean upDateScore(@PathVariable String userID,@PathVariable int score) {
		System.out.println("upDateScore Process ...");
		return iQuizServices.upDateScore(userID,score);
	}
	
	// saveExam -> for student
	@PostMapping("/saveExam/{userID}/{examSchema}/{score}") 
	public boolean saveExam(@PathVariable String userID,@PathVariable String examSchema, @PathVariable int score) {
		System.out.println("saveExam process ...");
		return iQuizServices.saveExam(userID,examSchema,score);
	}
	
	// examIsExist -> for student
	@GetMapping("/examIsExist/{userID}/{examSchema}") 
	public String examIsExist(@PathVariable String userID, @PathVariable String examSchema) {
		System.out.println("getExam process ...");
		return iQuizServices.examIsExist(userID,examSchema);
	}
	
	// statistic function 
	// getExamNumberOfTrim -> for student ,new (internally and externally function) x عدد الاختبارات
	@GetMapping("/getExamNumberOfTrim/{userID}/{trim}") 
	public int getExamNumberOfTrim(@PathVariable String userID,@PathVariable String trim) {
		System.out.println("getExamNumberOfTrim process ...");
        return iQuizServices.getExamNumberOfTrim(userID,trim);
    }
	
	// getNumberOfExamsInTrim -> for student for student ,new (internally and externally function ) y عدد الإختبارات الإجمالي
	@GetMapping("/getNumberOfExamsInTrim/{trim}") 
	public int getNumberOfExamsInTrim(@PathVariable String trim) {
		System.out.println("getNumberOfExamsInTrim process ...");
		return iQuizServices.getNumberOfExamsInTrim(trim);
	}
	
	// getNumberOfExamsInTrim -> for student ,new (internally and externally function ) xy مجموع النقاط في ثلاثي
	@GetMapping("/getScoreOfTrim/{userID}/{trim}") 
	public int getScoreOfTrim(@PathVariable String userID,@PathVariable String trim) {
		System.out.println("getScoreTrim process ...");
		return iQuizServices.getScoreOfTrim(userID,trim);
	}
	
	// new function1 getUserCoins 
	@GetMapping("/getUserCoins/{userID}") 
	public int getUserCoins(@PathVariable String userID) {
		System.out.println("getUserCoins process ...");
		return iQuizServices.getUserCoins(userID);
	}
	
	// new function2 setUserCoins
	@GetMapping("/setUserCoins/{userID}/{coins}") 
	public boolean setUserCoins(@PathVariable String userID,@PathVariable int coins) {
		System.out.println("setUserCoins process ...");
		return iQuizServices.setUserCoins(userID,coins);
	}
	
	// addNewTeacher -> for teacher, to new one 
	@PostMapping("/addNewTeacher") 
	public boolean addNewTeacher(@RequestBody teacher _teacher) {
		System.out.println("addNewTeacher ...");
		return iQuizServices.addNewTeacher(_teacher);
	}
	
	// verified is the number that teacher try to register with, is already exist or not  -> for teacher 
	@GetMapping("/isNumberTeacherExist/{teacherNumber}") 
	public String isNumberTeacherExist(@PathVariable String teacherNumber) {
		System.out.println("isNumberTeacherExist ...");
		return iQuizServices.isNumberTeacherExist(teacherNumber);
	}
	
	// teacher login 
	@PostMapping("/teacherLogin")
	public teacher teacherLogin(@RequestBody credentialTeacherLogin _credentialTeacherLogin) {
		System.out.println("teacherLogin ...");
	    System.out.println("-> "+iQuizServices.teacherLogin(_credentialTeacherLogin.getPhoneNumber(),_credentialTeacherLogin.getPassword()));
		return iQuizServices.teacherLogin(_credentialTeacherLogin.getPhoneNumber(),_credentialTeacherLogin.getPassword());
	}
}
