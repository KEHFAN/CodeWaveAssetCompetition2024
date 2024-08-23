package com.netease.lowcode.pdf.extension.utils;

import com.itextpdf.io.font.FontNames;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.netease.lowcode.core.annotation.NaslLogic;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FontUtils {

    public static PdfFont createDefaultFont() {
        try {
            return PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PdfFont createFont(String font) {

        if (StringUtils.isBlank(font)) {
            return createDefaultFont();
        }

        // 注册系统字体
        PdfFontFactory.registerSystemDirectories();
        // 获取已注册字体集合
        Set<String> registeredFonts = PdfFontFactory.getRegisteredFonts();

        if(CollectionUtils.isEmpty(registeredFonts)){
            return createDefaultFont();
        }

        for (String registeredFont : registeredFonts) {
            try {
                PdfFont pdfFont = PdfFontFactory.createRegisteredFont(registeredFont);
                FontNames fontNames = pdfFont.getFontProgram().getFontNames();
                String[][] fullName = fontNames.getFullName();
                PdfFont tmp = compareName(fullName, font, pdfFont);
                if (Objects.nonNull(tmp)) return tmp;

                String fontName = fontNames.getFontName();
                if (StringUtils.equalsIgnoreCase(fontName, font)) {
                    return pdfFont;
                }

                String[][] familyName = fontNames.getFamilyName();
                tmp = compareName(familyName, font, pdfFont);
                if (Objects.nonNull(tmp)) return tmp;
                String[][] familyName2 = fontNames.getFamilyName2();
                tmp = compareName(familyName2, font, pdfFont);
                if (Objects.nonNull(tmp)) return tmp;

            } catch (Exception e) {
                // do nothing
            }
        }

        return createDefaultFont();
    }

    private static PdfFont compareName(String[][] names, String fontName, PdfFont pdfFont) {
        if (Objects.isNull(names) || StringUtils.isBlank(fontName)) {
            return null;
        }
        for (String[] name : names) {
            if (Objects.isNull(name)) {
                continue;
            }
            for (String s : name) {
                if (StringUtils.equalsIgnoreCase(s, fontName)) {
                    return pdfFont;
                }
            }
        }

        return null;
    }

    /**
     * 查看系统字体family
     *
     * @return
     */
    @NaslLogic
    public static List<String> getAvailableFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontFamilyNames = ge.getAvailableFontFamilyNames();
        return Arrays.asList(fontFamilyNames);
    }

    /**
     * 查看已注册的字体
     *
     * @return
     */
    @NaslLogic
    public static List<String> getRegisteredFonts() {
        return Arrays.asList(PdfFontFactory.getRegisteredFonts().toArray(new String[0]));
    }

}
