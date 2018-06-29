package hello.Controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserInvitableFriend;
import org.springframework.social.facebook.api.UserTaggableFriend;
import org.springframework.social.facebook.api.WorkEntry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Facebook")
public class FacebookController {

	private Facebook facebook;
	private ConnectionRepository connectionRepository;
	User user;
	String[] userlist = new String[100];
	private PagedList<UserTaggableFriend> page;

	public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
		this.facebook = facebook;
		this.connectionRepository = connectionRepository;

	}

	@GetMapping
	public String helloFacebook(Model model) {
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return "redirect:/connect/facebook";
		}
		model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
		this.user = facebook.userOperations().getUserProfile();
		this.page = facebook.friendOperations().getTaggableFriends();
		PagedList<Post> feed = facebook.feedOperations().getFeed();
		model.addAttribute("friend", facebook.friendOperations().getFriends());
		model.addAttribute("feed", feed);
		getUserInfo();
		return "Facebook";
	}

	public void getUserInfo() {
		//System.out.println(user.getName());
		//System.out.println(user.getEmail());
		//System.out.println(user.getBirthday());
		//System.out.println(user.getLocation().getName());		
		Iterator<Reference> u = facebook.friendOperations().getFriends().iterator(); //.getFriendProfiles().iterator()
		while(u.hasNext())
		System.out.println(u.next().getName());
	};

}
