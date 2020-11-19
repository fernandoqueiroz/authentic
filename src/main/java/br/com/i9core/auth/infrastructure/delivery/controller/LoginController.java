package br.com.i9core.auth.infrastructure.delivery.controller;

import br.com.i9core.auth.infrastructure.adapter.UserServiceAdapter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log
@Controller
public class LoginController {

    @Autowired
    UserServiceAdapter userServiceAdapter;

    @GetMapping(path = "/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity me(OAuth2Authentication principal) {
        return ResponseEntity.ok(userServiceAdapter.loadUserByUsername(principal.getUserAuthentication().getName()));
    }

}
