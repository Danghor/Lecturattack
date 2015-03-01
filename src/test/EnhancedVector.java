package test;

import org.newdawn.slick.geom.Vector2f;

@SuppressWarnings("serial")
public class EnhancedVector extends Vector2f {

	public EnhancedVector(float x, float y) {
		super(x, y);
	}

	//cross multiplication (only returns a scalar)
	public float crossProductValue(EnhancedVector partner) {
		return (this.x * partner.y - this.y * partner.x);
	}

	//rotation
	//NEEDS TO BE IMPROVED
	public EnhancedVector getRotated(float angle, EnhancedVector center) {
		float x = this.x - center.x;
		float y = this.y - center.y;

		float x_prime = (float) (center.x + ((x * Math.cos(angle)) - (y * Math.sin(angle))));
		float y_prime = (float) (center.y + ((x * Math.sin(angle)) + (y * Math.cos(angle))));

		return new EnhancedVector(x_prime, y_prime);
	}

	@Override
	public EnhancedVector scale(float a) {
		return (EnhancedVector) super.scale(a);
	}
}
