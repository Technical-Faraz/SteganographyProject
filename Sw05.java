import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class Sw05 {
	public static BufferedImage revealImage(BufferedImage img1) {
		BufferedImage imgResult = new BufferedImage(img1.getWidth(),img1.getWidth(),BufferedImage.TYPE_INT_ARGB);
		
		for(int y = 0; y < img1.getHeight(); y++) {
			for(int x = 0; x < img1.getWidth(); x++) {
				//Getting pixels of first image 
				int p1 = img1.getRGB(x,y);
				int r1 = (p1>>16) & 0xff;	//get red
				int g1 = (p1>>8) & 0xff; //get green
				int b1 = p1 & 0xff; //get blue
				
				//Steganography algorithm to reveal
				int a = 255;
				int r = (r1<<4) & 0xf0;
				int g = (g1<<4) & 0xf0;
				int b = (b1<<4) & 0xf0;
				//setting pixel for result image
				int p = (a<<24) | (r<<16) | (g<<8) | b;
			    imgResult.setRGB(x, y, p);
			}
		}
		return imgResult;
	}
		
	public static BufferedImage hideImage(BufferedImage img1, BufferedImage img2) {
		BufferedImage imgResult = new BufferedImage(img1.getWidth(),img1.getWidth(),BufferedImage.TYPE_INT_ARGB);
		
		for(int y = 0; y < img1.getHeight(); y++) {
			for(int x = 0; x < img1.getWidth(); x++) {
				//Getting pixels of first image 
				int p1 = img1.getRGB(x,y);
				int r1 = (p1>>16) & 0xff;	//get red
				int g1 = (p1>>8) & 0xff; //get green
				int b1 = p1 & 0xff; //get blue
				
				//Getting pixels of second image
				int p2 = img2.getRGB(x,y);
				int r2 = (p2>>16) & 0xff;	//get red
				int g2 = (p2>>8) & 0xff; //get green
				int b2 = p2 & 0xff; //get blue
				
				//Steganography algorithm to hide
				int a = 255;
				int r = (r1 & 0xf0) | ((r2>>4) & 0xf);
				int g = (g1 & 0xf0) | ((g2>>4) & 0xf);
				int b = (b1 & 0xf0) | ((b2>>4) & 0xf);
				//setting pixel for result image
				int p = (a<<24) | (r<<16) | (g<<8) | b;
			    imgResult.setRGB(x, y, p);
			}
		}
		return imgResult;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Selection option.
		String[] options = {"Hide an image behind another", "Reveal the hidden picture"};
		int select = JOptionPane.showOptionDialog(null, "Click to perform the task", "Steganography Java program by Faraz ahmed (19sw05)", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
/**************************************************************************************************************************************************************************************/
		if(select == 0) {
			try {
				  // Taking Foreground pic  
				JOptionPane.showMessageDialog(null , "Select Image in which you want to hide another image");
				JFileChooser choose = new JFileChooser();
				int check = choose.showOpenDialog(null);  
				File file1 = null; 
				if(check == JFileChooser.APPROVE_OPTION) { 
					file1 = choose.getSelectedFile();  
				}
	
				//Get size from main pic
				BufferedImage sizePic = ImageIO.read(file1);
				int width = sizePic.getWidth();		int height = sizePic.getHeight();
				
				BufferedImage img1 = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
				img1 = ImageIO.read(file1);
			
				
				//Taking Pic to hide
				JOptionPane.showMessageDialog(null , "Select Image that you want to hide");
				JFileChooser choose2 = new JFileChooser(file1);
				check = choose2.showOpenDialog(null);  
				File file2 = null; 
				if(check == JFileChooser.APPROVE_OPTION) { 
					file2 = choose2.getSelectedFile();  
				}
				BufferedImage img2 = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
				img2 = ImageIO.read(file2);
				
				
				//Create a duplicated pic to store the result	
				File file = new File("(Secret)-"+file1.getName());
				file.createNewFile();
				ImageIO.write(img1, "png", file);
				
				
				BufferedImage imgResult = new BufferedImage(img1.getWidth(),img1.getWidth(),BufferedImage.TYPE_INT_ARGB);
				
				imgResult = hideImage(img1,img2);
				
				File resultImg = new File(file.getName());
				ImageIO.write(imgResult, "png", resultImg);
				JOptionPane.showMessageDialog(null,"Secret image generated successfully...\nFile name: "+file.getName(), "Made By faraz ahmed (19sw05)", JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,"Error found!\nBoth images should be of same size\nAgain open this program", "Made By faraz ahmed (19sw05)", JOptionPane.ERROR_MESSAGE);
			}
		}
		/**************************************************************************************************************************************************************************************/	
		else {
			try {
				  // Taking Foreground pic  
				JOptionPane.showMessageDialog(null , "Select Image to reveal the secret pic");
				JFileChooser choose = new JFileChooser(FileSystemView.getFileSystemView());
				int check = choose.showOpenDialog(null);  
				File file1 = null; 
				if(check == JFileChooser.APPROVE_OPTION) { 
					file1 = choose.getSelectedFile();  
				}
	
				//Get size from main pic
				BufferedImage sizePic = ImageIO.read(file1);
				int width = sizePic.getWidth();		int height = sizePic.getHeight();
				
				BufferedImage img1 = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
				img1 = ImageIO.read(file1);
			

				//Create a duplicated pic to store the result	
				File file = new File("(Revealed)-"+file1.getName());
				file.createNewFile();
				ImageIO.write(img1, "png", file);
				
				
				BufferedImage imgResult = new BufferedImage(img1.getWidth(),img1.getWidth(),BufferedImage.TYPE_INT_ARGB);
				
				imgResult = revealImage(img1);
				
				File resultImg = new File(file.getName());
				ImageIO.write(imgResult, "png", resultImg);
				JOptionPane.showMessageDialog(null,"Secret Image Revealed successfully...\nFile name: "+file.getName(), "Made By faraz ahmed (19sw05)", JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,"Error found!\nAgain open this program", "Made By faraz ahmed (19sw05)", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}

}
