package com.viva.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.viva.dto.BlogDto;
import com.viva.dto.UserCommentDto;
import com.viva.dto.HomePageDto;
import com.viva.dto.ShowPageDto;
import com.viva.model.BlogModel;
import com.viva.model.UserCommentHistory;
import com.viva.model.UserInfo;
import com.viva.model.UserReactionHistory;
import com.viva.repository.AuthRepository;
import com.viva.repository.BlogRespository;
import com.viva.repository.PostReactionRepository;
import com.viva.repository.UserCommentRepository;
import com.viva.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BlogController {
	@Autowired
	BlogRespository blogRespository;
	@Autowired
	UserCommentRepository commentRepository;
	@Autowired
	PostReactionRepository postReactRepository;
	@Autowired
	AuthRepository authRepository;
	@Autowired
	UserService service;
	@GetMapping("/blog")
	public String index1(Model model,Model model2,HttpSession session,HomePageDto dto) {
		List<BlogModel> blogList = blogRespository.findAllByOrderByCreatedDateDesc();
		List<HomePageDto> dtoList = new ArrayList<>();
		int userId=(int)session.getAttribute("userId");
		for(BlogModel blog:blogList) {
			dtoList.add(HomePageDto.builder()
					.title(blog.getTitle())
					.createdDate(blog.getCreatedDate())
					.id(blog.getId())
					.userName(blog.getUser().getUserName())
					.userID(blog.getUser().getId())
					.build());
		}
		model2.addAttribute("userId",userId);
		model.addAttribute("blogList", dtoList);
		return "view";
	}

	@GetMapping("/create") 
	public String createBlog(Model model) {
		BlogModel blog =new BlogModel();
		model.addAttribute("blog",blog);
		return "new_post";
	}
	//HttpServletRequest request
//	//@PostMapping("/blog/search")
	@PostMapping("/save")
	public String save(BlogDto dto, Model model, RedirectAttributes attributes, HttpSession session) {
		
		Date date = new Date();
		int userId = (int)session.getAttribute("userId");
		BlogModel blog = blogRespository.save(BlogModel.builder().title(dto.getTitle())
				.content(dto.getContent())
				.user(UserInfo.builder().id(userId).build())
				.createdDate(date)
				.build());
		int blogId = blog.getId();
		//postReactRepository.save(BlogPostUserReaction.builder().blogPostReact(BlogModel.builder().id(blogId).build()).build());
		attributes.addFlashAttribute("message", "New blog created");
		return "redirect:/blog";
	}
	@GetMapping("/blog/{id}")
	  public String show(@PathVariable String id,Model model){
	      int blogId = Integer.parseInt(id);
	      BlogModel blog = blogRespository.findById(blogId).get();
	      model.addAttribute("blog",blog);
	      return "findid";
	  }
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable(name = "id") String id,Model model,HttpSession session,Model model2) {
		int blogId = Integer.parseInt(id);
		int userId = (int)session.getAttribute("userId");
		String Check = "not writer";
		BlogModel blog = blogRespository.findById(blogId).get();
	    if((blog.getUser().getId())== userId) {
	    	Check = "writer";
	    	model.addAttribute("blog",blog);
	    	
	    }
	    model2.addAttribute("Check", Check);
		return "edit_post";

	}
	@PostMapping("/update/{blogId}")
	public String update(@PathVariable(name="blogId") int blogId,BlogDto dto, Model model, RedirectAttributes attributes, HttpSession session) {
		int userId = (int)session.getAttribute("userId");
		Date date = new Date();
		blogRespository.save(BlogModel.builder().title(dto.getTitle())
				.content(dto.getContent())
				.modifiedDate(date)
				.id(blogId).user(UserInfo.builder().id(userId).build()).build());
		attributes.addFlashAttribute("message", "Blog Updated");
		return "redirect:/blog";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name="id") int id,Model model) {
		blogRespository.deleteById(id);
		return "redirect:/blog";
	}
	@GetMapping("/show/{id}")
	public String show(@PathVariable(name="id") int id ,Model model,Model model2,Model model3,Model model4) {
		BlogModel blogModel = blogRespository.findById(id).get();
		String name = blogModel.getUser().getUserName();
		model.addAttribute("blog",blogModel);
		UserCommentHistory react = new UserCommentHistory();
		model2.addAttribute("react", react);
		List<UserCommentHistory> reaction = commentRepository.findAll();
		List<ShowPageDto> dtoList = new ArrayList<>();
		for(UserCommentHistory comment: reaction) {
			dtoList.add(ShowPageDto.builder()
					.comment(comment.getComment())
					.userName(comment.getUserReact().getUserName())
					.id(comment.getBlogReact().getId())
					.build());
		}
		model3.addAttribute("reaction", dtoList);
		model4.addAttribute("writer",name);
		return "show";
	}
	
	@GetMapping("/myblogs")
	public String myBlog(HttpSession session , Model model) {
		int userId = (int)session.getAttribute("userId");
//		List<BlogModel> blogmodel = blogRespository.getAllBlogsByUser(userId);
		List<BlogModel> blogmodel = blogRespository.findAllByUser(UserInfo.builder().id(userId).build());
		model.addAttribute("blog", blogmodel);
		return "myblogs";
	}
	@PostMapping(value = "/search")
	public String search(@RequestParam("title") String  text, Model model ) {
		List<BlogModel> blogList =  blogRespository.findAllByTitleContaining(text);
		model.addAttribute("blogList",blogList);
		return "findid";
	}
	@GetMapping("/home")
	public String home(Model model,HomePageDto dto) {

		List < BlogModel > bloglist = blogRespository.findAllByOrderByCreatedDateDesc();
		List<HomePageDto> dtoList = new ArrayList<>();
		
		for(BlogModel blogModel : bloglist) {
			dtoList.add(HomePageDto.builder().createdDate(blogModel.getCreatedDate())
									 .title(blogModel.getTitle())
									 .userName(blogModel.getUser().getUserName())
									 .id(blogModel.getId())
									 .build());
		}		
		model.addAttribute("bloglist", dtoList);
		
		return "home";
	}
	@PostMapping("/savecomment")
	public String comment(UserCommentDto dto,Model model,HttpSession session,@RequestParam("blogId") int blogId) {
		int userId = (int) session.getAttribute("userId");
		commentRepository.save(UserCommentHistory.builder().comment(dto.getComment()).userReact(UserInfo.builder().id(userId).build()).blogReact(BlogModel.builder().id(blogId).build()).build());
		return "redirect:/show/"+blogId;
	}
	@GetMapping("/about")
	public String about() {
		return "about";
	}
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
	
}