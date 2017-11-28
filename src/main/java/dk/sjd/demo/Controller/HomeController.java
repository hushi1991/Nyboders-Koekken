package dk.sjd.demo.Controller;

import dk.sjd.demo.Model.Entities.Employee;
import dk.sjd.demo.Model.Entities.Reservation;
import dk.sjd.demo.Model.Entities.Shift;
import dk.sjd.demo.Model.Entities.User;
import dk.sjd.demo.Model.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class HomeController {

    ArrayList<User> users = new ArrayList<>();
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Reservation> reservations = new ArrayList<>();
    ArrayList<Shift> shifts = new ArrayList<>();

    @Autowired
    IUserRepository userRepo = new UserRepository();

    @Autowired
    IEmployeeRepository employRepo = new EmployeeRepository();

    @Autowired
    IShiftRepository shiftrepo = new ShiftRepository();

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user, Model model) {
        if(userRepo.login(user.getUsername(), user.getPassword()) != null) {
            User u = userRepo.login(user.getUsername(), user.getPassword());
            if(u.isAdmin() == true) {
                employees = employRepo.readAll();
                model.addAttribute("e", employees);
                return "adminemployee";
            }
            return "hjem";
        }
        return "login";
    }

    @GetMapping("/adminshift")
    public String admin(@RequestParam("name") String name, Model model){
        shifts = shiftrepo.read(name);
        model.addAttribute("s", shifts);
        return "adminshift";
    }

    @RequestMapping(value = {"/shiftexchange"}, method = RequestMethod.GET)
    public String shiftexchange()
    {
        return "shiftexchange";
    }


    @RequestMapping(value = {"","/","index"}, method = RequestMethod.GET)
    public String index()
    {
        return "index";
    }

}
