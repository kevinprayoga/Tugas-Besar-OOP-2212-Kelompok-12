package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public int arrowCode;
    public int code;
    
    private String input = "";
    private int inputLimit;
    private int read;

    private boolean isPressed = false;

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
        read = e.getKeyCode();
        char keyChar = e.getKeyChar();
        
        if (!isPressed) {
            arrowCode = e.getKeyCode();
            isPressed = true;
        }

        code = read;
        if (read == KeyEvent.VK_UP || read == KeyEvent.VK_DOWN || read == KeyEvent.VK_LEFT || read == KeyEvent.VK_RIGHT) {
            arrowCode = read;
        }

        if ((Character.isLetter(keyChar) || keyChar == ' ') && input.length() < inputLimit && !Character.isISOControl(keyChar)) {
            input += keyChar;
        }
        if (read == 8 && input.length() > 0) {
            input = input.substring(0, input.length() - 1);
        }

        // System.out.println(read); // Key debugger

        if (read == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (read == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (read == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (read == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int read = e.getKeyCode();
        arrowCode = 0;

        if (read == code) {
            code = KeyEvent.VK_UNDEFINED;
        }

        if (read == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (read == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (read == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (read == KeyEvent.VK_D) {
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