package org.dxc.service;

import org.dxc.payload.PostDTO;
import org.dxc.payload.PostResponse;

public interface PostServiceInterface {
	PostDTO createPost(PostDTO postDto);
	
	PostResponse getAppPosts(int PageNo, int pageSize, String sortBy, String sortDir);

	PostDTO getPostById(long id);
	
	PostDTO updatePost(PostDTO postDto, long id);
	
	void deletPostById(long id);
}

