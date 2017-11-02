package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

//@author Aleksandar Stancic

public class Util 
{
	public static WritableRaster createRaster(int width, int height, boolean alpha)
	{
		if(alpha)
		{
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
			return image.getRaster();
		}
		else
		{
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			return image.getRaster();
		}
	}
	

	public static BufferedImage rasterToImage(WritableRaster raster)
	{
		if(raster.getNumBands() == 3) // RGB
		{
			BufferedImage image = new BufferedImage(raster.getWidth(), raster.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			image.setData(raster);
			return image;
		}
		else if(raster.getNumBands() == 4) // RGBA
		{
			BufferedImage image = new BufferedImage(raster.getWidth(), raster.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			image.setData(raster);
			return image;
		}
		else
		{
			System.out.println("LOLWUT?");
			return null;
		}
	}
	
	public static float[][][] rasterToFloatMap(WritableRaster raster)
	{
		if(raster.getNumBands() == 3) // RGB
		{
			float[][][] frgb = new float[raster.getWidth()][raster.getHeight()][3];
			int[] irgb = new int[3];
			for(int y = 0; y < raster.getHeight(); ++y)
			{
				for(int x = 0; x < raster.getWidth(); ++x)
				{
					raster.getPixel(x, y, irgb);
					frgb[x][y][0] = irgb[0] / 255.0f;
					frgb[x][y][1] = irgb[1] / 255.0f;
					frgb[x][y][2] = irgb[2] / 255.0f;
				}
			}
			return frgb;
		}
		else if(raster.getNumBands() == 4) // RGBA
		{
			float[][][] frgb = new float[raster.getWidth()][raster.getHeight()][4];
			int[] irgb = new int[4];
			for(int y = 0; y < raster.getHeight(); ++y)
			{
				for(int x = 0; x < raster.getWidth(); ++x)
				{
					raster.getPixel(x, y, irgb);
					frgb[x][y][0] = irgb[0] / 255.0f;
					frgb[x][y][1] = irgb[1] / 255.0f;
					frgb[x][y][2] = irgb[2] / 255.0f;
					frgb[x][y][3] = irgb[3] / 255.0f;
				}
			}
			return frgb;
		}
		else
		{
			System.out.println("LOLWUT?");
			return null;
		}
	}

	public static boolean writeFloatMapToRaster(float fmap[][][], WritableRaster raster)
	{
		if(fmap[0][0].length != raster.getNumBands())
		{
			System.out.println("Channel number mismatch!");
			return false;
		}
		
		if(fmap.length != raster.getWidth())
		{
			System.out.println("Width mismatch!");
			return false;
		}
		
		if(fmap[0].length != raster.getHeight())
		{
			System.out.println("Height mismatch!");
			return false;
		}
		
		if(raster.getNumBands() == 3) // RGB
		{
			int[] rgb = new int[3];
			for(int y = 0; y < raster.getHeight(); ++y)
			{
				for(int x = 0; x < raster.getWidth(); ++x)
				{
					rgb[0] = (int)(fmap[x][y][0] * 255.0f);
					rgb[1] = (int)(fmap[x][y][1] * 255.0f);
					rgb[2] = (int)(fmap[x][y][2] * 255.0f);
					
					if(rgb[0] < 0) rgb[0] = 0;
					if(rgb[1] < 0) rgb[1] = 0;
					if(rgb[2] < 0) rgb[2] = 0;
					
					if(rgb[0] > 255) rgb[0] = 255;
					if(rgb[1] > 255) rgb[1] = 255;
					if(rgb[2] > 255) rgb[2] = 255;
					
					raster.setPixel(x, y, rgb);
				}
			}
		}
		else
		{
			int[] rgba = new int[4];
			for(int y = 0; y < raster.getHeight(); ++y)
			{
				for(int x = 0; x < raster.getWidth(); ++x)
				{
					rgba[0] = (int)(fmap[x][y][0] * 255.0f);
					rgba[1] = (int)(fmap[x][y][1] * 255.0f);
					rgba[2] = (int)(fmap[x][y][2] * 255.0f);
					rgba[3] = (int)(fmap[x][y][3] * 255.0f);
					
					if(rgba[0] < 0) rgba[0] = 0;
					if(rgba[1] < 0) rgba[1] = 0;
					if(rgba[2] < 0) rgba[2] = 0;
					if(rgba[3] < 0) rgba[3] = 0;
					
					if(rgba[0] > 255) rgba[0] = 255;
					if(rgba[1] > 255) rgba[1] = 255;
					if(rgba[2] > 255) rgba[2] = 255;
					if(rgba[3] > 255) rgba[3] = 255;
					
					raster.setPixel(x, y, rgba);
				}
			}
		}
		
		return true;
	}
	

	public static boolean mapFloatMapToRaster(float fmap[][], float fmin, float fmax, int[] colMin, int[] colMax, WritableRaster raster)
	{
		if(fmap.length != raster.getWidth())
		{
			System.out.println("Width mismatch!");
			return false;
		}
		
		if(fmap[0].length != raster.getHeight())
		{
			System.out.println("Height mismatch!");
			return false;
		}
		
		if(raster.getNumBands() == 3) // RGB
		{
			int[] rgb = new int[3];
			for(int y = 0; y < raster.getHeight(); ++y)
			{
				for(int x = 0; x < raster.getWidth(); ++x)
				{
					float grad = (fmap[x][y] - fmin) / (fmax - fmin);
					if(grad < 0.0f) grad = 0.0f;
					if(grad > 1.0f) grad = 1.0f;
					lerpRGBi(colMin, colMax, grad, rgb);
					
					if(rgb[0] < 0) rgb[0] = 0;
					if(rgb[1] < 0) rgb[1] = 0;
					if(rgb[2] < 0) rgb[2] = 0;
					
					if(rgb[0] > 255) rgb[0] = 255;
					if(rgb[1] > 255) rgb[1] = 255;
					if(rgb[2] > 255) rgb[2] = 255;
					
					raster.setPixel(x, y, rgb);
				}
			}
		}
		else
		{
			int[] rgba = new int[4];
			for(int y = 0; y < raster.getHeight(); ++y)
			{
				for(int x = 0; x < raster.getWidth(); ++x)
				{
					float grad = (fmap[x][y] - fmin) / (fmax - fmin);
					if(grad < 0.0f) grad = 0.0f;
					if(grad > 1.0f) grad = 1.0f;
					lerpRGBi(colMin, colMax, grad, rgba);
					
					if(rgba[0] < 0) rgba[0] = 0;
					if(rgba[1] < 0) rgba[1] = 0;
					if(rgba[2] < 0) rgba[2] = 0;
					if(rgba[3] < 0) rgba[3] = 0;
					
					if(rgba[0] > 255) rgba[0] = 255;
					if(rgba[1] > 255) rgba[1] = 255;
					if(rgba[2] > 255) rgba[2] = 255;
					if(rgba[3] > 255) rgba[3] = 255;
					
					raster.setPixel(x, y, rgba);
				}
			}
		}
		
		return true;
	}

	public static boolean mapFloatMapViaGradient(float fmap[][], float fmin, float fmax, int[][] gradient, WritableRaster raster)
	{
		if(fmap.length != raster.getWidth())
		{
			System.out.println("Width mismatch!");
			return false;
		}
		
		if(fmap[0].length != raster.getHeight())
		{
			System.out.println("Height mismatch!");
			return false;
		}
		
		if(raster.getNumBands() == 3) // RGB
		{
			int[] rgb = new int[3];
			for(int y = 0; y < raster.getHeight(); ++y)
			{
				for(int x = 0; x < raster.getWidth(); ++x)
				{
					float grad = (fmap[x][y] - fmin) / (fmax - fmin);
					gradientSample(gradient, grad, rgb);
					
					if(rgb[0] < 0) rgb[0] = 0;
					if(rgb[1] < 0) rgb[1] = 0;
					if(rgb[2] < 0) rgb[2] = 0;
					
					if(rgb[0] > 255) rgb[0] = 255;
					if(rgb[1] > 255) rgb[1] = 255;
					if(rgb[2] > 255) rgb[2] = 255;
					
					raster.setPixel(x, y, rgb);
				}
			}
			
			return true;
		}
		else
		{
			System.out.println("FAIL");
			return false;
		}
	}
	
	/**
	 * Skalira jednu 2D float matricu u drugu, bilinearno interpolirajući vrijednosti
	 * @param source izvorna matrica
	 * @param destination matrica u koju će biti upisane vrijednosti za njene dimenzije
	 */
	public static void floatMapRescale(float[][] source, float[][] destination)
	{
		int sourceW = source.length;
		int sourceH = source[0].length;
		int targetW = destination.length;
		int targetH = destination[0].length;
		
		for(int x = 0; x < targetW; x++)
		{
			float fx = (float)x / targetW;
			
			for(int y = 0; y < targetH; y++)
			{
				float fy = (float)y / targetH;
				
				float sx = fx * sourceW;
				float sy = fy * sourceH;
				
				int isx0 = (int)sx;
				int isy0 = (int)sy;
				int isx1 = isx0 + 1;
				int isy1 = isy0 + 1;
				if(isx1 >= sourceW) isx1 = sourceW - 1;
				if(isy1 >= sourceH) isy1 = sourceH - 1;
				
				sx -= isx0;
				sy -= isy0;
				
				float a = lerpF(source[isx0][isy0], source[isx1][isy0], sx);
				float b = lerpF(source[isx0][isy1], source[isx1][isy1], sx);
				
				destination[x][y] = lerpF(a, b, sy);
			}
		}
	}

	public static void floatMapRescaleCos(float[][] source, float[][] destination)
	{
		int sourceW = source.length;
		int sourceH = source[0].length;
		int targetW = destination.length;
		int targetH = destination[0].length;
		
		for(int x = 0; x < targetW; x++)
		{
			float fx = (float)x / targetW;
			
			for(int y = 0; y < targetH; y++)
			{
				float fy = (float)y / targetH;
				
				float sx = fx * sourceW;
				float sy = fy * sourceH;
				
				int isx0 = (int)sx;
				int isy0 = (int)sy;
				int isx1 = isx0 + 1;
				int isy1 = isy0 + 1;
				if(isx1 >= sourceW) isx1 = sourceW - 1;
				if(isy1 >= sourceH) isy1 = sourceH - 1;
				
				sx -= isx0;
				sy -= isy0;
				
				float a = cosInterp(source[isx0][isy0], source[isx1][isy0], sx);
				float b = cosInterp(source[isx0][isy1], source[isx1][isy1], sx);
				
				destination[x][y] = cosInterp(a, b, sy);
			}
		}
	}

	public static void floatMapMAD(float[][] source, float[][] destination, float multiplier)
	{
		if(source.length != destination.length)
		{
			System.out.println("floatMapMAD width mismatch!");
			return;
		}
		
		if(source[0].length != destination[0].length)
		{
			System.out.println("floatMapMAD height mismatch!");
			return;
		}
		
		for(int x = 0; x < source.length; ++x)
		{
			for(int y = 0; y < source.length; ++y)
			{
				destination[x][y] += source[x][y] * multiplier;
			}
		}
	}

	public static int[][] imageToGradient(BufferedImage image)
	{
		WritableRaster raster = image.getRaster();
		int[][] gradient = new int[image.getWidth()][raster.getNumBands()];
		for(int x = 0; x < image.getWidth(); x++)
			raster.getPixel(x, 0, gradient[x]);
		return gradient;
	}

	public static void gradientSample(int[][] gradient, float x, int[] result)
	{
		if(x < 0.0f) x = 0.0f;
		if(x > 1.0f) x = 1.0f;
		
		x *= (gradient.length - 1);
		int ix0 = (int)x;
		x -= ix0;
		int ix1 = ix0 + 1;
		if(ix1 >= gradient.length) ix1 = gradient.length - 1;
		
		lerpRGBi(gradient[ix0], gradient[ix1], x, result);
	}
	

	public static BufferedImage browseForImage(String startingDir, Component parentWindow)
	{
		if(startingDir == null) startingDir = ".";
		
		JFileChooser fileChooser = new JFileChooser(startingDir);
		
		fileChooser.setMultiSelectionEnabled(false);
		
		fileChooser.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() 
			{
				return "Supported image formats (BMP, PNG, GIF, JPEG)";
			}
			
			@Override
			public boolean accept(File f) 
			{
				String lowerName = f.getName().toLowerCase();
				
				if(f.isDirectory()) return true;
				return lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg") || 
						lowerName.endsWith(".png") || lowerName.endsWith(".bmp") ||
						lowerName.endsWith(".gif") || f.isDirectory();
			}
		});
		
		int dlgResult = fileChooser.showDialog(parentWindow, "Open image...");
		
		if(dlgResult == JFileChooser.APPROVE_OPTION)
			return loadImage(fileChooser.getSelectedFile().getAbsolutePath());
		else
			return null;
	}
	
