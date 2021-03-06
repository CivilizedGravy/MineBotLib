package com.CivilizedGravy.MineBotLib.util;

public class Vector3D {
	/** X coordinate of Vec3D */
	public double xCoord;

	/** Y coordinate of Vec3D */
	public double yCoord;

	/** Z coordinate of Vec3D */
	public double zCoord;

	/**
	 * Static method for creating a new Vec3D given the three x,y,z values. This
	 * is only called from the other static method which creates and places it
	 * in the list.
	 */
	public static Vector3D createVectorHelper(double x, double y, double z) {
		return new Vector3D(x, y, z);
	}

	protected Vector3D(double x, double y, double z) {
		if (x == -0.0D) {
			x = 0.0D;
		}

		if (y == -0.0D) {
			y = 0.0D;
		}

		if (z == -0.0D) {
			z = 0.0D;
		}

		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
	}

	/**
	 * Sets the x,y,z components of the vector as specified.
	 */
	protected Vector3D setComponents(double x, double y, double z) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		return this;
	}

	/**
	 * Returns a new vector with the result of the specified vector minus this.
	 */
	public Vector3D subtract(Vector3D vec) {
		return createVectorHelper(vec.xCoord - this.xCoord, vec.yCoord
				- this.yCoord, vec.zCoord - this.zCoord);
	}

	/**
	 * Normalizes the vector to a length of 1 (except if it is the zero vector)
	 */
	public Vector3D normalize() {
		double var1 = (double) MathUtil.sqrt_double(this.xCoord * this.xCoord
				+ this.yCoord * this.yCoord + this.zCoord * this.zCoord);
		return var1 < 1.0E-4D ? createVectorHelper(0.0D, 0.0D, 0.0D)
				: createVectorHelper(this.xCoord / var1, this.yCoord / var1,
						this.zCoord / var1);
	}

	public double dotProduct(Vector3D vec) {
		return this.xCoord * vec.xCoord + this.yCoord * vec.yCoord
				+ this.zCoord * vec.zCoord;
	}

	/**
	 * Returns a new vector with the result of this vector x the specified
	 * vector.
	 */
	public Vector3D crossProduct(Vector3D vec) {
		return createVectorHelper(this.yCoord * vec.zCoord - this.zCoord
				* vec.yCoord, this.zCoord * vec.xCoord - this.xCoord
				* vec.zCoord, this.xCoord * vec.yCoord - this.yCoord
				* vec.xCoord);
	}

	/**
	 * Adds the specified x,y,z vector components to this vector and returns the
	 * resulting vector. Does not change this vector.
	 */
	public Vector3D addVector(double x, double y, double z) {
		return createVectorHelper(this.xCoord + x, this.yCoord + y, this.zCoord
				+ z);
	}

	/**
	 * Euclidean distance between this and the specified vector, returned as
	 * double.
	 */
	public double distanceTo(Vector3D vec) {
		double var2 = vec.xCoord - this.xCoord;
		double var4 = vec.yCoord - this.yCoord;
		double var6 = vec.zCoord - this.zCoord;
		return (double) MathUtil.sqrt_double(var2 * var2 + var4 * var4 + var6
				* var6);
	}

	/**
	 * The square of the Euclidean distance between this and the specified
	 * vector.
	 */
	public double squareDistanceTo(Vector3D vec) {
		double var2 = vec.xCoord - this.xCoord;
		double var4 = vec.yCoord - this.yCoord;
		double var6 = vec.zCoord - this.zCoord;
		return var2 * var2 + var4 * var4 + var6 * var6;
	}

	/**
	 * The square of the Euclidean distance between this and the vector of x,y,z
	 * components passed in.
	 */
	public double squareDistanceTo(double x, double y, double z) {
		double var7 = x - this.xCoord;
		double var9 = y - this.yCoord;
		double var11 = z - this.zCoord;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}

	/**
	 * Returns the length of the vector.
	 */
	public double lengthVector() {
		return (double) MathUtil.sqrt_double(this.xCoord * this.xCoord
				+ this.yCoord * this.yCoord + this.zCoord * this.zCoord);
	}

	/**
	 * Returns a new vector with x value equal to the second parameter, along
	 * the line between this vector and the passed in vector, or null if not
	 * possible.
	 */
	public Vector3D getIntermediateWithXValue(Vector3D vec, double x) {
		double var4 = vec.xCoord - this.xCoord;
		double var6 = vec.yCoord - this.yCoord;
		double var8 = vec.zCoord - this.zCoord;

		if (var4 * var4 < 1.0000000116860974E-7D) {
			return null;
		} else {
			double var10 = (x - this.xCoord) / var4;
			return var10 >= 0.0D && var10 <= 1.0D ? createVectorHelper(
					this.xCoord + var4 * var10, this.yCoord + var6 * var10,
					this.zCoord + var8 * var10) : null;
		}
	}

	/**
	 * Returns a new vector with y value equal to the second parameter, along
	 * the line between this vector and the passed in vector, or null if not
	 * possible.
	 */
	public Vector3D getIntermediateWithYValue(Vector3D vec, double y) {
		double var4 = vec.xCoord - this.xCoord;
		double var6 = vec.yCoord - this.yCoord;
		double var8 = vec.zCoord - this.zCoord;

		if (var6 * var6 < 1.0000000116860974E-7D) {
			return null;
		} else {
			double var10 = (y - this.yCoord) / var6;
			return var10 >= 0.0D && var10 <= 1.0D ? createVectorHelper(
					this.xCoord + var4 * var10, this.yCoord + var6 * var10,
					this.zCoord + var8 * var10) : null;
		}
	}

	/**
	 * Returns a new vector with z value equal to the second parameter, along
	 * the line between this vector and the passed in vector, or null if not
	 * possible.
	 */
	public Vector3D getIntermediateWithZValue(Vector3D vec, double z) {
		double var4 = vec.xCoord - this.xCoord;
		double var6 = vec.yCoord - this.yCoord;
		double var8 = vec.zCoord - this.zCoord;

		if (var8 * var8 < 1.0000000116860974E-7D) {
			return null;
		} else {
			double var10 = (z - this.zCoord) / var8;
			return var10 >= 0.0D && var10 <= 1.0D ? createVectorHelper(
					this.xCoord + var4 * var10, this.yCoord + var6 * var10,
					this.zCoord + var8 * var10) : null;
		}
	}

	public String toString() {
		return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord
				+ ")";
	}

	/**
	 * Rotates the vector around the x axis by the specified angle.
	 */
	public void rotateAroundX(float x) {
		float var2 = MathUtil.cos(x);
		float var3 = MathUtil.sin(x);
		double var4 = this.xCoord;
		double var6 = this.yCoord * (double) var2 + this.zCoord * (double) var3;
		double var8 = this.zCoord * (double) var2 - this.yCoord * (double) var3;
		this.setComponents(var4, var6, var8);
	}

	/**
	 * Rotates the vector around the y axis by the specified angle.
	 */
	public void rotateAroundY(float y) {
		float var2 = MathUtil.cos(y);
		float var3 = MathUtil.sin(y);
		double var4 = this.xCoord * (double) var2 + this.zCoord * (double) var3;
		double var6 = this.yCoord;
		double var8 = this.zCoord * (double) var2 - this.xCoord * (double) var3;
		this.setComponents(var4, var6, var8);
	}

	/**
	 * Rotates the vector around the z axis by the specified angle.
	 */
	public void rotateAroundZ(float z) {
		float var2 = MathUtil.cos(z);
		float var3 = MathUtil.sin(z);
		double var4 = this.xCoord * (double) var2 + this.yCoord * (double) var3;
		double var6 = this.yCoord * (double) var2 - this.xCoord * (double) var3;
		double var8 = this.zCoord;
		this.setComponents(var4, var6, var8);
	}
}
