package artizens.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import artizens.controller.dto.artwork.StoreFileDTO;
import artizens.controller.dto.artwork.UploadFileDTO;
import artizens.domain.UploadFile;
import artizens.domain.UserProfile;
import artizens.mapper.UserMapper;
import artizens.service.ArtService;
import artizens.web.aws.FileUploadService;
import artizens.web.session.SessionConst;

@Controller
@RequestMapping("/artizen")
public class ArtController {
		
	private static final Logger LOGGER = LoggerFactory.getLogger(ArtController.class);
	
	@Autowired FileUploadService fileUploadService;
	@Autowired UserMapper userMapper;
	@Autowired ArtService artService;
	
	@GetMapping("/blog/{nickname}")
	public String userBlog( @PathVariable String nickname, RedirectAttributes redirectAttribute,
							@SessionAttribute(name = SessionConst.LOGIN_USER, required = false ) UserProfile user,
							Model model) throws Exception {
		if ( user != null && user.getCreator().getNickName() != null ) {
			// 현재 로그인한 사람이 크리에이터라면 자신의 블로그로 이동.
			LoginUser loginUser = new LoginUser( user.getId(), user.getName() );
			// URL
			String[] email = user.getEmail().split("@");
			nickname = email[0];
			// 로그인한 유저의 크리에이터 이름
			String creatorId = user.getCreator().getNickName(); 
			model.addAttribute( "nickname", nickname ); 
			model.addAttribute( "creatorId", creatorId ); 
		}else if( user == null ) {
			model.addAttribute("member" , null);
			model.addAttribute("nickname" , null);
			return "redirect:/artizen/artwork/main";
		}
		List<StoreFileDTO> store = artService.findByImageURL();
		model.addAttribute("store",store);
		for( StoreFileDTO storeFileDTO : store) {
			LOGGER.info("storeFile={}",storeFileDTO.getStoreFileName());
		}
		return "artWork/blog";
	}
	
	
	@GetMapping("/upload")
	public String FileUpload(	@SessionAttribute(name = SessionConst.LOGIN_USER, required = false ) UserProfile user,
								Model model) {
		
		if ( user.getCreator() == null ) {
			model.addAttribute("Creator",null);
		}else { 
			model.addAttribute("Creator",user.getCreator().getNickName());
		}
		
		return "FileUpload/Upload";
	}
	
	@PostMapping("/upload")
	public String FileSave( @ModelAttribute UploadFileDTO uploadfiledto ) throws Exception {
		
		// ImageUpload 시, Creator nickname을 가짐.
		
		// 파라미터 값을 받은 DTO객체에 값을 불러드려 Service로 보낸 후 반환된 아이디값을 가짐. 
		UploadFileDTO upload = artService.insertText( uploadfiledto.getTitle(), uploadfiledto.getNickname(), uploadfiledto.getSubject() );
		
		// MultipartFile을 아마존 AWS에 저장하고, 저장된 URL을 StoreFileName과 UploadFileName으로 나누어 String 값으로 불러옴.
		UploadFile uploadFile = fileUploadService.uploadImage( uploadfiledto.getFile() );
		
		// artwork_Images 테이블에 image URL 데이터 삽입
		artService.insertImageURL(uploadFile.getStoreFileName(), uploadFile.getUploadFileName(), upload.getArtworkId());
		
		return "FileUpload/Upload";
		
	}
	
	
	
}
