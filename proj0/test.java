	public class test{

		public static void main(String args[]) {
			/**
			double T = Double.parseDouble(args[0]);
			double dt = Double.parseDouble(args[1]);
			double radius = 10;
			System.out.println(T);
			// Planet[] planets = readPlanets(filename);

			String imageToDraw = "images/starfield.jpg";
			StdDraw.setScale(-radius, radius); // unary minus operator
			StdDraw.clear();
			StdDraw.picture(0, 0, imageToDraw);
			StdDraw.show();
			*/
			String imgFileName = "mars.gif";

			StdDraw.setScale(-110, 110);
			String imageToDraw = "images/" + imgFileName;
			StdDraw.clear();
			StdDraw.picture(100, -100, imageToDraw);
			StdDraw.show();
		}

	}