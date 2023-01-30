package uet.kltn.judgment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.kltn.judgment.constant.State;
import uet.kltn.judgment.exception.ResourceNotFoundException;
import uet.kltn.judgment.model.User;
import uet.kltn.judgment.respository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailAndState(userName, State.ACTIVE.getId());
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username : " + userName);
        }

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(String id) {
        User user = userRepository.findByUid(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", id);
        }

        return UserPrincipal.create(user);
    }
}