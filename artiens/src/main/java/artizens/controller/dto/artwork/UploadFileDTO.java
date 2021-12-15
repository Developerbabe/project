package artizens.controller.dto.artwork;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileDTO {
	
	/**
	 * 아이디
	 */
	private Long artworkId;
	
	/**
	 * 작품 제목
	 */
	private String title;
	
	/**
	 * 닉네임
	 */
	private String nickname;

	/**
	 * 이미지 파일
	 */
	private MultipartFile file;
	
	/**
	 * 태그 종류
	 */
	private String tagbox;
	
	/**
	 * 작품 종류
	 */
	private String subject;
	
	/**
	 * 작가의 말
	 */
	private String talk;
	/**
	 * 크리에이터 아이디
	 */
	private Long CreatorId;
	
	
	
	@Override
	public String toString() {
		return "UploadFileDTO [id=" + artworkId + ", title=" + title + ", nickname=" + nickname + ", file=" + file
				+ ", tagbox=" + tagbox + ", subject=" + subject + ", talk=" + talk + ", CreatorId=" + CreatorId + "]";
	}

	public Long getCreatorId() {
		return CreatorId;
	}

	public void setCreatorId(Long creatorId) {
		CreatorId = creatorId;
	}
	
	public Long getArtworkId() {
		return artworkId;
	}

	public void setArtworkId(Long artworkId) {
		this.artworkId = artworkId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public UploadFileDTO() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getTagbox() {
		return tagbox;
	}

	public void setTagbox(String tagbox) {
		this.tagbox = tagbox;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTalk() {
		return talk;
	}

	public void setTalk(String talk) {
		this.talk = talk;
	}
	
	
	
}
