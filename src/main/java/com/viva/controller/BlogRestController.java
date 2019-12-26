package com.viva.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viva.model.BlogModel;
import com.viva.model.UserInfo;
import com.viva.model.UserReactionHistory;
import com.viva.repository.BlogRespository;
import com.viva.repository.PostReactionRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BlogRestController {
	
	@Autowired
	private BlogRespository blogRespository;
	@Autowired
	private PostReactionRepository postReactRepository;
//	@RequestMapping(method = RequestMethod.POST, path = "/userreaction")
//	public String likePost(Model model) {
//		log.debug("CHECK");
//		return "liked";
//	}
//	
//	@RequestMapping(method = RequestMethod.GET, path = "/test")
//	public Integer test(Model model) {
//		BlogModel blog = blogRepository.save(BlogModel.builder()
//				.title("a")
//				.content("b")
//				.user(UserInfo.builder().id(39).build())
//				.build());
//		return blog.getId();
//	}
	@PostMapping("/userreaction")
	public int reaction(Model model,@RequestParam("blogId")int id,@RequestParam("chkval") int val,HttpSession session) {
		int userId = (int)session.getAttribute("userId");
		List<UserReactionHistory> userReact = postReactRepository.findAll();
		int flag = 1;
		int totalCount=0;
		BlogModel blog = blogRespository.findById(id).get();
		for(int i=0;i<userReact.size();++i) {
			UserReactionHistory reaction = userReact.get(i);
			if (reaction.getUser_id() == userId && reaction.getBlogPostReact().getId() == id) {//checking if someone already given a reaction
				if (val == 1) {
					int value=0;
					if(reaction.getDisliked_Post()==0) {
						if (reaction.getLiked_Post() == 1) {
							reaction.setLiked_Post(0);
							value = -1;
						}
						else if(reaction.getLiked_Post() == 0) {
							reaction.setLiked_Post(1);
							value = 1;
						}
					}
					int count=blog.getTotalPostLike()+value;
					blog.setTotalPostLike(count);
					blogRespository.save(blog);
					totalCount = blog.getTotalPostLike();
				} else {
					int value = 0;
					if(reaction.getLiked_Post()==0) {
						if (reaction.getDisliked_Post() == 1) {
							reaction.setDisliked_Post(0);
							value = -1;
						}
							
						else if(reaction.getDisliked_Post() == 0) {
							reaction.setDisliked_Post(1);
							value = 1;
						}
					}
					int count=blog.getTotalPostDislike()+value;
					blog.setTotalPostDislike(count);
					blogRespository.save(blog);
					totalCount = blog.getTotalPostDislike();
				}
				postReactRepository.save(reaction);
				flag=0;
			}
			
		}
		if(flag==1) {
			
			if (val == 1 ) {
				postReactRepository.save(UserReactionHistory.builder().user_id(userId)
						.blogPostReact(BlogModel.builder().id(id).build()).liked_Post(1).build());
				int count=blog.getTotalPostLike();
				blog.setTotalPostLike(++count);
				blogRespository.save(blog);
				totalCount = blog.getTotalPostLike();
			} else {
				postReactRepository.save(UserReactionHistory.builder().user_id(userId)
						.blogPostReact(BlogModel.builder().id(id).build()).disliked_Post(1).build());
				int count=blog.getTotalPostDislike();
				blog.setTotalPostDislike(++count);
				blogRespository.save(blog);
				totalCount = blog.getTotalPostDislike();
			}
		} 
		
		//model.addAttribute("blog",blog);
		return totalCount;
	}
}