package com.itemsharing.itemservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.itemsharing.itemservice.model.Item;
import com.itemsharing.itemservice.model.User;

public interface ItemRepository extends CrudRepository<Item, Long>{

	Item findByName(String name);

	List<Item> findByUser(User user);

}
