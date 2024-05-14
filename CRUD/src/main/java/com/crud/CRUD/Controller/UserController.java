package com.crud.CRUD.Controller;

import com.crud.CRUD.Repository.UserRepository;
import com.crud.CRUD.Service.UserDetails;
import com.crud.CRUD.Entity.Users;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository repo;

    @GetMapping({"", "/"})
    public String UserList(Model model) {
        List<Users> user = repo.findAll();
        model.addAttribute("users", user);
        return "users/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        UserDetails userDetails = new UserDetails();
        model.addAttribute("userDetails", userDetails);
        return "users/CreateUser";
    }

    @PostMapping("/create")
    public String createUser(
            @Valid @ModelAttribute UserDetails userDetails,
            BindingResult result,
            Model model
    ) {
        // Check if the user already exists in the database

        Users existingEmail = repo.findByEmail(userDetails.getEmail());
        Users existingPhoneNumaer = repo.findByPhone(userDetails.getPhone());
        if (existingEmail != null) {
            result.rejectValue("email", "error.userDetails", "User with this email already exists");
        }
        if (existingPhoneNumaer != null) {
            result.rejectValue("phone", "error.userDetails", "Phone number already exists");
        }
        if (result.hasErrors()) {
            model.addAttribute("userDetails", userDetails);
            return "users/CreateUser";
        }

        // Save userDetails to the database
        Users user = new Users();
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setPhone(userDetails.getPhone());
        repo.save(user);

        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam int id, Model model) {
        try {
            Users user = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
            model.addAttribute("user", user);
            UserDetails userDetails = new UserDetails();
            userDetails.setName(user.getName());
            userDetails.setEmail(user.getEmail());
            userDetails.setPassword(user.getPassword());
            userDetails.setPhone(user.getPhone());
            model.addAttribute("userDetails", userDetails);
        } catch (EntityNotFoundException ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/users";
        }
        return "users/EditUser";
    }

    @PostMapping("/edit")
    public String updateUser(Model model, @RequestParam int id, @Valid @ModelAttribute UserDetails userDetails,
                             BindingResult result) {
        Users existingEmailUser = repo.findByEmail(userDetails.getEmail());
        Users existingPhoneNumber = repo.findByPhone(userDetails.getPhone());
        Users currentUser = repo.findById(id).orElse(null);
        try {
            if (existingEmailUser != null && !existingEmailUser.equals(currentUser)) {
                result.rejectValue("email", "error.userDetails", "User with this email already exists");
            }
            if (existingPhoneNumber != null && !existingPhoneNumber.equals(currentUser)) {
                result.rejectValue("phone", "error.userDetails", "Phone number already exists");
            }
            Users user = repo.findById(id).get();
            model.addAttribute("user", user);
            if (result.hasErrors()) {
                return "users/EditUser";
            }
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setPhone(userDetails.getPhone());
            repo.save(user);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        // model.addAttribute("successMessage", "User updated successfully!"); // Add success message to the model
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String deleteProduct(
            @RequestParam int id
    ) {
        Users user = repo.findById(id).get();
        repo.delete(user);
        return "redirect:/users";
    }
}
