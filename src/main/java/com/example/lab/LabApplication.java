package com.example.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping(value = "hello")
public class LabApplication {
	public static List<Car> listCar = new ArrayList<Car>();
	static {
		listCar.add(new Car("Blue", "Truck", 1000, false, 60));
		listCar.add(new Car("Blue", "Truck", 1000, false, 60));
		listCar.add(new Car("Blue", "Truck", 1000, false, 60));
	}

	public static void main(String[] args) {
		SpringApplication.run(LabApplication.class, args);
	}

	@RequestMapping(value="/{firstName}/{lastName}" , method = RequestMethod.GET)
	public String hello(@PathVariable("firstName") String firstName,
						@PathVariable("lastName") String lastName){
		return String.format("{\"message\":\"Hello %s %s\"}", firstName, lastName);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String hello2(@RequestBody Object object){
		return String.format("{\"payload\":\"%s\"}", object);
	}

	@RequestMapping(value = "/get")
	public ResponseEntity<List<Car>> get() {
		return new ResponseEntity<List<Car>>(this.listCar, HttpStatus.OK);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public ResponseEntity<Car> update(@RequestBody Car car ,@PathVariable int id){
		String color = car.getColor();
		String type	= car.getType();
		int displacement = car.getDisplacement();
		boolean hasSunroof = car.isSunroof();
		int speed = car.getSpeed();
		int count = 0;
		for (Car item: this.listCar){
			count += 1;
			if(id == count){
				item.setColor(color);
				item.setType(type);
				item.setDisplacement(displacement);
				item.setSunroof(hasSunroof);
				item.setSpeed(speed);
				break;
			}
		}
		return new ResponseEntity<Car>(car, HttpStatus.OK);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<List<Car>> delete(@PathVariable int id){
		int count = 0;
		for (Car item:this.listCar){
			count += 1;
			if(id == count){
				listCar.remove(item);
				break;
			}
		}
		return new ResponseEntity<List<Car>>(this.listCar, HttpStatus.OK);
	}

	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public ResponseEntity<Car> getById(@PathVariable int id){
		int count = 0;
		for (Car item:this.listCar){
			count += 1;
			if (id == count){
				return new ResponseEntity<Car>(item, HttpStatus.OK);
			}
		}
		return null;
	}

	@RequestMapping(value = "/create" , method = RequestMethod.POST)
	public ResponseEntity<List<Car>> create(@RequestBody Car car ) {
		String color = car.getColor();
		String type	= car.getType();
		int displacement = car.getDisplacement();
		boolean hasSunroof = car.isSunroof();
		int speed = car.getSpeed();
		this.listCar.add(new Car(color, type, displacement,hasSunroof,speed));

		return new ResponseEntity<List<Car>>(this.listCar, HttpStatus.OK);
	}

}
