import java.net.*;
import java.util.Calendar;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;

class Server implements ActionListener
{
            ServerSocket ser = null;
            Socket soc;
            JFrame fr;
            Container cnt;
            JLabel l1, l2,l3;
            static JTextField t1;
            static JTextArea  a1,a2;
            static JButton b, b1;
            static InputStream in = null ;
            static OutputStream out  = null; 
            static File fl =null;             
      void  StartServer()               
                  {  
                       try 
                       {
		
                            ser = new ServerSocket(9090);
                            System.out.println("Server Started. Server running on port number 9090");
                            System.out.println("Waiting For Client to start chatting .....");
                            soc = ser.accept();
                            in=soc.getInputStream();
	          out=soc.getOutputStream();

                        }                                                                                                                            
                       catch(Exception ee)
                        {
                            System.out.println("Error 1 is  :-" + ee);
                        } 
                    
                  } 
public void display()
                     {
                                 fr = new JFrame("Server Side");
                                 cnt = fr.getContentPane();
                                 l1 = new JLabel("Chatting Messages :");
                                 l2 = new JLabel("Type your message here:");
                                 l3 = new JLabel("Files :");
                                 t1 = new JTextField("");
                                 a1 = new JTextArea("");
                                 a2 = new JTextArea("");
                                 b = new JButton("SEND");
                                 b1 = new JButton("BROWSE");
                                 cnt.setLayout(null);
                                 l1.setBounds(20,20,250,50);
                                 a1.setBounds(170,20,500,150);
                                 a2.setBounds(170,180,500,150);
                                 l2.setBounds(20,400,250,50);
                                 t1.setBounds(170,400,500,75);
                                 b.setBounds(800,600,100,25);
                                 b1.setBounds(1000,600,100,25);
                                 l3.setBounds(20,180,250,50);
                                 cnt.add(l1);
                                 cnt.add(t1);
                                 cnt.add(l2);
                                 cnt.add(l3);
                                 cnt.add(a1);
                                 cnt.add(a2);
                                 cnt.add(b);
                                 cnt.add(b1);
                                 cnt.setBackground(Color.orange);
                                 fr.setSize(1376,768);
                                 fr.setVisible(true);
                                 fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                 b.addActionListener(this); 
                                 b1.addActionListener(this); 
              }
              
