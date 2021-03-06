/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
class VetController {

    private final VetRepository vets;

    public VetController(VetRepository clinicService) {
        this.vets = clinicService;
    }

    @GetMapping("/vets.html")
    public String showVetList(Map<String, Object> model) {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for Object-Xml mapping
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vets.findAll());
        model.put("vets", vets);
        return "vets/vetList";
    }

    @GetMapping({ "/vets" })
    public @ResponseBody Vets showResourcesVetList() {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for JSon/Object mapping
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vets.findAll());
        return vets;
    }
    
    /**
     * Handler for showing the information of a vet.
     *
     * @param vetId ID of the vet to display
     * @return a ModelMap with the model attributes for the view
     */
    @GetMapping("/vets/{vetId}")
    public ModelAndView showVet(@PathVariable("vetId") int vetId) {
        ModelAndView mav = new ModelAndView("vets/vetDetails");
        mav.addObject(this.vets.findById(vetId));
        return mav;
    }
    
    @GetMapping("/vets/vetSpecialties")
    public String showVetSpecialties(Map<String, Object> model) {
        Collection <Specialty> specialties = this.vets.findSpecialties();
        model.put("specialties", specialties);
        return "vets/vetSpecialties";
    }
    
    
    @ModelAttribute("specialties")
    public Collection<Specialty> populateSpecialties() {
        return this.vets.findSpecialties();
    }

    @GetMapping("/vets/new")
    public String initCreationForm(Map<String, Object> model) {
        Vet vet = new Vet();
        model.put("vet", vet);        
        return "vets/createOrUpdateVetForm";
    }

    @PostMapping("/vets/new")
    public String processCreationForm(@Valid Vet vet, BindingResult result, @RequestParam(required=false, name="specialties") String specialties) {
        if (result.hasErrors()) {
            return "vets/createOrUpdateVetForm";
        } else {
        	
        	if (specialties!=null && specialties.length()>0) {
	        	List<String> items = Arrays.asList(specialties.split("\\s*,\\s*"));
	        	for (String string : items) {
	        		vet.addSpecialty(this.vets.findSpecialtyById(Integer.parseInt(string)));
				}
        	}
        	
        	
            this.vets.save(vet);
            return "redirect:/vets.html";
        }
    }

}
