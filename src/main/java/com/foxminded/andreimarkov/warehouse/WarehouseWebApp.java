package com.foxminded.andreimarkov.warehouse;

import com.foxminded.andreimarkov.warehouse.dao.AbstractDAO;
import com.foxminded.andreimarkov.warehouse.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class WarehouseWebApp {

	private static AbstractDAO<Person> personDAO;

	public WarehouseWebApp(AbstractDAO<Person> personDAO) {
		this.personDAO = personDAO;
	}

	public static void main(String[] args) {
		SpringApplication.run(WarehouseWebApp.class, args);
		List<Person> persons = personDAO.findAll();
		System.out.println(persons);

	}

}
