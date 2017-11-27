package dk.sjd.demo.Controller;

import dk.sjd.demo.Model.Entities.Employee;
import dk.sjd.demo.Model.Entities.Reservation;
import dk.sjd.demo.Model.Entities.User;
import dk.sjd.demo.Model.Repositories.IUserRepository;
import dk.sjd.demo.Model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
public class HomeController {

    ArrayList<User> users = new ArrayList<>();
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Reservation> customers = new ArrayList<>();

    @Autowired
    IUserRepository userRepo = new UserRepository();

    @RequestMapping(value = {"/login","","/","index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user, Model model) {

        if(userRepo.login(user.getUsername(), user.getPassword()) != null) {
            User u = userRepo.login(user.getUsername(), user.getPassword());
            if(u.isAdmin() == true) {
                return "admin";
            }

            return "hjem";
        }

        return "login";
    }

}
