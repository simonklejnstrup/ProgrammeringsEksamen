package kea.eksamen.controller;


import kea.eksamen.model.database.Kommune;
import kea.eksamen.model.database.Sogn;
import kea.eksamen.service.Dataservice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@Controller
public class MyController {

    private Dataservice dataService;

    public MyController(Dataservice dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/")
    public String hentSogne(Model model) {
        model.addAttribute("sogne", dataService.getAllSogne());
        model.addAttribute("kommuner", dataService.getAllKommuner());
        return "index";
    }

    @PostMapping("/create")
    public String opretSogn(WebRequest request) {

        int sogneKode = Integer.parseInt(request.getParameter("sogneKode"));
        String sogneNavn = request.getParameter("sogneNavn");
        String kommuneNavn = request.getParameter("kommune");
        int incidens = Integer.parseInt(request.getParameter("incidens"));
        LocalDate nedlukning = LocalDate.parse(request.getParameter("nedlukning"));

        dataService.createSogn(sogneKode, kommuneNavn, sogneNavn, incidens, nedlukning);

        return "redirect:/";
    }




    @PostMapping("/delete/{navn}")
    public String deleteSogn(@PathVariable("navn") String navn, WebRequest request, Model model) {
        //String sogneNavn = request.getParameter("sogneNavn");

        dataService.deleteSogn(navn);

        model.addAttribute("sogne", dataService.getAllSogne());
        model.addAttribute("kommuner", dataService.getAllKommuner());

        return "index";
    }

    @PostMapping("/close")
    public String closeSogn(WebRequest request, Model model) {
        String sogneNavn = request.getParameter("sogneNavn");

        dataService.closeSogn(sogneNavn);

        model.addAttribute("sogne", dataService.getAllSogne());
        model.addAttribute("kommuner", dataService.getAllKommuner());


        return "index";
    }

    @GetMapping("/update/{navn}")
    public String updateSogn(@PathVariable("navn") String navn, Model model){
        //System.out.println(dataService.findByName(navn).getSogneKode());

        model.addAttribute("sogn", dataService.findByName(navn));

        return "update";
    }


    @PostMapping("/update")
    public String update(WebRequest request, Model model) {
        String sogneNavn = request.getParameter("sogneNavn");
        int incidens = Integer.parseInt(request.getParameter("incidens"));
        Kommune kommune = new Kommune(request.getParameter("kommune"));
        System.out.println(request.getParameter("sogneKode"));
        int sogneKode = Integer.parseInt(request.getParameter("sogneKode"));
        LocalDate nedlukning = LocalDate.parse(request.getParameter("nedlukning"));


        dataService.updateSogn(sogneKode,kommune, sogneNavn, incidens, nedlukning);

        model.addAttribute("sogne", dataService.getAllSogne());
        model.addAttribute("kommuner", dataService.getAllKommuner());

        return "index";
    }



}
