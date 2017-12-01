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

import javax.jws.WebParam;
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

    @Autowired
    IReservationRepository reserrepo = new ReservationRepository();

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String loginIndex(Model model) {
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
            employees = employRepo.readAll();
            model.addAttribute("e", employees);
            return "employee";
        }
        return "login";
    }

    @GetMapping("/reservationemployee")
    public String resEmployee(Model model) {
        reservations = reserrepo.readAll();
        model.addAttribute("r", reservations);
        return "reservationemployee";
    }

    @GetMapping("/adminshift")
    public String admin(@RequestParam("name") String name, Model model){
        shifts = shiftrepo.read(name);
        model.addAttribute("s", shifts);
        return "adminshift";
    }

    @GetMapping("/shiftdelete")
    public String delete(@RequestParam("id") int id, Model model){
        model.addAttribute("shift", shiftrepo.readSpecific(id));
        return "shiftdelete";
    }

    @PostMapping("shiftdelete")
    public String deleteshift(@ModelAttribute Shift shift){
        shiftrepo.delete(shift.getId());
        return "/login";
    }

    @RequestMapping(value = {"/shiftupdate"}, method = RequestMethod.GET)
    public String shiftUpdate(@RequestParam("id") int id, Model model) {
        model.addAttribute("shift", shiftrepo.readSpecific(id));
        return "shiftupdate";
    }

    @PostMapping("shiftupdate")
    public String updateShift(@ModelAttribute Shift shift){
        shiftrepo.updateShift(shift);
        return "redirect:/";
    }

    @GetMapping("/shift")
    public String shift(@RequestParam("name") String name, Model model){
        shifts = shiftrepo.read(name);
        model.addAttribute("s", shifts);
        return "shift";
    }

    @RequestMapping(value = {"/shiftexchange"}, method = RequestMethod.GET)
    public String shiftexchange(@RequestParam("id") int id, Model model) {
        shifts = shiftrepo.readAll();
        model.addAttribute("s", shifts);
        model.addAttribute("shift", shiftrepo.readSpecific(id));
        return "shiftexchange";
    }

    @PostMapping("shiftexchange")
    public String exchangeShift(@ModelAttribute Shift shift){
        shiftrepo.updateShift(shift);
        return "redirect:/";
    }


    @RequestMapping(value = {"","/","index"}, method = RequestMethod.GET)
    public String index()
    {
        return "index";
    }


    @RequestMapping(value = {"/reservation"}, method = RequestMethod.GET)
    public String reservation(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation";
    }

    @PostMapping("reservation")
    public String reservation(@ModelAttribute Reservation reservation){
        reserrepo.create(reservation);
        return "redirect:/";
    }
/*
    @GetMapping("/reservationdelete")
    public String delete(@RequestParam("name") String name, Model model){
        model.addAttribute("reservation", reserrepo.readAll(name));
        return "reservationdelete";
    }

    @PostMapping("reservationdelete")
    public String deletereservation(@ModelAttribute Reservation reservation){
        reserrepo.delete(reservation.getName());
        return "/index";
    }
*/
}

