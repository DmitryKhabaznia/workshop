package workshop.bean;

import java.time.LocalDate;
import java.util.Objects;

public class PaymentCard {

    private Long cardNumber;
    private LocalDate cardDate;
    private Integer cardCVV;

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getCardDate() {
        return cardDate;
    }

    public void setCardDate(LocalDate cardDate) {
        this.cardDate = cardDate;
    }

    public Integer getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(Integer cardCVV) {
        this.cardCVV = cardCVV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentCard that = (PaymentCard) o;
        return Objects.equals(getCardNumber(), that.getCardNumber()) &&
                Objects.equals(getCardDate(), that.getCardDate()) &&
                Objects.equals(getCardCVV(), that.getCardCVV());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardNumber(), getCardDate(), getCardCVV());
    }

    @Override
    public String toString() {
        return "PaymentCard{" +
                "cardNumber=" + cardNumber +
                ", cardDate=" + cardDate +
                ", cardCVV=" + cardCVV +
                '}';
    }

}
