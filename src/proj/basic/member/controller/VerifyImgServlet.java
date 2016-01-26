package proj.basic.member.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class VerifyImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Random random = new Random();
	private char[] character = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
			'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '2', '3', '4', '5', '6', '8', '9' };

	private Color buildColor() {
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}

	private Color reverseColor(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		HttpSession session = request.getSession();
		StringBuffer verifyCode = new StringBuffer();

		for (int i = 0; i < 6; i++) {
			verifyCode.append(character[random.nextInt(character.length)]);
		}
		session.setAttribute("verifyCode", verifyCode);

		int width = 120, height = 30;

		Color c = buildColor();
		Color reverseC = reverseColor(c);
		BufferedImage imgBuf = new BufferedImage(width, height,	BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphic = imgBuf.createGraphics();
		graphic.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

		graphic.setColor(c);
		graphic.fillRect(0, 0, width, height);

		graphic.setColor(reverseC);
		graphic.drawString(verifyCode.toString(), 18, 20);

		System.out.println(verifyCode.toString());
		
		for (int i = 0, n = random.nextInt(100); i < n; i++) {
			graphic.drawRect(random.nextInt(width), random.nextInt(height),1, 1);
		}

		ServletOutputStream out = response.getOutputStream();

		JPEGImageEncoder imgEncoder = JPEGCodec.createJPEGEncoder(out);

		imgEncoder.encode(imgBuf);

		out.flush();

	}

}
