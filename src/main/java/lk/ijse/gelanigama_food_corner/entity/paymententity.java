package lk.ijse.gelanigama_food_corner.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class paymententity {
    private String paymentId;
    private String orderId;
    private String customerId;
    private LocalDate paymentDate;
    private LocalDateTime paymentDateTime;
    private String paymentMethod;
    private String total;
    private String paidAmount;
    private String balance;

    public paymententity() {
        this.paymentDate = LocalDate.now();
        this.paymentDateTime = LocalDateTime.now();
    }

    public paymententity(String paymentId, String orderId, String customerId, String paymentMethod, String total, String paidAmount, String balance) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.paymentMethod = paymentMethod;
        this.total = total;
        this.paidAmount = paidAmount;
        this.balance = balance;
        this.paymentDate = LocalDate.now();
        this.paymentDateTime = LocalDateTime.now();
    }

    public paymententity(String paymentId, String orderId, String customerId, LocalDate paymentDate, LocalDateTime paymentDateTime, String paymentMethod, String total, String paidAmount, String balance) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.paymentDate = paymentDate;
        this.paymentDateTime = paymentDateTime;
        this.paymentMethod = paymentMethod;
        this.total = total;
        this.paidAmount = paidAmount;
        this.balance = balance;
    }

    // getters & setters (corrected field names)
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public LocalDateTime getPaymentDateTime() { return paymentDateTime; }
    public void setPaymentDateTime(LocalDateTime paymentDateTime) { this.paymentDateTime = paymentDateTime; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }
    public String getPaidAmount() { return paidAmount; }
    public void setPaidAmount(String paidAmount) { this.paidAmount = paidAmount; }
    public String getBalance() { return balance; }
    public void setBalance(String balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "paymententity{" +
                "paymentId='" + paymentId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", paymentDate=" + paymentDate +
                ", paymentDateTime=" + paymentDateTime +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", total='" + total + '\'' +
                ", paidAmount='" + paidAmount + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}