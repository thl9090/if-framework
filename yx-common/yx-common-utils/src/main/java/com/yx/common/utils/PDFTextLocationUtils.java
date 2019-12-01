package com.yx.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取文字在pdf中的位置
 *
 * @author YanBingHao
 * @since 2018/9/17
 */
public class PDFTextLocationUtils {

    public static Map<String, List<CharPosition>> findKeywordPostions(File pdfFile, List<String> keywords) throws IOException {
        byte[] pdfData = new byte[(int) pdfFile.length()];
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(pdfFile);
            inputStream.read(pdfData);
        } catch (IOException e) {
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }

        return findKeywordPostions(pdfData, keywords);
    }

    /**
     * findKeywordPostions
     *
     * @param pdfData
     * @param keywords
     * @throws IOException
     */
    public static Map<String, List<CharPosition>> findKeywordPostions(byte[] pdfData, List<String> keywords) throws IOException {
        Map<String, List<CharPosition>> maps = Maps.newHashMap();
        List<PdfPageContentPositions> pdfPageContentPositions = getPdfContentPostionsList(pdfData);
        List<CharPosition> charPositions = null;
        List<CharPosition> tempList = null;
        for (String keyword : keywords) {
            charPositions = Lists.newArrayList();
            for (PdfPageContentPositions pdfPageContentPosition : pdfPageContentPositions) {
                tempList = findPositions(keyword, pdfPageContentPosition);
                if (CollectionUtils.isEmpty(tempList)) {
                    continue;
                }
                charPositions.addAll(tempList);
            }
            maps.put(keyword, charPositions);
        }
        return maps;
    }

    public static List<CharPosition> findKeywordPostions(File pdfFile, String keyword) throws IOException {
        byte[] pdfData = new byte[(int) pdfFile.length()];
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(pdfFile);
            inputStream.read(pdfData);
        } catch (IOException e) {
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return findKeywordPostions(pdfData, keyword);
    }

    /**
     * findKeywordPostions
     *
     * @param pdfData
     * @param keyword
     * @throws IOException
     */
    public static List<CharPosition> findKeywordPostions(byte[] pdfData, String keyword) throws IOException {
        List<CharPosition> result = new ArrayList<CharPosition>();
        List<PdfPageContentPositions> pdfPageContentPositions = getPdfContentPostionsList(pdfData);


        for (PdfPageContentPositions pdfPageContentPosition : pdfPageContentPositions) {
            List<CharPosition> charPositions = findPositions(keyword, pdfPageContentPosition);
            if (charPositions == null || charPositions.size() < 1) {
                continue;
            }
            result.addAll(charPositions);
        }
        return result;
    }


    private static List<PdfPageContentPositions> getPdfContentPostionsList(byte[] pdfData) throws IOException {
        PdfReader reader = new PdfReader(pdfData);


        List<PdfPageContentPositions> result = new ArrayList<>();


        int pages = reader.getNumberOfPages();
        for (int pageNum = 1; pageNum <= pages; pageNum++) {
            float width = reader.getPageSize(pageNum).getWidth();
            float height = reader.getPageSize(pageNum).getHeight();

            PdfRenderListener pdfRenderListener = new PdfRenderListener(pageNum, width, height);

            //解析pdf，定位位置
            PdfContentStreamProcessor processor = new PdfContentStreamProcessor(pdfRenderListener);
            PdfDictionary pageDic = reader.getPageN(pageNum);
            PdfDictionary resourcesDic = pageDic.getAsDict(PdfName.RESOURCES);
            try {
                processor.processContent(ContentByteUtils.getContentBytesForPage(reader, pageNum), resourcesDic);
            } catch (IOException e) {
                reader.close();
                throw e;
            }


            String content = pdfRenderListener.getContent();
            List<CharPosition> charPositions = pdfRenderListener.getCharPositions();


            List<float[]> positionsList = new ArrayList<>();
            for (CharPosition charPosition : charPositions) {
                float[] positions = new float[]{charPosition.getStartPageNum(), charPosition.getStartX(), charPosition.getStartY()};
                positionsList.add(positions);
            }


            PdfPageContentPositions pdfPageContentPositions = new PdfPageContentPositions();
            pdfPageContentPositions.setContent(content);
            pdfPageContentPositions.setPostions(positionsList);


            result.add(pdfPageContentPositions);
        }
        reader.close();
        return result;
    }


    private static List<CharPosition> findPositions(String keyword, PdfPageContentPositions pdfPageContentPositions) {
        List<CharPosition> result = new ArrayList<CharPosition>();

        String content = pdfPageContentPositions.getContent();
        List<float[]> charPositions = pdfPageContentPositions.getPositions();

        CharPosition position = null;
        for (int pos = 0; pos < content.length(); ) {
            int positionIndex = content.indexOf(keyword, pos);
            if (positionIndex == -1) {
                break;
            }
            float[] startPostions = charPositions.get(positionIndex);
            float[] endPostions = charPositions.get(positionIndex + keyword.length() - 1);
            position = new CharPosition();
            position.setStartPageNum((int) startPostions[0]);
            position.setStartX(startPostions[1]);
            position.setStartY(startPostions[2]);
            position.setEndPageNum((int) endPostions[0]);
            position.setEndX(endPostions[1]);
            position.setEndY(endPostions[2]);
            result.add(position);
            pos = positionIndex + 1;
        }
        return result;
    }


    private static class PdfPageContentPositions {
        private String content;
        private List<float[]> positions;


        public String getContent() {
            return content;
        }


        public void setContent(String content) {
            this.content = content;
        }


        public List<float[]> getPositions() {
            return positions;
        }


        public void setPostions(List<float[]> positions) {
            this.positions = positions;
        }
    }


    private static class PdfRenderListener implements RenderListener {
        private int pageNum;
        private float pageWidth;
        private float pageHeight;
        private StringBuilder contentBuilder = new StringBuilder();
        private List<CharPosition> charPositions = new ArrayList<>();


        public PdfRenderListener(int pageNum, float pageWidth, float pageHeight) {
            this.pageNum = pageNum;
            this.pageWidth = pageWidth;
            this.pageHeight = pageHeight;
        }


        @Override
        public void beginTextBlock() {

        }


        @Override
        public void renderText(TextRenderInfo renderInfo) {
            List<TextRenderInfo> characterRenderInfos = renderInfo.getCharacterRenderInfos();
            for (TextRenderInfo textRenderInfo : characterRenderInfos) {
                String word = textRenderInfo.getText();
                if (word.length() > 1) {
                    word = word.substring(word.length() - 1, word.length());
                }
                com.itextpdf.awt.geom.Rectangle2D.Float rectangle = textRenderInfo.getAscentLine().getBoundingRectange();
                double x = rectangle.getMinX();
                double y = rectangle.getMaxY();


                float xPercent = Math.round(x / pageWidth * 10000) / 10000f;
                float yPercent = Math.round((1 - y / pageHeight) * 10000) / 10000f;// 淇濈暀鍥涗綅灏忔暟


                CharPosition charPosition = new CharPosition(pageNum, xPercent, yPercent);
                charPositions.add(charPosition);
                contentBuilder.append(word);
            }
        }


        @Override
        public void endTextBlock() {


        }


        @Override
        public void renderImage(ImageRenderInfo renderInfo) {


        }


        public String getContent() {
            return contentBuilder.toString();
        }


        public List<CharPosition> getCharPositions() {
            return charPositions;
        }
    }


    public static class CharPosition {
        private int startPageNum = 0;
        private float startX = 0;
        private float startY = 0;
        private int endPageNum = 0;
        private float endX = 0;
        private float endY = 0;

        public CharPosition() {
        }

        public CharPosition(int pageNum, float x, float y) {
            this.startPageNum = pageNum;
            this.startX = x;
            this.startY = y;
        }

        public int getStartPageNum() {
            return startPageNum;
        }

        public void setStartPageNum(int startPageNum) {
            this.startPageNum = startPageNum;
        }

        public float getStartX() {
            return startX;
        }

        public void setStartX(float startX) {
            this.startX = startX;
        }

        public float getStartY() {
            return startY;
        }

        public void setStartY(float startY) {
            this.startY = startY;
        }

        public int getEndPageNum() {
            return endPageNum;
        }

        public void setEndPageNum(int endPageNum) {
            this.endPageNum = endPageNum;
        }

        public float getEndX() {
            return endX;
        }

        public void setEndX(float endX) {
            this.endX = endX;
        }

        public float getEndY() {
            return endY;
        }

        public void setEndY(float endY) {
            this.endY = endY;
        }
    }


    public static void main(String[] args) throws IOException {
        File pdfFile = new File("C:\\Users\\YX\\Desktop\\6076ceeb7a2c4ae1a9a79ac9be9136e3.pdf");
        String keyword = "甲方(出借人)：张**";
        List<CharPosition> positions = findKeywordPostions(pdfFile, keyword);

        System.out.println("total:" + positions.size());
        if (positions != null && positions.size() > 0) {
            for (CharPosition position : positions) {
                System.out.print("pageNum: " + position.getStartPageNum());
                System.out.print("\tx: " + position.getStartX());
                System.out.println("\ty: " + position.getStartY());
                System.out.print("pageNum: " + position.getEndPageNum());
                System.out.print("\tx: " + position.getEndX());
                System.out.println("\ty: " + position.getEndY());
            }
        }
    }

}