	private static boolean imageLoadingFailed = false;

	public static BufferedImage loadImage(String fileName)
	{
		if(fileName.startsWith("/"))
		{
			fileName = fileName.substring(1);
			URL imgURL = Util.class.getClassLoader().getResource(fileName);
			try
			{
				BufferedImage image = ImageIO.read(imgURL.toURI().toURL());
				return image;
			} 
			catch (IOException e)
			{
				imageLoadingFailed = true;
				return null;
			} 
			catch (URISyntaxException e)
			{
				imageLoadingFailed = true;
				return null;
			}
		}
		else
		{
			try 
			{
				BufferedImage image = ImageIO.read(new File(fileName));
				return image;
			} 
			catch (IOException e) 
			{
				imageLoadingFailed = true;
				return null;
			}
		}
	}
	

	public static boolean hasImageLoadingFailed()
	{
		return imageLoadingFailed;
	}
	

	public static void resetImageLoadingFailed()
	{
		imageLoadingFailed = false;
	}
	

	public static boolean saveImage(BufferedImage image, String fileName)
	{
		String lowerName = fileName.toLowerCase();
		String format = "JPG";
		
		if(lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg"))
			format = "JPG";
		else if(lowerName.endsWith(".png"))
			format = "PNG";
		else if(lowerName.endsWith(".bmp"))
			format = "BMP";
		else
		{
			System.out.println("Invalid filename/format");
			return false;
		}
		
		try 
		{
			ImageIO.write(image, format, new File(fileName));
		} 
		catch (IOException e) 
		{
			return false;
		}
		
		return true;
	}
	

	public static int[] pickColor(int[] color, String title, Component parent)
	{
		if(color != null)
		{
			if(color.length < 3 || color.length > 4)
			{
				System.out.println("Color int[] array is NOT 3 or 4 elements long!");
				color = new int[3];
			}
		}
		
		if(color == null) color = new int[4];
		
		if(title == null) title = "Choose a color";
		
		Color rcolor = JColorChooser.showDialog(parent, title, Color.white);
		
		if(rcolor != null)
		{
			color[0] = rcolor.getRed();
			color[1] = rcolor.getGreen();
			color[2] = rcolor.getBlue();
			
			if(color.length == 4) color[3] = rcolor.getAlpha();
			
			return color;
		}
		else
		{
			return color;
		}
	}
	

	public static int lerpI(int a, int b, float x)
	{
		return (int)(a + (b - a) * x);
	}
	

	public static float lerpF(float a, float b, float x)
	{
		return a + (b - a) * x;
	}
	

	public static int cosInterp(int a, int b, float x)
	{
		x = (1.0f - x) * 3.14159265359f;
		return (int)(a + (b - a) * ((float)Math.cos(x) * 0.5f + 0.5f));
	}
	

	public static float cosInterp(float a, float b, float x)
	{
		x = (1.0f - x) * 3.14159265359f;
		return a + (b - a) * ((float)Math.cos(x) * 0.5f + 0.5f);
	}
	

	public static void lerpRGBi(int[] a, int[] b, float x, int[] result)
	{
		result[0] = (int)(a[0] + (b[0] - a[0]) * x);
		result[1] = (int)(a[1] + (b[1] - a[1]) * x);
		result[2] = (int)(a[2] + (b[2] - a[2]) * x);
	}
	

	public static void lerpRGBAi(int[] a, int[] b, float x, int[] result)
	{
		result[0] = (int)(a[0] + (b[0] - a[0]) * x);
		result[1] = (int)(a[1] + (b[1] - a[1]) * x);
		result[2] = (int)(a[2] + (b[2] - a[2]) * x);
		result[3] = (int)(a[3] + (b[3] - a[3]) * x);
	}
	

	public static void lerpRGBif(int[] a, int[] b, float x, float[] result)
	{
		result[0] = a[0] + (b[0] - a[0]) * x;
		result[1] = a[1] + (b[1] - a[1]) * x;
		result[2] = a[2] + (b[2] - a[2]) * x;
	}
	

	public static void lerpRGBAif(int[] a, int[] b, float x, float[] result)
	{
		result[0] = a[0] + (b[0] - a[0]) * x;
		result[1] = a[1] + (b[1] - a[1]) * x;
		result[2] = a[2] + (b[2] - a[2]) * x;
		result[3] = a[3] + (b[3] - a[3]) * x;
	}
	

	public static void lerpRGBf(float[] a, float[] b, float x, float[] result)
	{
		result[0] = a[0] + (b[0] - a[0]) * x;
		result[1] = a[1] + (b[1] - a[1]) * x;
		result[2] = a[2] + (b[2] - a[2]) * x;
	}
	

	public static void lerpRGBAf(float[] a, float[] b, float x, float[] result)
	{
		result[0] = a[0] + (b[0] - a[0]) * x;
		result[1] = a[1] + (b[1] - a[1]) * x;
		result[2] = a[2] + (b[2] - a[2]) * x;
		result[3] = a[3] + (b[3] - a[3]) * x;
	}

	public static int[] colorToArray(Color c)
	{
		int[] rgb = new int[3];
		rgb[0] = c.getRed();
		rgb[1] = c.getGreen();
		rgb[2] = c.getBlue();
		return rgb;
	}
	

	public static int[] colorToArrayA(Color c)
	{
		int[] rgb = new int[4];
		rgb[0] = c.getRed();
		rgb[1] = c.getGreen();
		rgb[2] = c.getBlue();
		rgb[3] = c.getAlpha();
		return rgb;
	}
	

	public static int clamp(int value)
	{
		if(value < 0) return 0;
		if(value > 255) return 255;
		return value;
	}
	

	public static int clamp(int value, int min, int max)
	{
		if(value < min) return min;
		if(value > max) return max;
		return value;
	}
	

	public static float clamp(float value)
	{
		if(value < 0.0f) return 0.0f;
		if(value > 1.0f) return 1.0f;
		return value;
	}
	

	public static float clamp(float value, float min, float max)
	{
		if(value < min) return min;
		if(value > max) return max;
		return value;
	}
	

	public static double clamp(double value)
	{
		if(value < 0.0) return 0.0;
		if(value > 1.0) return 1.0;
		return value;
	}
	
	public static double clamp(double value, double min, double max)
	{
		if(value < min) return min;
		if(value > max) return max;
		return value;
	}
	
	public static void clampRGB(int[] color)
	{
		if(color[0] < 0) color[0] = 0;
		if(color[1] < 0) color[1] = 0;
		if(color[2] < 0) color[2] = 0;
		if(color[0] > 255) color[0] = 255;
		if(color[1] > 255) color[1] = 255;
		if(color[2] > 255) color[2] = 255;
	}
	
	public static void clampRGBA(int[] color)
	{
		if(color[0] < 0) color[0] = 0;
		if(color[1] < 0) color[1] = 0;
		if(color[2] < 0) color[2] = 0;
		if(color[3] < 0) color[3] = 0;
		if(color[0] > 255) color[0] = 255;
		if(color[1] > 255) color[1] = 255;
		if(color[2] > 255) color[2] = 255;
		if(color[3] > 255) color[3] = 255;
	}
	
	public static void pointSample(WritableRaster src, float u, float v, int[] color)
	{
		int x = (int)u;
		int y = (int)v;
		if(x < 0) x = 0;
		if(y < 0) y = 0;
		if(x >= src.getWidth()) x = src.getWidth() - 1;
		if(y >= src.getHeight()) x = src.getHeight() - 1;
		src.getPixel(x, y, color);
	}

	public static void bilSample(WritableRaster src, float u, float v, int[] color)
	{
		float[] a = new float[3];
		float[] b = new float[3];
		
		int[] UL = new int[3];
		int[] UR = new int[3];
		int[] LL = new int[3];
		int[] LR = new int[3];

		int x0 = (int)u;
		int y0 = (int)v;
		int x1 = x0 + 1;
		int y1 = y0 + 1;
		
		u -= x0;
		v -= y0;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 < 0) x1 = 0;
		if(y1 < 0) y1 = 0;
		
		if(x0 >= src.getWidth()) x0 = src.getWidth() - 1;
		if(y0 >= src.getHeight()) y0 = src.getHeight() - 1;
		if(x1 >= src.getWidth()) x1 = src.getWidth() - 1;
		if(y1 >= src.getHeight()) y1 = src.getHeight() - 1;
		
		src.getPixel(x0, y0, UL);
		src.getPixel(x1, y0, UR);
		src.getPixel(x0, y1, LL);
		src.getPixel(x1, y1, LR);
		
		lerpRGBif(UL, UR, u, a);
		lerpRGBif(LL, LR, u, b);
		
		color[0] = (int)(lerpF(a[0], b[0], v));
		color[1] = (int)(lerpF(a[1], b[1], v));
		color[2] = (int)(lerpF(a[2], b[2], v));
		
		clampRGB(color);
	}
	

