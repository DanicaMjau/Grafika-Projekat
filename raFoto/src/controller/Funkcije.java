
public class Funkcije {
BufferedImage slika=Util.loadImage("doge.png");
	

	public void flip(BufferedImage slika){
		
		if(slika == null) { 
			System.out.println("Nema slike!"); 
		 }
		
		WritableRaster source = slika.getRaster();
		WritableRaster target = util.Util.createRaster(source.getWidth(), source.getHeight(), false);
		
		
		int rgb[] = new int[3];
		
		for(int y = 0; y < source.getHeight(); y++)
		{
			for(int x = 0; x < source.getWidth(); x++)
			{
				
				source.getPixel(x, y, rgb);
				
				target.setPixel(source.getWidth() - x - 1, y, rgb);
			}
		}
	
		
		//ImageViewer.showImageWindow(Util.rasterToImage(target), "RAF Racunarska Grafika");
	}
	
	
	public void rotacija(BufferedImage slika){

		if(slika == null) { 
			System.out.println("Nema slike!");
			}
		
		WritableRaster source =slika.getRaster();
		WritableRaster target =util.Util.createRaster(source.getHeight(), source.getWidth(), false);
		
		int rgb[] = new int[3];
		
		for(int y = 0; y < source.getHeight(); y++)
		{
			for(int x = 0; x < source.getWidth(); x++)
			{
				source.getPixel(x, y, rgb);
			
				target.setPixel(source.getWidth() - y - 1, x, rgb);
			}
		}


		//ImageViewer.showImageWindow(Util.rasterToImage(target), "RAF Racunarska Grafika");
	
	}
	
	public void sharpen(BufferedImage slika){
		if(slika == null) { 
			System.out.println("Nema slike!"); 
			}
		
		WritableRaster source = slika.getRaster();
		WritableRaster target = util.Util.createRaster(source.getWidth(), source.getHeight(), false);
		
		int rgb[] = new int[3];
		int pixel[] = new int[3];
		
	
		int matrix[][] = 
		{
			{ -1, -1, -1 },
			{ -1,  9, -1 },
			{ -1, -1, -1 }
		};
		
		for(int y = 1; y < source.getHeight() - 1; y++)
		{
			for(int x = 1; x < source.getWidth() - 1; x++)
			{
				
				pixel[0] = 0;
				pixel[1] = 0;
				pixel[2] = 0;
				
				
				for(int Y = 0; Y < 3; Y++)
				{
					for(int X = 0; X < 3; X++)
					{
	
						source.getPixel(x + X - 1, y + Y - 1, rgb);
						
						
						pixel[0] += rgb[0] * matrix[X][Y];
						pixel[1] += rgb[1] * matrix[X][Y];
						pixel[2] += rgb[2] * matrix[X][Y];
					}
				}
				
				
				pixel[0] = saturate(pixel[0]);
				pixel[1] = saturate(pixel[1]);
				pixel[2] = saturate(pixel[2]);
				
				target.setPixel(x, y, pixel);
			}
		}
	
		//ImageViewer.showImageWindow(Util.rasterToImage(target), slika, "RAF Racunarska Grafika");
	}
	
	
	public void brigthness(BufferedImage slika){
		
		if(slika == null) { 
			
			System.out.println("Nema slike!"); 
			return; }
		
		WritableRaster source = slika.getRaster();
		WritableRaster target = util.Util.createRaster(source.getWidth(), source.getHeight(), false);
		
		int rgb[] = new int[3];
		
		
		int brightness = 50;
		
		for(int y = 0; y < source.getHeight(); y++)
		{
			for(int x = 0; x < source.getWidth(); x++)
			{
				source.getPixel(x, y, rgb);
			
				
				rgb[0] += brightness;
				rgb[1] += brightness;
				rgb[2] += brightness;
				
				
				if(rgb[0] < 0) rgb[0] = 0;
				if(rgb[1] < 0) rgb[1] = 0;
				if(rgb[2] < 0) rgb[2] = 0;
				
				if(rgb[0] > 255) rgb[0] = 255;
				if(rgb[1] > 255) rgb[1] = 255;
				if(rgb[2] > 255) rgb[2] = 255;
				
			
				target.setPixel(x, y, rgb);
			}
		}
	
		
		//ImageViewer.showImageWindow(Util.rasterToImage(target), slika, "RAF Racunarska Grafika");
	}
		
	public void negativ(BufferedImage slika){
		if(slika == null) { 
			System.out.println("Nema slike!");
			return; 
			}
		
		WritableRaster source = slika.getRaster();
		WritableRaster target = util.Util.createRaster(source.getWidth(), source.getHeight(), false);
		
		int rgb[] = new int[3];
		
		for(int y = 0; y < source.getHeight(); y++)
		{
			for(int x = 0; x < source.getWidth(); x++)
			{
				source.getPixel(x, y, rgb);
			
				
				rgb[0] = 255 - rgb[0];
				rgb[1] = 255 - rgb[1];
				rgb[2] = 255 - rgb[2];
				
				target.setPixel(x, y, rgb);
			}
		}
	
		//ImageViewer.showImageWindow(Util.rasterToImage(target), "RAF Racunarska Grafika");
	
	}

}
