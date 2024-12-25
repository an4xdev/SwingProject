package edu.uws.ii.project.services.user;

import edu.uws.ii.project.domain.User;

public interface IUserService {
    Long getCurrentUserId();

    String getCurrentUsername();

    String getCurrentUserEmail();

    User getCurrentUser();
}
