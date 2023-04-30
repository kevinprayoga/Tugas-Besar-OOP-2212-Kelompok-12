package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public int code;
    private String input = "";
    private int inputLimit;

    public KeyHandler(int inputLimit) {
        this.inputLimit = inputLimit;
    }

    public KeyHandler() {
        this.inputLimit = 20;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        code = e.getKeyCode();
        char keyChar = e.getKeyChar();
        if ((Character.isLetter(keyChar) || keyChar == ' ') && input.length() < inputLimit && !Character.isISOControl(keyChar)) {
            input += keyChar;
        }
        if (code == 8 && input.length() > 0) {
            input = input.substring(0, input.length() - 1);
        }

        // System.out.println(code); // Key debugger

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

    public String getInput() {
        return input;
    }

    public void clearInput() {
        input = "";
    }

    public void setInputLimit(int inputLimit) {
        this.inputLimit = inputLimit;
    }
}