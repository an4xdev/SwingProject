package edu.uws.ii.project.controllers;

import edu.uws.ii.project.configurations.ProfileNames;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@Profile(ProfileNames.DEVELOPMENT)
public class TestController {

    @GetMapping("/test403")
    public void test403() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
    }

    @GetMapping("/test404")
    public void test404() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }

    @GetMapping("/test500")
    public void test500() {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }
}
