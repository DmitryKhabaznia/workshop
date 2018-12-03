package workshop.captcha;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Object of captcha that contains image of captcha, an value on it, and time of expiring of this captcha.
 */
public class Captcha {

    private String value;
    private BufferedImage image;
    private LocalDateTime expiringDate;

    /**
     * Constructs captcha with specified value, specified {@link BufferedImage}
     * and specified time of expiring of the captcha.
     *
     * @param value specified value
     * @param image specified {@link BufferedImage}
     * @param time  time of expiring of the captcha
     */
    public Captcha(String value, BufferedImage image, int time) {
        this.value = value;
        this.image = image;
        expiringDate = LocalDateTime.now().plusSeconds(time);
    }

    public String getValue() {
        return value;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isExpired() {
        return expiringDate.isBefore(LocalDateTime.now());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Captcha captcha = (Captcha) o;
        return Objects.equals(getValue(), captcha.getValue()) &&
                Objects.equals(getImage(), captcha.getImage()) &&
                Objects.equals(expiringDate, captcha.expiringDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getImage(), expiringDate);
    }

}
