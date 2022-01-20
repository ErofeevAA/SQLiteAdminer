package utils;

import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CodeTextDocumentStyle extends DefaultStyledDocument {

    private final SimpleAttributeSet mainAttribute;
    private final SimpleAttributeSet funcAttribute;
    private final SimpleAttributeSet numAttribute;
    private final SimpleAttributeSet commentAttribute;
    private final SimpleAttributeSet stringAttribute;
    private final SimpleAttributeSet otherAttribute;

    private final ArrayList<BufferModel> bufferUndo;
    private final ArrayList<BufferModel> bufferRedo;

    private boolean isUndoRedo = false;
    private String prevStateText = "";

    public CodeTextDocumentStyle() {
        bufferUndo = new ArrayList<>();
        bufferRedo = new ArrayList<>();

        mainAttribute = new SimpleAttributeSet();
        StyleConstants.setBold(mainAttribute, true);
        StyleConstants.setItalic(mainAttribute, false);
        StyleConstants.setForeground(mainAttribute, new Color(0, 0, 128));

        funcAttribute = new SimpleAttributeSet();
        StyleConstants.setBold(funcAttribute, false);
        StyleConstants.setItalic(funcAttribute, true);
        StyleConstants.setForeground(funcAttribute, Color.BLACK);


        numAttribute = new SimpleAttributeSet();
        StyleConstants.setBold(numAttribute, false);
        StyleConstants.setItalic(numAttribute, false);
        StyleConstants.setForeground(numAttribute, new Color(0, 0, 255));

        commentAttribute = new SimpleAttributeSet();
        StyleConstants.setBold(commentAttribute, false);
        StyleConstants.setItalic(commentAttribute, true);
        StyleConstants.setForeground(commentAttribute, new Color(128, 128, 128));

        stringAttribute = new SimpleAttributeSet();
        StyleConstants.setBold(stringAttribute, false);
        StyleConstants.setItalic(stringAttribute, false);
        StyleConstants.setForeground(stringAttribute, new Color(0, 128, 0));

        otherAttribute = new SimpleAttributeSet();
        StyleConstants.setBold(otherAttribute, false);
        StyleConstants.setItalic(otherAttribute, false);
        StyleConstants.setForeground(otherAttribute, Color.BLACK);
    }

    public void undo() {
        if (bufferUndo.size() == 0) {
            return;
        }
        BufferModel model = bufferUndo.remove(bufferUndo.size() - 1);
        bufferRedo.add(model);
        if (bufferRedo.size() > 15) {
            bufferRedo.remove(0);
        }
        isUndoRedo = true;
        try {
            if (model.state == BufferModel.STATE_DEL) {
                insertString(model.posStart, model.str, null);
            } else {
                remove(model.posStart, model.str.length());
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void redo() {
        if (bufferRedo.size() == 0) {
            return;
        }
        BufferModel model = bufferRedo.remove(bufferRedo.size() - 1);
        bufferUndo.add(model);
        if (bufferUndo.size() > 15) {
            bufferUndo.remove(0);
        }
        isUndoRedo = true;
        try {
            if (model.state == BufferModel.STATE_ADD) {
                insertString(model.posStart, model.str, null);
            } else {
                remove(model.posStart, model.str.length());
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, str, a);

        prevStateText = getText(0 , getLength());
        if (!isUndoRedo) {
            bufferUndo.add(new BufferModel(offset, str, BufferModel.STATE_ADD));
            if (bufferUndo.size() > 15) {
                bufferUndo.remove(0);
            }
        }
        isUndoRedo = false;

        styledCode();
    }

    @Override
    public void remove (int offs, int len) throws BadLocationException {
        super.remove(offs, len);

        if (!isUndoRedo) {
            String str = prevStateText.substring(offs, offs + len);
            bufferUndo.add(new BufferModel(offs, str, BufferModel.STATE_DEL));
            if (bufferUndo.size() > 15) {
                bufferUndo.remove(0);
            }
        }
        isUndoRedo = false;
        prevStateText = getText(0 , getLength());

        styledCode();
    }

    private void styledCode() throws BadLocationException {
        String text = getText(0, getLength());

        int curStart = 0;
        int curLength = 0;

        boolean isLineComment = false;
        boolean isBlockComment = false;
        boolean isOneQuote = false;
        boolean isTwoQuote = false;

        StringBuilder checkedWord = new StringBuilder();

        for (int i = 0; i < text.length(); ++i) {
            checkedWord.append(text.charAt(i));
            ++curLength;

            if (isOneQuote) {
                setCharacterAttributes(curStart, curLength, stringAttribute, false);

                if (text.charAt(i) == '\'' || i + 1 == text.length()) {
                    isOneQuote = false;

                    curLength = 0;
                    curStart = i + 1;
                }
                continue;
            }
            if (isTwoQuote) {
                setCharacterAttributes(curStart, curLength, stringAttribute, false);

                if (text.charAt(i) == '\"' || i + 1 == text.length()) {
                    isTwoQuote = false;

                    curLength = 0;
                    curStart = i + 1;
                }
                continue;
            }

            if (isLineComment) {
                setCharacterAttributes(curStart, curLength, commentAttribute, false);

                if (text.charAt(i) == '\n' || i + 1 == text.length()) {
                    isLineComment = false;
                    curLength = 0;
                    curStart = i + 1;
                }
                continue;
            }
            if (isBlockComment) {
                setCharacterAttributes(curStart, curLength, commentAttribute, false);

                if (checkedWord.toString().equals("*")) {
                    continue;
                }
                if ((checkedWord.toString().equals("*/"))) {
                    isBlockComment = false;

                    curStart = i + 1;
                    curLength = 0;
                }
                checkedWord = new StringBuilder();
                continue;
            }
            if (checkedWord.toString().equals("--")) {
                isLineComment = true;
                checkedWord = new StringBuilder();
                setCharacterAttributes(curStart, curLength, commentAttribute, false);
                continue;
            }
            if (checkedWord.toString().equals("/*")) {
                isBlockComment = true;
                checkedWord = new StringBuilder();
                setCharacterAttributes(curStart, curLength, commentAttribute, false);
                continue;
            }
            if (checkedWord.toString().matches("\\d+")) {
                setCharacterAttributes(curStart, curLength, numAttribute, false);
                continue;
            }

            String word = checkedWord.toString().toUpperCase();
            if (word.substring(word.length() - 1).matches("\\w")) {
                if (Arrays.binarySearch(Keywords.commandWords, word) >= 0 || Arrays.binarySearch(Keywords.typeDataWords, word) >= 0) {
                    setCharacterAttributes(curStart, curLength, mainAttribute, false);
                    continue;
                }
                setCharacterAttributes(curStart, curLength, otherAttribute,false);
                continue;
            }
            if (word.charAt(word.length() - 1) == '(' && Arrays.binarySearch(Keywords.funcWords, word.substring(0, word.length() - 1)) >= 0) {
                setCharacterAttributes(curStart, curLength - 1, funcAttribute, false);
                setCharacterAttributes(curStart + curLength - 1, curLength, otherAttribute, false);
                curStart = i + 1;
                curLength = 0;
                continue;
            }
            if (word.length() > 1) {
                word = word.substring(0, word.length() - 1);
                if (word.matches("\\d+")) {
                    setCharacterAttributes(curStart, curLength - 1, numAttribute, false);
                } else if (Arrays.binarySearch(Keywords.commandWords, word) >= 0 || Arrays.binarySearch(Keywords.typeDataWords, word) >= 0) {
                    setCharacterAttributes(curStart, curLength - 1, mainAttribute, false);
                } else {
                    setCharacterAttributes(curStart, curLength - 1, otherAttribute, false);
                }
            }
            checkedWord = new StringBuilder();
            curStart = i + 1;
            curLength = 0;

            if (text.charAt(i) == '\'') {
                curStart = i;
                curLength = 1;
                isOneQuote = true;
                setCharacterAttributes(curStart, curLength, stringAttribute, false);
                continue;
            }
            if (text.charAt(i) == '\"') {
                curStart = i;
                curLength = 1;
                isTwoQuote = true;
                setCharacterAttributes(curStart, curLength, stringAttribute, false);
                continue;
            }

            if (text.charAt(i) == '-' || text.charAt(i) == '/') {
                curStart = i;
                curLength = 1;
                checkedWord = new StringBuilder();
                checkedWord.append(text.charAt(i));
            }

            setCharacterAttributes(curStart, curLength, otherAttribute,false);
        }
    }
}
