package com.netease.lowcode.pdf.extension.utils;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.netease.lowcode.core.annotation.NaslLogic;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontUtils {

    public static PdfFont createFont(String fontPath, String encoding) throws IOException {
        // EmbeddingStrategy.FORCE_EMBEDDED 强制将字体嵌入文档，以便于在没有安装字体的机器上展示
        if (StringUtils.isNotBlank(fontPath)) {
            if (fontPath.endsWith(".ttf")) {
                return PdfFontFactory.createFont(fontPath, StringUtils.isBlank(encoding) ? PdfEncodings.IDENTITY_H : encoding, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED, true);
            }
            if (fontPath.endsWith(".ttc")) {
                // ttc字体索引默认0
                return PdfFontFactory.createTtcFont(fontPath, 0, StringUtils.isBlank(encoding) ? PdfEncodings.IDENTITY_H : encoding, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED, true);
            }
            return PdfFontFactory.createFont(fontPath, encoding);
        }
        return PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H");
    }

    /**
     * 判断字体在本地是否可用
     *
     * @param fontName
     * @return
     */
    public static boolean available(String fontName) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontFamilyNames = ge.getAvailableFontFamilyNames();
        if (Objects.isNull(fontFamilyNames) || fontFamilyNames.length == 0) {
            return false;
        }
        for (String fontFamilyName : fontFamilyNames) {
            if (StringUtils.equals(fontFamilyName, fontName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 传入字体判断本地是否可用
     *
     * @param fontName
     * @return
     */
    @NaslLogic
    public static Boolean fontExist(String fontName) {
        return available(fontName);
    }

}
