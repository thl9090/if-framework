package com.yx.common.utils;

import com.itextpdf.text.pdf.BaseFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author YanBingHao
 * @since 2018/8/10
 */
public class PDFUtils {

    private static final Color BACKGROUND_COLOR = new Color(210, 210, 210);

    public static File createPDFByHtml(String pdfFilePath, String htmlInfo, String fontUrl) throws Exception {
        File pdfFile = new File(pdfFilePath);
        ITextRenderer render = new ITextRenderer();
        ITextFontResolver fontResolver = render.getFontResolver();
        fontResolver.addFont(fontUrl, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        render.setDocumentFromString(htmlInfo);
        render.layout();
        FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
        render.createPDF(fileOutputStream);
        render.finishPDF();
        fileOutputStream.close();
        fileOutputStream.flush();
        return pdfFile;
    }

    public static File createPDFPreviewImg(File pdfFile, String imgFilePath) throws Exception {
        PDDocument pdDocument = PDDocument.load(pdfFile);
        PDFRenderer renderer = new PDFRenderer(pdDocument);
        int pageCount = pdDocument.getNumberOfPages();
        BufferedImage[] images = new BufferedImage[pageCount];
        int startHeight = 0, interval = 30;
        int[][] rgbs = new int[pageCount][];
        BufferedImage targetImage = null;
        for (int i = 0; i < pageCount; i++) {
            images[i] = renderer.renderImage(i, 1.5f);
            rgbs[i] = new int[images[i].getWidth() * images[i].getHeight()];
            rgbs[i] = images[i].getRGB(0, 0, images[i].getWidth(), images[i].getHeight(), rgbs[i], 0, images[i].getWidth());
            if (targetImage == null) {
                targetImage = new BufferedImage(images[0].getWidth(), images[0].getHeight() * pageCount + interval * (pageCount - 1), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = (Graphics2D) targetImage.createGraphics();
                graphics.setColor(BACKGROUND_COLOR);
                graphics.fillRect(0,0,images[0].getWidth(),images[0].getHeight() * pageCount + interval * (pageCount - 1));//填充整个屏幕
                graphics.dispose();
            }
            targetImage.setRGB(0, startHeight, images[i].getWidth(), images[i].getHeight(), rgbs[i], 0, images[i].getWidth());
            startHeight += images[i].getHeight() + interval;
        }
        File pdfImgFile = new File(imgFilePath);
        ImageIO.write(targetImage, "png", pdfImgFile);
        pdDocument.close();
        return pdfImgFile;
    }


    public static void main(String[] args) throws Exception {
        //createPDFPreviewImg(new File("C:\\Users\\YX\\Desktop\\融泰正本——王石达.pdf"), "C:\\Users\\YX\\Desktop\\融泰.jpg");
        //BufferedImage targetImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);

        BufferedImage image = new BufferedImage(600, 600,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.createGraphics();
        g.setColor(new Color(210, 210, 210));
        g.fillRect(0,0,600,600);//填充整个屏幕
        //g.setColor(Color.BLACK);
        //g.drawLine(10, 100, 300, 100);
        g.dispose();
        File file = new File("C:\\Users\\YX\\Desktop\\test.jpg");
        ImageIO.write(image, "jpg", file);

    }

}
