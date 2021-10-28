package com.zai;

import java.util.Arrays;
import java.util.List;

public class KoreanDivider {

    private static final String[] CHO = new String[]{
        "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
        "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    };

    private static final String[] JOONG = new String[]{
        "ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ",
        "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ"
    };

    private static final String[] JONG = new String[]{
        "", "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ",
        "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ",
        "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    };

    private static final int NUM_CHO = 19;
    private static final int NUM_JOONG = 21;
    private static final int NUM_JONG = 28;

    private static final int HANGUL_START = 0xAC00; // '가'
    private static final int HANGUL_END = 0xD7A3; // '힣'

    private static final List<Character> IGNORELIST = Arrays.asList(
        ' ', '\t', '\n', ',', '.', '-', '_'
    );

    /*
        Unicode = 0xAC00 + (Chosung_index * NUM_JOONG * NUM_JONG) + 
                  (Joongsung_index * NUM_JONG) + (Jongsung_index)
    */
    private static void decomposeAndAppend(char letter, StringBuilder sb) {
        if (letter >= HANGUL_START && letter <= HANGUL_END) {
            int normalized = letter - 0xAC00;
            int jongIdx = (normalized % NUM_JONG);
            normalized /= NUM_JONG;
            int joongIdx =  normalized % NUM_JOONG; 
            normalized /= NUM_JOONG;
            int choIdx = normalized; 
    
            sb.append(CHO[choIdx]);
            sb.append(JOONG[joongIdx]);
            sb.append(JONG[jongIdx]);
        }
        else if (IGNORELIST.contains(letter))
            return;
        else
            sb.append(Character.toLowerCase(letter));
    }

    public static String decompose(String text) {
        char[] textArray = text.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < textArray.length; i++)
            decomposeAndAppend(textArray[i], sb);

        return sb.toString();
    }
}