	public static void bilSampleA(WritableRaster src, float u, float v, int[] color)
	{
		float[] a = new float[4];
		float[] b = new float[4];
		
		int[] UL = new int[4];
		int[] UR = new int[4];
		int[] LL = new int[4];
		int[] LR = new int[4];

		int x0 = (int)u;
		int y0 = (int)v;
		int x1 = x0 + 1;
		int y1 = y0 + 1;
		
		u -= x0;
		v -= y0;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 < 0) x1 = 0;
		if(y1 < 0) y1 = 0;
		
		if(x0 >= src.getWidth()) x0 = src.getWidth() - 1;
		if(y0 >= src.getHeight()) y0 = src.getHeight() - 1;
		if(x1 >= src.getWidth()) x1 = src.getWidth() - 1;
		if(y1 >= src.getHeight()) y1 = src.getHeight() - 1;
		
		src.getPixel(x0, y0, UL);
		src.getPixel(x1, y0, UR);
		src.getPixel(x0, y1, LL);
		src.getPixel(x1, y1, LR);
		
		lerpRGBAif(UL, UR, u, a);
		lerpRGBAif(LL, LR, u, b);
		
		color[0] = (int)(lerpF(a[0], b[0], v));
		color[1] = (int)(lerpF(a[1], b[1], v));
		color[2] = (int)(lerpF(a[2], b[2], v));
		color[2] = (int)(lerpF(a[3], b[3], v));
		
