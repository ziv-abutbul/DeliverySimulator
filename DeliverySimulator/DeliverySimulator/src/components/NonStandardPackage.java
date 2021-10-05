package components;
/**
 * @authors Ziv Abutbul - 205778939
 * 			Shlomo Shnur - 206067811
 * 
 */
public class NonStandardPackage extends Package {
	private int width, length, height;	
	
	public NonStandardPackage(Priority priority, Address senderAddress,Address destinationAdress,int width, int length, int height) {
			super( priority, senderAddress,destinationAdress);
			this.width=width;
			this.length=length;
			this.height=height;	
			System.out.println("Creating " + this);
	}
	
	
	public int getWidth() {
		return width;
	}
	
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	
	public int getLength() {
		return length;
	}
	
	
	public void setLength(int length) {
		this.length = length;
	}
	
	
	public int getHeight() {
		return height;
	}
	
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	
	@Override
	public String toString() {
		return "NonStandardPackage ["+super.toString() + ", width=" + width + ", length=" + length + ", height=" + height + "]";
	}
	
}
