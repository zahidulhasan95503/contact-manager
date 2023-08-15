package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entites.Contact;
import com.smart.entites.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {

		String userName = principal.getName();

		System.out.println(userName);

		// Get user by username(email)

		User user = userRepository.GetUserByUserName(userName);

		System.out.println(user);

		model.addAttribute("user", user);

	}

	@RequestMapping("/index")
	public String dashbord(Model model, Principal principal) {

		return "normal/user_dashbord";
	}

	// method for opening add contacts form

	@GetMapping("/add-contact")
	public String openAddContact(Model model) {

		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());

		return "normal/add_contact_form";
	}

	@PostMapping("/process-contact")
	public String processcontact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Principal principal, HttpSession session) {

		try {
			String name = principal.getName();
			User user = this.userRepository.GetUserByUserName(name);

			contact.setUser(user);

			user.getContacts().add(contact);

			// processing and uploading the file

			if (file.isEmpty()) {
				System.out.println("please select an image");
				contact.setImageurl("contact.png");
			}

			else {

				// upload the file to folder and update the name to contact

				contact.setImageurl(file.getOriginalFilename());

				File saveFile = new ClassPathResource("static/images").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("image is uploaded");

			}

			this.userRepository.save(user);

			// System.out.println("Contact Details"+contact);

			System.out.println("Added to database");

			// message success

			session.setAttribute("message", new Message("You Contact is Added !! Add more", "success"));

		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			// message error
			session.setAttribute("message", new Message("Something went wrong please try again", "danger"));
		}

		return "normal/add_contact_form";
	}

	// per page 5[n]
	// current page = 0 [page]
	@GetMapping("/view-contacts/{page}")
	public String viewcontacts(@PathVariable("page") Integer page, Model model, Principal principal) {

		model.addAttribute("title", "View Contacts");

		String userName = principal.getName();

		User user = this.userRepository.GetUserByUserName(userName);

		Pageable pageable = PageRequest.of(page, 5);

		Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);

		model.addAttribute("contacts", contacts);
		model.addAttribute("currentpage", page);
		model.addAttribute("totalpages", contacts.getTotalPages());

		return "normal/view_contacts";
	}
      //thiss is used to show contact details
	@RequestMapping("/{Cid}/contact")
	public String showContactDetails(@PathVariable("Cid") Integer Cid, Model model, Principal principal) {

		Optional<Contact> contactOptional = this.contactRepository.findById(Cid);

		Contact contact = contactOptional.get();

		String userName = principal.getName();

		User user = this.userRepository.GetUserByUserName(userName);

		if (user.getId() == contact.getUser().getId()) {
			model.addAttribute("contact", contact);
			model.addAttribute("title", contact.getName());
		}
		return "normal/contact_details";
	}

	@GetMapping("/delete/{Cid}")
	public String delete_contact(@PathVariable("Cid") Integer cId, Model model, Principal principal,
			HttpSession session) {

	   	Optional<Contact> contactOptional = this.contactRepository.findById(cId);
		
		Contact contact = contactOptional.get();

		String username = principal.getName();

		User user = this.userRepository.GetUserByUserName(username);

		
		
		if (user.getId() == contact.getUser().getId()) {

			user.getContacts().remove(contact);

			this.userRepository.save(user);

		}

		session.setAttribute("message", new Message("contact deleted successfully", "success"));

		return "redirect:/user/view-contacts/0";
	}

	@PostMapping("/update-contact/{Cid}")
	public String update_contact_form(@PathVariable("Cid") Integer cid, Model m) {

		m.addAttribute("title", "Update your contact");

		Contact contact = this.contactRepository.findById(cid).get();

		m.addAttribute("contact", contact);

		return "normal/update_contact_form";
	}

	@PostMapping("/process-update")
	public String process_update(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Model m, HttpSession session, Principal principal) {

		try {

			// old contact details

			Contact oldcontactdetail = this.contactRepository.findById(contact.getCid()).get();

			// image
			if (!file.isEmpty()) {

				// rewrite old photo to new

				// delete old photo

				File deletefile = new ClassPathResource("static/images").getFile();

				File file1 = new File("deletefile", oldcontactdetail.getImageurl());
				file1.delete();
				// update new photo

				File saveFile = new ClassPathResource("static/images").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				contact.setImageurl(file.getOriginalFilename());
			}

			else {
				contact.setImageurl(oldcontactdetail.getImageurl());
			}

			User user = this.userRepository.GetUserByUserName(principal.getName());

			contact.setUser(user);

			this.contactRepository.save(contact);

			session.setAttribute("message", new Message("contact updated succesfully", "success"));

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("contact name=" + contact.getName());
		System.out.println("contact id=" + contact.getCid());

		return "redirect:/user/" + contact.getCid() + "/contact";
	}

	@GetMapping("/profile")
	public String profile(Model m) {

		m.addAttribute("title", "Profile page");

		return "normal/profile";
	}

	@GetMapping("/deleteUser/{Id}")
	public String delete_user(@PathVariable("Id") Integer id, Model model, Principal principal, HttpSession session) {

	
		Optional<User> userOptional = this.userRepository.findById(id);
		
		User user = userOptional.get();
		
		String username = principal.getName();
		
		User user1 = this.userRepository.GetUserByUserName(username);
		
		user1.getContacts().removeAll(user1.getContacts());
	    this.userRepository.delete(user1);	
		
	    this.userRepository.delete(user1);
		
		session.setAttribute("message",new Message("User deleted successfully","success"));
		
		return "redirect:/signin";
	}

}
