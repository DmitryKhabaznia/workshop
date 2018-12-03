package workshop.bean;

import java.util.Objects;

/**
 * Form bean for registration form.
 */
public class RegistrationFormBean {

    private String firstName;
    private String secondName;
    private String login;
    private String email;
    private String password;
    private String passwordConfirm;
    private String gender;
    private String captcha;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
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
        RegistrationFormBean that = (RegistrationFormBean) o;
        return Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getSecondName(), that.getSecondName()) &&
                Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getPasswordConfirm(), that.getPasswordConfirm()) &&
                Objects.equals(getGender(), that.getGender()) &&
                Objects.equals(getCaptcha(), that.getCaptcha());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(),
                getSecondName(),
                getLogin(),
                getEmail(),
                getPassword(),
                getPasswordConfirm(),
                getGender(),
                getCaptcha());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RegistrationFormBean{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", gender='" + gender + '\'' +
                ", captcha='" + captcha + '\'' +
                '}';
    }

}
