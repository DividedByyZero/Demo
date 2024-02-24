package application;
	
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import com.itextpdf.text.pdf.PdfWriter;

public class Main extends Application{
	///////////////////////////////////////////////////////////////////
	static Map<String,Batch> B = new HashMap<String,Batch>();
	static Map<String,Professor> PR = new HashMap<String,Professor>();
	static Map<String,Course> CR = new HashMap<String,Course>();
	static ArrayList<String> ProfessorList = new ArrayList<String>();
	static ArrayList<String> AssociateProfessorList = new ArrayList<String>();
	static ArrayList<String> AssistantProfessorList = new ArrayList<String>();
	static ArrayList<String> BatchList = new ArrayList<String>();
	static ArrayList<String> CourseList = new ArrayList<String>();
	static ArrayList<String> LabList = new ArrayList<String>();
	static String LABroutine[][] = new String[5][6];
	@FXML
	TextField session;
	@FXML
	TextField course;
	@FXML
	CheckBox credit1;
	@FXML
	CheckBox credit2;
	@FXML
	TextField coursecode;
	@FXML
	TextField professorname;
	@FXML
	CheckBox professor;
	@FXML
	CheckBox associateprofessor;
	@FXML
	CheckBox assistantprofessor;
	@FXML
	ChoiceBox<String> courselist;
	@FXML
	ChoiceBox<String> professorlist;
	@FXML
	ChoiceBox<String> batchlist;
	
	
	String getAlphaNumericString(int n) 
    {  
	     String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	            + "0123456789"
	            + "abcdefghijklmnopqrstuvxyz"; 
	    
	     StringBuilder sb = new StringBuilder(n); 
	    
	     for (int i = 0; i < n; i++) { 
	      int index 
	       = (int)(AlphaNumericString.length() 
	         * Math.random());
	      sb.append(AlphaNumericString 
	         .charAt(index)); 
	     } 
	    
	     return sb.toString(); 
    }
	
	void TotalGeneratePdf() {
  	  Document document = new Document();
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(this.getAlphaNumericString(8)+"_Total.pdf"));
            document.open();
            ArrayList<String> professor=new ArrayList<String>();
        	Collections.shuffle(ProfessorList);
        	Collections.shuffle(AssociateProfessorList);
        	Collections.shuffle(AssistantProfessorList);
        	professor.addAll(ProfessorList);
    		professor.addAll(AssociateProfessorList);
    		professor.addAll(AssistantProfessorList);
    		
