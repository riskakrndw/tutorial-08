package com.apap.tu08.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tu08.model.UserRoleModel;
import com.apap.tu08.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
    @Autowired
    private UserRoleService userService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
        if (user.getPassword().length() < 8) {
            model.addAttribute("alertmessage", "Password tidak boleh kurang dari 8 karakter");
            return "home";
        } 
        else {
            if (user.getPassword().matches(".*[a-zA-Z].*") && user.getPassword().matches(".*[0-9].*")) {
                userService.addUser(user);
                model.addAttribute("alertmessage", "User baru berhasil ditambahkan");
                return "home";
            } else {
                model.addAttribute("alertmessage", "Password harus mengandung angka dan huruf");
                return "home";
            }
        }
    }

    @RequestMapping(value="/changePassword", method=RequestMethod.GET)
	private String update(@ModelAttribute UserRoleModel user) {
		return "home";
	}

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    private String updatePasswordSubmit(String oldPassword, String newPassword, String confirmationPassword, String username, Model model) {
		UserRoleModel user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		
        if(userService.matchPassword(oldPassword, user.getPassword())) {
            if (newPassword.matches("((?=.*\\d)(?=.*[a-zA-Z]).{8,})")) {
                if(newPassword.equals(confirmationPassword)) {
                    user.setPassword(newPassword);
                    userService.addUser(user);
                    
                    model.addAttribute("alertmessage", "Password " + user.getUsername() + " berhasil diubah");
                    return "home";
                } else {
                    model.addAttribute("alertmessage", "Password baru dan konfirmasi tidak sesuai");
                }
            }else {
    			model.addAttribute("alertmessage", "Password tidak boleh kurang dari 8 karakter dan mengandung angka & huruf");
    		}
        }else {
            model.addAttribute("alertmessage", "Password lama salah");
            return "home";
        }
        return "home";
	}
}