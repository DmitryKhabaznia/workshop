package workshop.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import static java.awt.RenderingHints.*;

/**
 * Class that creates {@link Captcha}
 */
public class CaptchaGenerator {

    private static final int WIDTH = 150;
    private static final int HEIGHT = 50;
    private static final int BOLD_SIZE = 18;
    private static final Color COLOR = new Color(150, 234, 0);
    private static final int PAINT_HEIGHT = HEIGHT / 2;
    private static final int START_POINT_X = 10;
    private static final int START_POINT_Y = 20;
    private static final int RANDOM_COEFFICIENT_X = 15;
    private static final int RANDOM_COEFFICIENT_Y = 20;
    private static final int CHARS_NUMBER = 6;
    private static final String FONT_NAME = "Georgia";
    private static final String CANDIDATE_CHARS = "abcdefghijklmnopqurstuvwxyzABCDEFGHIJKLMONOPQURSTUVWXYZ0123456789";

    /**
     * Paints picture of the captcha and creates new object of {@link Captcha} and returns it.
     *
     * @param time specified time of expiring captcha
     * @return new Object of {@link Captcha}
     */
    public Captcha create(int time) {
        String value = generateValue();
        BufferedImage bufferedImage = createCaptchaImage(value.toCharArray());
        return new Captcha(value, bufferedImage, time);
    }

    private String generateValue() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CHARS_NUMBER; i++) {
            sb.append(CANDIDATE_CHARS.charAt(random.nextInt(CANDIDATE_CHARS.length())));
        }
        return sb.toString();
    }

    private BufferedImage createCaptchaImage(char[] chars) {
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setFont(new Font(FONT_NAME, Font.BOLD, BOLD_SIZE));
        RenderingHints rh = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        rh.put(KEY_RENDERING, VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        g2d.setPaint(new GradientPaint(0, 0, Color.orange, 0, PAINT_HEIGHT, Color.black, true));
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        g2d.setColor(COLOR);
        Random r = new Random();
        int x = 0;
        int y;
        for (int i = 0; i < chars.length; i++) {
            x += START_POINT_X + (Math.abs(r.nextInt()) % RANDOM_COEFFICIENT_X);
            y = START_POINT_Y + Math.abs(r.nextInt()) % RANDOM_COEFFICIENT_Y;
            g2d.drawChars(chars, i, 1, x, y);
        }
        g2d.dispose();
        return bufferedImage;
    }

}
