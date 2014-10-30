package com.CivilizedGravy.MineBotLib.util;

public class AABB {
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;

	public static AABB getBoundingBox(double minX, double minY, double minZ,
			double maxX, double maxY, double maxZ) {
		return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	protected AABB(double minX, double minY, double minZ, double maxX,
			double maxY, double maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public AABB setBounds(double minX, double minY, double minZ, double maxX,
			double maxY, double maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		return this;
	}

	public AABB addCoord(double x, double y, double z) {
		double minX = this.minX;
		double minY = this.minY;
		double minZ = this.minZ;
		double maxX = this.maxX;
		double maxY = this.maxY;
		double maxZ = this.maxZ;
		if (x < 0.0D) {
			minX += x;
		}

		if (x > 0.0D) {
			maxX += x;
		}

		if (y < 0.0D) {
			minY += y;
		}

		if (y > 0.0D) {
			maxZ += y;
		}

		if (z < 0.0D) {
			minZ += z;
		}

		if (z > 0.0D) {
			maxZ += z;
		}

		return getBoundingBox(minX, minY, minZ, maxX, maxZ, maxZ);
	}

	public AABB expand(double x, double y, double z) {
		double minX = this.minX - x;
		double minY = this.minY - y;
		double minZ = this.minZ - z;
		double maxX = this.maxX + x;
		double maxY = this.maxY + y;
		double mazZ = this.maxZ + z;
		return getBoundingBox(minX, minY, minZ, maxX, maxY, mazZ);
	}

	public AABB func_111270_a(AABB p_111270_1_) {//
		double minX = Math.min(this.minX, p_111270_1_.minX);
		double minY = Math.min(this.minY, p_111270_1_.minY);
		double minZ = Math.min(this.minZ, p_111270_1_.minZ);
		double maxX = Math.max(this.maxX, p_111270_1_.maxX);
		double maxY = Math.max(this.maxY, p_111270_1_.maxY);
		double maxZ = Math.max(this.maxZ, p_111270_1_.maxZ);
		return getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public AABB getOffsetBoundingBox(double x, double y, double z) {
		return getBoundingBox(this.minX + x, this.minY + y, this.minZ + z,
				this.maxX + x, this.maxY + y, this.maxZ + z);
	}

	public double calculateXOffset(AABB aabb, double x) {
		if (aabb.maxY > this.minY && aabb.minY < this.maxY) {
			if (aabb.maxZ > this.minZ && aabb.minZ < this.maxZ) {
				double var4;
				if (x > 0.0D && aabb.maxX <= this.minX) {
					var4 = this.minX - aabb.maxX;
					if (var4 < x) {
						x = var4;
					}
				}

				if (x < 0.0D && aabb.minX >= this.maxX) {
					var4 = this.maxX - aabb.minX;
					if (var4 > x) {
						x = var4;
					}
				}

				return x;
			} else {
				return x;
			}
		} else {
			return x;
		}
	}

	public double calculateYOffset(AABB aabb, double y) {
		if (aabb.maxX > this.minX && aabb.minX < this.maxX) {
			if (aabb.maxZ > this.minZ && aabb.minZ < this.maxZ) {
				double var4;
				if (y > 0.0D && aabb.maxY <= this.minY) {
					var4 = this.minY - aabb.maxY;
					if (var4 < y) {
						y = var4;
					}
				}

				if (y < 0.0D && aabb.minY >= this.maxY) {
					var4 = this.maxY - aabb.minY;
					if (var4 > y) {
						y = var4;
					}
				}

				return y;
			} else {
				return y;
			}
		} else {
			return y;
		}
	}

	public double calculateZOffset(AABB aabb, double z) {
		if (aabb.maxX > this.minX && aabb.minX < this.maxX) {
			if (aabb.maxY > this.minY && aabb.minY < this.maxY) {
				double var4;
				if (z > 0.0D && aabb.maxZ <= this.minZ) {
					var4 = this.minZ - aabb.maxZ;
					if (var4 < z) {
						z = var4;
					}
				}

				if (z < 0.0D && aabb.minZ >= this.maxZ) {
					var4 = this.maxZ - aabb.minZ;
					if (var4 > z) {
						z = var4;
					}
				}

				return z;
			} else {
				return z;
			}
		} else {
			return z;
		}
	}

	public boolean intersectsWith(AABB aabb) {
		return aabb.maxX > this.minX && aabb.minX < this.maxX ? (aabb.maxY > this.minY
				&& aabb.minY < this.maxY ? aabb.maxZ > this.minZ
				&& aabb.minZ < this.maxZ : false)
				: false;
	}

	public AABB offset(double x, double y, double z) {
		this.minX += x;
		this.minY += y;
		this.minZ += z;
		this.maxX += x;
		this.maxY += y;
		this.maxZ += z;
		return this;
	}

	public boolean isVecInside(Vector3D vec) {
		return vec.xCoord > this.minX && vec.xCoord < this.maxX ? (vec.yCoord > this.minY
				&& vec.yCoord < this.maxY ? vec.zCoord > this.minZ
				&& vec.zCoord < this.maxZ : false)
				: false;
	}

	public double getAverageEdgeLength() {
		double var1 = this.maxX - this.minX;
		double var3 = this.maxY - this.minY;
		double var5 = this.maxZ - this.minZ;
		return (var1 + var3 + var5) / 3.0D;
	}

	public AABB contract(double x, double y, double z) {
		double var7 = this.minX + x;
		double var9 = this.minY + y;
		double var11 = this.minZ + z;
		double var13 = this.maxX - x;
		double var15 = this.maxY - y;
		double var17 = this.maxZ - z;
		return getBoundingBox(var7, var9, var11, var13, var15, var17);
	}

	public AABB copy() {
		return getBoundingBox(this.minX, this.minY, this.minZ, this.maxX,
				this.maxY, this.maxZ);
	}

	private boolean isVecInYZ(Vector3D vec) {
		return vec == null ? false : vec.yCoord >= this.minY
				&& vec.yCoord <= this.maxY && vec.zCoord >= this.minZ
				&& vec.zCoord <= this.maxZ;
	}

	private boolean isVecInXZ(Vector3D vec) {
		return vec == null ? false : vec.xCoord >= this.minX
				&& vec.xCoord <= this.maxX && vec.zCoord >= this.minZ
				&& vec.zCoord <= this.maxZ;
	}

	private boolean isVecInXY(Vector3D vec) {
		return vec == null ? false : vec.xCoord >= this.minX
				&& vec.xCoord <= this.maxX && vec.yCoord >= this.minY
				&& vec.yCoord <= this.maxY;
	}

	public void setBB(AABB aabb) {
		this.minX = aabb.minX;
		this.minY = aabb.minY;
		this.minZ = aabb.minZ;
		this.maxX = aabb.maxX;
		this.maxY = aabb.maxY;
		this.maxZ = aabb.maxZ;
	}

	public String toString() {
		return "box[" + this.minX + ", " + this.minY + ", " + this.minZ
				+ " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ
				+ "]";
	}
}
