public class Planet {
	//non-static variable
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass; 
	public String imgFileName;
	public double ax;
	public double ay;

	// first constructor 
	public Planet(double xP, double yP, double xV, 
				double yV, double m, String img) {
		xxPos = xP; // this.xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	// second constructor
	public Planet(Planet p) { 
		xxPos = p.xxPos; // this.xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	// nonstatic aka instance method
	public double calcDistance(Planet p) {
		double r = (xxPos - p.xxPos)*(xxPos - p.xxPos) + (yyPos - p.yyPos)*(yyPos - p.yyPos);
		return Math.sqrt(r);
	}

	static final double constant = 6.67e-11; //cannot be within a method?

	public double calcForceExertedBy(Planet p) {
		return mass*p.mass*constant/(this.calcDistance(p)*this.calcDistance(p));
	}

	public double calcForceExertedByX(Planet p) {
		return this.calcForceExertedBy(p)*(p.xxPos - this.xxPos)/this.calcDistance(p);
	}

	public double calcForceExertedByY(Planet p) {
		return this.calcForceExertedBy(p)*(p.yyPos - this.yyPos)/this.calcDistance(p);
	}

                  
	public double calcNetForceExertedByX(Planet[] planets) {
		double sumx = 0.0;
		for (int i = 0; i < planets.length; i++) {
			if (planets[i].equals(this)) {
				continue; 
			}			
			sumx += this.calcForceExertedByX(planets[i]);
		}
		return sumx;
	}

	public double calcNetForceExertedByY(Planet[] planets) {
		double sumy = 0.0;
		for (int i = 0; i < planets.length; i++) {
			if (planets[i].equals(this)) {
				continue; 
			}			
			sumy += this.calcForceExertedByY(planets[i]);
		}
		return sumy;
	}

	public void update(double dt, double fX, double fY) {
		ax = fX/mass;
		ay = fY/mass;
		xxVel = xxVel + ax*dt;
		yyVel = yyVel + ay*dt;
		xxPos = xxPos + xxVel*dt;
		yyPos = yyPos + yyVel*dt;
	}

	public void draw() {
		String imageToDraw = "images/" + imgFileName;
		// StdDraw.clear();
		StdDraw.picture(xxPos, yyPos, imageToDraw);
		// StdDraw.show();
	}

}