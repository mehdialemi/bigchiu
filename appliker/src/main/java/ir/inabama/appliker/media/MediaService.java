package ir.inabama.appliker.media;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.github.instagram4j.instagram4j.models.media.timeline.TimelineMedia;
import com.github.instagram4j.instagram4j.requests.feed.FeedUserRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaActionRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaCommentRequest;
import com.github.instagram4j.instagram4j.responses.IGResponse;
import com.github.instagram4j.instagram4j.responses.feed.FeedUserResponse;
import com.github.instagram4j.instagram4j.responses.media.MediaCommentResponse;
import ir.appliker.instagram.account.Account;
import ir.appliker.instagram.proxy.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {

	@Autowired
	private Client client;

	@Autowired
	private MediaRepository mediaRepository;

	public void update(Account account) throws IGLoginException {
		IGClient login = client.login(account);
		FeedUserRequest request = new FeedUserRequest(account.getPk());
		FeedUserResponse feedUserResponse = login.sendRequest(request).join();
		List <TimelineMedia> items = feedUserResponse.getItems();
		for (TimelineMedia item : items) {
			register(account, item);
		}
	}

	private void register(Account account, TimelineMedia item) {
		Media media = mediaRepository.findByMediaId(item.getId()).orElse(new Media());
		media.setMediaId(item.getId());
		media.setCommentCount(item.getComment_count());
		media.setLikeCount(item.getLike_count());
		media.setCaption(item.getCaption().getText());
		media.setMediaType(item.getMedia_type());
		media.setAccount(account);
		mediaRepository.save(media);
	}

	public Media get(String mediaId){
		try {
			return getOrThrowException(mediaId);
		} catch (MediaNotFoundException e) {
			return null;
		}
	}

	public Media getOrThrowException(String mediaId) throws MediaNotFoundException {
		return mediaRepository.findByMediaId(mediaId)
				.orElseThrow(() -> new MediaNotFoundException(mediaId));
	}

	public void like(Account liker, String id) throws IGLoginException, MediaActionException {
		IGClient login = client.login(liker);
		IGResponse response = new MediaActionRequest(id, MediaActionRequest.MediaAction.LIKE)
				.execute(login)
				.join();
		if (!response.getStatus().equals("ok")) {
			throw new MediaActionException(id, response.getStatus());
		}
	}

	public void comment(Account account, String id, String comment) throws IGLoginException, MediaActionException {
		IGClient login = client.login(account);
		MediaCommentResponse response = new MediaCommentRequest(id, comment)
				.execute(login)
				.join();
		if (!response.getStatus().equalsIgnoreCase("ok")) {
			throw new MediaActionException(id, response.getStatus());
		}

	}


}
