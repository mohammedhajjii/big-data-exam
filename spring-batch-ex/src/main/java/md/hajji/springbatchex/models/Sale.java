package md.hajji.springbatchex.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor @Builder
@ToString
public class Sale {

    @Id
    private Long transactionId;
    private String productName;
    private String category;
    private int quantity;
    private double unitPrice;
    private LocalDate purchaseDate;
    private double totalPrice;



    public Sale computeTotalPrice(){
        totalPrice = quantity * unitPrice;
        return this;
    }
}
