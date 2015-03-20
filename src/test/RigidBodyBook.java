package test;

import org.lwjgl.util.vector.Matrix2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

class RigidBodyBook {
	//linear properties
	private Vector2f m_position = new Vector2f();
	private Vector2f m_velocity = new Vector2f();
	private Vector2f m_forces = new Vector2f();
	private float m_mass;

	//angular properties
	private float m_angle;
	private float m_angularVelocity;
	private float m_torque;
	private float m_inertia;

	//cached output transforms
	private Matrix2f m_positionMatrix = new Matrix2f();
	private Matrix2f m_rotationMatrix = new Matrix2f();
	private Matrix2f m_invRotationMatrix = new Matrix2f();

	//graphical properties
	private Vector2f m_halfSize = new Vector2f();
	private Rectangle rect = new Rectangle(0, 0, 0, 0);
	private Color m_color = new Color(0);

	public RigidBodyBook() {
		//set these defaults so you don't get divide by zeros
		m_mass = 1.0f;
		m_inertia = 1.0f;
	}

	public void Setup(Vector2f halfSize, float mass, Color color) {
		//store physical parameters
		m_halfSize = halfSize;
		m_mass = mass;
		m_color = color;
		m_inertia = (1.0f / 12.0f) * (halfSize.x * halfSize.x) * (halfSize.y * halfSize.y) * mass;

		//generate our viewable rectangle
		rect.setX((int) -m_halfSize.x);
		rect.setY((int) -m_halfSize.y);
		rect.setWidth((int) m_halfSize.x * 2.0f);
		rect.setHeight((int) m_halfSize.y * 2.0f);
	}

	public void SetPosition(Vector2f position, float angle) {
		m_position = position;
		m_angle = angle;
		CacheOutput();
	}

	public Vector2f GetPosition() {
		return m_position;
	}

	public void Update(float timeStep) {
		//integrate physics
		//linear
		Vector2f acceleration = m_forces.scale(1 / m_mass);
		m_velocity.add(acceleration.scale(timeStep));
		m_position.add(m_velocity.scale(timeStep));
		m_forces = new Vector2f(0, 0); //clear forces

		//angular
		float angAcc = m_torque / m_inertia;
		m_angularVelocity += angAcc * timeStep;
		m_angle += m_angularVelocity * timeStep;
		m_torque = 0; //clear torque
		CacheOutput();
	}

	public void CacheOutput() {
		m_positionMatrix.setIdentity();
		m_positionMatrix.m00 += m_position.x;
		m_positionMatrix.m01 += m_position.x;
		m_positionMatrix.m10 += m_position.y;
		m_positionMatrix.m11 += m_position.y;

		m_rotationMatrix.setIdentity();
	}
}
