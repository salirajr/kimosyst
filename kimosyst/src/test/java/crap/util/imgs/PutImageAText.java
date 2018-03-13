/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util.imgs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author JOVIR
 */
public class PutImageAText {

    public static void mains(String[] args) throws IOException {
        File imgPath = new File("E:\\Branches\\kampf\\kimosyst\\BDIR\\income\\312017\\376.png");
        BufferedImage image = ImageIO.read(imgPath);

        File imgPath2 = new File("E:\\Branches\\kampf\\kimosyst\\rsrc\\logo8.png");
        BufferedImage image2 = ImageIO.read(imgPath2);
        
        File imgPath3 = new File("E:\\Branches\\kampf\\kimosyst\\rsrc\\void.png");
        BufferedImage image3 = ImageIO.read(imgPath3);

        Graphics g = image.getGraphics();
        g.setFont(g.getFont().deriveFont(12f));
        g.setColor(Color.YELLOW);
        g.drawString("ATMB CR Transfer 00001006 /\\401459255/ATB-0000000000147 39000 Transfer 00000",
                 10, 20);
        //g.setColor(Color.blue);
        g.drawString("10|ATM-TRANSFER,MANDIRI,66000000,2017-09-12,098765467898,0987",
                10, 40);
        g.setColor(Color.GREEN);
        g.drawString("#Validated by JS @2017-10-08 12:55",
                 10, 480);
        //g.drawImage(image2, 100, 100, null);
        //g.drawImage(image2, 170, 227, 160, 47, null);
        g.drawImage(image2, 0, 227, 500, 72, null);
        g.drawImage(image3, 0, 297, 500, 22, null);

        g.dispose();
        ImageIO.write(image, "png", new File("C:\\Users\\jovir\\Desktop\\_temp\\image1.jpg"));

    }

    public static byte[] extractBytes2(String source) throws IOException {
        File imgPath = new File(source);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", bos);
        } finally {
            try {
                bos.close();
            } catch (Exception e) {
            }
        }

        return bos == null ? null : bos.toByteArray();

    }
}
