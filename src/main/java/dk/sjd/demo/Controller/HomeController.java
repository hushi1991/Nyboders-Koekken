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
import java.util.List;

//Silas, Joachim & David
@Controller
public class HomeController {

    //Arraylister og Repositories oprettes udenfor metoder så hele HomeControlleren kan kalde på den og ikke kun metoder
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

    //Metoden for at finde vores index(Startside) og hvilke getters der leder derhen
    @RequestMapping(value = {"","/","index"}, method = RequestMethod.GET)
    public String index()
    {
        return "index";
    }

    //Silas, Joachim & David
    //Login metode-start. Her bliver man ledt til via GET valuen og der bliver skabt en User-entitiy til brug
    // @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    @GetMapping ("/login")
    public String loginIndex(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    //Silas, Joachim & David
    //Login metode-slut. Her bliver brugeren, baseret på login oplysninger, ført videre til admin eller alm. bruger sider.
    //@RequestMapping(value = "/login", method = RequestMethod.POST)
    @PostMapping ("/login")
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

    //Her vises adminshift siden med en specific medarbejders vagter baseret på navnet.
    @GetMapping("/adminshift")
    public String admin(@ModelAttribute("name") String name, Model model){
        shifts = shiftRepo.read(name);
        model.addAttribute("s", shifts);
        return "adminshift";
    }
    //Her vises en specific vagt baseret på vagtens id så man kan tage stilling til om den skal opdateres
    //På shiftupdate for admin kan alle informationer rettes i.
    @GetMapping ("/shiftupdate")
    public String shiftUpdate(@ModelAttribute("id") int id, Model model) {
        shifts = shiftRepo.readAll();
        model.addAttribute("s", shifts);
        model.addAttribute("shift", shiftRepo.readSpecific(id));
        return "shiftupdate";
    }

    //Metoden hvor shifts kan blive lavet af admin.
    @GetMapping("/shiftcreate")
    public String create(Model model){
        model.addAttribute("shift", new Shift());
        return "shiftcreate";
    }

    //Postmapping for skabelsen af shifts. admin bliver returneret til medarbejdersiden(for admins)
    @PostMapping("/shiftcreate")
    public String createShift(@ModelAttribute Shift s, Model model){
        shiftRepo.create(s);
        //Disse tre linjer går igen nogle steder. De sørger for at adminemployee siden kan tilgås.
        employees = employRepo.readAll();
        model.addAttribute("e", employees);
        return "adminemployee";
    }

    //Her vises en specific vagt baseret på vagtens id så man kan tage stilling til om den skal slettes
    @GetMapping("/shiftdelete")
    public String delete(@ModelAttribute("id") int id, Model model){
        Shift s = shiftRepo.readSpecific(id);
        model.addAttribute("shift", s);
        return "shiftdelete";
    }

    //Den valgte vagt bliver slettet. Returnering af admin til adminemployee
    @PostMapping("/shiftdelete")
    public String deleteshift(@ModelAttribute("id") int id, Model model){
        shiftRepo.delete(id);

        employees = employRepo.readAll();
        model.addAttribute("e", employees);
        return "adminemployee";
    }


    //Den valgte vagt bliver opdateret med de ønskede informationer
    @PostMapping("/shiftupdate")
    public String updateShift(@ModelAttribute Shift shift, Model model){
        shiftRepo.updateShift(shift);
        employees = employRepo.readAll();
        model.addAttribute("e", employees);
        return "adminemployee";
    }

    //Her vises de specifikke vagter for den ønskede medarbejder baseret på navn for den alm. bruger
    @GetMapping("/shift")
    public String shift(@ModelAttribute("name") String name, Model model){
        shifts = shiftRepo.read(name);
        model.addAttribute("s", shifts);
        return "shift";
    }

    //Her vises en specific vagt baseret på vagtens id så man kan tage stilling til om den skal byttes
    //Det er kun navnet der kan ændres for den alm. bruger
    //@RequestMapping(value = {"/shiftexchange"}, method = RequestMethod.GET)
    @GetMapping ("/shiftexchange")
    public String shiftexchange(@ModelAttribute("id") int id, Model model) {
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

    //Den metode som de alm medarbejdere bruger til at ændre tidspunkter skulle disse ændres under vagten
    //Det er kun tidspunkterne der kan ændres
    // @RequestMapping(value = {"/shifttime"}, method = RequestMethod.GET)
    @GetMapping ("/shifttime")
    public String shifttime(@ModelAttribute("id") int id, Model model) {
        shifts = shiftRepo.readAll();
        model.addAttribute("s", shifts);
        model.addAttribute("shift", shiftRepo.readSpecific(id));
        return "shifttime";
    }

    //Den valgte vagt bliver opdateret med de nye tider
    @PostMapping("/shifttime")
    public String timeShift(@ModelAttribute Shift shift){
        shiftRepo.updateShift(shift);
        return "redirect:/";
    }

    //Her bliver man ledt til siden hvor man som medarbejder & admin kan se alle de aktive reservationer
    @GetMapping("/reservationemployee")
    public String resEmployee(Model model) {
        reservations = reserRepo.readAll();
        model.addAttribute("r", reservations);
        return "reservationemployee";
    }

    //Reservations update funktionen som ansatte kan tilgå for at ændre reservationerne
    // @RequestMapping(value = {"/reservationupdate"}, method = RequestMethod.GET)
    @GetMapping ("/reservationupdate")
    public String reservationUpdate(@ModelAttribute("id") int id, Model model) {
        model.addAttribute("reservation", reserRepo.readSpecific(id));
        return "reservationupdate";
    }

    //Den valgte reservation bliver opdateret med de ønskede informationer
    @PostMapping("/reservationupdate")
    public String updateReservation(@ModelAttribute Reservation reservation, Model model){
        reserRepo.updateReservation(reservation);
        //Man bliver returneret til listen af alle aktive reservationer
        reservations = reserRepo.readAll();
        model.addAttribute("r", reservations);
        return "reservationemployee";
    }

    //Siden hvor reservationer kan skabes
    // @RequestMapping(value = {"/reservation"}, method = RequestMethod.GET)
    @GetMapping ("/reservation")
    public String reservation(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation";
    }

    //Den indtastede information til reservationen gemmes og bliver gemt i DB.
    @PostMapping("/reservation")
    public String createReservation(@ModelAttribute Reservation reservation){
        reserRepo.create(reservation);
        return "redirect:/";
    }

    //Siden hvor medarbejdere kan skabes
    // @RequestMapping(value = {"/employeecreate"}, method = RequestMethod.GET)
    @GetMapping ("/employeecreate")
    public String employee(Model model) {
        model.addAttribute("employee", new Employee());
        return "employeecreate";
    }


    //Den indtastede information til medarbejderen gemmes og bliver gemt i DB.
    @PostMapping("/employeecreate")
    public String createEmployee(@ModelAttribute Employee employee, Model model){
        employRepo.createEmployee(employee);

        employees = employRepo.readAll();
        model.addAttribute("e", employees);
        return "adminemployee";
    }

    //I denne metode kan man slette en bestemt medarbejder baseret på id
    @GetMapping("/employeedelete")
    public String deleteEmployee(@ModelAttribute("id") int id, Model model){
        Employee e = employRepo.readSpecificEmployee(id);
        model.addAttribute("employee", e);
        return "employeedelete";
    }

    //Den valgte medarbejder bliver slettet
    @PostMapping("/employeedelete")
    public String employeeDelete(Employee employee, Model model){
        employRepo.deleteEmployee(employee.getId());
        employees = employRepo.readAll();
        model.addAttribute("e", employees);
        return "adminemployee";
    }

    //En anderledes login metode. Tjekker for et matchene tlf nr i DB.
    //@RequestMapping(value = {"/phonelogin"}, method = RequestMethod.GET)
    @GetMapping ("/phonelogin")
    public String phonelogin(Model model) {
        model.addAttribute("res", new Reservation());
        return "phonelogin";
    }

    //Hvis tlf nr passer i DB vises de aktive reservationer der matcher tlf nr.
    // @RequestMapping(value = "/phonelogin", method = RequestMethod.POST)
    @PostMapping("/phonelogin")
    public String phonelogin(@ModelAttribute Reservation r, Model model) {
        if (reserRepo.login(r.getPhone()) != null) {
            Reservation ress = reserRepo.login(r.getPhone());
            reservations = reserRepo.readAllPhone(r.getPhone());
            model.addAttribute("r", reservations);
            return "reservationcollection";
        }
        //Hvis nummeret ikke passer, eller der findes ingen match, så bliver man på loginsiden
        return "phonelogin";
    }

    //Slet en reservation baseret på id. Både kunder og ansatte har denne funktion.
    @GetMapping("/reservationdelete")
    public String deleteReservation(@ModelAttribute("id") int id, Model model){
        Reservation r = reserRepo.readSpecific(id);
        model.addAttribute("reservation", r);
        return "reservationdelete";
    }

    //Reservationen bliver slettet
    @PostMapping("/reservationdelete")
    public String reservationDelete(@ModelAttribute Reservation reservation){
        reserRepo.delete(reservation.getId());
        return "/index";
    }
}