package org.dxc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.dxc.entity.Post;
import org.dxc.exception.ResourceNotFoundException;
import org.dxc.payload.PostDTO;
import org.dxc.payload.PostResponse;
import org.dxc.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImplmentation implements PostServiceInterface{
	
	@Autowired
	private PostRepository postRepository;
	//convert entity to DTO
	private PostDTO mapToDto(Post post) {
		PostDTO postDto = new PostDTO();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());
		
		return postDto;
	}
	
	//convert DTO to entity
	public Post mapToEntity(PostDTO postDTO) {
		Post post = new Post();
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		
		return post;
	}
	
	//implementing Create PostBlog
	@Override
	public PostDTO createPost(PostDTO postDto) {
		// convert Dto to entity
		Post post = mapToEntity(postDto);
		Post newPost = postRepository.save(post);
		
		//convert entity to DTO
		PostDTO postResponse = mapToDto(newPost);
		return postResponse;
	}

	@Override
	public PostResponse getAppPosts(int PageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
			Sort.by(sortBy).ascending()
			:Sort.by(sortBy).descending();
		Pageable pagable = PageRequest.of(PageNo, pageSize, sort);
		Page<Post> posts=postRepository.findAll(pagable);
		List<Post> listOfPosts=posts.getContent();
		
		List<PostDTO> content=listOfPosts.stream().map(
				post -> mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElement(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		return postResponse;
	}

	@Override
	public PostDTO getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(
				() ->new ResourceNotFoundException("Post", "id", id)
				);
				
		return mapToDto(post);
	}

	@Override
	public PostDTO updatePost(PostDTO postDto, long id) {
		Post post = postRepository.findById(id).orElseThrow(
				() ->new ResourceNotFoundException("Post", "id", id)
				);
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		postRepository.save(post);
		return mapToDto(post);
	}

	@Override
	public void deletPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(
				() ->new ResourceNotFoundException("Post", "id", id)
				);
		
		postRepository.delete(post);
		
	}
	
	
}
