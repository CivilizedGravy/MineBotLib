package com.CivilizedGravy.MineBotLib.entity.minesha;

public abstract class Bounds {

	private double minX = 0.0;
	private double minY = 0.0;
	private double minZ = 0.0;
	private double maxX;
	private double maxY;
	private double maxZ;

	private double width;
	private double height;
	private double depth;

	public Bounds(double minX, double minY, double minZ, double width,
			double height, double depth) {
		this.height = height;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.width = width;
		this.depth = depth;
		this.maxX = minX + width;
		this.maxY = minY + height;
		this.maxZ = minZ + depth;

	}
	
	
	public void setMinX(double minX) {
		this.minX = minX;
	}


	public void setMinY(double minY) {
		this.minY = minY;
	}


	public void setMinZ(double minZ) {
		this.minZ = minZ;
	}


	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}


	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}


	public void setMaxZ(double maxZ) {
		this.maxZ = maxZ;
	}


	public void setWidth(double width) {
		this.width = width;
	}


	public void setHeight(double height) {
		this.height = height;
	}


	public void setDepth(double depth) {
		this.depth = depth;
	}


	public double getMaxX() {
		return maxX;
	}


	public double getMaxY() {
		return maxY;
	}


	public double getMaxZ() {
		return maxZ;
	}


	public double getMinX() {
		return minX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMinZ() {
		return minZ;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getDepth() {
		return depth;
	}

	public abstract boolean contains(Bounds b);

	public abstract boolean contains(double x, double y);

	public abstract boolean contains(double x, double y, double z);

	public abstract boolean contains(double x, double y, double w,
			double h);

	public abstract boolean contains(double x, double y, double z, double w,
			double h, double d);

	public abstract boolean intersects(Bounds b);
	public abstract boolean intersects(double x, double y, double w, double h);
	public abstract boolean intersects(double x, double y ,double z, double w, double h, double d);
	public abstract boolean isEmpty();

}
