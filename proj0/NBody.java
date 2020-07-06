public class NBody {
	// Retrun a double corresponding to the radius of the universe in that file
	public static double readRadius(String filename) {
		In in = new In(filename);
		int num = in.readInt();
		double radius = in.readDouble();
		/**
		double xxPos = in.readDouble();
		double yyPos = in.readDouble();
		double xxVel = in.readDouble();
		double yyVel = in.readDouble();
		double mass = in.readDouble();
		String imgFileName = in.readString();
		*/
		return radius;
	}

	public static int readNum(String filename) {
		In in = new In(filename);
		int num = in.readInt();
		/**
		double xxPos = in.readDouble();
		double yyPos = in.readDouble();
		double xxVel = in.readDouble();
		double yyVel = in.readDouble();
		double mass = in.readDouble();
		String imgFileName = in.readString();
		*/
		return num;
	}

	public static Planet[] readPlanets(String filename) {
		In in = new In(filename);
		int num = in.readInt();
		double radius = in.readDouble();
		Planet[] planets = new Planet[num];

		for (int i = 0; i < num; i++) {
			planets[i] = new Planet(in.readDouble(), in.readDouble(), 
				in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
		}

		return planets;
	}

	public static void main(String args[]) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2]; 
		int num = readNum(filename);
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);

		String imageToDraw = "images/starfield.jpg";
		StdDraw.setScale(-radius, radius); // unary minus operator
		StdDraw.clear();
		// StdDraw.picture(0, 0, imageToDraw);
		
		/**
		for (Planet planet: planets) {
			planet.draw();
		}
		StdDraw.enableDoubleBuffering();
		StdDraw.show();
		StdDraw.pause(10);
		*/

		double[] xForces = new double[num];
		double[] yForces = new double[num];
		for (int time = 0; time < T; time += dt) {
			for (int i = 0; i < num; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);				
			}
			StdDraw.clear();
			StdDraw.picture(0, 0, imageToDraw);

			for (int i = 0; i < num; i++) {
				planets[i].update(dt, xForces[i], yForces[i]);
				// System.out.println(planets[i].xxPos);
				planets[i].draw();
			}
			StdDraw.enableDoubleBuffering();
			StdDraw.show();
			StdDraw.pause(10);			
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            	planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}


	}

}