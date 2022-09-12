package org.dxc.postcontroller;

import javax.websocket.server.PathParam;

import org.dxc.payload.PostDTO;
import org.dxc.payload.PostResponse;
import org.dxc.service.PostServiceInterface;
import org.dxc.utils.AppConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/posts")
public class PostController {
	
	@Autowired
	private PostServiceInterface postService;
	
	//create blog Post
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto){
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	//Get all Post REST API
	@GetMapping
	public PostResponse getAllBlobs(
			@RequestParam(value="pageNo", defaultValue = AppConst.DEFAULT_PAGE_NUMBER, required=false)int pageNo, 
			@RequestParam(value="pageSize", defaultValue=AppConst.DEFAULT_PAGE_SIZE,required=false)int pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConst.DEFAULT_SORT_BY,required=false)String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConst.DEFAULT_SORT_DIRECTION,required=false)String sortDir
			){
				return postService.getAppPosts(pageNo, pageSize, sortBy, sortDir);	
	}
	
	//Get all Post By Id
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable(name="id") long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	//Update post by ID
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDto, @PathVariable(name="id") long id){
		PostDTO postResponse = postService.updatePost(postDto, id);
		return new ResponseEntity<PostDTO>(postResponse, HttpStatus.OK);
	}
	
	//Delete post by Id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable(name="id") long id){
		postService.deletPostById(id);
		return new ResponseEntity<String>("Post Entity Deleted Successfully", HttpStatus.OK);
	}
	
	
	
}