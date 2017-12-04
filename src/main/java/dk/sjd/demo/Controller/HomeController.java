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

//Silas, Joachim & David
@Controller
public class HomeController {

    //ArrayLists der indeholder de forskellige entities i programmet så de kan bruges i metoderne
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Reservation> reservations = new ArrayList<>();
    ArrayList<Shift> shifts = new ArrayList<>();

    //Repositories der kan kaldes med de forskellige metoder de indeholder
    @Autowired
    IUserRepository userRepo = new UserRepository();

    @Autowired
    IEmployeeRepository employRepo = new EmployeeRepository();

    @Autowired
    IShiftRepository shiftRepo = new ShiftRepository();

    @Autowired
    IReservationRepository reserRepo = new ReservationRepository();

    //Silas, Joachim & David
    //Login metode-start. Her bliver man ledt til via GET valuen og der bliver skabt en User-entitiy til brug
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String loginIndex(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    //Silas, Joachim & David
    //Login metode-slut. Her bliver brugeren, baseret på login oplysninger, ført videre til admin eller alm. bruger sider.
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user, Model model) {
        if(userRepo.login(user.getUsername(), user.getPassword()) != null) {
            User u = userRepo.login(user.getUsername(), user.getPassword());
            //Her testes for adminrettigheder
            if(u.isAdmin() == true) {
                employees = employRepo.readAll();
                model.addAttribute("e", employees);
                return "adminemployee";
            }
            //Hvis man ikke er admin ender man på employee siden.
            employees = employRepo.readAll();
            model.addAttribute("e", employees);
            return "employee";
        }
        //Bliver ens login ikke accepteret bliver man på login siden.
        return "login";
    }

    //Her bliver man ledt til siden hvor man som medarbejder kan se alle de aktive reservationer
    @GetMapping("/reservationemployee")
    public String resEmployee(Model model) {
        reservations = reserRepo.readAll();
        model.addAttribute("r", reservations);
        return "reservationemployee";
    }

    //Her vises adminshift siden med en specific medarbejders vagter baseret på navnet.
    @GetMapping("/adminshift")
    public String admin(@RequestParam("name") String name, Model model){
        shifts = shiftRepo.read(name);
        model.addAttribute("s", shifts);
        return "adminshift";
    }

    @GetMapping("/shiftcreate")
    public String create(Model model){
        model.addAttribute("shift", new Shift());
        return "shiftcreate";
    }

    @PostMapping("/shiftcreate")
    public String createShift(@ModelAttribute Shift s){
        shiftRepo.create(s);
        return "redirect:/";
    }

    //Her vises en specific vagt baseret på vagtens id så man kan tage stilling til om den skal slettes
    @GetMapping("/shiftdelete")
    public String delete(@RequestParam("id") int id, Model model){
        Shift s = shiftRepo.readSpecific(id);
        model.addAttribute("shift", s);
        return "shiftdelete";
    }

    //Den valgte vagt bliver slettet
    @PostMapping("/shiftdelete")
    public String deleteshift(@RequestParam(name = "id") int shift, Model model){
        shiftRepo.delete(shift);
        employees = employRepo.readAll();
        model.addAttribute("e", employees);
        return "adminemployee";
    }

    //Her vises en specific vagt baseret på vagtens id så man kan tage stilling til om den skal opdateres
    //På shiftupdate for admin kan alle informationer rettes i.
    @RequestMapping(value = {"/shiftupdate"}, method = RequestMethod.GET)
    public String shiftUpdate(@RequestParam("id") int id, Model model) {
        employees = employRepo.readAll();
        model.addAttribute("e", employees);
        model.addAttribute("shift", shiftRepo.readSpecific(id));
        return "shiftupdate";
    }

    //Den valgte vagt bliver opdateret med de ønskede informationer
    @PostMapping("shiftupdate")
    public String updateShift(@ModelAttribute Shift shift){
        shiftRepo.updateShift(shift);
        return "redirect:/";
    }

    //Her vises de specifikke vagter for den ønskede medarbejder baseret på navn for den alm. bruger
    @GetMapping("/shift")
    public String shift(@RequestParam("name") String name, Model model){
        shifts = shiftRepo.read(name);
        model.addAttribute("s", shifts);
        return "shift";
    }

    //Her vises en specific vagt baseret på vagtens id så man kan tage stilling til om den skal byttes
    //Det er kun navnet der kan ændres for den alm. bruger
    @RequestMapping(value = {"/shiftexchange"}, method = RequestMethod.GET)
    public String shiftexchange(@RequestParam("id") int id, Model model) {
        //En arrayliste med alle shifts bliver uploadet for at man kan vælge det navn der skal overtage vagten
        shifts = shiftRepo.readAll();
        model.addAttribute("s", shifts);
        model.addAttribute("shift", shiftRepo.readSpecific(id));
        return "shiftexchange";
    }

    //Den valgte vagt bliver opdateret med det udvalgte navn
    @PostMapping("shiftexchange")
    public String exchangeShift(@ModelAttribute Shift shift){
        shiftRepo.updateShift(shift);
        return "redirect:/";
    }

    //Metoden for at finde vores index(Startside) og hvilke getters der leder derhen
    @RequestMapping(value = {"","/","index"}, method = RequestMethod.GET)
    public String index()
    {
        return "index";
    }

    //Siden hvor reservationer kan skabes
    @RequestMapping(value = {"/reservation"}, method = RequestMethod.GET)
    public String reservation(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation";
    }

    //Den indtastede information til reservationen gemmes og bliver gemt i DB.
    @PostMapping("reservation")
    public String createReservation(@ModelAttribute Reservation reservation){
        reserRepo.create(reservation);
        return "redirect:/";
    }
/*
    @GetMapping("/reservationdelete")
    public String deleteReservation(@RequestParam("phone") String phone, Model model){
        model.addAttribute("reservation", reserrepo.readAll(phone));
        return "reservationdelete";
    }

    @PostMapping("reservationdelete")
    public String reservationDelete(@ModelAttribute Reservation reservation){
        reserrepo.delete(reservation.getPhone());
        return "/index";
    }
*/
}

