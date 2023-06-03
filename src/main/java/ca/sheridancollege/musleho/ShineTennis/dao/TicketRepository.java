package ca.sheridancollege.musleho.ShineTennis.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.musleho.ShineTennis.bean.Ticket;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class TicketRepository {
    private NamedParameterJdbcTemplate jdbc;
    
    public void addItem(Ticket ticket) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "INSERT INTO tickets(customer_name, customer_age, seat_number, ticket_category, ticket_price, userId) " + 
                       "VALUES (:name, :cAge, :seatNum, :tCat, :ticket_price, :userId)";
        params.addValue("name", ticket.getCustomerName());
        params.addValue("cAge", ticket.getCustomerAge());
        params.addValue("seatNum", ticket.getSeatNumber());
        params.addValue("tCat", ticket.getTicketCategory());
        params.addValue("ticket_price", ticket.getTicketPrice());
        params.addValue("userId", ticket.getUserId().toString());
        jdbc.update(query, params);
    }

    public Ticket getItemById(UUID id) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "SELECT * FROM tickets WHERE ticketID=:id";
        params.addValue("id", id);
        List<Map<String, Object>> table = jdbc.queryForList(query, params);
        for (Map<String, Object> row : table) {
            Ticket ticket = new Ticket();
            ticket.setId(UUID.fromString((String)(row.get("ticketID"))));
            ticket.setCustomerName((String) (row.get("customer_name")));
            ticket.setCustomerAge((Integer) (row.get("customer_age")));
            ticket.setSeatNumber((String) (row.get("seat_number")));;
            ticket.setTicketCategory((String) (row.get("ticket_category")));
            ticket.setTicketPrice((Double) (row.get("ticket_price")));
            ticket.setUserId(UUID.fromString((String) (row.get("userId"))));
            tickets.add(ticket);
        }
        return tickets.size() > 0 ? tickets.get(0) : null;
    }

    public ArrayList<Ticket> getTicketsByUser(UUID userId) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "SELECT * FROM tickets WHERE userId=:id";
        params.addValue("id", userId);
        List<Map<String, Object>> table = jdbc.queryForList(query, params);
        for (Map<String, Object> row : table) {
            Ticket ticket = new Ticket();
            ticket.setId(UUID.fromString((String)(row.get("ticketID"))));
            ticket.setCustomerName((String) (row.get("customer_name")));
            ticket.setCustomerAge((Integer) (row.get("customer_age")));
            ticket.setSeatNumber((String) (row.get("seat_number")));;
            ticket.setTicketCategory((String) (row.get("ticket_category")));
            ticket.setTicketPrice((Double) (row.get("ticket_price")));
            ticket.setUserId(userId);
            tickets.add(ticket);
        }
        return tickets;
    }

    public ArrayList<Ticket> getAllItems() {
        ArrayList<Ticket> tickets = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "SELECT * FROM tickets";
        List<Map<String, Object>> table = jdbc.queryForList(query, params);
        for (Map<String, Object> row : table) {
            Ticket ticket = new Ticket();
            ticket.setId(UUID.fromString((String)(row.get("ticketID"))));
            ticket.setCustomerName((String) (row.get("customer_name")));
            ticket.setCustomerAge((Integer) (row.get("customer_age")));
            ticket.setSeatNumber((String) (row.get("seat_number")));;
            ticket.setTicketCategory((String) (row.get("ticket_category")));
            ticket.setTicketPrice((Double) (row.get("ticket_price")));
            ticket.setUserId(UUID.fromString((String) (row.get("userId"))));
            tickets.add(ticket);
        }
        return tickets;
    }

    public void updateItem (Ticket ticket) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "UPDATE tickets SET customer_name=:name, customer_age=:cAge, seat_number=:seatNum, ticket_category=:tCat, ticket_price=:tPrice, " + 
                       "userId=:userId WHERE ticketID=:id";
        params.addValue("id", ticket.getId());
                       params.addValue("name", ticket.getCustomerName());
        params.addValue("cAge", ticket.getCustomerAge());
        params.addValue("seatNum", ticket.getSeatNumber());
        params.addValue("tCat", ticket.getTicketCategory());
        params.addValue("tPrice", ticket.getTicketPrice());
        params.addValue("userId", ticket.getUserId().toString());
        jdbc.update(query, params);
    }

    public void deleteItem(UUID id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "DELETE FROM tickets WHERE ticketID=:id";
        params.addValue("id", id);
        jdbc.update(query, params);
    }

    private ArrayList<String> allSeats() {
        ArrayList<String> allSeats = new ArrayList<>();
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < 20; j++) {
                allSeats.add(letters[i] + (j + 1));
            }
        }
        return allSeats;
    }

    public ArrayList<String> getAvailableSeats(Ticket ticket) {
        ArrayList<String> seats = allSeats();
        String ticketId = ticket == null? "" : ticket.getId().toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "SELECT ticketID, seat_number FROM tickets";
        List<Map<String, Object>> table = jdbc.queryForList(query, params);
        for (Map<String, Object> row : table) {
            String id = (String) (row.get("ticketID"));
            String seatNumber = (String) (row.get("seat_number"));
            if (!(ticketId.equals(id))) seats.remove(seatNumber);
        }
        return seats;
    }
}