    		for (String s : professor) {
                Professor p = PR.get(s);
	            document.add(new Paragraph(p.ProfessorName));
	
	            PdfPTable table = new PdfPTable(7);
	            table.setWidthPercentage(105);
	            table.setSpacingBefore(11f);
	            table.setSpacingAfter(11f);
	
	            float[] colWidth = {2f, 2f, 2f, 2f, 2f, 2f, 2f};
	            table.setWidths(colWidth);
	            String[] time = {"9:30-10:30","10:30-11:30","11:30-12:30","12:30-1:30","02:00-03:00","3:00-04:00",};
	            String[] day  = {"Sunday","Monday","Tuesday","Wednesday","Thurseday"};
	            
	            
	            // Add the "Day" header cell to the first row
	      	  PdfPCell dayCell = new PdfPCell(new Paragraph("Day"));
	      	  table.addCell(dayCell);
	
	            // Add time cells for the first row
	            for (int i = 0; i < 6; i++) {
	                PdfPCell timeCell = new PdfPCell(new Paragraph(time[i]));
	                table.addCell(timeCell);
	            }
	            // Add time cells for the second row
	            for(int j=0;j<5;j++) {
	          	  PdfPCell d = new PdfPCell(new Paragraph(day[j]));
	          	  table.addCell(d);
		              for (int i = 0; i < 6; i++) {
		            	  if(p.routine[j][i]==null) {
		            		  PdfPCell timeCell = new PdfPCell(new Paragraph(""));
			                  table.addCell(timeCell);
		            	  }
		            	  else {
		            		  PdfPCell timeCell = new PdfPCell(new Paragraph(p.routine[j][i]));
			                  table.addCell(timeCell);
		            	  }
		                  
		              }
	            }
	            document.add(table);
    		}
            document.close();
            pdfWriter.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
  }

	
	
	static void CourseAssign(Course cr  , String batch , String professor){
        cr.BatchName = batch;
        cr.CourseProfessorName = professor;
        System.out.println("Course Assigned");
    }
	static void ProfessorAddCourse(Professor pr , String course,double cr){
        if(cr == 3.0) pr.Course.add(course);
        else pr.Lab.add(course);
    }
	static void ShowProfessorCourses(ArrayList<String> ProfessorList,Map<String,Professor> PR){
        for (String s : ProfessorList) {
            Professor pr = PR.get(s);
            pr.ShowCourses();
        }
    }
	static void CreatePDF(){
		ArrayList<String> professor=new ArrayList<String>();
    	Collections.shuffle(ProfessorList);
    	Collections.shuffle(AssociateProfessorList);
    	Collections.shuffle(AssistantProfessorList);
    	professor.addAll(ProfessorList);
		professor.addAll(AssociateProfessorList);
		professor.addAll(AssistantProfessorList);
        for (String s : BatchList) {
            Batch b = B.get(s);
            b.GeneratePdf(s);
        }
        for (String s : professor) {
            Professor b = PR.get(s);
            b.GeneratePdf(s);
        }
        for (String s : BatchList) {
            Batch b = B.get(s);
            b.ClearRoutine();
            for(int i=0;i<5;i++) {
            	b.Track.get(i).clear();
            }
        }
        for (String s : professor) {
            Professor b = PR.get(s);
            b.ClearRoutine();
        }
        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++){
                LABroutine[i][j]=null;
            }
            System.out.println("");
        }
    }
	static void ShowList(ArrayList<String> List){
        for(int i=0;i<List.size();i++){
            System.out.print( i+1 +") "+ List.get(i) +" ");
        }
    }
	static void ShowDetails(ArrayList<String> CourseList,Map<String,Course> CR){
        for (String s : CourseList) {
            Course cr = CR.get(s);
            cr.ShowDetails();
        }
    }
	static void ShowBatchRoutine(Map<String,Batch> B,ArrayList<String> batch){
        for(String b : batch){
            Batch BB = B.get(b);
            BB.ShowRoutine();
        }
    }
	///////////////////////////////////////////////////////////////////
	static boolean check(int i,int j) {
		if((LABroutine[i][j]==null || LABroutine[i][j]=="1") && (LABroutine[i][j+1]==null || LABroutine[i][j+1]=="1")  && (LABroutine[i][j+2]==null || LABroutine[i][j+2]=="1")) {
			return true;
		}
		else {
			return false;
		}
	}
	static ArrayList<Integer> GetSlotLab(Professor professor,Batch batch){
        ArrayList<Integer> code = new ArrayList<Integer>();
        for(int i=0;i<5;i++){
            for(int j=0;j<4;j++) {
                if(batch.Track.get(i).contains(professor.ProfessorName)){
                    System.out.println(professor.ProfessorName + " " + i);
                    break;
                }
                if((professor.routine[i][j]==null && batch.routine[i][j]==null) && (professor.routine[i][j+1]==null && batch.routine[i][j+1]==null) &&(professor.routine[i][j+2]==null && batch.routine[i][j+2]==null) && check(i,j)){
                    code.add((i*10) + j);
                }
            }
        }
        System.out.println(code);
        System.out.println(code.size());
        if(code.size() == 0) {
        	ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(-1);
            a.add(-1);
            return a;
        }
        Random rand = new Random();
        int random = rand.nextInt(code.size());
        int x = code.get(random)/10 ;
        int y = code.get(random)-(x*10);
        System.out.println(x+" "+y);
        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(x);
        a.add(y);
        System.out.println(code);
        return a;
    }
	static ArrayList<Integer> GetSlot(Professor professor,Batch batch){
        ArrayList<Integer> code = new ArrayList<Integer>();
        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++) {
                if(batch.Track.get(i).contains(professor.ProfessorName)){
                    System.out.println(professor.ProfessorName + " " + i);
                    break;
                }
                if(professor.routine[i][j]==null && batch.routine[i][j]==null){
                    code.add((i*10) + j);
                }
            }
        }
        if(code.size() == 0) {
        	ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(-1);
            a.add(-1);
            return a;
        }
        System.out.println(code);
        System.out.println(code.size());
        Random rand = new Random();
        int random = rand.nextInt(code.size());
        int x = code.get(random)/10 ;
        int y = code.get(random)-(x*10);
        System.out.println(x+" "+y);
        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(x);
        a.add(y);
        System.out.println(code);
        return a;
    }
    public void GenerateRoutine(ActionEvent e) throws IOException{
    	ArrayList<String> professor=new ArrayList<String>();
    	Collections.shuffle(ProfessorList);
    	Collections.shuffle(AssociateProfessorList);
    	Collections.shuffle(AssistantProfessorList);
    	professor.addAll(ProfessorList);
		professor.addAll(AssociateProfessorList);
		professor.addAll(AssistantProfessorList);
		Map<String, String[][]> SessionRoutine = new HashMap<>();
        for(String s : professor){
            Professor P = PR.get(s);
            Collections.shuffle(P.Lab);
            Collections.shuffle(P.Course);
            for(String cc : P.Lab){
                int PerWeek = 1;
                while(PerWeek != 0)
                {
                    Course nowCourse = CR.get(cc);
                    Batch bb = B.get(nowCourse.BatchName);
                    ArrayList<Integer> slot = GetSlotLab(P, bb);
                    if(slot.get(0) == -1) break;
                    String lab = "LAB-1";
                    if(LABroutine[slot.get(0)][slot.get(1)]==null) {
                    	LABroutine[slot.get(0)][slot.get(1)]="1";
                    }
                    else {
                    	LABroutine[slot.get(0)][slot.get(1)]="2";
                    	lab = "LAB-2";
                    }
                    
                    if(LABroutine[slot.get(0)][slot.get(1)+1]==null) {
                    	LABroutine[slot.get(0)][slot.get(1)+1]="1";
                    }
                    else {
                    	LABroutine[slot.get(0)][slot.get(1)+1]="2";
                    	lab = "LAB-2";
                    }
                    
                    if(LABroutine[slot.get(0)][slot.get(1)+2]==null) {
                    	LABroutine[slot.get(0)][slot.get(1)+2]="1";
                    }
                    else {
                    	LABroutine[slot.get(0)][slot.get(1)+2]="2";
                    	lab = "LAB-2";
                    }
                    for(int i=0;i<5;i++){
                        for(int j=0;j<6;j++){
                            System.out.print(LABroutine[i][j] + " ");
                        }
                        System.out.println("");
                    }
                    String ForProf = nowCourse.CourseName + "(" + bb.BatchName + ")"+"("+lab+")";
                    String ForBatch = nowCourse.CourseName + "(" + P.ProfessorName + ")"+"("+lab+")";
                    bb.routine[slot.get(0)][slot.get(1)] = ForBatch;
                    bb.routine[slot.get(0)][slot.get(1)+1] = ForBatch;
                    bb.routine[slot.get(0)][slot.get(1)+2] = ForBatch;
                    P.routine[slot.get(0)][slot.get(1)] = ForProf;
                    P.routine[slot.get(0)][slot.get(1)+1] = ForProf;
                    P.routine[slot.get(0)][slot.get(1)+2] = ForProf;
                    bb.Track.get(slot.get(0)).add(P.ProfessorName);
                    System.out.println(nowCourse.CourseName + " For " + bb.BatchName + " Assigned to " + P.ProfessorName);
                    System.out.println(bb.Track);
                    PerWeek = PerWeek - 1;
                }
            }
            for(String cc : P.Course){
                int PerWeek = 3;
                while(PerWeek != 0)
                {
                    Course nowCourse = CR.get(cc);
                    Batch bb = B.get(nowCourse.BatchName);
                    ArrayList<Integer> slot = GetSlot(P, bb);
                    if(slot.get(0) == -1) break;
                    String ForProf = nowCourse.CourseName + "(" + bb.BatchName+")";
                    String ForBatch = nowCourse.CourseName + "(" + P.ProfessorName+")";
                    bb.routine[slot.get(0)][slot.get(1)] = ForBatch;
                    P.routine[slot.get(0)][slot.get(1)] = ForProf;
                    bb.Track.get(slot.get(0)).add(P.ProfessorName);
                    System.out.println(nowCourse.CourseName + " For " + bb.BatchName + " Assigned to " + P.ProfessorName);
                    System.out.println(bb.Track);
                    PerWeek = PerWeek - 1;
                }
            }
        }
        for(String Session_name : BatchList) {
        	Batch Ss = B.get(Session_name);
        	
        }
        TotalGeneratePdf();
        CreatePDF();
        message("Successfully Generated Routine!");
    }
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	public void message(String msg) {
		Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Successful");
		alert.setContentText(msg);
		ButtonType ok = new ButtonType("OK" , ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(ok);
		alert.showAndWait();
	}
	public boolean warnings(String msg) {
		Alert alert= new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warnings!");
		alert.setContentText(msg);
		ButtonType ok = new ButtonType("OK");
		ButtonType cancel = new ButtonType("Cancel",ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(ok,cancel);
		Optional<ButtonType> result =alert.showAndWait();
		if(result.get() == ok) return false;
		else return true;
	}
	public void addSessionName(ActionEvent e) throws IOException {
		String SessionName = session.getText();
		String wrnmsg = "Session : " + SessionName +"\nWant to add?";
		if(warnings(wrnmsg)) return;
		Batch batch = new Batch(SessionName);
		BatchList.add(SessionName);
		B.put(SessionName, batch);
		
		String msg = "Session " + SessionName + " is successfully added!";
//		Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
//		alert.setTitle("Successful");
//		System.out.print(msg);
//		alert.setContentText(msg);
//		ButtonType ok = new ButtonType("OK" , ButtonData.CANCEL_CLOSE);
//		alert.getButtonTypes().setAll(ok);
//		Optional<ButtonType> result = alert.showAndWait();
		message(msg);
		session.clear();
	}
	public void addCourseName(ActionEvent e) throws IOException {
		String CourseName = course.getText();
		
		double credit = 0;
		if(credit1.isSelected()) {
			credit = 1.5;
		}
		if(credit2.isSelected()) {
			credit = 3.0;
		}
		
		String CourseCode = coursecode.getText();
		String warnmsg = "Course Name : " + CourseName +"\nCredit : "+ credit + "\nCourse Code : " +CourseCode;
		if(warnings(warnmsg)) return;
		Course c = new Course(CourseName, credit , CourseCode);
		if(credit==1.5) {
			LabList.add(CourseName);
		}
		else {
			CourseList.add(CourseName);
		}
        CR.put(CourseName, c);
        String msg = CourseName+" is successfully Added !";
        message(msg);
        credit1.setSelected(false);
		credit2.setSelected(false);
		course.clear();
		coursecode.clear();
        c.ShowDetails();
	}
	public void addProfessorName(ActionEvent e) throws IOException {
		String ProfessorName = professorname.getText();
		Professor p = new Professor(ProfessorName);
		String Designation="";
		if(professor.isSelected()) Designation ="Professor";
        if(associateprofessor.isSelected()) Designation ="Associate Professor";
        if(assistantprofessor.isSelected()) Designation ="AssistantProfessor";
        
		String warnmsg = "Course Name : " + ProfessorName +"\nDesignation : " + Designation;
		if(warnings(warnmsg)) return;
        if(professor.isSelected()) ProfessorList.add(ProfessorName);
        if(associateprofessor.isSelected()) AssociateProfessorList.add(ProfessorName);
        if(assistantprofessor.isSelected()) AssistantProfessorList.add(ProfessorName);
        PR.put(ProfessorName, p);
        String msg = ProfessorName+" is successfully Added !";
        message(msg);
        professorname.clear();
        professor.setSelected(false);
        associateprofessor.setSelected(false);
        assistantprofessor.setSelected(false);
        System.out.print(ProfessorName);
	}
	public void CourseAssignment(ActionEvent e) throws IOException {
		String course = courselist.getValue();
		String professor = professorlist.getValue();
		String batch =  batchlist.getValue();
		String warnmsg = "Course Name : " + course +"\nProfessor : "+ professor + "\nSession : " +batch;
		if(warnings(warnmsg)) return;
		Course cr = CR.get(course);
        Professor pr = PR.get(professor);
        CourseAssign(cr,batch,professor);
        ProfessorAddCourse(pr,course,cr.credit);
        message("Succesfully Assigned Course!");
        courselist.setValue(null);
        professorlist.setValue(null);
        batchlist.setValue(null);
	}
	public void refreshAssignCourse(ActionEvent e) throws IOException {
		ArrayList<String> Courses=new ArrayList<String>();
		ArrayList<String> Professors=new ArrayList<String>();
		Courses.addAll(LabList);
		Courses.addAll(CourseList);
		Professors.addAll(ProfessorList);
		Professors.addAll(AssociateProfessorList);
		Professors.addAll(AssistantProfessorList);
		courselist.getItems().addAll(Courses);
		professorlist.getItems().addAll(Professors);
		batchlist.getItems().addAll(BatchList);
	}
	
	public void assignCourseScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignCourse.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void addProfessorScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("addProfessor.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void addSessionScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("addSession.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void addCourseScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("addCourse.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void MainScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public void start(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root,Color.ORANGE);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Image image = new Image("/icon.png");
		stage.getIcons().add(image);
		stage.setTitle("Automatic Routine Generator");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
		System.out.print(CourseList);
		//CreatePDF(BatchList,B);
        ShowDetails(CourseList,CR);
        ShowList(CourseList);
        ShowList(ProfessorList);
        ShowList(BatchList);
        ShowBatchRoutine(B,BatchList);
	}
}