		clampRGBA(color);
	}
	
	public static void addRGB(int[] base, int[] addition)
	{
		base[0] += addition[0];
		base[1] += addition[1];
		base[2] += addition[2];
	}
	
	public static void addRGBA(int[] base, int[] addition)
	{
		base[0] += addition[0];
		base[1] += addition[1];
		base[2] += addition[2];
		base[3] += addition[3];
	}
	

	public static void divideRGB(int[] base, int divider)
	{
		base[0] /= divider;
		base[1] /= divider;
		base[2] /= divider;
	}
	

	public static void divideRGBA(int[] base, int divider)
	{
		base[0] /= divider;
		base[1] /= divider;
		base[2] /= divider;
		base[3] /= divider;
	}
	

	public static void multiplyRGB(int[] base, float multiplier)
	{
		base[0] *= multiplier;
		base[1] *= multiplier;
		base[2] *= multiplier;
	}
	

	public static void multiplyRGBA(int[] base, float multiplier)
	{
		base[0] *= multiplier;
		base[1] *= multiplier;
		base[2] *= multiplier;
		base[3] *= multiplier;
	}

	public static BufferedImage[] cutTiles1D(int numX, int numY, BufferedImage sheet)
	{
		BufferedImage[] tileSet = new BufferedImage[numX * numY];
		int tileW = (sheet.getWidth() / numX);
		int tileH = (sheet.getHeight() / numY);
		for(int y = 0, i = 0; y < numY; ++y)
		{
			for(int x = 0; x < numX; ++x, ++i)
			{
				tileSet[i] = new BufferedImage(tileW, tileH, sheet.getType());
				Graphics g = tileSet[i].getGraphics();
				g.drawImage(sheet, 0, 0, tileW, tileH, x * tileW, y * tileH, (x+1) * tileW, (y+1) * tileH, null);
				g.dispose();
			}
		}
		return tileSet;
	}

	public static BufferedImage[][] cutTiles2D(int numX, int numY, BufferedImage sheet)
	{
		BufferedImage[][] tileSet = new BufferedImage[numY][numX];
		int tileW = (sheet.getWidth() / numX);
		int tileH = (sheet.getHeight() / numY);
		for(int y = 0; y < numY; ++y)
		{
			for(int x = 0; x < numX; ++x)
			{
				tileSet[y][x] = new BufferedImage(tileW, tileH, sheet.getType());
				Graphics g = tileSet[y][x].getGraphics();
				g.drawImage(sheet, 0, 0, tileW, tileH, x * tileW, y * tileH, (x+1) * tileW, (y+1) * tileH, null);
				g.dispose();
			}
		}
		return tileSet;
	}

	public static int[] RGB(int[] array, int r, int g, int b)
	{
		if(array == null) array = new int[3];
		array[0] = r;
		array[1] = g;
		array[2] = b;
		return array;
	}
	

	public static int[] RGB(int r, int g, int b)
	{
		return new int[] {r, g, b};
	}
	

	public static int[] RGBA(int r, int g, int b, int a)
	{
		return new int[] {r, g, b, a};
	}
	

	public static int[] RGBA(int[] array, int r, int g, int b)
	{
		if(array == null) array = new int[4];
		array[0] = r;
		array[1] = g;
		array[2] = b;
		array[3] = b;
		return array;
	}

	public static int[] RGB(String hex)
	{
		Color c = hex2Rgb(hex);
		return new int[] { c.getRed(), c.getGreen(), c.getBlue() };
	}
	

	public static int[] RGB(int[] array, String hex)
	{
		if(array == null) array = new int[3];
		Color c = hex2Rgb(hex);
		array[0] = c.getRed();
		array[1] = c.getBlue();
		array[2] = c.getGreen();
		return array;
	}

	public static int[] RGBA(String hex)
	{
		Color c = hex2Rgba(hex);
		return new int[] { c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha() };
	}
	
	public static int[] RGBA(int[] array, String hex)
	{
		if(array == null) array = new int[4];
		Color c = hex2Rgb(hex);
		array[0] = c.getRed();
		array[1] = c.getBlue();
		array[2] = c.getGreen();
		array[3] = c.getAlpha();
		return array;
	}
	
	private static Color hex2Rgb(String colorStr) {
		if(colorStr.startsWith("#"))
		{
		    return new Color(
		            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
		            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
		            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
		}
		else
		{
			return new Color(
		            Integer.valueOf( colorStr.substring( 0, 2 ), 16 ),
		            Integer.valueOf( colorStr.substring( 2, 4 ), 16 ),
		            Integer.valueOf( colorStr.substring( 4, 6 ), 16 ) );
		}
	}
	
	private static Color hex2Rgba(String colorStr) {
		if(colorStr.startsWith("#"))
		{
		    return new Color(
		            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
		            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
		            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ),
		            Integer.valueOf( colorStr.substring( 7, 9 ), 16 ));
		}
		else
		{
			return new Color(
		            Integer.valueOf( colorStr.substring( 0, 2 ), 16 ),
		            Integer.valueOf( colorStr.substring( 2, 4 ), 16 ),
		            Integer.valueOf( colorStr.substring( 4, 6 ), 16 ),
		            Integer.valueOf( colorStr.substring( 6, 8 ), 16 ) );
		}
	}
}

