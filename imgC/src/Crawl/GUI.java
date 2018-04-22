package Crawl;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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

public class GUI extends JFrame {
	private Object textLog;
	Font font = new Font("돋움", Font.BOLD, 18);

	GUI() {
		setTitle("짤줍짤줍");

		Container c = getContentPane();
		c.setLayout(null);

		// CSS형태 입력란
		JLabel cssName = new JLabel("CSS형태");
		cssName.setBounds(10, 10, 100, 30);
		cssName.setHorizontalAlignment(SwingConstants.CENTER);
		cssName.setFont(font);

		JTextField css = new JTextField();
		css.setBounds(120, 10, 200, 30);

		c.add(cssName);
		c.add(css);

		// URL입력란
		JLabel urlName = new JLabel("url");
		urlName.setBounds(10, 50, 100, 30);
		urlName.setHorizontalAlignment(SwingConstants.CENTER);
		urlName.setFont(font);

		JTextField url = new JTextField();
		url.setBounds(120, 50, 500, 30);

		c.add(urlName);
		c.add(url);

		// 저장위치 입력란
		JLabel saveLocationName = new JLabel("저장 위치");
		saveLocationName.setBounds(10, 90, 100, 30);
		saveLocationName.setHorizontalAlignment(SwingConstants.CENTER);
		saveLocationName.setFont(font);

		JTextField saveLocation = new JTextField();
		saveLocation.setBounds(120, 90, 500, 30);

		c.add(saveLocationName);
		c.add(saveLocation);

		// 저장할 파일명 입력란
		// 파일은 "파일명_순번"의 형태로 저장됨. ex)수지_01
		JLabel fileName = new JLabel("파일명");
		fileName.setBounds(10, 130, 100, 30);
		fileName.setHorizontalAlignment(SwingConstants.CENTER);
		fileName.setFont(font);

		JTextField Name = new JTextField();
		Name.setBounds(120, 130, 200, 30);

		c.add(fileName);
		c.add(Name);

		// 시작버튼
		JButton start = new JButton("시작");
		start.setBounds(250, 170, 200, 30);
		start.setFont(font);
		c.add(start);

		// 결과알림 영역
		JTextArea txtLog = new JTextArea();

		JScrollPane scrollPane = new JScrollPane(txtLog);
		scrollPane.setBounds(10, 220, 665, 300);

		this.getContentPane().add(scrollPane);
		txtLog.setCaretPosition(txtLog.getDocument().getLength());

		// 기본설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 600);
		setVisible(true);

		ActionListener listener = e -> {
			String path = saveLocation.getText();
			File file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}					
			
			try {
				Document doc = Jsoup.connect(url.getText()).get();
				Element element = doc.select(css.getText()).get(0);
				Elements img = element.select("img");
				int fileNum = 1;
				for (Element e1 : img) {
					String url2 = e1.getElementsByAttribute("src").attr("src");

					URL imgUrl = new URL(url2);
					// System.out.println(imgUrl); //이미지 주소
					String confirmFormat = imgUrl.toString();
					String format = "";
					if (confirmFormat.contains(".jpg")) {
						format = ".jpg";
					} else if (confirmFormat.contains(".gif")) {
						format = ".gif";
					} else if (confirmFormat.contains(".bmp")) {
						format = ".bmp";
					} else if (confirmFormat.contains(".png")) {
						format = ".png";
					}

					HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
					System.out.println(conn.getContentLength());

					InputStream is = conn.getInputStream();
					BufferedInputStream bis = new BufferedInputStream(is);
					FileOutputStream os = new FileOutputStream(
							saveLocation.getText() + "\\" + Name.getText() + "_" + fileNum + format);
					BufferedOutputStream bos = new BufferedOutputStream(os);
					int byteImg;

					byte[] buf = new byte[conn.getContentLength()];
					while ((byteImg = bis.read(buf)) != -1) {
						bos.write(buf, 0, byteImg);
					}
					txtLog.append(Name.getText() + "_" + fileNum + " 저장완료" + "  src : " + imgUrl + "\n");
					fileNum += 1;
					bos.close();
					os.close();
					bis.close();
					is.close();

				}
			} catch (IOException e2) {
				System.out.println(e2.getMessage());
				txtLog.append(e2.getMessage() + "\n");
			}

		};

		start.addActionListener(listener);

	}

	public static void main(String[] args) {
		new GUI();

	}

}