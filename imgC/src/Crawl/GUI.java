package Crawl;

import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GUI extends JFrame{
	private Object textLog;


	GUI() {
		setTitle("IAT Project");
				
		Container c = getContentPane();
		c.setLayout(null);
		
		JLabel cssName = new JLabel("CSS형태");
		cssName.setBounds(10,10,100,30);
		
		JTextField css = new JTextField();
		css.setBounds(120,10,200,30);
		
		c.add(cssName);
		c.add(css);
		
		JLabel urlName = new JLabel("url");
		urlName.setBounds(10,50,100,30);
		
		JTextField url = new JTextField();
		url.setBounds(120,50,500,30);
		
		c.add(urlName);
		c.add(url);		
		
		JLabel startPageN = new JLabel("시작 페이지");
		startPageN.setBounds(10,90,100,30);
		
		JTextField startPage = new JTextField();
		startPage.setBounds(120,90,100,30);
		
		c.add(startPageN);
		c.add(startPage);
		
		JLabel endPageN = new JLabel("끝 페이지");
		endPageN.setBounds(230,90,100,30);
		
		JTextField endPage = new JTextField();
		endPage.setBounds(340,90,100,30);
		
		c.add(endPageN);
		c.add(endPage);		
		
		JLabel fileName = new JLabel("파일명");
		fileName.setBounds(10,130,100,30);
		
		JTextField Name = new JTextField();
		Name.setBounds(120,130,200,30);
		
		c.add(fileName);
		c.add(Name);
		
		JButton start = new JButton("시작");
		start.setBounds(250,170,200,30);
		c.add(start);
		
		JTextArea txtLog = new JTextArea();

		JScrollPane scrollPane = new JScrollPane(txtLog);
		scrollPane.setBounds(10,220,665,300);
		

		this.getContentPane().add(scrollPane);
		txtLog.setCaretPosition(txtLog.getDocument().getLength());


		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 600);
		setVisible(true);
		
		ActionListener listener = e->{
			if(startPage.getText().equals("") && endPage.getText().equals("")) {
			try {				
			Document doc = Jsoup.connect(url.getText()).get();
	        Element element = doc.select(css.getText()).get(0);
	        Elements img = element.select("img");
	        int fileNum = 1;
	        for (Element e1 : img) {
	            String url2 = e1.getElementsByAttribute("src").attr("src");
	            
	            URL imgUrl = new URL(url2);
	            
	            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
	            System.out.println(conn.getContentLength());
	            
	            InputStream is = conn.getInputStream();
	            BufferedInputStream bis = new BufferedInputStream(is);
	            FileOutputStream os = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\크롤링테스트\\"+Name.getText()+"_"+fileNum+".jpg");
	            BufferedOutputStream bos = new BufferedOutputStream(os);
	            int byteImg;
	            
	            byte[] buf = new byte[conn.getContentLength()];
	            while((byteImg = bis.read(buf)) != -1) {
	                bos.write(buf,0,byteImg);
	            }
	            txtLog.append(fileNum+"번 이미지 저장완료" + "\n");
	            fileNum += 1;	 
	            bos.close();
	            os.close();
	            bis.close();
	            is.close();
	            System.out.println("끝났엉");
	            
	        }
			}
	        catch(IOException e2) {
	        	System.out.println(e2.getMessage());
	        	txtLog.append(e2.getMessage());
	        }
			} else {
				try {
					int startPageI = Integer.parseInt(startPage.getText());
					int endPageI = Integer.parseInt(endPage.getText());
					
					for( int i= startPageI; i<endPageI; i++) {
					String startPageS = Integer.toString(startPageI);					
					Document doc = Jsoup.connect(url.getText()+startPageS).get();
			        Element element = doc.select(css.getText()).get(0);
			        Elements img = element.select("img");
			        int fileNum = 1;
			        for (Element e1 : img) {
			            String url2 = e1.getElementsByAttribute("src").attr("src");
			            
			            URL imgUrl = new URL(url2);
			            
			            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
			            System.out.println(conn.getContentLength());
			            
			            InputStream is = conn.getInputStream();
			            BufferedInputStream bis = new BufferedInputStream(is);
			            FileOutputStream os = new FileOutputStream("C:\\Users\\2-10\\TestA\\"+Name.getText()+"_"+fileNum+".jpg");
			            BufferedOutputStream bos = new BufferedOutputStream(os);
			            int byteImg;
			            
			            byte[] buf = new byte[conn.getContentLength()];
			            while((byteImg = bis.read(buf)) != -1) {
			                bos.write(buf,0,byteImg);
			            }
			            txtLog.append(fileNum+"번 이미지 저장완료" + "\n");
			            fileNum += 1;	 
			            bos.close();
			            os.close();
			            bis.close();
			            is.close();
			            System.out.println("끝났엉");
			        }
					}
				}
			        catch(IOException e2) {
			        	System.out.println(e2.getMessage());
			        	txtLog.append(e2.getMessage());
			        }
				}
			
	            
	        
		
		};
		
		start.addActionListener(listener);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GUI();

	}

}