package ca.sheridancollege.musleho.ShineTennis.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import ca.sheridancollege.musleho.ShineTennis.bean.Ticket;
import ca.sheridancollege.musleho.ShineTennis.bean.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", password = "password", roles = {"VENDOR"})
public class TicketRepositoryTest {
    @Autowired private TicketRepository tRepo;
    @Autowired private UserRepo uRepo;

    @Test
    public void testAddItem() {
        User testUser = uRepo.getUserByName("joe");
        int originalSize = tRepo.getAllItems().size();
        Ticket ticket = new Ticket(UUID.randomUUID(), "Jimmy James", 28, "A7", "Standard", 17.99, testUser.getUserId());
        tRepo.addItem(ticket);
        int finalSize = tRepo.getAllItems().size();
        assertEquals(finalSize, originalSize + 1);
    }

    @Test
    public void testDeleteItem() {
        ArrayList<Ticket> tickets = tRepo.getAllItems();
        Ticket test = tickets.get(tickets.size()-1);
        int originalSize = tRepo.getAllItems().size();
        tRepo.deleteItem(test.getId());
        int finalSize = tRepo.getAllItems().size();
        assertEquals(finalSize, originalSize -1);
    }

    public void testGetAllItems() {
        ArrayList<Ticket> tickets = tRepo.getAllItems();
        assertEquals(tickets.size(), 10);
    }

    @Test
    public void testGetAvailableSeats() {
        ArrayList<String> availableSeats = tRepo.getAvailableSeats(null);
        ArrayList<Ticket> tickets = tRepo.getAllItems();
        boolean good = true;
        for (Ticket ticket : tickets) {
            if (availableSeats.indexOf(ticket.getSeatNumber()) >= 0) {
                good = false;
                break;
            }
        }
        assertTrue(good);
    }

    @Test
    public void testGetItemById() {
        ArrayList<Ticket> tickets = tRepo.getAllItems();
        Ticket target = tickets.get(0);
        Ticket result = tRepo.getItemById(target.getId());
        assertTrue(target.equals(result));
    }

    @Test
    public void testGetTicketsByUser() {
        User testUser = uRepo.getUserByName("omar");
        ArrayList<Ticket> tickets = tRepo.getTicketsByUser(testUser.getUserId());
        assertEquals(tickets.size(), 2);
    }

    @Test
    public void testUpdateItem() {
        ArrayList<Ticket> tickets = tRepo.getAllItems();
        Ticket target = tickets.get(0);
        target.setCustomerName("TEST SUCCESSFUL");
        tRepo.updateItem(target);
        Ticket result = tRepo.getItemById(target.getId());
        String finalName = result.getCustomerName();
        assertEquals(finalName, "TEST SUCCESSFUL");
    }
}
