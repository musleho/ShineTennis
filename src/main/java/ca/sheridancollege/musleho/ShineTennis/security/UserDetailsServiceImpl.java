package ca.sheridancollege.musleho.ShineTennis.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.musleho.ShineTennis.dao.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo uRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ca.sheridancollege.musleho.ShineTennis.bean.User user = uRepo.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("That user does not exist");
        }

        ArrayList<String> roles = uRepo.getRolesByUserId(user.getUserId());
        ArrayList<GrantedAuthority> authList = new ArrayList<>();

        for (String role : roles) {
            authList.add(new SimpleGrantedAuthority(role));
        }

        return (UserDetails) new User(user.getUsername(), user.getEncPassword(), authList);
    }
    
}
