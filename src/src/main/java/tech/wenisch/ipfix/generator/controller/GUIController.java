package tech.wenisch.ipfix.generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import tech.wenisch.ipfix.generator.datastructures.IPFIXGeneratorJobRequest;
import tech.wenisch.ipfix.generator.service.IPFIXGeneratorService;
import tech.wenisch.ipfix.generator.threads.IPFIXGeneratorJob;

@Controller
public class GUIController {
	@Autowired
	private IPFIXGeneratorService ipfixGeneratorService;
	
	@GetMapping("/")
	public String home(HttpServletRequest request, Model model) {

		model.addAttribute("title", "Home");
		model.addAttribute("description",
				"This IPFIX  (IP Flow Information Export) Generator is a tool designed to create and send IPFIX traffic for testing, demonstration, and analysis purposes. It simulates network flow data by generating IPFIX packets, which can be used to test network monitoring systems, analyze network performance, and ensure the accuracy of flow data collection.");
		return "index";
	}
	@GetMapping("/jobs/{id}")
	public String jobDetails(@PathVariable("id") String jobId, HttpServletRequest request, Model model)
	{ 
		IPFIXGeneratorJob job= ipfixGeneratorService.getJobById(jobId);
		model.addAttribute("job",job);
		return "jobdetails";
	}

	@GetMapping("/jobs")
	public String jobs()
	{ 
		return "jobs";
	}

	@PostMapping("/create")
	public String processInput(@RequestParam("destHost") String destHost, @RequestParam("destPort") String destPort,@RequestParam("destPPS") String destPPS,@RequestParam("destTotalPackets") String destTotalPackets, @RequestParam(name = "template", required = false) String template, Model model) {
		IPFIXGeneratorJobRequest request = new IPFIXGeneratorJobRequest(destHost, destPort, destPPS, destTotalPackets, template);
		IPFIXGeneratorJob job= ipfixGeneratorService.startRequest(request);
		return "redirect:/jobs/"+job.getId();
	}
}
