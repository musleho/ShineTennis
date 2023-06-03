package ca.sheridancollege.musleho.ShineTennis.dao;

import java.util.ArrayList;

import ca.sheridancollege.musleho.ShineTennis.bean.Ticket;
import lombok.Getter;

public class StatsHandler {
    @Getter private String saleAverage;
    @Getter private String revenue;
    @Getter private String youngPct;
    @Getter private int stdCount;
    @Getter private int enhCount;
    @Getter private int vipCount;
    
    private Double findSaleAverage(ArrayList<Ticket> tickets) {
        double sum = 0;
        for (Ticket ticket : tickets) {
            sum += ticket.getTicketPrice();
        }
        return Math.round(sum/tickets.size()*100)/100.0;
    }

    private Double findRevenue(ArrayList<Ticket> tickets) {
        Double sum = (double) 0;
        for (Ticket ticket : tickets) {
            sum += ticket.getTicketPrice();
        }
        return Math.round(sum*100)/100.0;
    }

    private Double findYoungPercent(ArrayList<Ticket> tickets) {
        Integer count = 0;
        for (Ticket ticket : tickets) {
            count += ticket.getCustomerAge() < 25 ? 1 : 0;
        }
        return Math.round(100*count/((double) tickets.size())*100.0)/100.0;
    }

    private Integer findStdCount(ArrayList<Ticket> tickets) {
        Integer count = 0;
        for (Ticket ticket : tickets) {
            count += ticket.getTicketCategory().equals("Standard") ? 1 : 0;
        }
        return count;
    }

    private Integer findEnhCount(ArrayList<Ticket> tickets) {
        Integer count = 0;
        for (Ticket ticket : tickets) {
            count += ticket.getTicketCategory().equals("Enhanced") ? 1 : 0;
        }
        return count;
    }

    private Integer findVIPCount(ArrayList<Ticket> tickets) {
        Integer count = 0;
        for (Ticket ticket : tickets) {
            count += ticket.getTicketCategory().equals("VIP") ? 1 : 0;
        }
        return count;
    }

    public void update(ArrayList<Ticket> tickets) {
        this.saleAverage = String.format("$%.2f", findSaleAverage(tickets));
        this.revenue = String.format("$%.2f", findRevenue(tickets));
        this.youngPct = String.format("%.2f", findYoungPercent(tickets));
        this.stdCount = findStdCount(tickets);
        this.enhCount = findEnhCount(tickets);
        this.vipCount = findVIPCount(tickets);
    }
}
