package artizens.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import artizens.controller.dto.UploadImageDTO;
import artizens.controller.dto.artwork.InsertDto;
import artizens.domain.ArtWork;
import artizens.domain.Creator;
import artizens.domain.UploadFile;
import artizens.domain.UserProfile;
import artizens.mapper.ArtWorkMapper;
import artizens.mapper.UserMapper;
import artizens.mapper.dto.ArtWorkMainDto;
import artizens.service.ArtWorkService;
import artizens.web.aws.FileUploadService;
import artizens.web.session.SessionConst;

@Controller
@RequestMapping("/artizen")
public class ArtWorkController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArtWorkController.class);

	@Autowired
	FileUploadService fileUploadService;
	@Autowired
	UserMapper userMapper;
	@Autowired
	ArtWorkMapper artWorkMapper;
	@Autowired
	ArtWorkService artWorkService;

	@GetMapping("/artworkcrud")
	public String insertCRUD(Model model) {
		model.addAttribute("insertdto", new InsertDto());
		return "artWork/artWorkCRUD";
	}

	@PostMapping("/artworkcrud")
	public String viewCRUD(@ModelAttribute InsertDto insertDto, Model model) {
		ArtWork artWork = artWorkService.insertArtWotk(insertDto.getTitle(), insertDto.getContent());
		LOGGER.info("artWork={}", artWork.getId());
		LOGGER.info("insertDto={}", insertDto.toString());
		model.addAttribute("testModel", artWork);
		return "artWork/artWorkView";
	}

	/*
	 * @GetMapping("/artWorkMain") public String artworkMain() {
	 * List<ArtWorkMainDto> result = artWorkMapper.findArtWorkMainAll(); for
	 * (ArtWorkMainDto artWorkMainDto : result) {
	 * System.out.println("artWorkMainDto = "+ artWorkMainDto.getTitle()); } return
	 * "artWork/artWorkMain"; }
	 */

	// ?????? ?????? ?????????
	@GetMapping("/artwork/main")
	public String artworkMain(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) UserProfile user,
			Model model) {

		if (user != null) {
			
			Creator creator = user.getCreator();
			String[] email = user.getEmail().split("@");
			String nickname = "";
			
			if ( creator != null ) {
				nickname = email[0];
				model.addAttribute("nickname",nickname);
			}else {
				nickname = null;
				model.addAttribute("nickname",nickname);				
			}
			
			LoginUser loginUser = new LoginUser(user.getId(), user.getName());
			model.addAttribute("userid", loginUser.getUsername());
			
		}

		List<ArtWorkMainDto> result = artWorkService.selectAll();
		model.addAttribute("result", result);
		return "artWork/artWorkMain";
	}

	// ????????? ?????? ?????????
	@GetMapping("/artwork/inkPainting")
	public String inkpainting(Model model) {

		return "artWork/artWorkInkPainting";
	}

	// ????????? ?????? ?????????
	@GetMapping("/artwork/color")
	public String coloring(Model model) {

		return "artWork/artWorkColoring";
	}

	// ????????? ?????? ?????????
	@GetMapping("/artwork/landscape")
	public String landscape(Model model) {

		return "artWork/artWorkLandscape";
	}

	// ????????? ?????? ?????????
	@GetMapping("/artwork/figure")
	public String figure(Model model) {

		return "artWork/artWorkFigure";
	}

	// ????????? ?????? ?????????
	@GetMapping("/artwork/abstract")
	public String artworkAbsract(Model model) {

		return "artWork/artWorkAbstract";
	}

	// ????????? ?????? ?????????
	@GetMapping("/artwork/still")
	public String still(Model model) {

		return "artWork/artWorkStill";
	}

	// ????????? ?????? ?????????
	@GetMapping("/artwork/pop")
	public String popart(Model model) {

		return "artWork/artWorkPop";
	}

}
