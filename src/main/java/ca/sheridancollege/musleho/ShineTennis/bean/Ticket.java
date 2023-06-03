package ca.sheridancollege.musleho.ShineTennis.bean;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket implements Serializable{
    
    private UUID id; //This model's primary key
    private String customerName;
    private int customerAge;
    private String seatNumber;
    private String ticketCategory;
    private double ticketPrice;
    private UUID userId; //Foreign key to owner user
    public static final String[] CATEGORIES = {"Standard", "Enhanced", "VIP"};
}
