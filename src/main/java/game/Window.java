package game;

import org.lwjgl.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int WIDTH, HEIGHT;
    private String title;
    private long glfwWindow;
    private float r, g, b, a;

    private static Window window = null;

    private boolean fadeToBlack = false;

    private Window() {
        this.WIDTH = 1360;
        this.HEIGHT = 760;
        this.title = "Mario";
        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {
        System.out.println("LWJGL: " + Version.getVersion());

        init();
        loop();
        freeMemory();

    }

    public void init() {
        //--- Error Setup ---//
        GLFWErrorCallback.createPrint(System.err).set();

        //--- Initialize GLFW ---//
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        //--- GLFW Config ---//
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //--- Create window ---//
        glfwWindow = glfwCreateWindow(this.WIDTH, this.HEIGHT, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to load GLFW Window");
        }

        //--- Mouse Listeners ---//
        glfwSetCursorPosCallback(glfwWindow, MouseHandler::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseHandler::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseHandler::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyHandler::keyCallback);

        //--- OpenGL current context ---//
        glfwMakeContextCurrent(glfwWindow);
        //--- V-Sync ---//
        glfwSwapInterval(1);
        //--- Window visibility ---//
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }

    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            //--- Poll events ---//
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            //--- TEST ---//
            if (fadeToBlack) {
                r = Math.max(r - 0.01f, 0);
                g = Math.max(g - 0.01f, 0);
                b = Math.max(b - 0.01f, 0);
            }

            if (MouseHandler.mouseButtonDown(GLFW_MOUSE_BUTTON_1)) {
                fadeToBlack = true;
            }

            glfwSwapBuffers(glfwWindow);
        }
    }

    public void freeMemory() {
        //--- Free memory ---//
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //--- Terminate GLFW ---//
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}




















