package ir.inabama.shopping.controllers;

import ir.inabama.shopping.clothes.ClothingRequest;
import ir.inabama.shopping.services.ClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	@Autowired
	private ClothingService service;

	@PostMapping("/add")
	public void add(@RequestBody ClothingRequest request) {
		service.add(request.getItem(), request.getColors());
	}


}
