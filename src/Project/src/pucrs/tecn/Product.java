package pucrs.tecn;

public class Product {
	private int id;
	private String name, brand, weight;
	private float price, discount;
	
	public Product(int id, String name, float price, String brand, String weight, float discount){
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.weight = weight;
		this.price = price;
		this.discount = discount;
	}
	
	@Override
	public String toString(){
		return String.format("\nID: %d\nName: %s\nPrice: %.2f\nBrand: %s\nWeight: %s\nDiscount: %.2f", id, name, price, brand, weight, discount);
		
	}
}

