package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Routine {
    String routine[][] = new String[5][6];
    
    
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
    void ShowRoutine(){
        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++){
                System.out.print(this.routine[i][j] + " ");
            }
            System.out.println("");
        }
    }
    void ClearRoutine(){
        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++){
                this.routine[i][j] = null;
            }
        }
    }
    void DemoGeneratePdf(String s) {
  	  Document document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(this.getAlphaNumericString(8)+"_"+s+".pdf"));
            document.open();
            document.add(new Paragraph(8));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(105);
            table.setSpacingBefore(11f);
            table.setSpacingAfter(11f);

            float[] colWidth = {2f, 2f, 2f, 2f, 2f, 2f, 2f};
            table.setWidths(colWidth);
            String[] time = {"Session","9:30-10:30","10:30-11:30","11:30-12:30","12:30-1:30","02:00-03:00","3:00-04:00",};
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
	            	  if(this.routine[j][i]==null) {
	            		  PdfPCell timeCell = new PdfPCell(new Paragraph(""));
		                  table.addCell(timeCell);
	            	  }
	            	  else {
	            		  PdfPCell timeCell = new PdfPCell(new Paragraph(this.routine[j][i]));
		                  table.addCell(timeCell);
	            	  }
	                  
	              }
            }

            document.add(table);
            document.close();
            pdfWriter.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
  }
    void GeneratePdf(String s) {
    	  Document document = new Document(PageSize.A4.rotate());
          try {
              PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(this.getAlphaNumericString(8)+"_"+s+".pdf"));
              document.open();
              document.add(new Paragraph(s));

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
	            	  if(this.routine[j][i]==null) {
	            		  PdfPCell timeCell = new PdfPCell(new Paragraph(""));
		                  table.addCell(timeCell);
	            	  }
	            	  else {
	            		  PdfPCell timeCell = new PdfPCell(new Paragraph(this.routine[j][i]));
		                  table.addCell(timeCell);
	            	  }
	                  
	              }
              }

              document.add(table);
              document.close();
              pdfWriter.close();
          } catch (DocumentException e) {
              e.printStackTrace();
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          }
    }
}
