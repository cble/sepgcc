package com.sepgcc.site.controller;

import com.sepgcc.site.constants.SecurityConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class CaptchaController {

    private int xDelta = 10;
    private int imgWidth = 100;
    private int imgHeight = 44;
    private int codeCount = 4;
    private int x = imgWidth / (codeCount + 1);
    private int fontHeight = imgHeight - 20;
    private int codeY = imgHeight - 10;
    private String fontStyle = "Times New Roman";

    @RequestMapping(value = "/captcha", method = {RequestMethod.GET, RequestMethod.POST})
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        HttpSession session = request.getSession();
        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 生成随机类
        Random random = new Random();

        // 设定背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imgWidth, imgHeight);

        // 设定字体
        g.setFont(new Font(fontStyle, Font.PLAIN + Font.ITALIC, fontHeight));

        // 画边框
        g.setColor(new Color(55, 55, 12));
        g.drawRect(0, 0, imgWidth - 1, imgHeight - 1);

        // 随机产生干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 120; i++) {
            int x = random.nextInt(imgWidth);
            int y = random.nextInt(imgHeight);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 取随机产生的认证码(4位数字)
        String sRand = "";
        int red = 0, green = 0, blue = 0;
        for (int i = 0; i < codeCount; i++) {
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            int wordType = random.nextInt(3);
            char retWord = 0;
            int degree = random.nextInt() % 10;
            switch (wordType) {
//                case 0:
//                    retWord = this.getSingleNumberChar();
//                    break;
//                case 1:
//                    retWord = this.getLowerOrUpperChar(0);
//                    break;
//                case 2:
//                    retWord = this.getLowerOrUpperChar(1);
//                    break;
                default:
                    retWord = this.getSingleNumberChar();
                    break;
            }
            sRand += String.valueOf(retWord);
            g.setColor(new Color(red, green, blue));
            g.rotate(degree * Math.PI / 180, x + xDelta, 20);
            g.drawString(String.valueOf(retWord), (i) * x + xDelta, codeY);
            g.rotate(-degree * Math.PI / 180, x, 20);

        }
        // 将认证码存入SESSION
        session.setAttribute(SecurityConstants.SESSION_CAPTCHA, sRand);
        // 图象生效
        g.dispose();
        ServletOutputStream responseOutputStream = response.getOutputStream();
        // 输出图象到页面
        ImageIO.write(image, "JPEG", responseOutputStream);

        // 以下关闭输入流！
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private char getSingleNumberChar() {
        Random random = new Random();
        int numberResult = random.nextInt(10);
        int ret = numberResult + 48;
        return (char) ret;
    }

    private char getLowerOrUpperChar(int upper) {
        Random random = new Random();
        int numberResult = random.nextInt(26);
        int ret = 0;
        if (upper == 0) {// 小写
            ret = numberResult + 97;
        } else if (upper == 1) {// 大写
            ret = numberResult + 65;
        }
        return (char) ret;
    }
}
