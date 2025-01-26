package edu.uws.ii.project.services.user;

import edu.uws.ii.project.Repositories.UserRepository;
import edu.uws.ii.project.domain.User;
import edu.uws.ii.project.exceptions.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = userRepository.findByUsername(userDetails.getUsername());
            if (user != null) {
                return user.getId();
            }
        }
        return null;
    }

    @Override
    public String getCurrentUsername() {
        var id = getCurrentUserId();
        if (id != null) {
            var user = userRepository.findById(id).orElseThrow(
                    () -> new UserNotFound("In getting current user username")
            );
            return user.getUsername();
        }
        return "";
    }

    @Override
    public String getCurrentUserEmail() {
        var id = getCurrentUserId();
        if (id != null) {
            var user = userRepository.findById(id).orElseThrow(
                    () -> new UserNotFound("In getting current user email")
            );
            return user.getEmail();
        }
        return "";
    }

    @Override
    public User getCurrentUser() {
        var id = getCurrentUserId();
        if (id != null) {
            return userRepository.findById(id).orElseThrow(
                    () -> new UserNotFound("In getting current user")
            );
        }
        return null;
    }
}
