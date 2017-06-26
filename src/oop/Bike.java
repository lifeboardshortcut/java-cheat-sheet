package oop;

public class Bike extends Machine implements Drivable, Pedalable{
	public Bike(String name) { this.name = name; }
	public void drive(){ }
	public void pedal(int time){ }
}