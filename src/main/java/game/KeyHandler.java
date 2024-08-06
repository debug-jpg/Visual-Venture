package game;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyHandler {
    private static KeyHandler instance;
    private boolean keyPressed[] = new boolean[350];

    private KeyHandler() {

    }

    public static KeyHandler get() {
        if (KeyHandler.instance == null) {
            KeyHandler.instance = new KeyHandler();
        }

        return KeyHandler.instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        }
        else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        if (keyCode < get().keyPressed.length) {
            return get().keyPressed[keyCode];
        }
        else {
            return false;
        }
    }
}


