   public void actionPerformed( ActionEvent e)
                  {
                       try
                           {
                                   if (e.getSource() == b)
                                        {                                                   
                                            
                                             process ();
                                        } 
                                   else  if (e.getSource() == b1)   
                                         {       
                                                 JFileChooser fd= new JFileChooser();                                       
                                                  int openchoice = fd.showOpenDialog(null); 
                                                         if (openchoice == JFileChooser.APPROVE_OPTION)
                                                                {
                                                                        fl = fd.getSelectedFile();
                                                                        Server.a2.append("\nThe file selected is  :- " + fl.getName());
                                                                        Server.a2.append("\nThe file's path is :- " + fl.getPath());
                                                         
                                                                }
                                           } 
                              }
                             catch(Exception ee)
                                   {
                                     System.out.println("Error 2 is :-" + ee);
                                   }
                         }

void process()              
{
            
                 try
                 { 
                          if   (fl == null)
	                        {  
                                              Server.out.write('0');
                                              String cal=calendar();
                                              String s1=Server.t1.getText();
                                              String s = "You sent:- " +s1+ "         ("+cal+")"; 
                                              Server.a1.append("\n " +s); 
                                              String st =Server.t1.getText();                                                                                                          
                                              byte b[ ] =  st.getBytes();                                                        
                                              Server.out.write(b,0,b.length);                                                               
                                              Server.out.write('#');                                                                
                                              Server.t1.setText("");    
	                      }
	else 
                               if  (fl != null)     
	 
                               {                          String filename = fl.getName();
                                                          //, filename.length()
                                                          String extension = filename.substring(filename.lastIndexOf(".") + 1);
                                                   if     ( extension == "mp3"  ||    extension == "m4a")
                                                             {        Server.out.write('1');                                              // Sending protocol for this type of file.
                                                                        
                                                                      String msg1 = ""+filename+"%";                         // Sending File name and the protocol which indicates the end of File name.
                                                                      byte bp[ ] = msg1.getBytes(); 
                                                                      out.write(bp , 0 , bp.length);
                                                                      
                                                                      long len = fl.length();

                                                                      String msg = "FileLength="+len+"@";                                          
                                                                      byte br[ ] = msg.getBytes();
                                                                      Server.out.write(br , 0, br.length);                          // Sending the Length of the file now.
                                                                     
                                                                      FileInputStream fin = new FileInputStream(fl);
                                                                      int r = fin.read();                                                      // Finally sending the file.
                                                                      while( r != -1)  
                                                                      {
                                                                                out.write(r); 
                                                                                 fin.read();
                                                                       }  
                                                                       fin.close();
                                                             }
                                         else if  (extension == "mp4" || extension == "avi" || extension == "mkv" )
                                                           {       
                                                                      Server.out.write('2');
                                                                     
                                                                      String msg1 = ""+filename+"%"; 
                                                                      byte bp[ ] = msg1.getBytes(); 
                                                                      out.write(bp , 0 , bp.length);
                                                                       
                                                                      long len = fl.length();
                                                                      String msg = "FileLength="+len+"@";                                          
                                                                      byte br[ ] = msg.getBytes();
                                                                      Server.out.write(br , 0, br.length);
                                                                     
                                                                      FileInputStream fin = new FileInputStream(fl);
                                                                      int r = fin.read();
                                                                      while( r != -1)
                                                                      {
                                                                                 out.write(r); 
                                                                                 fin.read();
                                                                       }
                                                                       fin.close();  
                                                             } 
                                            else if  (extension == "pdf")
                                                           {       
                                                                      Server.out.write('3');
                                                                    
                                                                      String msg1 = ""+filename+"%"; 
                                                                      byte bp[ ] = msg1.getBytes(); 
                                                                      out.write(bp , 0 , bp.length);
                                                                    
                                                                      long len = fl.length();
                                                                      String msg = "FileLength="+len+"@";                                          
                                                                      byte br[ ] = msg.getBytes();
                                                                      Server.out.write(br , 0, br.length);
                                                                     
                                                                      FileInputStream fin = new FileInputStream(fl);
                                                                      int r = fin.read();
                                                                      while( r != -1)
                                                                      {
                                                                                 out.write(r); 
                                                                                 fin.read();
                                                                       } 
                                                                       fin.close(); 
                                                             } 
                                              else if  (extension == "txt" )
                                                           {       
                                                                      Server.out.write('4');
                                                                      
                                                                      String msg1 = ""+filename+"%"; 
                                                                      byte bp[ ] = msg1.getBytes(); 
                                                                      out.write(bp , 0 , bp.length);
                                                                      
                                                                      long len = fl.length();
                                                                      String msg = "FileLength="+len+"@";                                          
                                                                      byte br[ ] = msg.getBytes();
                                                                      Server.out.write(br , 0, br.length);
                                                                    
                                                                      FileInputStream fin = new FileInputStream(fl);
                                                                      int r = fin.read();
                                                                      while( r != -1)
                                                                      {
                                                                                out.write(r); 
                                                                                 fin.read();
                                                                       }
                                                                       fin.close();  
                                                             } 
                                             else if  (extension == "docx" )
                                                           {       
                                                                      Server.out.write('5');
                                                                      
                                                                      String msg1 = ""+filename+"%"; 
                                                                      byte bp[ ] = msg1.getBytes(); 
                                                                      out.write(bp , 0 , bp.length);
                                                                     
                                                                      long len = fl.length();
                                                                      String msg = "FileLength="+len+"@";                                          
                                                                      byte br[ ] = msg.getBytes();
                                                                      Server.out.write(br , 0, br.length);
                                                                   
                                                                      FileInputStream fin = new FileInputStream(fl);
                                                                      int r = fin.read();
                                                                      while( r != -1)
                                                                      {
                                                                                out.write(r); 
                                                                                 fin.read();
                                                                       }
                                                                       fin.close();  
                                                             } 
                                             else if  (extension == "jpg" || extension == "JPG" || extension == "jpeg" || extension == "JPEG"   || extension == "png" )
                                                           {       
                                                                      Server.out.write('6');
                                                                     
                                                                      String msg1 = ""+filename+"%"; 
                                                                      byte bp[ ] = msg1.getBytes(); 
                                                                      out.write(bp , 0 , bp.length);
                                                                     
                                                                      long len = fl.length();
                                                                      String msg = "FileLength="+len+"@";                                          
                                                                      byte br[ ] = msg.getBytes();
                                                                      Server.out.write(br , 0, br.length);
                                                                     
                                                                      FileInputStream fin = new FileInputStream(fl);
                                                                      int r = fin.read();
                                                                      while( r != -1)
                                                                      {
                                                                                out.write(r); 
                                                                                 fin.read();
                                                                       }
                                                                       fin.close();  
                                                             } 
                                                 else if  (extension == "xlsx")
                                                           {       
                                                                      Server.out.write('7');
                                                                     
                                                                      String msg1 = ""+filename+"%"; 
                                                                      byte bp[ ] = msg1.getBytes(); 
                                                                      out.write(bp , 0 , bp.length);
                                                                     
                                                                      long len = fl.length();
                                                                      String msg = "FileLength="+len+"@";                                          
                                                                      byte br[ ] = msg.getBytes();
                                                                      Server.out.write(br , 0, br.length);
                                                                     
                                                                      FileInputStream fin = new FileInputStream(fl);
                                                                      int r = fin.read();
                                                                      while( r != -1)
                                                                      {
                                                                                out.write(r); 
                                                                                 fin.read();
                                                                       }
                                                                       fin.close();  
                                                             } 
                                                  out.write('#');
                                                  String cal=calendar();
	                                String s = "You sent File:- " +fl.getName() + "         ("+cal+")"; 
                                                  Server.a1.append("\n " +s); 
                                                  Server.a2.setText("");
                                                  fl=null;
                                  
                                    }
                 	
                             }                                                           
                 
 catch(Exception ee)
                       {
                        System.out.println("Error 3 is :-" + ee);
                       } 

  }

public String calendar()
{
           Calendar now = Calendar.getInstance();
            int h = now.get(Calendar.HOUR_OF_DAY);
            int m = now.get(Calendar.MINUTE);
            int s = now.get(Calendar.SECOND);
            return("" + h + ":" + m + ":" + s);
}


public static void main ( String args [ ] )
       {
          try
                   {              
                         Server s = new Server(); 
                         s.display();                     
                         s.StartServer();    
                     
                    
                                 while(true)
                                                {          Runtime r = Runtime.getRuntime(); 
                                                           Process p ;
                                                           String str = "";
                                                           String str1 = "";
                                                           String str2 = "";
                                                           FileOutputStream fo = null;
                                                          
                                                           int t = in.read();                                                                                                 // Reading the protocol first.
                                                          
                                                          if    (t == '0')
                                                                     { 
                                                                          t = in.read();
			                    while ( t != '#' )
			                        {
			                                str=str+(char)t;
			                                 t = in.read();
			                        }
			                                String cale=s.calendar();
			                                str="Message from Client:- "+str+"              ("+cale+")";
			                                a1.append("\n" +str);
			                 }
                                                          
                                                         else if ( t=='1' )
                                                           {                               
                                                                                             t = in.read();                                                                         // Reading File name.
                                                                                            
                                                                                             while( t != '%')             
                                                                                              {       
                                                                                                    
                                                                                                     str = str+(char)t;
				                               t = in.read();
                                                                                               }
                                                                          
                                                                             String cale = s.calendar();                                                                // Appending File name in the TextArea. 
			                       str1 = "Client Sends File :- " +str+"      ("+cale+")";
                                                                             a1.append("\n" +str1);   
                                                                             str2 = str ;
                                                                                         
                                                                                            t = in.read();                                                                        // Reading the file length.
                                                                                            while( t != '@' )
		                                                                    {
		                                                                       char k=(char)t;
		                                                                       str=str+k;
		                                                                       t=in.read();
		                                                                    } 
                                                                                          int io = str.indexOf("=");
	                   	                                    if ( io != -1)
			                                                       {
		 	                                                                 String slen=str.substring(10+1);  
			                                                                 int flen=Integer.parseInt(slen);

			                                                              //   p = r.exec("copy con );
                                                                                                                       fo = new FileOutputStream("E:\\Chat"+str2);
			                                                                 for( int j = 1; j <= flen; j++ )
				                                                                          {
				                                                                                 t = in.read();
				                                                                                 fo.write(t);
				                                                                          }
                                                                                                                         fo.close();
			                                                         }
                                                                }
                                                               
                                                 }
                      }


catch(Exception ee)
              {
            System.out.println("Error 4 is :-" +ee);
              }
        }
}


                 













