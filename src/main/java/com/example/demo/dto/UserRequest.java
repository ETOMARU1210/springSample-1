package com.example.demo.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ユーザー情報 リクエストデータ
 */
@Data
public class UserRequest implements Serializable {
	/**
	 * 名前
	 */
	@NotEmpty(message="名前を入力してください")
	@Size(max=100, message="名前は１００桁以内にしてください")
	public String name;
	
	/**
	 * 住所
	 */
	
	@Size(max=255, message="住所は255文字以内にしてください")
	public String address;
	
	/**
	 * 電話番号
	 */
	@Pattern(regexp="0\\d{1,4}-\\d{1,4}-\\d{4}", message="電話番号の形式で入力してください")
	public String phone;
}
