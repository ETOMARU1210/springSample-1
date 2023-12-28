package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserSearchRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

/**
 * ユーザー情報 Controller
 */
@Controller
public class UserController {
	
	/**
	 * ユーザー情報 Serivce
	 */
	@Autowired
	UserService userService;
	
	
	/**
	 * ユーザー情報一覧画面を表示
	 * @param model Model
	 * @return ユーザー情報一覧画面のHTML
	 */
	@GetMapping(value="/user/list")
	public String displayList(Model model) {
		List<User> userList = userService.searchAll();
		model.addAttribute("userlist", userList);
		return "user/list";
	}
	
	/**
	 * ユーザー新規登録画面を表示
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@GetMapping(value="/user/add")
	public String displayAdd(Model model) {
		model.addAttribute("userRequest", new UserRequest()); 
		return "user/add";
	}
	
	/**
	 * ユーザー新規登録
	 * @param userRequestリクエストデータ
	 * @param model Model
	 * @return ゆーざー一覧画面
	 */
	
	@PostMapping(value="/user/create")
	public String create(@Validated @ModelAttribute UserRequest userRequest, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error: result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return "user/add";
		}
		//ユーザー情報の登録
		userService.create(userRequest);
		return "redirect:/user/list";
	}
	
	/**
	 * ユーザー情報詳細画面を表示
	 * @param id 表示するユーザーID
	 * @param model Model
	 * @return ユーザー情報詳細画面
	 */
	@GetMapping(value="/user/{id}")
	public String displayView(@PathVariable Long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("userData", user);
		return "user/view";
	}
	
	  /**
	   * ユーザー編集画面を表示
	   * @param id 表示するユーザーID
	   * @param model Model
	   * @return ユーザー編集画面
	   */
	@GetMapping(value="/user/{id}/edit")
	public String displayEdit(@PathVariable Long id, Model model) {
		User user = userService.findById(id);
		UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
		userUpdateRequest.setId(user.getId());
		userUpdateRequest.setName(user.getName());
		userUpdateRequest.setPhone(user.getPhone());
		userUpdateRequest.setAddress(user.getAddress());
		model.addAttribute(userUpdateRequest);
		return "user/edit";
	}
	
	  /**
	   * ユーザー更新
	   * @param userRequest リクエストデータ
	   * @param model Model
	   * @return ユーザー情報詳細画面
	   */
	@PostMapping(value="/user/update")
	public String update(@Validated UserUpdateRequest userUpdateRequest, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return "/user/edit";
		}
		
		//ユーザー情報の更新
		userService.update(userUpdateRequest);
		return String.format("redirect:/user/%d", userUpdateRequest.getId());
	}
	
	/**
	 * ユーザー情報削除
	 * @param id 表示するユーザーID
	 * @param model Model
	 * @return ユーザー情報詳細画面
	 */
	@GetMapping(value="/user/{id}/delete")
	public String delete(@PathVariable Long id, Model model) {
		//ユーザー情報の削除
		userService.delete(id);
		return "redirect:/user/list";
	}
	
	  @GetMapping(value = "/user/search")
	  public String displaySearch(Model model) {
	    model.addAttribute("userSearchRequest", new UserSearchRequest());
	    return "user/search";
	  }
	
	  /**
	   * ユーザー情報検索
	   * @param userSearchRequest リクエストデータ
	   * @param model Model
	   * @return ユーザー情報一覧画面
	   */
	@PostMapping(value="/user/id_search")
	public String search(@ModelAttribute UserSearchRequest userSearchRequest, Model model) {
		User user = userService.search(userSearchRequest);
		model.addAttribute("userinfo", user);
		return "user/search";
	}
}
