package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserSearchRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserMapper;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * ユーザー情報 Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UserService {
	
	/**
	 * ユーザー情報 Repositoty
	 */
	@Autowired
	UserRepository userRepository;
	private UserMapper userMapper;
	
	public List<User> searchAll() {
		//ユーザーTBLの内容を全検索
		return userRepository.findAll();
	}
	
	public User findById(Long id) {
		System.out.println(userRepository.findById(id));
		return userRepository.findById(id).get();
	}
	
	/**
	 * ユーザー登録
	 * @param user ユーザー情報
	 */
	public void create(UserRequest userRequest) {
		Date now = new Date();
		User user = new User();
		user.setName(userRequest.getName());
		user.setAddress(userRequest.getAddress());
		user.setPhone(userRequest.getPhone());
		user.setCreateDate(now);
		user.setUpdateDate(now);
		userRepository.save(user);
	}
	
	/**
	 * ユーザー情報 更新
	 * @param user ユーザー情報
	 */
	
	public void update(UserUpdateRequest userUpdateRequest) {
		User user = findById(userUpdateRequest.getId());
		user.setName(userUpdateRequest.getName());
		user.setPhone(userUpdateRequest.getPhone());
		user.setAddress(userUpdateRequest.getAddress());
		user.setUpdateDate(new Date());
		userRepository.save(user);
	}
	
	/**
	 * ユーザー 物理削除
	 * @param id ユーザーID
	 */
	public void delete(Long id) {
		User user = findById(id);
		userRepository.delete(user);
	}
	
    /**
     * ユーザー情報検索
     * @param userSearchRequest リクエストデータ
     * @return 検索結果
     */
	public User search(UserSearchRequest userSearchRequest) {
		return userMapper.search(userSearchRequest);
	}
}
