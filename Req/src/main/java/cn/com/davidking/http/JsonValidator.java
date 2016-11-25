/*
 *    功能名称   ： httpclient 封裝实现1.2
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.http;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
// TODO: Auto-generated Javadoc

/**
 * 用于校验一个字符串是否是合法的JSON格式.
 *
 * @author daikai
 */
public class JsonValidator {
 
    /** The it. */
    private CharacterIterator it;
    
    /** The c. */
    private char              c;
    
    /** The col. */
    private int               col;
 
    /**
     * The Constructor.
     */
    public JsonValidator(){
    }
 
    /**
     * 验证一个字符串是否是合法的JSON串.
     *
     * @param input 要验证的字符串
     * @return true-合法 ，false-非法
     */
    public boolean validate(String input) {
        input = input.trim();
        boolean ret = valid(input);
        return ret;
    }
 
    /**
     * Valid.
     *
     * @param input the input
     * @return true, if valid
     */
    private boolean valid(String input) {
        if ("".equals(input)) return true;
 
        boolean ret = true;
        it = new StringCharacterIterator(input);
        c = it.first();
        col = 1;
        if (!value()) {
            ret = error("value", 1);
        } else {
            skipWhiteSpace();
            if (c != CharacterIterator.DONE) {
                ret = error("end", col);
            }
        }
 
        return ret;
    }
 
    /**
     * Value.
     *
     * @return true, if value
     */
    private boolean value() {
        return literal("true") || literal("false") || literal("null") || string() || number() || object() || array();
    }
 
    /**
     * Literal.
     *
     * @param text the text
     * @return true, if literal
     */
    private boolean literal(String text) {
        CharacterIterator ci = new StringCharacterIterator(text);
        char t = ci.first();
        if (c != t) return false;
 
        int start = col;
        boolean ret = true;
        for (t = ci.next(); t != CharacterIterator.DONE; t = ci.next()) {
            if (t != nextCharacter()) {
                ret = false;
                break;
            }
        }
        nextCharacter();
        if (!ret) error("literal " + text, start);
        return ret;
    }
 
    /**
     * Array.
     *
     * @return true, if array
     */
    private boolean array() {
        return aggregate('[', ']', false);
    }
 
    /**
     * Object.
     *
     * @return true, if object
     */
    private boolean object() {
        return aggregate('{', '}', true);
    }
 
    /**
     * Aggregate.
     *
     * @param entryCharacter the entry character
     * @param exitCharacter the exit character
     * @param prefix the prefix
     * @return true, if aggregate
     */
    private boolean aggregate(char entryCharacter, char exitCharacter, boolean prefix) {
        if (c != entryCharacter) return false;
        nextCharacter();
        skipWhiteSpace();
        if (c == exitCharacter) {
            nextCharacter();
            return true;
        }
 
        for (;;) {
            if (prefix) {
                int start = col;
                if (!string()) return error("string", start);
                skipWhiteSpace();
                if (c != ':') return error("colon", col);
                nextCharacter();
                skipWhiteSpace();
            }
            if (value()) {
                skipWhiteSpace();
                if (c == ',') {
                    nextCharacter();
                } else if (c == exitCharacter) {
                    break;
                } else {
                    return error("comma or " + exitCharacter, col);
                }
            } else {
                return error("value", col);
            }
            skipWhiteSpace();
        }
 
        nextCharacter();
        return true;
    }
 
    /**
     * Number.
     *
     * @return true, if number
     */
    private boolean number() {
        if (!Character.isDigit(c) && c != '-') return false;
        int start = col;
        if (c == '-') nextCharacter();
        if (c == '0') {
            nextCharacter();
        } else if (Character.isDigit(c)) {
            while (Character.isDigit(c))
                nextCharacter();
        } else {
            return error("number", start);
        }
        if (c == '.') {
            nextCharacter();
            if (Character.isDigit(c)) {
                while (Character.isDigit(c))
                    nextCharacter();
            } else {
                return error("number", start);
            }
        }
        if (c == 'e' || c == 'E') {
            nextCharacter();
            if (c == '+' || c == '-') {
                nextCharacter();
            }
            if (Character.isDigit(c)) {
                while (Character.isDigit(c))
                    nextCharacter();
            } else {
                return error("number", start);
            }
        }
        return true;
    }
 
    /**
     * String.
     *
     * @return true, if string
     */
    private boolean string() {
        if (c != '"') return false;
 
        int start = col;
        boolean escaped = false;
        for (nextCharacter(); c != CharacterIterator.DONE; nextCharacter()) {
            if (!escaped && c == '\\') {
                escaped = true;
            } else if (escaped) {
                if (!escape()) {
                    return false;
                }
                escaped = false;
            } else if (c == '"') {
                nextCharacter();
                return true;
            }
        }
        return error("quoted string", start);
    }
 
    /**
     * Escape.
     *
     * @return true, if escape
     */
    private boolean escape() {
        int start = col - 1;
        if (" \\\"/bfnrtu".indexOf(c) < 0) {
            return error("escape sequence  \\\",\\\\,\\/,\\b,\\f,\\n,\\r,\\t  or  \\uxxxx ", start);
        }
        if (c == 'u') {
            if (!ishex(nextCharacter()) || !ishex(nextCharacter()) || !ishex(nextCharacter())
                || !ishex(nextCharacter())) {
                return error("unicode escape sequence  \\uxxxx ", start);
            }
        }
        return true;
    }
 
    /**
     * Ishex.
     *
     * @param d the d
     * @return true, if ishex
     */
    private boolean ishex(char d) {
        return "0123456789abcdefABCDEF".indexOf(d) >= 0;
    }
 
    /**
     * Next character.
     *
     * @return the char
     */
    private char nextCharacter() {
        c = it.next();
        ++col;
        return c;
    }
 
    /**
     * Skip white space.
     */
    private void skipWhiteSpace() {
        while (Character.isWhitespace(c)) {
            nextCharacter();
        }
    }
 
    /**
     * Error.
     *
     * @param type the type
     * @param col the col
     * @return true, if error
     */
    private boolean error(String type, int col) {
         System.out.printf("type: %s, col: %s%s", type, col, System.getProperty("line.separator"));
        return false;
    }
    
    /**
     * The main method.
     *
     * @param args the args
     */
    public static void main(String[] args){
        String jsonStr = "{\"website\":\"oschina.net\"}";
        System.out.println(jsonStr+":"+new JsonValidator().validate(jsonStr));
    }
    
    /**
     * *
     * 检测json是否合法.
     *
     * @param json the json
     * @return true, if check json valid
     */
    public static boolean checkJsonValid(String json){
    	return new JsonValidator().validate(json);
    }
}
