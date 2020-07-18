package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialsController {

    @Autowired
    private CredentialsService credentialsService;

    @PostMapping("/credentials")
    public String addCredentials(Authentication authentication, Credentials credential) {
        User user = (User) authentication.getPrincipal();
        if (!ObjectUtils.isEmpty(credential.getCredentialid())) {
            credentialsService.updateCredentials(credential);
        }
        else {
            credentialsService.addCredentials(credential, user.getUserid());
        }
        return "redirect:/result?success";
    }

    @GetMapping("/credentials/delete")
    public String deleteCredentials(@RequestParam("id") Integer credentialid) {
        if (credentialid > 0) {
            credentialsService.deleteCredentials(credentialid);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}
