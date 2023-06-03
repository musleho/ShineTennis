package ca.sheridancollege.musleho.ShineTennis.controller;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.musleho.ShineTennis.bean.Ticket;
import ca.sheridancollege.musleho.ShineTennis.bean.User;
import ca.sheridancollege.musleho.ShineTennis.dao.StatsHandler;
import ca.sheridancollege.musleho.ShineTennis.dao.TicketRepository;
import ca.sheridancollege.musleho.ShineTennis.dao.UserRepo;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MainController {
    
    private TicketRepository tixRepo;
    private UserRepo uRepo;

    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    @PostMapping("/")
    public String goHome() {
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/register")
    public String register(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "register.html";
    }

    @GetMapping("/book")
    public String book(Model model){
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("categories", Ticket.CATEGORIES);
        model.addAttribute("seats", tixRepo.getAvailableSeats(null));
        model.addAttribute("usernames", uRepo.getAllGuests()); 
        return "booking.html";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute User user, @RequestParam String confirmPassword, HttpServletRequest request) {
        if (!user.getEncPassword().equals(confirmPassword)) return "redirect:/register?passError"; //confirm password matches
        try {uRepo.createUser(user);} //attempt to create user
        catch (DuplicateKeyException e) {return "redirect:/register?nameError";} //if username already exists
        uRepo.giveRoleToUser(user.getUsername(), "GUEST"); //assign role of guest to user
        
        //Auto login after registering
        try {
            request.login(user.getUsername(), user.getEncPassword());
        } catch (ServletException e) {
            System.out.println("Error while logging in: " + e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/book")
    public String addItem(@ModelAttribute Ticket ticket, Authentication auth, @RequestParam(required=false) String username) {
        String name = auth.getName();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String authString = authority.getAuthority();
            if (authString.equalsIgnoreCase("ROLE_VENDOR")) {
                name = username;
                break;
            }
        }
        UUID userId = uRepo.getUserByName(name).getUserId();
        ticket.setUserId(userId);
        tixRepo.addItem(ticket);
        return "redirect:/view";
    }

    @GetMapping("/view")
    public String view(Model model, Authentication auth) {
        UUID userId = uRepo.getUserByName(auth.getName()).getUserId();
        ArrayList<Ticket> tickets = new ArrayList<>();
        for(GrantedAuthority authority : auth.getAuthorities()) {
            String authString = authority.getAuthority();
            if (authString.equalsIgnoreCase("ROLE_VENDOR")) {
                tickets = tixRepo.getAllItems();
                break;
            }
            else {
                tickets = tixRepo.getTicketsByUser(userId);
            }
        }
        double subtotal = 0;
        for (Ticket ticket : tickets) {
            subtotal += ticket.getTicketPrice();
        }
        double tax = subtotal * 0.13;
        double total = subtotal + tax;
        model.addAttribute("subtotal", String.format("$%.2f", subtotal));
        model.addAttribute("tax", String.format("$%.2f", tax));
        model.addAttribute("total", String.format("$%.2f", total));
        model.addAttribute("tickets", tickets);
        return "view.html";
    }

    @GetMapping("/edit/{id}")
    public String editLink(Model model, @PathVariable("id") String id) {
        Ticket ticket = tixRepo.getItemById(UUID.fromString(id));
        String username = uRepo.getUserById(ticket.getUserId()).getUsername();
        ArrayList<String> usernames = uRepo.getAllGuests();
        usernames.remove(username);
        model.addAttribute("usernames", usernames); //for all other users
        model.addAttribute("username", username); // for preselecting the username
        model.addAttribute("ticket", ticket);
        model.addAttribute("categories", Ticket.CATEGORIES);
        model.addAttribute("seats", tixRepo.getAvailableSeats(ticket)); 
        return "edit.html";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute Ticket ticket, @PathVariable("id") String id, @RequestParam String username) {
        UUID userId = uRepo.getUserByName(username).getUserId();
        ticket.setUserId(userId);
        tixRepo.updateItem(ticket);
        return "redirect:/edit/{id}";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        tixRepo.deleteItem(UUID.fromString(id));
        return "redirect:/view";
    }
    
    @GetMapping("/uranerd")
    public String stats(Model model){
        StatsHandler stats = new StatsHandler();
        stats.update(tixRepo.getAllItems());
        model.addAttribute("stats", stats);
        return "stats.html";
    }

    @GetMapping("/error/access-denied") 
    public String accessDenied() {
        return "error/access-denied.html";
    }
}
