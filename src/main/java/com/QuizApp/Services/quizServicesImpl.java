package com.QuizApp.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.QuizApp.Persistence.User;
import com.QuizApp.Persistence.Users;
import com.QuizApp.Persistence.question;
import com.QuizApp.Persistence.teacher;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteBatch;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class quizServicesImpl implements IQuizServices{

	String CollectionName="saison";
	String season="2021-2022"; // update by superAdmin when season end ,usually on September
	String userCollection ="users";
	String teacherCollection ="teacher";
	String statisticCollection="statistics";
	
	public String getSeason() {
		if(season=="2021-2022") {
			return "20212022";
		}else {
			if(season=="2022-2023") {
				return "20222023";
			}else {
				return "";
			}
		}
	}
	
	public boolean upDateScore(String userID,int score) {
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
			//WriteBatch batch = firestore.batch();
			DocumentReference documentReference=firestore.collection(userCollection).document(userID);
			documentReference.update("score",score); // <-- its came from here 
			//batch.update(sfRef, "score", score);
			return true;
		}catch(Exception e) {
			System.out.println("something went wrong in upDateScore"+e);
			return false;
		}
	}
	
    public ArrayList<String> getExamensList(String module, String trim) {
		ArrayList<String> ListSubCollectionToString = new ArrayList<String>() ;
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function 
			Iterable<CollectionReference> listSubCollection = null;
			
			 listSubCollection=firestore.collection(CollectionName).document(season).collection(module).document(trim).listCollections();
				for (CollectionReference collRef : listSubCollection) {
					  ListSubCollectionToString.add(collRef.getId());
				}
		} catch (Exception e) {
			System.out.println("something went wrong in getExamensList"+e);
			e.printStackTrace();
		}
		return ListSubCollectionToString;
	}
	
	public ArrayList<question> getListQuestion( String module, String trim, String examNumber) {
		
		ArrayList<question> questionList = new ArrayList<question>(); //
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function 
			question question_ = null;
			
			String str ;
			String questionNumber;
			for(int i=0;i<10;i++) {
				str = String.valueOf(i);
				questionNumber="q"+str;
				DocumentReference documentReference = firestore.collection(CollectionName).document(season).collection(module).document(trim)
						.collection(examNumber).document(questionNumber);
				ApiFuture<DocumentSnapshot>apiFuture= documentReference.get();
				DocumentSnapshot document =apiFuture.get();
				if(document.exists()) {
					question_=document.toObject(question.class);
					questionList.add(question_);
				}
			}			
		}catch(Exception e) {
			System.out.println("soemthing went wrong in getListQuestion"+e);
		}
		return questionList;
	}
	
	public Users userIsExist(String userID) {
		Users users;
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
			DocumentReference documentReference = firestore.collection(userCollection).document(userID);
			ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
			DocumentSnapshot document = apiFuture.get();
			if (document.exists()) {
				System.out.println("user detail ..."+document.getId());
				User user=document.toObject(User.class);
				System.out.println("Document data: " + user.getScore());
				System.out.println("userName: "+user.getUserName());
				users = new Users(document.getId(),user.getUserName(),user.getScore(),user.getCoins());
				return users;
			  
			} else {
			    System.out.println("No such document!");
				users = new Users("nullID","nullName",-1,-1);
			  return users;
			}
		}catch(Exception e) {
			System.out.println("something went wrong in userIsExist"+e);
			users = new Users("null","null",-1,-1);
			return users;
		}
	}
	
	// updated schema to achive an exam
	public String examIsExist(String userID, String examSchema) {
		System.out.println("userID value -> "+userID);
		System.out.println("examSchema value -> "+examSchema);
		try {
			Firestore firestore =FirestoreClient.getFirestore();
			DocumentReference documentReference;
			if(examSchema.contains("first_trim")) {
				 documentReference=firestore.collection(userCollection).document(userID).collection("first_trim").document(examSchema);
			}else {
				if(examSchema.contains("second_trim")) {
					 documentReference=firestore.collection(userCollection).document(userID).collection("second_trim").document(examSchema);
				}else {
					documentReference=firestore.collection(userCollection).document(userID).collection("third_trim").document(examSchema);
				}
			}
			
			ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
			DocumentSnapshot document = apiFuture.get(); 
			if(document.exists()) {
				String note= document.getData().get("note").toString();
				return note;
			}else {
				System.out.println("exam not exist");
				return "-1";
			}
		}catch(Exception e) {
			System.out.println("somthing went wrong in examIsExist : "+e);
			return "-1";
		}
	}
	
	// to add new user you have to add userID and userName and also add some other field like score and nrbOfExam of each trim and set their values to 0;
	public boolean addUser(String userID, String userName) {
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
			Map<String,Object> userInfoMap = new HashMap<String, Object>(); // create a Map
			userInfoMap.put("userName",userName);
			userInfoMap.put("score_first_trim",0); // score  trim 1
			userInfoMap.put("number_exam_first_trim",0); // nbr exam 1 trim
			userInfoMap.put("score_second_trim",0); // score trim 2
			userInfoMap.put("number_exam_second_trim",0); // nbr exam 2
			userInfoMap.put("score_third_trim",0); // score trim 3
			userInfoMap.put("number_exam_third_trim",0); // nbr exam 3
			userInfoMap.put("coins",10); // nbr exam 3
			firestore.collection(userCollection).document(userID).set(userInfoMap);
			System.out.println("user added successfelly ...");
			return true;
		}catch(Exception e){
			System.out.println("something went wrong in addUser"+e);
			return false;
		}
	}
	
	public boolean addQuestion( String module, String trim,ArrayList<question> questionList){
		try {
			int i=-1;
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function 
			ArrayList<String> lastOne= getExamensList(module,trim);
			int lastOneValue=0;
			if(lastOne.size()>0) {
				lastOneValue=Integer.valueOf(lastOne.get(lastOne.size()-1));
			}
			Map<String,Object> mapQuestion = new HashMap<String, Object>(); // create a Map
			for(question value:questionList) { // put quetionList to mapQustion
				mapQuestion.put("correctRepNum",value.getCorrectRepNum());
				mapQuestion.put("rep1",value.getRep1());
				mapQuestion.put("rep2",value.getRep2());
				mapQuestion.put("rep3",value.getRep3());
				mapQuestion.put("rep4",value.getRep4());
				mapQuestion.put("questionText",value.getQuestionText());
				
				firestore.collection(CollectionName).document(season).collection(module).document(trim)
				.collection(String.valueOf(lastOneValue+1)).document("q"+Integer.toString(i+1)).set(mapQuestion);
				mapQuestion.clear();
				System.out.println("map isEmpty ? "+mapQuestion.isEmpty());;
				i=i+1;
			}
			// increase number of exam 
			Map<String,Object> _setNumberOfExamsInTrim = new HashMap<String, Object>(); // create a Map
			if(getNumberOfExamsInTrim(trim)>=0) {
				_setNumberOfExamsInTrim.put("numberOfExams",getNumberOfExamsInTrim(trim)+1);
				firestore.collection(statisticCollection).document(trim).set(_setNumberOfExamsInTrim);
			}else {
				System.out.println("soemthing went wrong  in addQuestion ....");
			}
			return true;
		}catch(Exception e){
			System.out.println("soemthing went wrong  in addQuestion ...."+e);
			return false;
		}
	}
	
    // to save an exam you have to do 3 things , N1 : save exam schema, N2 : update score of a trimester and 3therdlly, update nbr of exam of a trimester
	public boolean saveExam(String userID,String examSchema, int score) {
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
			Map<String,Object> examScore = new HashMap<String, Object>(); // create a Map
			Map<String,Object> GlobalExamScore = new HashMap<String, Object>(); // create a Map
			Map<String,Object> examNumberOfTrim = new HashMap<String, Object>(); // create a Map
			examScore.put("note",score);
			if(examSchema.contains("first_trim")) {
				//examScore.put("note",score);
				firestore.collection(userCollection).document(userID).collection("first_trim").document(examSchema).set(examScore); // save exam and score
				//
				GlobalExamScore.put("score_first_trim",getScoreOfTrim(userID,"first_trim") + score); // 1 step of update global score in first trimester
				firestore.collection(userCollection).document(userID).update(GlobalExamScore); //  2 step of update global score in first trimester
				//
				examNumberOfTrim.put("number_exam_first_trim",getExamNumberOfTrim(userID,"first_trim") + 1); // 1 step of update nbr exam 1 in first trimester
				firestore.collection(userCollection).document(userID).update(examNumberOfTrim); //  2 step of update nbr exam 1 in first trimester
				System.out.println("exam saved successfully ...");
				return true;
			}else {
				if(examSchema.contains("second_trim")) {
					//examScore.put("note",score);
					firestore.collection(userCollection).document(userID).collection("second_trim").document(examSchema).set(examScore); // save exam and score
					//
					GlobalExamScore.put("score_second_trim",getScoreOfTrim(userID,"second_trim") + score); // 1 step of update global score in second trimester
					firestore.collection(userCollection).document(userID).update(GlobalExamScore); //  2 step of update global score in first trimester
					//
					examNumberOfTrim.put("number_exam_second_trim",getExamNumberOfTrim(userID,"second_trim") + 1); // 1 step of update nbr exam 2 in second trimester
					firestore.collection(userCollection).document(userID).update(examNumberOfTrim); //  2 step of update nbr exam 2 in second trimester
					System.out.println("exam saved successfully ...");
					return true;
				}else {
					if(examSchema.contains("third_trim")) {
						//examScore.put("note",score);
						firestore.collection(userCollection).document(userID).collection("third_trim").document(examSchema).set(examScore); // save exam and score
						//
						GlobalExamScore.put("score_third_trim",getScoreOfTrim(userID,"third_trim") + score); // 1 step of update global score in third trimester
						firestore.collection(userCollection).document(userID).update(GlobalExamScore); //  2 step of update global score in first trimester
						//
						examNumberOfTrim.put("number_exam_third_trim",getExamNumberOfTrim(userID,"third_trim") + 1); // 1 step of update nbr exam 3 in third trimester
						firestore.collection(userCollection).document(userID).update(examNumberOfTrim); //  2 step of update nbr exam 3 in third trimester
						System.out.println("exam saved successfully ...");
						return true;
					}else {
						return false;
					}
				}
			}
		}catch(Exception e) {
			System.out.println("something went wrong ..."+e);
			return false;
		}
	}

	// new (internally and externally function) y  عدد الإختبارات الإجمالي 
	public int getNumberOfExamsInTrim(String trim) {
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
			DocumentReference documentReference=firestore.collection(statisticCollection).document(trim);
			ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
			DocumentSnapshot document = apiFuture.get();
			String obj= document.getData().get("numberOfExams").toString();
		    int i= Integer.parseInt(obj);
			System.out.println("numberOfExams= "+i);
			return  i;
			
		}catch(Exception e){
			System.out.println("something went wrong in saveExam..."+e);
			return -1;
		}
	}
	
	// new (internally and externally function ) xy مجموع النقاط في ثلاثي
	public int getScoreOfTrim(String userID,String trim) {
		String parametre="";
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
			DocumentReference documentReference=firestore.collection(userCollection).document(userID);
			
			if(trim.equals("first_trim")) { // first_trim
				parametre="score_first_trim";
			}else {
				if(trim.equals("second_trim")) { //second_trim
					parametre="score_second_trim";
				}else {
					 if (trim.equals("third_trim")) { //thirdTrim
						 parametre="score_third_trim";	 
					 }
				}
			}
			
			ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
			DocumentSnapshot document = apiFuture.get();
			if(document.exists()) {
				String obj= document.getData().get(parametre).toString(); // <-- something went wrong here ?
				System.out.println("score of this trim is equal to : ");
				System.out.println(obj);
			    int scoreOfTrim= Integer.parseInt(obj);
				return  scoreOfTrim;
			}else {
				return 0;
			}
			
		}catch(Exception e) {
			System.out.println("something went wrong in getScoreTrim "+e);
			return -1;
		}
	}

	// new (internally and externally function) x عدد الاختبارات
	public int getExamNumberOfTrim(String userID, String trim) {
		String parametre="";
		System.out.println(userID+" / "+trim);
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
			DocumentReference documentReference=firestore.collection(userCollection).document(userID);
			
			if(trim.equals("first_trim")) { // first_trim
				parametre="number_exam_first_trim";
			}else {
				if(trim.equals("second_trim")) { //second_trim
					parametre="number_exam_second_trim";
				}else {
					 if (trim.equals("third_trim")) { //thirdTrim
						 parametre="number_exam_third_trim";	 
					 }
					 System.out.println("we are here4");
				}
			}
			ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
			DocumentSnapshot document = apiFuture.get();
			if(document.exists()) {
				String obj= document.getData().get(parametre).toString(); // <-- something went wrong here ?
			    int scoreOfTrim= Integer.parseInt(obj);
			    System.out.println("number of exam passed by  "+userID+" is "+scoreOfTrim);
			    return scoreOfTrim;
			    //return  String.valueOf(scoreOfTrim);
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("something went wrong in getExamNumberOfTrim "+e);
			return -1;
		}
	}

	public boolean addNewTeacher(teacher _teacher) {
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
			Map<String,Object> teacherInfoMap = new HashMap<String, Object>(); // create a Map
			System.out.println("teacher number "+_teacher.getPhoneNumber());
			teacherInfoMap.put("firstName",_teacher.getFirstName());
			teacherInfoMap.put("lastName",_teacher.getLastName());
			teacherInfoMap.put("module",_teacher.getModule());
			teacherInfoMap.put("password",_teacher.getPassword());
			teacherInfoMap.put("isActivated",false);
			firestore.collection(teacherCollection).document(_teacher.getPhoneNumber()).set(teacherInfoMap);
			System.out.println("Teacher added successfully");
			return true;
		}catch(Exception e) {
			System.out.println("something went wrong in addNewTeacher "+e);
			return false;
		}
	}

	public String isNumberTeacherExist(String teacherNumber) {
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
			DocumentReference documentReference=firestore.collection(teacherCollection).document(teacherNumber);
			ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
			DocumentSnapshot document = apiFuture.get();
			if(document.exists()) {
				// user exist 
				return "isExist";
			}else {
				return "notExist";
			}
		}catch(Exception e) {
			System.out.println("something went wrong in isNumberTeacherExist "+e);
			return "_error";
		}
		
	}
	
	public teacher teacherLogin(String phoneNumber,String password) {
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
			DocumentReference documentReference=firestore.collection(teacherCollection).document(phoneNumber);
			ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
			DocumentSnapshot document = apiFuture.get();
			if(document.exists()) {
				// user exist 
				String _password= document.getData().get("password").toString();
				if(password.equals(_password)) {
					// phone number and password match, so the user is really exist but still dont know if his account is activated or not
					
					teacher _teacher = new teacher(); 
						String firstName = document.getData().get("firstName").toString();
						String lastName = document.getData().get("lastName").toString();	
						String isActivated = document.getData().get("isActivated").toString();
						_teacher.setPhoneNumber(phoneNumber);
						_teacher.setFirstName(firstName);
						_teacher.setLastName(lastName);
						// check if the user account is activated or not 
						if(isActivated.equals("true")) {
							_teacher.setActivated(true);
						}else {
							_teacher.setActivated(false);
						}
					
					return _teacher; // account exist, you have to check if its activated or not (check it in the front-end)
			}else {
				// wrong passwrod
				teacher _teacherWrongPassword = new teacher();
				_teacherWrongPassword.setFirstName("_");
				_teacherWrongPassword.setLastName("_");
				_teacherWrongPassword.setActivated(false);
				return _teacherWrongPassword;
			}	
			}else {
				// phone number doe's not exist
				teacher _teacherWrongPhoneNumber = new teacher();
				_teacherWrongPhoneNumber.setFirstName("/");
				_teacherWrongPhoneNumber.setLastName("/");
				_teacherWrongPhoneNumber.setActivated(false);
				return _teacherWrongPhoneNumber; // acount dont exist
			}
		}catch(Exception e) {
			System.out.println("something went wrong in teacherLogin "+e);
			teacher _teacherWrong= new teacher();
			_teacherWrong.setFirstName("?");
			_teacherWrong.setLastName("?");
			_teacherWrong.setActivated(false);
			return _teacherWrong; // something went wrong 
		}
	}

	// new
	public int getUserCoins(String userID) {
		Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
		DocumentReference documentReference=firestore.collection(userCollection).document(userID);
		try {
			ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
			DocumentSnapshot document = apiFuture.get();
			if(document.exists()) {
				String coins= document.getData().get("coins").toString(); 
				System.out.println("coins of this user is equal to : ");
				System.out.println(coins);
			    int userCoins= Integer.parseInt(coins);
				return  userCoins;
			}else {
				System.out.println("getUserCoins -> document does't exist ...");
				return 0;
			}
			
		}catch(Exception e) {
			System.out.println("getUserCoins -> something went wrong "+e);
		}
		return 0;
	}

	// new
	public boolean setUserCoins(String userID, int coins) {
		int actualCoins=getUserCoins(userID);
		try {
			Firestore firestore = FirestoreClient.getFirestore(); // must be in a function
			DocumentReference documentReference=firestore.collection(userCollection).document(userID);
			if(coins>0) { // -> its mean that i will add new coins 
				documentReference.update("coins",actualCoins+coins); 
				return true;	
			}else { // -> its mean that i will reduce the number of coins
				if(actualCoins>=3) {
					documentReference.update("coins",actualCoins+coins); 
					return true;
				}else {
					System.out.println("number of coins less than 3");
					return false;
				}
				
			}
		}catch(Exception e) {
			System.out.println("something went wrong in upDateScore"+e);
			return false;
		}
	}
	
	
}